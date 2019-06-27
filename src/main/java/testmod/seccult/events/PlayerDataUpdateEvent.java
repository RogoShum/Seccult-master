package testmod.seccult.events;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.accessorie.PlayerAccessorieHandler;
import testmod.seccult.init.ModDamage;
import testmod.seccult.magick.MagickCompiler;

public class PlayerDataUpdateEvent {
    public static final List<String> MagicDamage  = new ArrayList<>();
    public static List<MagickCompiler> compiler = new ArrayList<>();
    
    
    public PlayerDataUpdateEvent() 
    {
    	MagicDamage.add(ModDamage.darkMagic.damageType);
    	MagicDamage.add(ModDamage.forbiddenMagic.damageType);
    	MagicDamage.add(ModDamage.normalMagic.damageType);
    	MagicDamage.add(ModDamage.pureMagic.damageType);
	}
	
	
	@SubscribeEvent
	public void onPlayerTick(LivingUpdateEvent event) 
	{
		if(event.getEntityLiving() instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			PlayerDataHandler.get(player).tick();
			PlayerAccessorieHandler.get(player).tick();
		}
	}
	
	@SubscribeEvent
	public void onServerTick(ServerTickEvent event) 
	{
		for(int i = 0; i < compiler.size(); i++)
		{
			compiler.get(i).onUpdate();
			if(compiler.get(i).done)
				compiler.remove(i);
		}
		if(event.phase == Phase.END) {
			PlayerDataHandler.cleanup();
			PlayerAccessorieHandler.cleanup();
		}
	}
	
	@SubscribeEvent
	public void onHurt(LivingAttackEvent event)
	{
		if(event.getSource().getTrueSource() instanceof EntityPlayer && MagicDamage.contains(event.getSource().getDamageType()))
		{
			EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
			PlayerDataHandler.get(player).addAttributeValue();
		}
	}
	
	@SubscribeEvent
	public void onCreatureDeath(LivingDeathEvent event)
	{
		if(event.getSource().getTrueSource() instanceof EntityPlayer && MagicDamage.contains(event.getSource().getDamageType()))
		{
			EntityPlayer player = (EntityPlayer)event.getSource().getTrueSource();
			PlayerDataHandler.get(player).levelUpper();
		}
	}
}
