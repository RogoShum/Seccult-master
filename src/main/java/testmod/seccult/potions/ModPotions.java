package testmod.seccult.potions;

import net.minecraft.potion.Potion;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

@Mod.EventBusSubscriber(modid = "psi")
public class ModPotions {
			
	public static final Potion lostmind = new PotionLostMind();
	public static final Potion damage = new PotionDamage();
	public static final Potion allstop = new PotionAllStop();
	public static final Potion reflect = new PotionReflect();
	public static final Potion curse = new PotionCurse();
	public static final Potion allsee = new PotionAllSeeEye();
			
	@SubscribeEvent
	public static void registerPotions(RegistryEvent.Register<Potion> evt){
		evt.getRegistry().register(lostmind);
		evt.getRegistry().register(damage);
		evt.getRegistry().register(allstop);
		evt.getRegistry().register(reflect);
		evt.getRegistry().register(curse);
		evt.getRegistry().register(allsee);
	}
}
