package testmod.seccult.init;

import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.registries.IForgeRegistry;
import testmod.seccult.Seccult;

@Mod.EventBusSubscriber(modid = Seccult.MODID)
public class ModSounds {
	public static final SoundEvent ringRingRing = Sound("ring");
	
	private static SoundEvent Sound(String name) {
		ResourceLocation loc = new ResourceLocation(Seccult.MODID, name);
		return new SoundEvent(loc).setRegistryName(loc);
	}
	
	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> evt) 
	{
		IForgeRegistry<SoundEvent> r = evt.getRegistry();
		r.register(ringRingRing);
	}
}
