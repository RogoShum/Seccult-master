package testmod.seccult.events;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.client.event.sound.PlaySoundEvent;
import net.minecraftforge.event.entity.PlaySoundAtEntityEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import testmod.seccult.entity.EntityBlackVelvetHell;
import testmod.seccult.entity.EntityTimeManager;
import testmod.seccult.entity.SpiritManager;
import testmod.seccult.entity.livings.EntitySpirit;
import testmod.seccult.entity.livings.EntitySpiritContainer;
import testmod.seccult.entity.livings.EntitySpiritDummy;

public class UnloadUndeadEntity {
	@SubscribeEvent
	public void onUpdate(LivingUpdateEvent event) {
		Entity e = event.getEntity();
		if(e instanceof EntityBlackVelvetHell) {
			e.setDead();
		}
	}
	
	@SubscribeEvent
	public void spawnSpirits(LivingDeathEvent event) {
		EntityLivingBase e = event.getEntityLiving();
		if(!(e instanceof EntitySpirit) && !(e instanceof EntitySpiritContainer) && !(e instanceof EntitySpiritDummy))
		{
			NBTTagCompound tag = new NBTTagCompound();
			e.writeToNBT(tag);
			if(!tag.hasKey("AvadaKedavra"))
				SpiritManager.replace(e);
		}
		
		if(e instanceof EntitySpiritContainer)
		{
			EntitySpiritContainer container = (EntitySpiritContainer) e;
			if(container.getSoul() != null)
				SpiritManager.replace(container.getSoul());
		}
	}
	
	@SubscribeEvent
	public void timeStopSound(PlaySoundAtEntityEvent event) {
		Entity e = event.getEntity();
		if(e != null && e.world != null && e.world.isRemote)
		{
			List<EntityTimeManager> time  = e.world.getEntitiesWithinAABB(EntityTimeManager.class, e.getEntityBoundingBox().grow(20));
		
			if(!time.isEmpty())
				event.setCanceled(true);
		}
	}
}
