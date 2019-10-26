package testmod.seccult.entity.livings;

import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.ItemSoulStone;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.potions.PotionAllSeeEye;
import testmod.seccult.world.gen.DimensionMagic;

public class EntitySpirit extends EntityBase{
	public EntityLivingBase living;
	
	public EntityLivingBase Body;
	
	public EntityLivingBase Owner;
	
	public int ServeingTime;
	
	private int walkingTime;
	
	private Vec3d pos = new Vec3d(0, 0, 0);
	
	private static final DataParameter<NBTTagCompound> NBT = EntityDataManager.<NBTTagCompound>createKey(EntitySpirit.class, DataSerializers.COMPOUND_TAG);
	public EntitySpirit(World worldIn) {
		super(worldIn);
		this.setNoGravity(true);
		this.noClip = true;
		this.collided = false;
		this.isImmuneToFire = true;
		this.setEntityInvulnerable(true);
	}
	
	public EntityLivingBase getEntity()
	{
		return this.living;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(0D);
	}
	
	@Override
	public void setDead() {}
	
	public void setRelease()
	{
        double[] pos = new double[3];
		pos[0] = this.posX;
		pos[1] = this.posY + this.height / 2;
		pos[2] = this.posZ;
		
		double[] vec = {0.5 - this.rand.nextFloat(), 0.5 - this.rand.nextFloat(), 0.5 - this.rand.nextFloat()};
		float[] color = {0, 0.7F, 1};
        NetworkHandler.getNetwork().sendToAllAround(new NetworkEffectData(pos, vec, color, (this.height + this.width) / 2, 100),
        		new TargetPoint(dimension, pos[0], pos[1], pos[2], 32));
		this.isDead = true;
	}
	
	@Override
	public boolean isEntityUndead() {
		return false;
	}
	
	@Override
	public boolean isEntityAlive() {
		return false;
	}
	
	public void setEntity(EntityLivingBase living)
	{
		living.deathTime = 0;
	    this.living = living;
	    setPositionAndRotation(living.posX, living.posY, living.posZ, living.rotationYaw, living.rotationPitch);
	    if(!this.world.isRemote && living != null)
	    {
	        NBTTagCompound entityNBT = new NBTTagCompound();
	        ResourceLocation className = EntityList.getKey(this.living.getClass());
		    if(className != null) 
		    {
		    	entityNBT.setString("id", className.toString());
		    	setTag(this.living.writeToNBT(entityNBT));
		    	NBTTagCompound spiritNBT = new NBTTagCompound();
		    	this.writeToNBT(spiritNBT);
		    	spiritNBT.setTag("OringinalCreature", getTag());
		    	this.readFromNBT(spiritNBT);
		    }
	    }
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		return false;
	}
	
	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
		
		if(player.getHeldItemMainhand().getItem() == ModItems.SoulStone)
		{
			if(ItemSoulStone.putSoul(player.getHeldItemMainhand(), this.living))
			{
				this.setRelease();
				return EnumActionResult.SUCCESS;
			}
			else
				return EnumActionResult.FAIL;
		}
		
		return EnumActionResult.PASS;
	}
	
	public void onUpdate()
	{
		super.onUpdate();
		pos = this.getPositionVector().addVector(this.LookX(), this.LookY(), this.LookZ());
		this.inWater = false;

		doUsualUpdate();
	  
		if(this.Owner == null)
			justFloating();
		else
		{
			if(this.ServeingTime > 0)
				this.ServeingTime--;
			
			if(this.ServeingTime < 0)
			{
				this.ServeingTime = 0;
				this.Owner = null;
				return;
			}
			protectOwner();
		}
	}
	
	@Override
	protected void collideWithEntity(Entity entityIn) {
		if(entityIn == this.getRevengeTarget() && this.ticksExisted % 20 == 0)
		{
			this.getRevengeTarget().hurtResistantTime = -1;
			this.getRevengeTarget().attackEntityFrom(DamageSource.WITHER, 1);
			this.getRevengeTarget().hurtResistantTime = -1;
			this.getRevengeTarget().attackEntityFrom(DamageSource.DROWN, 1);
			this.getRevengeTarget().hurtResistantTime = -1;
			this.getRevengeTarget().attackEntityFrom(DamageSource.FALL, 1);
			this.getRevengeTarget().hurtResistantTime = -1;
			this.getRevengeTarget().attackEntityFrom(DamageSource.MAGIC, 1);
			this.getRevengeTarget().hurtResistantTime = -1;
			this.getRevengeTarget().attackEntityFrom(DamageSource.OUT_OF_WORLD, 1);
			this.getRevengeTarget().hurtResistantTime = -1;
			this.getRevengeTarget().setHealth(this.getRevengeTarget().getHealth() - 1);
		}
		
		if(entityIn == this.getLastAttackedEntity() && this.ticksExisted % 20 == 0)
		{
			this.getLastAttackedEntity().hurtResistantTime = -1;
			this.getLastAttackedEntity().attackEntityFrom(DamageSource.WITHER, 1);
			this.getLastAttackedEntity().hurtResistantTime = -1;
			this.getLastAttackedEntity().attackEntityFrom(DamageSource.DROWN, 1);
			this.getLastAttackedEntity().hurtResistantTime = -1;
			this.getLastAttackedEntity().attackEntityFrom(DamageSource.FALL, 1);
			this.getLastAttackedEntity().hurtResistantTime = -1;
			this.getLastAttackedEntity().attackEntityFrom(DamageSource.MAGIC, 1);
			this.getLastAttackedEntity().hurtResistantTime = -1;
			this.getLastAttackedEntity().attackEntityFrom(DamageSource.OUT_OF_WORLD, 1);
			this.getLastAttackedEntity().hurtResistantTime = -1;
			this.getLastAttackedEntity().setHealth(this.getLastAttackedEntity().getHealth() - 1);
		}
		
		super.collideWithEntity(entityIn);
	}
	
	protected void protectOwner()
	{
		this.setRevengeTarget(this.Owner.getRevengeTarget());
		this.setLastAttackedEntity(this.Owner.getLastAttackedEntity());
		
		if(this.getRevengeTarget() == null && this.getLastAttackedEntity() == null)
			standBy();
		else if (this.getRevengeTarget() != null && Owner.getDistance(this) < 16)
		{
			faceEntity(this.getRevengeTarget(), 30, 20);
			this.Moveto(this.getRevengeTarget().posX, this.getRevengeTarget().posY, this.getRevengeTarget().posZ, 0.1F);
		}
		else if (this.getLastAttackedEntity() != null && Owner.getDistance(this) < 16)
		{
			faceEntity(getLastAttackedEntity(), 30, 20);
			this.Moveto(this.getLastAttackedEntity().posX, this.getLastAttackedEntity().posY, this.getLastAttackedEntity().posZ, 0.1F);
		}
		else
		if(Owner.getDistance(this) > 16)
		{
			faceEntity(Owner, 30, 20);
			this.Moveto(Owner.posX, Owner.posY, Owner.posZ, 0.05F);
		}
		
		if(Owner.getDistance(this) > 32)
		{
			this.attemptTeleport(Owner.posX, Owner.posY, Owner.posZ);
		}
	}
	
	protected void standBy()
	{
		if(walkingTime <= 10 && Owner.getDistance(this) > 7 || this.rand.nextInt(300) == 0)
		{
			walkingTime += this.rand.nextInt(100);
		}
		
		faceEntity(Owner, 30, 20);
		
		if(this.walkingTime > 0)
		{
			this.Moveto(Owner.posX, Owner.posY, Owner.posZ, 0.05F);
			walkingTime--;
		}
	}
	
	protected void justFloating()
	{
		  if(this.rand.nextInt(10) == 0)
		  {
		  	this.rotationYaw +=  this.rand.nextInt(20);
		  }
		  
		  if(this.rand.nextInt(25) == 0)
			  this.Moveto(pos.x, pos.y + (0.5 - rand.nextFloat()), pos.z, 0.02F);
		  
		  if(this.rand.nextInt(18) == 0)
			  this.Moveto(pos.x, pos.y + (0.25 - rand.nextFloat()*0.5F), pos.z, 0.01F);
		  
		  if(this.Body == null)
			  findBody();
		  else
			  tryGetBody();
	}
	
	protected void doUsualUpdate()
	{
		this.inWater = false;
		
		this.setNoGravity(true);
		this.noClip = true;
		
		if(this.ticksExisted > 400 && this.dimension != DimensionMagic.SPIRIT_ID)
			//this.changeDimension(DimensionMagic.SPIRIT_ID);
			this.setRelease();
			
		List<Entity> entity = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(64));
		
		for(int i = 0; i < entity.size(); i++)
		{
			Entity e = entity.get(i);
			if(e instanceof EntitySpirit)
			{
				EntitySpirit spi = (EntitySpirit) e;
				if(this.living != null && spi.living != null && spi.living.getUniqueID().equals(this.living.getUniqueID()))
				{
					if(this.ticksExisted == spi.ticksExisted)
						this.ticksExisted += this.rand.nextInt(3) + 1;
					
					if(this.ticksExisted < spi.ticksExisted)
						this.setRelease();
				}
			}
		}
		
	  if(this.living == null)
	  {
	    NBTTagCompound nbt = getTag();
	    if (nbt != null && !(getTag().getString("id").equals("")))
	    {
	        this.living = (EntityLivingBase) EntityList.createEntityFromNBT(getTag(), world);
	        if(this.living != null) {
	        this.living.readFromNBT(nbt);
	        this.living.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
	        }
	    }
	    else
	    	this.setRelease();
	  }
	  else
	  {
		  living.hurtTime = this.hurtTime;
	  }
	}
	
	protected void tryGetBody()
	{
		this.faceEntity(Body, 360, 360);
		
		if(this.rand.nextInt(10) == 0)
			  this.Moveto(this.Body.posX, this.Body.posY + (0.25 - rand.nextFloat()*0.5F), this.Body.posZ, 0.01F);
		
		if(this.Body.isDead)
			this.Body = null;
	}
	
	protected void findBody()
	{
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox());
		
		for(int i = 0; i < list.size(); ++i)
		{
			Entity e = list.get(i);
			if(e instanceof EntitySpiritContainer)
			{
				EntitySpiritContainer con = (EntitySpiritContainer) e;
				if(con.getBody() != null && con.getBody().getUniqueID().equals(this.living.getUniqueID()))
				{
					this.Body = con;
				}
			}
		}
	}
	
	@Override
	protected void entityInit() 
	{
		super.entityInit();
		this.dataManager.register(NBT, new NBTTagCompound());
	}
	
	public void setTag(NBTTagCompound nbt) 
	{
		this.dataManager.set(NBT, nbt);
	}
	  
	public NBTTagCompound getTag() 
	{
		return this.dataManager.get(NBT);
	}
	  
    public NBTTagList DoubleNBTList(double... numbers)
    {
        return this.newDoubleNBTList(numbers);
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setTag("OringinalCreature", getTag());
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if(nbt.hasKey("OringinalCreature"))
			this.setTag(nbt.getCompoundTag("OringinalCreature"));
	}
}
