package testmod.seccult.events;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import testmod.seccult.entity.EntityBlackVelvetHell;
import testmod.seccult.entity.SpiritManager;
import testmod.seccult.entity.livings.EntitySpirit;
import testmod.seccult.entity.livings.EntitySpiritContainer;

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
		if(!(e instanceof EntitySpirit) && !(e instanceof EntitySpiritContainer))
		{
			if(!e.getEntityData().hasKey("AvadaKedavra"))
				SpiritManager.replace(e);
		}
		
		if(e instanceof EntitySpiritContainer)
		{
			EntitySpiritContainer container = (EntitySpiritContainer) e;
			if(container.getSoul() != null)
				SpiritManager.replace(container.getSoul());
		}
	}
}
