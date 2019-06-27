package testmod.seccult.client.entity.render;

import org.lwjgl.opengl.GL11;

import com.jcraft.jorbis.DspState;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import testmod.seccult.ClientProxy;
import testmod.seccult.Seccult;
import testmod.seccult.entity.EntityTimeManager;

public class RenderTimeManager extends Render<EntityTimeManager>{

	private static ResourceLocation darkTexture = new ResourceLocation("seccult:textures/entity/blackvelvethell.png");
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
        int i = 15728880;
        int j = i % 65536;
        int k = i / 65536;
        
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        GlStateManager.enableBlend();
        GlStateManager.color(1, 1, 1, 0.1001F);
        GlStateManager.blendFunc(SourceFactor.ONE_MINUS_DST_COLOR, DestFactor.ONE_MINUS_DST_COLOR);
		Minecraft.getMinecraft().getTextureManager().bindTexture(darkTexture);
	    GlStateManager.callList(ClientProxy.sphereIdOutside);
	    GlStateManager.callList(ClientProxy.sphereIdInside);
	    GlStateManager.disableBlend();
	    GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityTimeManager entity) {
		// TODO Auto-generated method stub
		return null;
	}
}
