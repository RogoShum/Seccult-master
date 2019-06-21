package testmod.seccult.events;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraftforge.client.event.RenderGameOverlayEvent;
import net.minecraftforge.client.event.RenderGameOverlayEvent.ElementType;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;

public class HUDHandler {
    protected Gui gui = new Gui();
	
	@SubscribeEvent
	@SideOnly(Side.CLIENT)
	public void onDraw(RenderGameOverlayEvent.Post event) {
		if (event.getType() == ElementType.ALL) {
			ScaledResolution resolution = event.getResolution();
			float partialTicks = event.getPartialTicks();

			renderMagickBar(resolution, partialTicks);
		}
	}
	
	public void renderMagickBar(ScaledResolution scaledRes, float x)
    {   
    	Minecraft mc = Minecraft.getMinecraft();
    	PlayerData data = PlayerDataHandler.get(mc.player);
		mc.mcProfiler.startSection("MagickBar");
	        mc.getTextureManager().bindTexture(Gui.ICONS);
	        int i = (int) data.getMaxMana();

	        if (i > 0)
	        {
	            int j = 182;
	            int k = (int)(data.getMana() * 183.0F);
	            int l = scaledRes.getScaledHeight() - 32 + 3;
	            gui.drawTexturedModalRect(x, l, 0, 64, 182, 5);

	            if (k > 0)
	            {
	            	gui.drawTexturedModalRect(x, l, 0, 69, k, 5);
	            }
	        }

	        mc.mcProfiler.endSection();

	        if (data.getMana() > 0)
	        {
	            mc.mcProfiler.startSection("MagickLevel");
	            String s = "" + data.getMana();
	            int i1 = (scaledRes.getScaledWidth() - this.getFontRenderer(mc).getStringWidth(s)) / 2;
	            int j1 = scaledRes.getScaledHeight() - 31 - 4;
	            this.getFontRenderer(mc).drawString(s, i1 + 1, j1, 0);
	            this.getFontRenderer(mc).drawString(s, i1 - 1, j1, 0);
	            this.getFontRenderer(mc).drawString(s, i1, j1 + 1, 0);
	            this.getFontRenderer(mc).drawString(s, i1, j1 - 1, 0);
	            this.getFontRenderer(mc).drawString(s, i1, j1, 8453920);
	            mc.mcProfiler.endSection();
	        }
    }
	
    public FontRenderer getFontRenderer(Minecraft mc)
    {
        return mc.fontRenderer;
    }
}
