package testmod.seccult.entity;

import net.minecraft.entity.Entity;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;

public class DummyManager
{
  public static EntityDummy replace(Entity entity)
  {
    World world = entity.world;
    EntityDummy dummy = new EntityDummy(world);
    dummy.setEntity(entity);
    entity.setDead();
    world.spawnEntity(dummy);
    return dummy;
  }
  
  public static Entity restore(EntityDummy dummy)
  {
    NBTTagCompound nbt = new NBTTagCompound();
    World world = dummy.world;
    Entity entity = null;
    if(dummy.getEntity() == null)
    	return null;
    try
    {
      entity = (Entity)dummy.getEntity().getClass().getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });
      dummy.getEntity().writeToNBT(nbt);
      entity.readFromNBT(nbt);
      world.spawnEntity(entity);
      dummy.setDead();
    }
    catch (Exception e)
    {
      e.printStackTrace();
    }
    return entity;
  }
}
