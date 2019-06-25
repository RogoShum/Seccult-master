package testmod.seccult.client.gui;

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
	protected void drawGuiContainerBackgroundLayer(float partialTicks, int mouseX, int mouseY) {
		GlStateManager.pushMatrix();
		mc.getTextureManager().bindTexture(TEXTURE);
		//drawDefaultBackground();
    	xSize = 176;
    	ySize = 172;
    	offsetX = (this.width - this.xSize) / 2;
    	offsetY = (this.height - this.ySize) / 2;
		drawTexturedModalRect(offsetX, offsetY, 0, 0, xSize, ySize);
		GlStateManager.popMatrix();
	}

}
