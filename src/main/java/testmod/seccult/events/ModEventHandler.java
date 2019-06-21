package testmod.seccult.events;

import net.minecraftforge.common.MinecraftForge;

public class ModEventHandler {
	public static void RegisterEvents() {
		MinecraftForge.EVENT_BUS.register(new MobUnhurtableEvent());
		//MinecraftForge.EVENT_BUS.register(new TimeStopEvent());
		MinecraftForge.EVENT_BUS.register(new UnloadUndeadEntity());
		MinecraftForge.EVENT_BUS.register(new PlayerDataUpdateEvent());
		MinecraftForge.EVENT_BUS.register(new HUDHandler());
	}
}
