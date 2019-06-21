package testmod.seccult.events;

import net.minecraft.entity.Entity;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import testmod.seccult.entity.EntityBlackVelvetHell;

public class UnloadUndeadEntity {
	@SubscribeEvent
	public void onUpdate(LivingUpdateEvent event) {
		Entity e = event.getEntity();
		if(e instanceof EntityBlackVelvetHell) {
			e.setDead();
		}
	}
}
