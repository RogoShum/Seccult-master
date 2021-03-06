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
	public static final SoundEvent A184 = Sound("a184");
	public static final SoundEvent zero = Sound("zero");
	public static final SoundEvent qualia = Sound("qualia");
	public static final SoundEvent slit = Sound("slit");
	public static final SoundEvent afterglow = Sound("afterglow");
	public static final SoundEvent imprinting = Sound("imprinting");
	public static final SoundEvent light = Sound("light");
	public static final SoundEvent saika = Sound("saika");
	public static final SoundEvent summer = Sound("summer");
	public static final SoundEvent dadada = Sound("dadada");
	public static final SoundEvent gatorix = Sound("gatorix");
	public static final SoundEvent twentyone = Sound("twentyone");
	public static final SoundEvent storia = Sound("storia");
	public static final SoundEvent np = Sound("np");
	public static final SoundEvent dream_living = Sound("dream_living");
	public static final SoundEvent dream_hurt = Sound("dream_hurt");
	public static final SoundEvent dream_death = Sound("dream_death");
	public static final SoundEvent nightmare_living = Sound("nightmare_living");
	public static final SoundEvent nightmare_hurt = Sound("nightmare_hurt");
	public static final SoundEvent nightmare_death = Sound("nightmare_death");
	public static final SoundEvent gatorix_spawn = Sound("gatorix_spawn");
	public static final SoundEvent gatorix_flying = Sound("gatorix_flying");
	
	private static SoundEvent Sound(String name) {
		ResourceLocation loc = new ResourceLocation(Seccult.MODID, name);
		return new SoundEvent(loc).setRegistryName(loc);
	}
	
	@SubscribeEvent
	public static void registerSounds(RegistryEvent.Register<SoundEvent> evt) 
	{
		IForgeRegistry<SoundEvent> r = evt.getRegistry();
		r.register(ringRingRing);
		r.register(A184);
		r.register(zero);
		r.register(qualia);
		r.register(slit);
		r.register(afterglow);
		r.register(imprinting);
		r.register(light);
		r.register(saika);
		r.register(summer);
		r.register(dadada);
		r.register(gatorix);
		r.register(twentyone);
		r.register(storia);
		r.register(np);
		r.register(dream_living);
		r.register(dream_hurt);
		r.register(dream_death);
		r.register(nightmare_living);
		r.register(nightmare_hurt);
		r.register(nightmare_death);
		r.register(gatorix_spawn);
		r.register(gatorix_flying);
	}
}
