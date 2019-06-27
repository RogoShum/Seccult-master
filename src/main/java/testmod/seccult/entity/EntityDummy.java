package testmod.seccult.entity;

import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.item.EntityItem;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import testmod.seccult.entity.livings.EntityNotoriousBIG;

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
    if(!this.world.isRemote && this.Entity != null)
    {
        NBTTagCompound entityNBT = new NBTTagCompound();
        ResourceLocation className = EntityList.getKey(this.Entity.getClass());
	    if(className != null) 
	    {
	    	entityNBT.setString("id", className.toString());
	    	setTag(this.Entity.writeToNBT(entityNBT));
	    }
	    else if(this.Entity instanceof EntityItem)
	    {
	    	entityNBT.setString("id", EntityList.getKey(EntityItem.class).toString());
	    	setTag(this.Entity.writeToNBT(entityNBT));
	    }
    }
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
	    if(this.world.isRemote && this.Entity == null)
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
	public void readEntityFromNBT(NBTTagCompound compound) {
      /*NBTTagCompound nbt = getTag();
      if (nbt != null)
      {
          this.Entity = EntityList.createEntityFromNBT(getTag(), world);
          if(this.Entity != null) {
          this.Entity.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
          world.spawnEntity(Entity);
          this.setDead();
          }
      }*/
	}
  
  @Override
  protected void writeEntityToNBT(NBTTagCompound nbttagcompound){}

@Override
protected void entityInit() 
{
	this.dataManager.register(NBT, new NBTTagCompound());
	this.dataManager.register(Saved, false);
}
}
