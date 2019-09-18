package testmod.seccult.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.client.FX.FogFX;
import testmod.seccult.items.armor.ShadowSkyArmor;

public class EntityRenderHandler {
	public EntityRenderHandler() {}
	
	@SideOnly(Side.CLIENT)
	@SubscribeEvent
	public void onShadowMode(RenderPlayerEvent.Pre event)
	{
		EntityPlayer player = (EntityPlayer)event.getEntityPlayer();
		if(ShadowSkyArmor.hasArmorSetItem(player) && player.isSneaking())
		{
			event.setCanceled(true);
		}
	}
}

