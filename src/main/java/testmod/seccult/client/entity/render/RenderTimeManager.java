package testmod.seccult.client.entity.render;

import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import testmod.seccult.ClientProxy;
import testmod.seccult.Seccult;
import testmod.seccult.entity.EntityTimeManager;

public class RenderTimeManager extends Render<EntityTimeManager>{

	private static ResourceLocation darkTexture = new ResourceLocation("seccult:textures/entity/darktexture.png");
	private int lifeTiming;
	public RenderTimeManager(RenderManager manager) {
        super(manager);
        this.shadowSize = 0;
	}

	@Override
	public void doRender(EntityTimeManager entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(x, y + entity.height / 2, z);
	    double size;
	    if(entity.getRenderSkin() == 1) 
	    {
	    	this.lifeTiming--;
	    	size = this.lifeTiming;
	    	if(size < 0)
	    		size = 0;
	    }
	    else 
	    {
	    	this.lifeTiming = 160;
	    	size = entity.ticksExisted * 12;
	    if (size > 160.0D) 
	    {
	        size = 160.0D;
	    }
	    }
	    GlStateManager.scale(size, size, size);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
        int i = 15728880;
        int j = i % 65536;
        int k = i / 65536;
        
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        GlStateManager.enableNormalize();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			Minecraft.getMinecraft().getTextureManager().bindTexture(darkTexture);
	    GlStateManager.callList(ClientProxy.sphereIdOutside);
	    GlStateManager.callList(ClientProxy.sphereIdInside);
	    GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
	    GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTimeManager entity) {
		// TODO Auto-generated method stub
		return null;
	}
}
