package testmod.seccult.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import testmod.seccult.magick.magickState.StateManager;

public class PotionProtection extends PotionMod {
	
	public PotionProtection() {
		super("protection_shield", true, 0X8B008B, 0);
		MinecraftForge.EVENT_BUS.register(this);
		
	}
	
	@SubscribeEvent
	public void onMobHurt(LivingHurtEvent event) {
		EntityLivingBase living = event.getEntityLiving();
		if(living.isPotionActive(ModPotions.protection)){
			int level = living.getActivePotionEffect(ModPotions.protection).getAmplifier();
			StateManager.setState(living, StateManager.Protection, 1, 6);
			if(level >= event.getAmount())
				event.setCanceled(true);
			else
			{
				event.setAmount(event.getAmount() - level);
				if(event.getAmount() > level * 3 && living.isHandActive())
				{
					living.stopActiveHand();
				}
			}
		}
	}
}
