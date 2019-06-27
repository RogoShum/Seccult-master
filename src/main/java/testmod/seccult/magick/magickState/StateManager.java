package testmod.seccult.magick.magickState;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import testmod.seccult.Seccult;

public class StateManager {
	public static final String FROZEN = "Frozen";
	public static final String NOCLIP = "noClip";
	public static final String LOST_MIND = "LostMind";
	public static Random rand = new Random();
	public StateManager() 
	{
	}
	
	@SubscribeEvent
	public void CheckState(WorldTickEvent event)
	{
		
		List<Entity> entityList = event.world.loadedEntityList;
		for(int i = 0; i < entityList.size(); i++) {
			Entity entity = entityList.get(i);
		if(CheckIfStated(entity, FROZEN) > 0)
		{
			entity.motionX = 0;
			entity.motionY = 0;
			entity.motionZ = 0;
			entity.setPositionAndRotation(entity.prevPosX, entity.prevPosY, entity.prevPosZ, entity.prevRotationYaw, entity.prevRotationPitch);
		}
		
		if(CheckIfStated(entity, NOCLIP) > 0)
		{
			if(CheckIfStated(entity, NOCLIP) >= 5)
			{
				entity.noClip = true;
			}
			else
				entity.noClip = false;
		}
		
		if(CheckIfStated(entity, LOST_MIND) > 0)
		{
			entity.motionX = 1 - 2*rand.nextFloat();
			entity.motionY = 1 - 2*rand.nextFloat();
			entity.motionZ = 1 - 2*rand.nextFloat();
			entity.setRotationYawHead(rand.nextInt(360));
			entity.rotationYaw = rand.nextInt(360);
			if(entity.ticksExisted % (rand.nextInt(100) + 1) == 0)
			entity.attackEntityFrom(DamageSource.causeThornsDamage(entity), rand.nextInt(5));
		}
		
		}
	}
	
	public static int CheckIfStated(Entity entity, String s)
	{
		NBTTagCompound state = getStateListForEntity(entity);
			if(state.hasKey(s)) 
			{
				int tick = state.getInteger(s);
				
				if(tick < 0)
				{
					state.removeTag(s);
					return 0;
				}
					state.setInteger(s, tick-1);
					return tick;
			}
		return 0;
	}
	
	public static void setState(Entity entity, String s, int time)
	{
		getStateListForEntity(entity).setInteger(s, time * 20);
	}
	
	public static NBTTagCompound getStateListForEntity(Entity entity) {
		NBTTagCompound forgeData = entity.getEntityData();
		if(!forgeData.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
			forgeData.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());

		NBTTagCompound persistentData = forgeData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		if(!persistentData.hasKey(Seccult.Data))
			persistentData.setTag(Seccult.Data, new NBTTagCompound());

		NBTTagCompound stateData = persistentData.getCompoundTag(Seccult.Data);
		if(!stateData.hasKey("State"))
			stateData.setTag("State", new NBTTagCompound());
		
		return stateData.getCompoundTag("State");
	}
}
