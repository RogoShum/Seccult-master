package testmod.seccult.client.gui.button;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.FontRenderer;
import net.minecraft.client.gui.GuiButton;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import testmod.seccult.Seccult;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.active.Magick;

public class MagickInfoButton extends GuiButton{
	public boolean pushed;
	public int MagickId;
	public int Magick_Power;
	public int Magick_Attribute;
	public int spellSort;
	public boolean isMagick;
	public int insideSort;
	protected FontRenderer fontRenderer;
	
	public MagickInfoButton(int x, int y, int wid, int hei, boolean display) {
		super(0, x, y, wid, hei, "");
		this.fontRenderer = Minecraft.getMinecraft().fontRenderer;
	}

	public void pushInfo(int MagickId, int Magick_Power, int MagickId_Attribute, int spellSort, boolean isMagick, int insideSort)
	{
		this.MagickId = MagickId;
		this.Magick_Power = Magick_Power;
		this.Magick_Attribute = MagickId_Attribute;
		this.spellSort = spellSort;
		this.isMagick = isMagick;
		this.insideSort = insideSort;
	}
	
	@Override
	public void drawButton(Minecraft mc, int mouseX, int mouseY, float partialTicks) {
		if (this.visible)
		{
			String name = ModMagicks.getI18nNameByID(MagickId);
			String spell_name = I18n.format(name + "_name");

			String spell_power = I18n.format("power");
			String spell_attribute = I18n.format("attribute");
			
			boolean doAttribute = false;
			
			if(MagickId >= 7)
			{
				Magick magick = ModMagicks.getMagickFromName(
			    		ModMagicks.GetMagickStringByID(MagickId));
				doAttribute = magick.doMagickNeedAtrribute();
			}
			
			this.fontRenderer.drawStringWithShadow(spell_name, this.x, this.y, 0xFFF5EE);
			if(Magick_Power != 0)
				this.fontRenderer.drawStringWithShadow(spell_power + ": " + Magick_Power, this.x, this.y + 8, 0xFFF5EE);
			if(doAttribute && Magick_Attribute!=0)
			this.fontRenderer.drawStringWithShadow(spell_attribute + ": " + Magick_Attribute, this.x, this.y + 16, 0xFFF5EE);
		}
	}
}
