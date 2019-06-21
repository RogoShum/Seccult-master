package testmod.seccult.entity;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityDummy extends Entity
{
  private Entity Entity;
  private static final DataParameter<NBTTagCompound> NBT = EntityDataManager.<NBTTagCompound>createKey(EntityDummy.class, DataSerializers.COMPOUND_TAG);
  private static final DataParameter<Boolean> Saved = EntityDataManager.<Boolean>createKey(EntityNotoriousBIG.class, DataSerializers.BOOLEAN);
  
  public EntityDummy(World par1World)
  {
    super(par1World);
  }
  
  public void setEntity(Entity entity)
  {
    this.Entity = entity;
    setPositionAndRotation(entity.posX, entity.posY, entity.posZ, entity.rotationYaw, entity.rotationPitch);
  }
  
  public void setTag(NBTTagCompound nbt) 
  {
	  this.dataManager.set(NBT, nbt);
  }
  
  public NBTTagCompound getTag() 
  {
	  return this.dataManager.get(NBT);
  }
  
  public void setToSave(Boolean b) 
  {
	  this.dataManager.set(Saved, b);
  }
  
  public Boolean getSaved() 
  {
	  return this.dataManager.get(Saved);
  }
  
  public Entity getEntity()
  {
	  return this.Entity;
  }
    
  public void onUpdate()
  {
	    if(!this.world.isRemote && this.Entity != null)
	    {
	        NBTTagCompound entity = new NBTTagCompound();
	        ResourceLocation className = EntityList.getKey(this.Entity.getClass());
		    if(className != null) 
		    {
		    	entity.setString("id", className.toString());
		    	setTag(this.Entity.writeToNBT(entity));
		    }
		    else if(this.Entity instanceof EntityItem)
		    {
		    	entity.setString("id", EntityList.getKey(EntityItem.class).toString());
		    	setTag(this.Entity.writeToNBT(entity));
		    }
	    }
	    else if(this.world.isRemote && this.Entity == null)
	    {
	      NBTTagCompound nbt = getTag();
	      if (nbt != null && !(getTag().getString("id").equals("")))
	      {
	          this.Entity = EntityList.createEntityFromNBT(getTag(), world);
	          if(this.Entity != null) {
	          this.Entity.readFromNBT(nbt);
	          this.Entity.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
	          }
	      }
	    }
  }
  
  @Override
	public NBTTagCompound writeToNBT(NBTTagCompound compound) {
      NBTTagCompound nbt = getTag();
      /*if (nbt != null)
      {
          this.Entity = EntityList.createEntityFromNBT(getTag(), world);
          if(this.Entity != null) {
          this.Entity.readFromNBT(nbt);
          this.Entity.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
          this.setDead();
          }
      }*/
	 return getTag();
  }
  
  /*@Override
	public void readFromNBT(NBTTagCompound compound) {
      NBTTagCompound nbt = getTag();
      if (nbt != null)
      {
          this.Entity = EntityList.createEntityFromNBT(getTag(), world);
          if(this.Entity != null) {
          this.Entity.readFromNBT(nbt);
          this.Entity.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
          this.setDead();
          }
      }
	  super.readFromNBT(compound);
	}*/
  
  @Override
  protected void readEntityFromNBT(NBTTagCompound nbttagcompound){} 
  @Override
  protected void writeEntityToNBT(NBTTagCompound nbttagcompound){}

@Override
protected void entityInit() 
{
	this.dataManager.register(NBT, new NBTTagCompound());
	this.dataManager.register(Saved, false);
}
}
