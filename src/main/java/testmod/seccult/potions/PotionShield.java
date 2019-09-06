package testmod.seccult.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingHurtEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import testmod.seccult.magick.magickState.StateManager;

public class PotionShield extends PotionMod {
	
	public PotionShield() {
		super("shield", true, 0X8B008B, 0);
		MinecraftForge.EVENT_BUS.register(this);
		
	}

	@SubscribeEvent
	public void onMobAttack(LivingAttackEvent event) {
		EntityLivingBase living = event.getEntityLiving();
		if(living.isPotionActive(ModPotions.shield)){
			StateManager.setState(living, StateManager.Shield, 2, 6);
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onMobHurt(LivingHurtEvent event) {
		EntityLivingBase living = event.getEntityLiving();
		if(living.isPotionActive(ModPotions.shield)){
			StateManager.setState(living, StateManager.Shield, 2, 6);
			event.setCanceled(true);
		}
	}
	
	@SubscribeEvent
	public void onMobDeath(LivingDeathEvent event) {
		EntityLivingBase living = event.getEntityLiving();
		if(living.isPotionActive(ModPotions.shield)){
			StateManager.setState(living, StateManager.Shield, 2, 6);
			event.setCanceled(true);
	}
	}
}
