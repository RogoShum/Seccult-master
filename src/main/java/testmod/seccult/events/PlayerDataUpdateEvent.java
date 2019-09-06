package testmod.seccult.events;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.RenderWorldLastEvent;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingDeathEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.common.gameevent.TickEvent.ServerTickEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.accessorie.PlayerAccessorieHandler;
import testmod.seccult.init.ModDamage;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.ItemWand;
import testmod.seccult.magick.MagickCompiler;
import testmod.seccult.magick.active.MagickTeleporter;

public class PlayerDataUpdateEvent {
    public static final List<String> MagicDamage  = new ArrayList<>();
    public static List<MagickCompiler> compiler = new ArrayList<>();
    public static List<MagickTeleporter> Teleporter = new ArrayList<>();
    
    
    public PlayerDataUpdateEvent() 
    {
    	MagicDamage.add(ModDamage.darkMagic.damageType);
    	MagicDamage.add(ModDamage.forbiddenMagic.damageType);
    	MagicDamage.add(ModDamage.normalMagic.damageType);
    	MagicDamage.add(ModDamage.pureMagic.damageType);
	}
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onRenderWorldLast(RenderWorldLastEvent event) {
		Minecraft mc = Minecraft.getMinecraft();

		float partialTicks = event.getPartialTicks();

		for(EntityPlayer player : mc.world.playerEntities)
		{
			if(player.getHeldItemMainhand().getItem() == ModItems.Wand)
			{
				ItemWand wand = (ItemWand) player.getHeldItemMainhand().getItem();
				wand.render(player, partialTicks);
			}
		}
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
		
		for(int i = 0; i < Teleporter.size(); i++)
		{
			Teleporter.get(i).onUpdate();
			if(Teleporter.get(i).done)
				Teleporter.remove(i);
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
