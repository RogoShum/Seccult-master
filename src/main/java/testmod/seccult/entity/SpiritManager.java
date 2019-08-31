package testmod.seccult.entity;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.item.ItemBucketMilk;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.world.World;
import testmod.seccult.entity.livings.EntitySpirit;

public class SpiritManager {
	  public static EntitySpirit replace(EntityLivingBase entity)
	  {
	    World world = entity.world;
	    EntitySpirit spirit = new EntitySpirit(world);
	    spirit.setEntity(entity);
	    //world.removeEntity(entity);
	    if(!world.isRemote)
	    world.spawnEntity(spirit);
	    return spirit;
	  }
	  
	  public static EntityLivingBase restore(EntitySpirit spirit)
	  {
	    NBTTagCompound nbt = new NBTTagCompound();
	    World world = spirit.world;
	    EntityLivingBase entity = null;
	    if(spirit.getEntity() == null)
	    	return null;
	    try
	    {
		      entity = (EntityLivingBase)spirit.getEntity().getClass().getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });
		      spirit.getEntity().writeToNBT(nbt);
		      nbt.setTag("Pos", spirit.DoubleNBTList(spirit.posX, spirit.posY, spirit.posZ));
		      entity.readFromNBT(nbt);
		      entity.isDead = false;
		      entity.setHealth(entity.getMaxHealth());
		      entity.motionX = spirit.motionX;
		      entity.motionY = spirit.motionY;
		      entity.motionZ = spirit.motionZ;
		      entity.hurtResistantTime = 200;
		      entity.curePotionEffects(new ItemStack(new ItemBucketMilk()));
		      entity.setFire(0);
		      entity.setPositionAndRotationDirect(spirit.posX, spirit.posY, spirit.posZ, spirit.rotationYaw, spirit.rotationPitch, 3, false);
		      if(!world.isRemote)
		      world.spawnEntity(entity);
		      spirit.setRelease();
	    }
	    catch (Exception e)
	    {
	      e.printStackTrace();
	    }
	    
	    return entity;
	  }
}
