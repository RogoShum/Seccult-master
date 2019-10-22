package testmod.seccult.magick.magickState;

import java.util.List;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityAgeable;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.WorldTickEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import testmod.seccult.Seccult;
import testmod.seccult.entity.EntityDummy;
import testmod.seccult.entity.EntityProtectionShieldFX;
import testmod.seccult.entity.EntityShieldFX;
import testmod.seccult.entity.livings.EntitySpiritDummy;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkEntityMoving;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.potions.ModPotions;

public class StateManager {
	public static final String FROZEN = "Frozen";
	public static final String NOCLIP = "noClip";
	public static final String LOST_MIND = "LostMind";
	public static final String NO_AI = "NoAI";
	public static final String WhiteAlbum = "WhiteAlbum";
	public static final String GratefulDead = "GratefulDead";
	public static final String KraftWork = "KraftWork";
	public static final String Floating = "Floating";
	public static final String Shield = "Shield";
	public static final String Protection = "Protection";
	
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
			int level = getLevel(entity, FROZEN);

			float motion = 1 - (0.1F * level);
			if(level < 6)
			{
			entity.motionX *= motion;
			entity.motionY *= motion;
			entity.motionZ *= motion;
		    
			setPlayerMove(entity, entity.motionX *= motion, entity.motionY *= motion, entity.motionZ *= motion, 1);
			}
			else
			{
				entity.motionX = 0;
				entity.motionY = 0;
				entity.motionZ = 0;
			    
				setPlayerMove(entity, 0, 0, 0, 1);
				
				setPlayerTP(entity, entity.prevPosX, entity.prevPosY, entity.prevPosZ, 0);
				entity.setPositionAndRotation(entity.prevPosX, entity.prevPosY, entity.prevPosZ, entity.prevRotationYaw, entity.prevRotationPitch);
			}
			if(!entity.isEntityAlive())
				setState(entity, FROZEN, -10, level);
		}
		
		int white = CheckIfStated(entity, WhiteAlbum);
		if(white > 0)
		{
			int level = getLevel(entity, WhiteAlbum);
			if(level > 5)
				level = 5;
			float motion = 1 - (0.1F * level);
			
			entity.motionX *= motion;
			entity.motionY *= motion;
			entity.motionZ *= motion;
			double[] vec = {0,0,0};
			float[] color = {0,0,0};
			double[] pos = {entity.posX,entity.posY,entity.posZ};
			if(entity.ticksExisted % 2 == 0)
			NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, 0F, 3));
		    
			setPlayerMove(entity, entity.motionX *= motion, entity.motionY *= motion, entity.motionZ *= motion, 1);
			entity.ticksExisted -= 1;
			
			entity.setAir(0);
			List<Entity> eList = event.world.getEntitiesWithinAABBExcludingEntity(entity, entity.getEntityBoundingBox().grow(level));
			for(Entity e: eList)
			{
				setState(e, WhiteAlbum, white / 20, level);
			}
			if(!entity.isEntityAlive())
				setState(entity, WhiteAlbum, -10, level);
		}
		
		if(CheckIfStated(entity, GratefulDead) > 0)
		{
			int level = getLevel(entity, GratefulDead);
			if(level > 5)
				level = 5;
			float motion = 1 - (0.1F * level);
			
			if(entity instanceof EntityAgeable)
			{
				((EntityAgeable) entity).setGrowingAge(((EntityAgeable) entity).getGrowingAge() + level * 20);
			}
			
			entity.motionX *= motion;
			entity.motionY *= motion;
			entity.motionZ *= motion;
			setPlayerMove(entity, entity.motionX *= motion, entity.motionY *= motion, entity.motionZ *= motion, 1);
			entity.ticksExisted += level * 20;
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
			int level = getLevel(entity, LOST_MIND);
			entity.motionX = (0.25 - 0.5 * rand.nextFloat()) * level;
			entity.motionY = (0.25 - 0.5 * rand.nextFloat()) * level;
			entity.motionZ = (0.25 - 0.5 * rand.nextFloat()) * level;
			
			if(entity instanceof EntityPlayer)
			{
				EntityPlayer player = (EntityPlayer) entity;
				setPlayerMove(player, (1 - 2*rand.nextFloat()) * level, (1 - 2*rand.nextFloat()) * level, (1 - 2*rand.nextFloat()) * level, 1);
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

			if(getStateListForEntity(entity).hasKey(KraftWork + "TAG"))
			{
				NBTTagCompound oldNbt = getStateListForEntity(entity).getCompoundTag(KraftWork + "TAG");
				entity.readFromNBT(oldNbt.copy());
			}
		}
		else
		{
			getStateListForEntity(entity).removeTag(KraftWork + "TAG");
		}
		
		int floating = CheckIfStated(entity, Floating);
		if(floating > 0)
		{
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
		
		int shield = CheckIfStated(entity, Shield);
		if(shield > 0)
		{
		}
		
		int protection = CheckIfStated(entity, Protection);
		if(protection > 0)
		{
		}
		
		}
	}
	
	public static int CheckIfStated(Entity entity, String s)
	{
		NBTTagCompound state = getStateListForEntity(entity);
			if(state.hasKey(s)) 
			{
				NBTTagCompound getState = state.getCompoundTag(s);
				int tick = getState.getInteger("time");
				
				if(tick < 0)
				{
					state.removeTag(s);
					return 0;
				}
					
				getState.setInteger("time", tick-1);
				return tick;
			}
			
		return 0;
	}
	
	public static int getLevel(Entity entity, String s)
	{
		NBTTagCompound state = getStateListForEntity(entity);
			if(state.hasKey(s)) 
			{
				NBTTagCompound getState = state.getCompoundTag(s);
				return getState.getInteger("level");
			}
		return 0;
	}
	
	public static boolean CheckIfStatedSafe(Entity entity, String s)
	{
		NBTTagCompound state = getStateListForEntity(entity);
		boolean ifState = false;
			if(state.hasKey(s)) 
			{
				NBTTagCompound getState = state.getCompoundTag(s);
				int tick = getState.getInteger("time");
				
				if (tick > 0)
					ifState = true;
			}
		return ifState;
	}
	
	public static void setPlayerMove(Entity e, double movex, double movey, double movez, int type)
	{
		double[] pos = {0, 0, 0};
		double[] move = {movex, movey, movez};
		NetworkHandler.getNetwork().sendToAllAround(new NetworkEntityMoving(e.getUniqueID(), pos, move, 1), new TargetPoint(e.dimension, e.posX, e.posY, e.posZ, 3));
	}
	
	public static void setPlayerTP(Entity e, double movex, double movey, double movez, int type)
	{
		double[] move = {0, 0, 0};
		double[] pos = {movex, movey, movez};
		NetworkHandler.getNetwork().sendToAllAround(new NetworkEntityMoving(e.getUniqueID(), pos, move, 0), new TargetPoint(e.dimension, e.posX, e.posY, e.posZ, 3));
	}
	
	public static void setState(Entity entity, String s, int time, int level)
	{
		if(entity instanceof EntityShieldFX || entity instanceof EntityProtectionShieldFX) return;
		
		if(entity instanceof EntityLivingBase)
		{
			EntityLivingBase living = (EntityLivingBase) entity;
			if(living.isPotionActive(ModPotions.protection) || living.isPotionActive(ModPotions.shield) || living instanceof EntitySpiritDummy)
			{
				if(s.equals(FROZEN) || s.equals(LOST_MIND) || s.equals(NO_AI) || s.equals(WhiteAlbum) || s.equals(KraftWork))
					return;
			}
		}
		NBTTagCompound nbt = new NBTTagCompound();
		nbt.setInteger("time", time * 20);
		nbt.setInteger("level", level);
		getStateListForEntity(entity).setTag(s, nbt);
		
	}
	
	public static NBTTagCompound getStateListForEntity(Entity entity) {
		if(entity == null)
			return new NBTTagCompound();
		
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
