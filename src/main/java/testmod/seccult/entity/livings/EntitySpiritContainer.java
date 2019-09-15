package testmod.seccult.entity.livings;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import testmod.seccult.entity.SpiritManager;
import testmod.seccult.magick.active.MoveMagick;
import testmod.seccult.magick.active.TeleportMagick;
import testmod.seccult.network.NetworkEntityMoving;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.NetworkMutekiGamer;

public class EntitySpiritContainer extends EntityBase{
	public EntityLivingBase body;
	
	public EntityLivingBase soul;
	
	private static final DataParameter<NBTTagCompound> BodyNBT = EntityDataManager.<NBTTagCompound>createKey(EntitySpiritContainer.class, DataSerializers.COMPOUND_TAG);
	private static final DataParameter<NBTTagCompound> SoulNBT = EntityDataManager.<NBTTagCompound>createKey(EntitySpiritContainer.class, DataSerializers.COMPOUND_TAG);
	
	public EntitySpiritContainer(World worldIn) {
		super(worldIn);
		setAlwaysRenderNameTag(false);
	}
	
	public EntityLivingBase getBody()
	{
		return this.body;
	}
	
	public EntityLivingBase getSoul()
	{
		return this.soul;
	}
	
	@Override
	public void setDead() {
		if(soul != null)
		SpiritManager.replace(soul);
		
		if(body != null)
			body.setDead();
		
		super.setDead();
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
	}
	
	public void setBody(EntityLivingBase living)
	{
		living.deathTime = 0;
	    this.body = living;
	    setPositionAndRotation(living.posX, living.posY, living.posZ, living.rotationYaw, living.rotationPitch);
	    if(!this.world.isRemote && living != null)
	    {
	        NBTTagCompound entityNBT = new NBTTagCompound();
	        ResourceLocation className = EntityList.getKey(this.body.getClass());
		    if(className != null) 
		    {
		    	entityNBT.setString("id", className.toString());
		    	setBodyTag(this.body.writeToNBT(entityNBT));
		    	//NBTTagCompound spiritNBT = new NBTTagCompound();
		    	//this.writeToNBT(spiritNBT);
		    	//spiritNBT.setTag("OringinalCreature", getBodyTag());
		    	//this.readFromNBT(spiritNBT);
		    	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(living.getMaxHealth());
		    	this.setHealth(living.getHealth());
		    	living.world.removeEntityDangerously(living);
		    	List<Entity> list = this.world.getLoadedEntityList();
				if(list.contains(living))
					list.remove(living);
		    }
	    }
	}

	public void setSoul(EntityLivingBase living)
	{
		living.deathTime = 0;
	    this.soul = living;
	    if(!this.world.isRemote && living != null)
	    {
	        NBTTagCompound entityNBT = new NBTTagCompound();
	        ResourceLocation className = EntityList.getKey(this.soul.getClass());
		    if(className != null) 
		    {
		    	entityNBT.setString("id", className.toString());
		    	setSoulTag(this.soul.writeToNBT(entityNBT));
		    	//NBTTagCompound spiritNBT = new NBTTagCompound();
		    	//this.writeToNBT(spiritNBT);
		    	//spiritNBT.setTag("ContainerSoul", getSoulTag());
		    	//this.readFromNBT(spiritNBT);
		    	this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(living.getMaxHealth());
		    	this.setHealth(living.getHealth());
		    }
	    }
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		/*if(this.body != null)
			return this.body.attackEntityFrom(source, amount);
		else*/
			return super.attackEntityFrom(source, amount);
	}
	
	public void onUpdate()
	{
		super.onUpdate();
		//System.out.println(this.posX +" QAQ "+ this.posY +" QAQ "+ this.posZ);
		 if(this.soul != null && this.body != null)
		  if(this.soul.getUniqueID().equals(this.body.getUniqueID()) || this.soul == this.body)
			  ReleaseSoul();

		  if(this.soul == null && this.ticksExisted % 200 == 0)
		  {
			  AbsorbSoul();
		  }
		  
		List<Entity> entity = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(64));
		
		for(int i = 0; i < entity.size(); i++)
		{
			Entity e = entity.get(i);
			if(e instanceof EntitySpiritContainer)
			{
				EntitySpiritContainer spi = (EntitySpiritContainer) e;
				if(this.body != null && spi.body != null && spi.body.getUniqueID().equals(this.body.getUniqueID()))
				{
					if(this.ticksExisted == spi.ticksExisted)
						this.ticksExisted += this.rand.nextInt(3) + 1;
					
					if(this.ticksExisted < spi.ticksExisted)
						this.setDead();
				}
			}
		}
	
		  if(this.body == null)
		  {
		    NBTTagCompound nbt = getBodyTag();
		    if (nbt != null && !(getBodyTag().getString("id").equals("")))
		    {
		        this.body = (EntityLivingBase) EntityList.createEntityFromNBT(getBodyTag(), world);
		        if(this.body != null) {
		        this.body.readFromNBT(nbt);
		        this.body.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
		        }
		    }
		    else
		    	this.setDead();
		  }
		  else
		  {
			  body.hurtTime = this.hurtTime;
			  body.setHealth(this.getHealth());
			  this.setSize(body.width, body.height);	
		  }
		
		  if(this.soul == null)
		  {
		    NBTTagCompound nbt = getSoulTag();
		    if (nbt != null && !(getSoulTag().getString("id").equals("")))
		    {
		        this.soul = (EntityLivingBase) EntityList.createEntityFromNBT(getSoulTag(), world);
		        if(this.soul != null) {
		        this.soul.readFromNBT(nbt);
		        this.soul.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
		        }
		    }
		  }
		  
		  /*if(this.soul != null)
			  System.out.println(this.soul.getPosition());
		  
		  if(this.soul != null)
		  {
			  this.soul.onUpdate();
			  if(this.soul.isDead)
			  {
				  this.soul = null;
				  return;
			  }

			  if(!this.world.isRemote)
			  {
				  setPlayerTP(soul, soul.posX, soul.posY, soul.posZ, 0);
			  }
			  
			  this.posX = this.soul.posX;
			  this.posY = this.soul.posY;
			  this.posZ = this.soul.posZ;
			  this.motionX = this.soul.motionX;
			  this.motionY = this.soul.motionY; 
			  this.motionZ = this.soul.motionZ;
			  this.swingingHand = this.soul.swingingHand;
			  this.swingProgress = this.soul.swingProgress;
			  this.swingProgressInt = this.soul.swingProgressInt;
			  this.isSwingInProgress = this.soul.isSwingInProgress;
			  this.limbSwing = this.soul.limbSwing;
			  this.limbSwingAmount = this.soul.limbSwingAmount;
			  this.prevLimbSwingAmount = this.soul.prevLimbSwingAmount;
			  this.prevSwingProgress = this.soul.prevSwingProgress;
		  }
		  
		 // if(this.body != null)
			  //this.setSize(this.body.width, this.body.height);
			   * */

	}
	
	public static void setPlayerTP(Entity e, double movex, double movey, double movez, int type)
	{
		double[] move = {0, 0, 0};
		double[] pos = {movex, movey, movez};
		NetworkHandler.getNetwork().sendToAll(new NetworkEntityMoving(e.getUniqueID(), pos, move, type));
	}
	
	protected void ReleaseSoul()
	{
		NBTTagCompound nbt = new NBTTagCompound();
	    Entity entity = null;
		
	    try
	    {
	      entity = (Entity)this.body.getClass().getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    
	    if(entity != null)
	    {
	    	this.body.writeToNBT(nbt);
	    	nbt.setTag("Pos", this.newDoubleNBTList(this.posX, this.posY, this.posZ));
	    	nbt.setTag("Motion", this.newDoubleNBTList(this.motionX, this.motionY, this.motionZ));
	    	nbt.setTag("Rotation", this.newFloatNBTList(this.rotationYaw, this.rotationPitch));
	    	entity.readFromNBT(nbt);
	    	if(entity instanceof EntityLivingBase)
	    		((EntityLivingBase) entity).setHealth(this.getHealth());
	    	//System.out.println(this.posX +" QAQ "+ this.posY +" QAQ "+ this.posZ);
	    	//entity.setPositionAndRotationDirect(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch, 3, false);
	    	if(!this.world.isRemote)
		    	world.spawnEntity(entity);
	    	//TeleportMagick.setPlayerTP(entity, this.posX, this.posY, this.posZ, 0);
	    	this.soul = null;
	    	this.setDead();
	    }
	}
	
	protected void AbsorbSoul()
	{
		
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(1));
		
		float distance = 100;
		
		EntitySpirit rightSpirit = null;
		
		for(int i = 0; i < list.size(); ++i)
		{
			if(list.get(i) instanceof EntitySpirit && list.get(i).getDistance(list.get(i)) < distance)
			{
				distance = list.get(i).getDistance(list.get(i));
				rightSpirit = (EntitySpirit) list.get(i);
			}
		}
		
		if(rightSpirit != null)
		{
			this.setSoul(rightSpirit.living);
			NetworkHandler.getNetwork().sendToAll(new NetworkMutekiGamer(true, rightSpirit, 2));
			rightSpirit.setRelease();
		}
	}
	
	@Override
	protected void entityInit() 
	{
		super.entityInit();
		this.dataManager.register(BodyNBT, new NBTTagCompound());
		this.dataManager.register(SoulNBT, new NBTTagCompound());
	}
	
	public void setBodyTag(NBTTagCompound nbt) 
	{
		this.dataManager.set(BodyNBT, nbt);
	}
	  
	public NBTTagCompound getBodyTag() 
	{
		return this.dataManager.get(BodyNBT);
	}
	  
	public void setSoulTag(NBTTagCompound nbt) 
	{
		this.dataManager.set(SoulNBT, nbt);
	}
	  
	public NBTTagCompound getSoulTag() 
	{
		return this.dataManager.get(SoulNBT);
	}
	
    public NBTTagList DoubleNBTList(double... numbers)
    {
        return this.newDoubleNBTList(numbers);
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		nbt.setTag("OringinalCreature", getBodyTag());
		nbt.setTag("ContainerSoul", getSoulTag());
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if(nbt.hasKey("OringinalCreature"))
			this.setBodyTag(nbt.getCompoundTag("OringinalCreature"));
		
		if(nbt.hasKey("ContainerSoul"))
			this.setSoulTag(nbt.getCompoundTag("ContainerSoul"));
	}
}
