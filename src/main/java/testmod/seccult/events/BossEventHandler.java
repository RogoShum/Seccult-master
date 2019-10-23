package testmod.seccult.events;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.audio.MovingSound;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BossInfoServer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.entity.livings.EntityBase;
import testmod.seccult.entity.livings.IBossBase;
import testmod.seccult.init.ModItems;

public class BossEventHandler {
	private ArrayList<EntityBase> bosses;
	private BossInfoServer bossInfo;
	private MovingSound BGM;
	
	public BossEventHandler(ArrayList<EntityBase> bosses) {
		this.bosses = bosses;
		EntityBase one = bosses.get(0);
		bossInfo = (BossInfoServer)(new BossInfoServer(one.getDisplayName(), ((IBossBase)one).getBarColor(), ((IBossBase)one).getOverlay())).setDarkenSky(((IBossBase)one).DarkenSky());
		ModEventHandler.bossEvent.getBossHandler().add(this);
		if(bosses.size() <= 1)
		{
			BGM = new BossMusic(one);
			Minecraft.getMinecraft().getSoundHandler().playSound(BGM);
		}
		else
		{
			BGM = new BossMusicMult(bosses);
			Minecraft.getMinecraft().getSoundHandler().playSound(BGM);
		}
	}
	
	public void onUpdate()
	{
		boolean stillAlive = false;
		boolean cloesE = false;
		float health = 0;
		float maxHealth = 0;
		for(int i = 0; i < bosses.size(); ++i)
		{
			EntityBase boss = bosses.get(i);
			if(boss.getHealth() < 0)
				boss.setHealth(0);;
			health += boss.getHealth();
			maxHealth += boss.getMaxHealth();
			
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
					EntityBase boss = bosses.get(i);
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
			if(this.BGM instanceof BossMusicMult)
			{
				((BossMusicMult) this.BGM).donePlay();
			}
			
			if(this.BGM instanceof BossMusic)
			{
				((BossMusic) this.BGM).donePlay();
			}
			
			bossInfo = null;
			bosses = null;
			ModEventHandler.bossEvent.getBossHandler().remove(this);
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static class BossMusic extends MovingSound {
	
		private final EntityBase guardian;

		public BossMusic(EntityBase guardian) {
			super(((IBossBase)guardian).getBGM(), SoundCategory.RECORDS);
			this.guardian = guardian;
			this.repeat = true;
			this.volume = 5;
		}

		@Override
		public void update() {
			if (!guardian.isEntityAlive() || guardian.isDead) {
				this.volume = 0;
				donePlaying = true;
			}
			else
			{
				this.xPosF = (float) guardian.posX;
				this.yPosF = (float) guardian.posY;
				this.zPosF = (float) guardian.posZ;
			}
		}
		
		protected void donePlay()
		{
			this.volume = 0;
			donePlaying = true;
		}
	}
	
	@SideOnly(Side.CLIENT)
	private static class BossMusicMult extends MovingSound {
		private ArrayList<EntityBase> bosses;
		private BlockPos pos = null;
		
		public BossMusicMult(ArrayList<EntityBase> guardian) {
			super(((IBossBase)guardian.get(0)).getBGM(), SoundCategory.RECORDS);
			this.bosses = guardian;
			this.repeat = true;
			this.volume = 5;
		}

		@Override
		public void update() {
			boolean stillAlive = false;
			for(int i = 0; i < bosses.size(); ++i)
			{
				EntityBase boss = bosses.get(i);
				if(boss.isEntityAlive() && !boss.isDead)
				{
					if(pos == null)
						pos = boss.getPosition();
					
					BlockPos newPos = new BlockPos((boss.posX + pos.getX()) / 2,(
							boss.posY + pos.getY()) / 2,
							(boss.posZ + pos.getZ()) / 2);
					
					pos = newPos;
					stillAlive = true;
				}
			}
			
			if(!stillAlive)
			{
				this.volume = 0;
				donePlaying = true;
			}
			
			this.xPosF = (float) pos.getX();
			this.yPosF = (float) pos.getY();
			this.zPosF = (float) pos.getZ();
		}
		
		protected void donePlay()
		{
			this.volume = 0;
			donePlaying = true;
		}
	}
}
