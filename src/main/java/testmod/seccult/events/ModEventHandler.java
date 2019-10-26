package testmod.seccult.events;

import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.magick.magickState.StateManager;

public class ModEventHandler {
	public static PlayerDataUpdateEvent playerData = new PlayerDataUpdateEvent();
	public static BossCheckerEvent bossEvent = new BossCheckerEvent();
	
	public static void RegisterEvents() {
		MinecraftForge.EVENT_BUS.register(new MobUnhurtableEvent());
		MinecraftForge.EVENT_BUS.register(new UnloadUndeadEntity());
		MinecraftForge.EVENT_BUS.register(playerData);
		MinecraftForge.EVENT_BUS.register(bossEvent);
		MinecraftForge.EVENT_BUS.register(new StateManager());
	}
}
