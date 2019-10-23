package testmod.seccult.events;

import java.util.ArrayList;
import java.util.List;

import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;

public class BossCheckerEvent {
	private List<BossEventHandler> bossE = new ArrayList<>();
	
	public List<BossEventHandler> getBossHandler()
	{
		return bossE;
	}
	
	@SubscribeEvent
	public void onBossUpdate(ServerTickEvent event)
	{
		for(int i = 0; i < bossE.size(); ++i)
		{
			bossE.get(i).onUpdate();
		}
	}
}
