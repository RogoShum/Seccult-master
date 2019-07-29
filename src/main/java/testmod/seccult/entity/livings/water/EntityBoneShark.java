package testmod.seccult.entity.livings.water;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import testmod.seccult.magick.active.TeleportMagick;
import testmod.seccult.network.NetworkEntityMoving;
import testmod.seccult.network.NetworkHandler;

public class EntityBoneShark extends EntityWaterCreature{
	private EntityLivingBase target; 
	private List<DamageReduce> damageList = new ArrayList<DamageReduce>();
	private int swimingTime;
	private int warningTime;
	
	public EntityBoneShark(World worldIn) {
		super(worldIn);		
		this.setSize(4F, 4);
		this.swimingTime += 35;
		this.swingLimit = 15;
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
		
		if(!this.getIsSleeping())
			searchingTarget();
		else
			sleepingMode();
		
		if(this.rand.nextInt(200) == 0)
		{
			this.rotationYaw += this.rand.nextInt(360) - 180;
			this.rotationPitch += this.rand.nextInt(90) - 45;
		}
	}
	
	@Override
	public void waterMoving() 
	{
		this.setNoGravity(true);
		
		BlockPos air1 = new BlockPos(this).up().up().up();
		BlockPos air2 = new BlockPos(air1).up();
		
		if(this.world.isAirBlock(air1) && this.world.isAirBlock(air2))
			this.motionY += -0.05;
		
		if(!this.world.isRemote && this.target == null) {
			EntityPlayer player = this.world.getNearestPlayerNotCreative(this, 5.0D);
            if (player != null)
            {
            	this.target = player;
            	warningOthers();
            }
			
            if(this.rand.nextInt(100) == 0)
            	swimingTime += this.rand.nextInt(200) + 20;
            if(swimingTime > 0)
            {
            	EntityWaterCreature.inWaterWalk(this, 0.1F, 5, 3F);
            	this.turn(2);
            	if(this.rand.nextInt(3) == 0)
            	{
            		EntityWaterCreature.inWaterWalk(this, 0.2F, 5, 3F);
            		this.turn(2);
            	}
            	swimingTime--;
            }
		}
		else if(this.target != null)
		{
			this.faceEntity(this.target, 40, 30);
			this.Moveto(this.target.posX, this.target.posY, this.target.posZ, 0.2F);
		}
		
		if(warningTime > 0)
			warningTime--;
	}
	
	public void sleepingMode()
	{
		this.setSize(2F, 4);
		if(this.target != null)
		{
			this.faceEntity(this.target, 40, 30);
			
			//setPlayerTP(target, this.posX + this.LookX(), this.posY + this.LookY() - this.target.height, this.posZ + this.LookZ(),0);
			target.setPositionAndRotation(this.posX + this.LookX(), this.posY + this.LookY() - this.target.height, this.posZ + this.LookZ(), target.rotationYaw, target.rotationPitch);
			target.posX = this.posX + this.LookX();
			target.posY = this.posY + this.LookY() - this.target.height;
			target.posZ = this.posZ + this.LookZ();
			
			boolean attack = this.target.attackEntityFrom(DamageSource.OUT_OF_WORLD, 5f);
			if(attack && (this.target instanceof EntityWaterCreature || this.target instanceof EntityWaterMob))
				this.heal(2.5f);
		}
		else
			this.setIsSleeping(false);
	}
	
	public static void setPlayerTP(Entity e, double movex, double movey, double movez, int type)
	{
		double[] move = {0, 0, 0};
		double[] pos = {movex, movey, movez};
		NetworkHandler.getNetwork().sendToAll(new NetworkEntityMoving(e.getUniqueID(), pos, move, type));
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
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
		
		boolean attack = super.attackEntityFrom(source, amount);
		if(attack)
		{
			this.warningTime += 1;
			warningOthers();
		}
		
		if(source.damageType.equals(DamageSource.IN_WALL.damageType))
		{
    		amount = 0;
		}
		
		if(source.getTrueSource() instanceof EntityLivingBase)
			this.target = (EntityLivingBase) source.getTrueSource();

		return attack;
	}
	
	@Override
	public void applyEntityCollision(Entity entityIn) {
		super.applyEntityCollision(entityIn);
		
		if(entityIn == this.target)
		{
			this.setIsSleeping(true);
		}
	}
	
	private void warningOthers()
	{
		List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(20));
		
		for(Entity e : entities)
		{
			if(e instanceof EntityBoneShark)
			{
				EntityBoneShark fish = (EntityBoneShark) e;
				if(this.target != null && fish.target == null)
					fish.target = this.target;
			}
		}
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
	
	private void searchingTarget() {
		this.setSize(4F, 2);
		AxisAlignedBB box = new AxisAlignedBB(this.posX, this.posY - height, this.posZ, this.posX, this.posY, this.posZ).grow(30, 30, 30);
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, box);
			
		for(int i = 0; i < list.size(); i++)
		{
			Entity e = list.get(i);
			if(e instanceof EntityLivingBase && !(e instanceof EntityBoneShark)  && !(e instanceof EntityVillager) && this.canSeeTarget(e))
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
		return this.height * 0.5F;
	}
	
	@Override
	public void GroundThing() 
	{
		if(!(this.ticksExisted % 20 == 0))
			return;
		this.setNoGravity(false);
		
		boolean water = false;
		Iterable<BlockPos> blocks= BlockPos.getAllInBox(getPosition().add(-30, -30, -30), getPosition().add(30, 30, 30));
		BlockPos waterPos = null;
		for(BlockPos pos : blocks)
		{
			if(this.world.getBlockState(pos).getBlock() == Blocks.WATER || this.world.getBlockState(pos).getBlock() == Blocks.FLOWING_WATER)
			{
				water = true;
				
				if(waterPos == null)
					waterPos = pos;
				if(waterPos != null && this.getIfCloestTarget(this, pos, waterPos))
					waterPos = pos;
			}
		}
		
		if(water)
			new TeleportMagick(this, waterPos.add(0, -2, 0));
		else
		{
			Iterable<BlockPos> blocks1= BlockPos.getAllInBox(getPosition().add(-2, -1, -2), getPosition().add(2, 1, 2));
		
			for(BlockPos pos : blocks1)
			{
				if(this.world.isAirBlock(pos))
					this.world.setBlockState(pos, Blocks.WATER.getDefaultState());
			}
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
			this.reduce *= 0.9;
			return this.reduce;
		}
		
		public String getDamage()
		{
			return this.damage;
		}
	}
}
