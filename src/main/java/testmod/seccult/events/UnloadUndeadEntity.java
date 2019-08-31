package testmod.seccult.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import testmod.seccult.entity.EntityBlackVelvetHell;
import testmod.seccult.entity.SpiritManager;
import testmod.seccult.entity.livings.EntitySpirit;

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
		if(!(e instanceof EntitySpirit))
			SpiritManager.replace(e);
	}
}
