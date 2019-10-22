package testmod.seccult.client.gui;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiContainer;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import testmod.seccult.Seccult;

public class AccessoriesGui extends GuiContainer{
    private static final ResourceLocation TEXTURE = new ResourceLocation(Seccult.MODID + ":" + "textures/gui/gui_accessories.png");
    int xSize, ySize, offsetX, offsetY;
	public AccessoriesGui(AccessoriesContainer inventorySlotsIn) {
		super(inventorySlotsIn);
	}

	@Override
	public void drawScreen(int mouseX, int mouseY, float partialTicks) {
		drawDefaultBackground();
		super.drawScreen(mouseX, mouseY, partialTicks);
		
		/*
		 * GlStateManager.pushMatrix();
		
		GlStateManager.popMatrix();
		 */
		this.renderHoveredToolTip(mouseX, mouseY);
	}

	@Override
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		mc.getTextureManager().bindTexture(TEXTURE);
    	xSize = 176;
    	ySize = 172;
    	offsetX = (this.width - this.xSize) / 2;
    	offsetY = (this.height - this.ySize) / 2;
		drawTexturedModalRect(offsetX, offsetY, 0, 0, xSize, ySize);
	}
}
