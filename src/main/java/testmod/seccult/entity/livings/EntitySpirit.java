package testmod.seccult.entity.livings;

import java.util.List;

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
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import testmod.seccult.potions.PotionAllSeeEye;

public class EntitySpirit extends EntityBase{
	public EntityLivingBase living;
	private static final DataParameter<NBTTagCompound> NBT = EntityDataManager.<NBTTagCompound>createKey(EntitySpirit.class, DataSerializers.COMPOUND_TAG);
	public EntitySpirit(World worldIn) {
		super(worldIn);
		this.setNoGravity(true);
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
		this.isDead = true;
	}
	
	@Override
	public boolean isEntityUndead() {
		return false;
	}
	
	@Override
	public boolean isEntityAlive() {
		//if(this.getIsDayTime())
			this.setRelease();
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
	
	public void onUpdate()
	{
		super.onUpdate();
		
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
	    	this.setDead();
	  }
	  else
	  {
		  living.hurtTime = this.hurtTime;
		  EntityPlayer player = Minecraft.getMinecraft().player;
		  
		  if(player != null && PotionAllSeeEye.hasAllSee(player))
		  {
			 this.setSize(living.width, living.height);
		  }
		  else
			  this.setSize(0, 0);
				
	  }
	  
	  Vec3d pos = this.getPositionVector().addVector(this.LookX(), this.LookY(), this.LookZ());
	  
	  this.setNoGravity(true);
	  this.noClip = true;
	  
	  if(this.rand.nextInt(10) == 0)
	  {
	  	this.rotationYaw +=  this.rand.nextInt(20);
	  }
	  
	  if(this.rand.nextInt(25) == 0)
		  this.Moveto(pos.x, pos.y + (0.5 - rand.nextFloat()), pos.z, 0.02F);
	  
	  if(this.rand.nextInt(18) == 0)
		  this.Moveto(pos.x, pos.y + (0.25 - rand.nextFloat()*0.5F), pos.z, 0.01F);
  }
	

	
    /*@SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    public float getBrightness()
    {
        return 5.0F;
    }
	*/
	
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
