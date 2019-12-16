package testmod.seccult.client.entity.render;

import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ParticleRender {
	public final static float[] defaultColor = {1F, 1F, 1F};
	
	public static void renderFX(TextureAtlasSprite textureatlassprite, double x, double y, double z, float size, float blend, @Nullable float[] color)
	{
		int cc = 15728880;
        int j = cc % 65536;
        int k = cc / 65536;
        
        Entity entity = Minecraft.getMinecraft().player;
        
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
        
		x -= entity.posX;
		y -= entity.posY;
		z -= entity.posZ;
		
		if(color == null)
		color = defaultColor;
		GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.enableRescaleNormal();
        GlStateManager.enableBlend();
        GlStateManager.scale(size, size, size);
        GlStateManager.color(color[0], color[1], color[2], blend);
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        RenderManager render = Minecraft.getMinecraft().getRenderManager();
        float f = textureatlassprite.getMinU();
        float f1 = textureatlassprite.getMaxU();
        float f2 = textureatlassprite.getMinV();
        float f3 = textureatlassprite.getMaxV();
        GlStateManager.rotate(180.0F - render.playerViewY, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate((float)(render.options.thirdPersonView == 2 ? -1 : 1) * -render.playerViewX, 1.0F, 0.0F, 0.0F);
        
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_TEX_NORMAL);
        bufferbuilder.pos(-0.5D, -0.25D, 0.0D).tex((double)f, (double)f3).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.5D, -0.25D, 0.0D).tex((double)f1, (double)f3).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(0.5D, 0.75D, 0.0D).tex((double)f1, (double)f2).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(-0.5D, 0.75D, 0.0D).tex((double)f, (double)f2).normal(0.0F, 1.0F, 0.0F).endVertex();
        tessellator.draw();
        GlStateManager.disableBlend();
        GlStateManager.disableRescaleNormal();
        GlStateManager.popMatrix();
	}
}
