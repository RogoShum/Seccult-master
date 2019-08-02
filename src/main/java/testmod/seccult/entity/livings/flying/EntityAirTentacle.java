package testmod.seccult.entity.livings.flying;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.world.World;
import testmod.seccult.entity.livings.water.EntityWaterCreature;
import testmod.seccult.entity.livings.water.EntityRockShellLeviathan.DamageReduce;

public class EntityAirTentacle extends EntityFlyable{

	private EntityLivingBase target; 
	private List<DamageReduce> damageList = new ArrayList<DamageReduce>();
	
	public EntityAirTentacle(World worldIn) {
		super(worldIn);
		this.setSize(1.8F, 10);
		this.swingLimit = 10;
		this.setIsBatHanging(true);
		this.noClip = true;
		this.setNoGravity(true);
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(40.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
        if(this.getHealth() > this.getMaxHealth() / 2)
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(12.0D);
        else
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0);
		
		if(this.target != null && !this.target.isEntityAlive())
			this.target = null;
		else 
		{
			if(this.posY < 70)
				this.motionY += 0.1;
			
			if(this.posY > 240)
				this.motionY -= 0.1;
		}
		
		if(this.getIsSleeping())
			sleepingMode();
		
		if(this.rand.nextInt(200) == 0)
			this.rotationYaw += this.rand.nextInt(360) - 180;
	}
	
	@Override
	public void applyEntityCollision(Entity entityIn) {
		super.applyEntityCollision(entityIn);
		
		if(entityIn == this.target)
		{
			this.setIsSleeping(true);
			this.setIsBatHanging(true);
		}
	}
	
	@Override
	public void hangingMode() 
	{
		EntityWaterCreature.inWaterWalk(this, 0.2F, 5, 1);
		if(!this.getIsSleeping())
			searchingTarget();
		if(this.target != null && !this.getIsSleeping())
			this.setIsBatHanging(false);
	}

	public void sleepingMode()
	{
		if(this.target != null)
		{
			target.setPositionAndRotation(target.posX = this.posX, this.posY + this.getEyeHeight() - this.target.height, this.posZ, target.rotationYaw, target.rotationPitch);
			if(this.target.attackEntityFrom(DamageSource.OUT_OF_WORLD, 5F) && (this.target instanceof EntityFlyable || this.target instanceof EntityFlying))
			this.heal(2.5f);
		}
		else
			this.setIsSleeping(false);
	}
	
	@Override
	protected void doSwing() {
		float motion = (float) (Math.abs(this.motionX) + Math.abs(this.motionY) + Math.abs(this.motionZ));
		
		if(swingUp)
		{
			if(motion > 0)
				swing += motion;
		}
		else
		{
			if(motion > 0)
				swing -= motion;
		}
		
		if(swing > this.swingLimit)
			swingUp = false;
		else if(swing < -this.swingLimit)
			swingUp = true;
	}
	
	@Override
	public void movingMode() 
	{
		if(this.target != null)
		{
			this.faceEntity(this.target, 40, 30);
			
			this.Moveto(this.target.posX, this.target.posY, this.target.posZ, 0.2F);
		}
		else
			this.setIsBatHanging(true);
	}

	private void searchingTarget() {
		double height = this.posY / 3;
		AxisAlignedBB box = new AxisAlignedBB(this.posX, this.posY - height, this.posZ, this.posX, this.posY, this.posZ).grow(5, height, 5);
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, box);
			
		for(int i = 0; i < list.size(); i++)
		{
			Entity e = list.get(i);
			if(e instanceof EntityLivingBase && !(e instanceof EntityAirTentacle)  && !(e instanceof EntityVillager))
			{
				boolean creat = false;
				EntityLivingBase living = (EntityLivingBase) e;
				if(living instanceof EntityPlayer)
				{
					EntityPlayer p = (EntityPlayer) living;
					if(p.capabilities.isCreativeMode)
						creat = true;
				}
				
				if(!creat)
				{
				if(this.target == null)
					this.target = living;
				else if(this.getIfCloestTarget(this, living, this.target))
				{
					this.target = living;
				}
				}
			}
		}
	}
	
	@Override
	public float getEyeHeight() {
		return this.height * 0.4F;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		if(source.getTrueSource() instanceof EntityLivingBase)
			this.target = (EntityLivingBase) source.getTrueSource();
		
		boolean hasDamage = false;
		for(DamageReduce re : damageList)
		{
			if(re.damage.equals(source.damageType))
			{
				hasDamage = true;
				amount *= re.getReduce();
			}
		}
		
		if(!hasDamage)
		{
			DamageReduce reduce = new DamageReduce(source.damageType);
			damageList.add(reduce);
		}
		
		return super.attackEntityFrom(source, amount);
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		
		if(compound.hasKey("SC_DamageReduce"))
		{
			NBTTagList list = compound.getTagList("SC_DamageReduce", 10);
			for(int i = 0; i < list.tagCount(); ++i)
			{
				NBTTagCompound nbt = list.getCompoundTagAt(i);
				if(nbt.hasKey("DamageType"))
				{
					DamageReduce reduce = new DamageReduce(nbt.getString("DamageType"));
					reduce.setReduce(nbt.getFloat("Reduce"));
					this.damageList.add(reduce);
				}
			}
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		
		if(this.damageList != null)
		{
			NBTTagList list = new NBTTagList();
			for(DamageReduce reduce : this.damageList)
			{
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setFloat("Reduce", reduce.getTrueReduce());
				nbt.setString("DamageType", reduce.getDamage());
				list.appendTag(nbt);
			}
			compound.setTag("SC_DamageReduce", list);
		}
	}
	
	public class DamageReduce
	{
		private String damage;
		private float reduce;
		
		public DamageReduce(String damage) {
			this.damage = damage;
			this.reduce = 1.0F;
		}
		
		public float getReduce()
		{
			this.reduce *= 0.7;
			return this.reduce;
		}
		
		public float getTrueReduce()
		{
			return this.reduce;
		}
		
		public void setReduce(float re)
		{
			this.reduce = re;
		}
		
		public String getDamage()
		{
			return this.damage;
		}
	}
}
