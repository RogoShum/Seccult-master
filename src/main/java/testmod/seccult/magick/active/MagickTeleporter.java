package testmod.seccult.magick.active;

import java.lang.reflect.InvocationTargetException;
import java.util.Random;

import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import testmod.seccult.events.ModEventHandler;
import testmod.seccult.events.PlayerDataUpdateEvent;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;

public class MagickTeleporter {
	
	private Entity entity;
	private BlockPos pos;
	private World world;
	private int click;
	public boolean done;
	private Random rand = new Random();
	
	public MagickTeleporter(Entity entity, World world, BlockPos pos) {
		this.entity = entity;
		this.pos = pos;
		this.world = world;
	}
	
	public void onUpdate()
	{
		click++;
		
		if(this.world == null || this.entity == null)
		{
			ModEventHandler.playerData.getTeleporter().remove(this);
			 return;
		}
		
		if(click > 20)
		{
			if(this.pos != null)
			{
				NBTTagCompound nbt = new NBTTagCompound();
				
				entity.writeToNBT(nbt);
			    
				try {
					entity = (Entity)entity.getClass().getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				entity.readFromNBT(nbt);
				entity.setPositionAndRotation(pos.getX(), pos.getY() + 1, pos.getZ(), entity.rotationYaw, entity.rotationPitch);
				if(!this.world.isRemote)
				this.world.spawnEntity(entity);
				MagickFX();
				this.done = true;
			}
			else
			{
				NBTTagCompound nbt = new NBTTagCompound();
				
				entity.writeToNBT(nbt);
			    
				try {
					entity = (Entity)entity.getClass().getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
				entity.readFromNBT(nbt);
				if(!this.world.isRemote)
				this.world.spawnEntity(entity);
				MagickFX();
				this.done = true;
			}
		}
	}
	
	public void setTeleport()
	{
		ModEventHandler.playerData.getTeleporter().add(this);
	}
	
	public void preTeleport()
	{
		 preMagickFX();
		 if(this.world != null && this.entity != null)
			 world.removeEntity(entity);
		 
		 //System.out.println(this.world != null && this.entity != null);
		 setTeleport();
	}
	
	void MagickFX() {
		 if(this.entity == null)
			 return;
		 entity.playSound(SoundEvents.ENTITY_SHULKER_TELEPORT, 1.0F, 1.0F);
		double[] pos = new double[3], vec = new double[3];
		pos[0] = entity.posX;
		pos[1] = entity.posY;
		pos[2] = entity.posZ;
		float[] color = {0, 1, 0.55F};
        NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, 0, 100));
	}
	
	void preMagickFX() {
		if(this.entity == null)
			 return;
		entity.playSound(SoundEvents.ENTITY_SHULKER_TELEPORT, 1.0F, 1.0F);
		for(int i = 0; i < 20; i++) {
		double[] pos = new double[3], vec = new double[3];
		pos[0] = entity.posX + (this.rand.nextDouble() - 0.5D) * (double)entity.width;
		pos[1] = entity.posY + this.rand.nextDouble() * (double)entity.height - 0.25D;
		pos[2] = entity.posZ + (this.rand.nextDouble() - 0.5D) * (double)entity.width;
		
		vec[0] = (this.rand.nextDouble()*0.5D - 0.25D);
		vec[1] = -this.rand.nextDouble();
		vec[2] = (this.rand.nextDouble()*0.5D - 0.25D);
		
		float[] color = {0, 1, 0.55F};
        NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, 1, 0));
		}
	}
}
