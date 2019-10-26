package testmod.seccult.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BossInfoServer;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.entity.EntitySound;
import testmod.seccult.entity.livings.EntityBase;
import testmod.seccult.entity.livings.EntityEoW;
import testmod.seccult.entity.livings.IBossBase;
import testmod.seccult.init.ModItems;

public class BossEventHandler {
	private ArrayList<Entity> bosses;
	private BossInfoServer bossInfo;
	
	public BossEventHandler(ArrayList<Entity> bosses) {
		this.bosses = bosses;
		Entity one = bosses.get(0);
		
		bossInfo = (BossInfoServer)(new BossInfoServer(one.getDisplayName(), ((IBossBase)one).getBarColor(), ((IBossBase)one).getOverlay())).setDarkenSky(((IBossBase)one).DarkenSky());
		ModEventHandler.bossEvent.getBossHandler().add(this);
		
		if(one instanceof EntityEoW)
		{
		}
		else
		Seccult.proxy.BossSound(bosses);
	}
	
	public void onUpdate()
	{
		boolean stillAlive = false;
		boolean cloesE = false;
		float health = 0;
		float maxHealth = 0;
		for(int i = 0; i < bosses.size(); ++i)
		{
			Entity boss = bosses.get(i);
			if(boss instanceof EntityLiving)
			{
				EntityLiving living = (EntityLiving) boss;
				
			if(living.getHealth() < 0)
				living.setHealth(0);;
			health += living.getHealth();
			maxHealth += living.getMaxHealth();
			}
			if(boss.isEntityAlive() && !boss.isDead)
				stillAlive = true;
		}
		
		List<EntityPlayer> players = bosses.get(0).world.playerEntities;
		for(int c = 0; c < players.size(); ++c)
		{
			
			EntityPlayer player = players.get(c);
			if(player instanceof EntityPlayerMP)
			{
				
				EntityPlayerMP plMP = (EntityPlayerMP) player;
				
				for(int i = 0; i < bosses.size(); ++i)
				{
					Entity boss = bosses.get(i);
					if(plMP.dimension == boss.dimension && plMP.getDistance(boss) < 72)
						cloesE = true;
				}
				
				if(cloesE && !bossInfo.getPlayers().contains(plMP))
				{
					bossInfo.addPlayer(plMP);
				}
				else if(!cloesE)
					bossInfo.removePlayer(plMP);
			}
		}
		
		bossInfo.setPercent(health / maxHealth);
		if(!stillAlive || health <= 0)
		{
			for(int i = 0; i < bossInfo.getPlayers().size(); ++i)
			{
				Iterator<EntityPlayerMP> it = bossInfo.getPlayers().iterator();
				while (it.hasNext()) {
					EntityPlayerMP MP = it.next();
					if(!MP.world.isRemote)
						MP.dropItem(ModItems.AlterScroll, 1);
					bossInfo.removePlayer(MP);
				}
			}

			bossInfo = null;
			bosses = null;
			ModEventHandler.bossEvent.getBossHandler().remove(this);
		}
	}

}
