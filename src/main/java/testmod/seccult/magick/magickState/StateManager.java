package testmod.seccult.magick.magickState;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import testmod.seccult.Seccult;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkEntityMoving;
import testmod.seccult.network.NetworkHandler;

public class StateManager {
	public static final String FROZEN = "Frozen";
	public static final String NOCLIP = "noClip";
	public static final String LOST_MIND = "LostMind";
	public static final String NO_AI = "NoAI";
	public static final String WhiteAlbum = "WhiteAlbum";
	public static final String GratefulDead = "GratefulDead";
	public static final String KraftWork = "KraftWork";
	public static final String Floating = "Floating";
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
			entity.ticksExisted -= 1;
			double[] vec = {0,0,0};
			float[] color = {entity.height,entity.width,0};
			double[] pos = {entity.posX,entity.posY + entity.height,entity.posZ};
			NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, 0F, 105));
			setPlayerMove(entity, 0, 0, 0, 1);
			entity.setPositionAndRotation(entity.prevPosX, entity.prevPosY, entity.prevPosZ, entity.prevRotationYaw, entity.prevRotationPitch);
			if(!entity.isEntityAlive())
				setState(entity, FROZEN, -10);
		}
		
		int white = CheckIfStated(entity, WhiteAlbum);
		if(white > 0)
		{
			entity.motionX *= 0.5;
			entity.motionY *= 0.5;
			entity.motionZ *= 0.5;
			double[] vec = {0,0,0};
			float[] color = {0,0,0};
			double[] pos = {entity.posX,entity.posY,entity.posZ};
			if(entity.ticksExisted % 2 == 0)
			NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, 0F, 3));
		    
			setPlayerMove(entity, entity.motionX *= 0.5, entity.motionY *= 0.5, entity.motionZ *= 0.5, 1);
			entity.ticksExisted -= 1;
			
			entity.setAir(0);
			List<Entity> eList = event.world.getEntitiesWithinAABBExcludingEntity(entity, entity.getEntityBoundingBox().grow(3));
			for(Entity e: eList)
			{
				setState(e, WhiteAlbum, white / 20);
			}
			if(!entity.isEntityAlive())
				setState(entity, WhiteAlbum, -10);
		}
		
		if(CheckIfStated(entity, GratefulDead) > 0)
		{
			entity.motionX *= 0.5;
			entity.motionY *= 0.5;
			entity.motionZ *= 0.5;
			setPlayerMove(entity, entity.motionX *= 0.5, entity.motionY *= 0.5, entity.motionZ *= 0.5, 1);
			entity.ticksExisted += 20;
		}
		
		if(CheckIfStated(entity, NOCLIP) > 0)
		{
			if(CheckIfStated(entity, NOCLIP) >= 5)
			{
				entity.noClip = true;
				if(entity instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer) entity;
					setPlayerMove(player, 0, 0, 0, 2);
				}
			}
			else
				entity.noClip = false;
		}
		
		if(CheckIfStated(entity, LOST_MIND) > 0)
		{
			entity.motionX = 1 - 2*rand.nextFloat();
			entity.motionY = 1 - 2*rand.nextFloat();
			entity.motionZ = 1 - 2*rand.nextFloat();
			
			if(entity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entity;
				setPlayerMove(player, 1 - 2*rand.nextFloat(), 1 - 2*rand.nextFloat(), 1 - 2*rand.nextFloat(), 1);
			}
			
			entity.setRotationYawHead(rand.nextInt(360));
			entity.rotationYaw = rand.nextInt(360);
			if(entity.ticksExisted % (rand.nextInt(200) + 1) == 0)
			entity.attackEntityFrom(DamageSource.causeThornsDamage(entity), rand.nextInt(5));
		}
		int no_ai = CheckIfStated(entity, NO_AI);
		if(no_ai > 0)
		{
			if(!(entity instanceof EntityLiving))
				continue;
			EntityLiving living = (EntityLiving) entity;
			if(no_ai >= 5)
			{
				living.setNoAI(true);
			}
			else
				living.setNoAI(false);
		}
		
		int kraftwork = CheckIfStated(entity, KraftWork);
		if(kraftwork > 0)
		{
			if(!getStateListForEntity(entity).hasKey(KraftWork + "TAG"))
			{
				NBTTagCompound nbt = new NBTTagCompound();
				entity.writeToNBT(nbt);
				getStateListForEntity(entity).setTag(KraftWork + "TAG", nbt.copy());
				continue;
			}

			if(kraftwork > 5 && getStateListForEntity(entity).hasKey(KraftWork + "TAG"))
			{
				NBTTagCompound oldNbt = getStateListForEntity(entity).getCompoundTag(KraftWork + "TAG");
				entity.readFromNBT(oldNbt.copy());
			}
			
			if(kraftwork < 5)
			{
				getStateListForEntity(entity).removeTag(KraftWork + "TAG");
			}
		}
		
		int floating = CheckIfStated(entity, Floating);
		if(floating > 0)
		{
			//entity.onGround = true;
			//entity.setAir(0);
			//entity.fallDistance = 0;
			if(entity.isSneaking())
			{
				if(entity.motionY > 0.2)
				{
					setPlayerMove(entity, entity.motionX, -0.2, entity.motionZ, 3);
					entity.motionY = 0.2;
				}
			}
			else if(entity.motionY < 0)
			{
				entity.motionY = 0;
				setPlayerMove(entity, entity.motionX, 0, entity.motionZ, 3);
			}
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
	
	public static void setPlayerMove(Entity e, double movex, double movey, double movez, int type)
	{
		double[] pos = {0, 0, 0};
		double[] move = {movex, movey, movez};
		NetworkHandler.getNetwork().sendToAllAround(new NetworkEntityMoving(e.getUniqueID(), pos, move, type), new TargetPoint(e.dimension, e.posX, e.posY, e.posZ, 3));
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
