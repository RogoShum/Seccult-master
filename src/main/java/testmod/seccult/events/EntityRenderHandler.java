package testmod.seccult.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.client.event.RenderPlayerEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.ClientTickEvent;
import net.minecraftforge.fml.common.gameevent.TickEvent.Phase;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.ClientProxy;
import testmod.seccult.Seccult;
import testmod.seccult.client.FX.FogFX;
import testmod.seccult.client.gui.GuiElementLoader;
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
	
	@SubscribeEvent
	public void clientTickEnd(ClientTickEvent event) {
		if(event.phase == Phase.END) {
			Minecraft mc = Minecraft.getMinecraft();

			GuiScreen gui = mc.currentScreen;
			KeyBinding[] keyBindings = ClientProxy.keyBindings;
			EntityPlayer player = mc.player;
			if(gui == null || !gui.doesGuiPauseGame()) {
				if(gui == null && keyBindings[2].isKeyDown())
					player.openGui(Seccult.instance, GuiElementLoader.GUI_Accessories, player.world, (int)player.posX, (int)player.posY, (int)player.posZ);
			}
		}
	}
}

