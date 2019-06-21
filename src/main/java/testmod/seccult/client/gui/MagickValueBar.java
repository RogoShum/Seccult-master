package testmod.seccult.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.Gui;
import net.minecraft.client.gui.ScaledResolution;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;

public class MagickValueBar {
    protected Minecraft mc;
    protected Gui gui = new Gui();
    protected PlayerData data;
    
	public void renderMagickBar(ScaledResolution scaledRes, int x)
    { 	
		data = PlayerDataHandler.get(this.mc.player);
        this.mc.mcProfiler.startSection("magickBar");
        this.mc.getTextureManager().bindTexture(Gui.ICONS);
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

        this.mc.mcProfiler.endSection();

        if (data.getMana() > 0)
        {
            this.mc.mcProfiler.startSection("magickValue");
            String s = "" + data.getMana();
            int i1 = (scaledRes.getScaledWidth() - this.getFontRenderer().getStringWidth(s)) / 2;
            int j1 = scaledRes.getScaledHeight() - 31 - 4;
            this.getFontRenderer().drawString(s, i1 + 1, j1, 0);
            this.getFontRenderer().drawString(s, i1 - 1, j1, 0);
            this.getFontRenderer().drawString(s, i1, j1 + 1, 0);
            this.getFontRenderer().drawString(s, i1, j1 - 1, 0);
            this.getFontRenderer().drawString(s, i1, j1, 8453920);
            this.mc.mcProfiler.endSection();
        }
    }
	
    public FontRenderer getFontRenderer()
    {
        return this.mc.fontRenderer;
    }
}
