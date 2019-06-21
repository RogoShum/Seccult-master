package testmod.seccult.potions;

import net.minecraft.entity.EntityLiving;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionLostMind extends PotionMod {

	public PotionLostMind() {
		super("lostmind", false, 0X8B008B, 0);
		MinecraftForge.EVENT_BUS.register(this);
		setBeneficial();
	}
	
	@SubscribeEvent
	public void onMobUpdate(LivingUpdateEvent event) {
		if(event.getEntityLiving() instanceof EntityLiving){
			EntityLiving mob = (EntityLiving) event.getEntityLiving();
			
			if(mob.isPotionActive(ModPotions.lostmind)){
				if(mob.getActivePotionEffect(ModPotions.lostmind).getDuration() > 5)
					mob.setNoAI(true);
				else 
					mob.setNoAI(false);
			}
		}
	}

}
