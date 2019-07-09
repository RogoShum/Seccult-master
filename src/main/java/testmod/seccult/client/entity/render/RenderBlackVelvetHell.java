package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import testmod.seccult.ClientProxy;
import testmod.seccult.entity.EntityBlackVelvetHell;

public class RenderBlackVelvetHell extends Render<EntityBlackVelvetHell>{

	private static ResourceLocation darkTexture = new ResourceLocation("seccult:textures/entity/blackvelvethell.png");
	private static ResourceLocation darkPTexture = new ResourceLocation("seccult:textures/entity/purpleblackvelvethell.png");
	
	public RenderBlackVelvetHell(RenderManager manager) {
        super(manager);
        this.shadowSize = 0;
	}

	@Override
	public void doRender(EntityBlackVelvetHell entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(x, y + entity.height / 2, z);
	    double size = entity.ticksExisted;
	    if(size > entity.getMySize())
	    	size = entity.getMySize();
	    GlStateManager.scale(size, size, size);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
        GlStateManager.enableNormalize();
        GlStateManager.depthMask(false);
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_COLOR);
			Minecraft.getMinecraft().getTextureManager().bindTexture(darkTexture);
	    GlStateManager.callList(ClientProxy.sphereIdOutside);
	    GlStateManager.callList(ClientProxy.sphereIdInside);
	    GlStateManager.depthMask(true);
        GlStateManager.disableNormalize();
	    GlStateManager.popMatrix();
	    if(entity.getPrisoner() > 1) {
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(x, y + entity.height / 2, z);
	    double sizee = size + 0.5;
	    GlStateManager.scale(sizee, sizee, sizee);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
        GlStateManager.enableNormalize();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_COLOR);
			Minecraft.getMinecraft().getTextureManager().bindTexture(darkPTexture);
	    GlStateManager.callList(ClientProxy.sphereIdOutside);
	    GlStateManager.callList(ClientProxy.sphereIdInside);
	    GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
	    GlStateManager.popMatrix();
	    }
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityBlackVelvetHell entity) {
		return null;
	}
}
