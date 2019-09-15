package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.chunk.RenderChunk;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import testmod.seccult.ClientProxy;
import testmod.seccult.client.entity.model.ModelSoC;
import testmod.seccult.entity.livings.EntityEoC;

public class RenderEoC extends RenderLiving<EntityEoC>{
	private static ResourceLocation eoc = new ResourceLocation("seccult:textures/entity/eoc.png");
	private static ResourceLocation eocfury = new ResourceLocation("seccult:textures/entity/eocfury.png");
	private static ResourceLocation eocinside = new ResourceLocation("seccult:textures/entity/eocinside.png");
	public RenderEoC(RenderManager rendermanagerIn) {
		super(rendermanagerIn, new ModelSoC(), 0.0F);
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityEoC entity) {
		return null;
	}
	
	@Override
	public void doRender(EntityEoC entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(x, y + entity.height / 2, z);
	    GlStateManager.scale(7.4F, 7.4F, 7.4F);
	    GlStateManager.rotate(-entityYaw  * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate(-(-entity.rotationPitch + 90F) * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
	    boolean flag1 = entity.hurtTime > 0 || entity.deathTime > 0;
	    
	    if(flag1)
	    	GlStateManager.color(1.0F, 0.75F, 0.75F, 1.0F);
	    else
	    	GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	    
        GlStateManager.enableNormalize();
        //GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		if(entity.getRenderSkin() != 2) {
			Minecraft.getMinecraft().getTextureManager().bindTexture(eoc);
		}
		else
		{
			Minecraft.getMinecraft().getTextureManager().bindTexture(eocfury);
		}
	    GlStateManager.callList(ClientProxy.sphereIdOutside);
	    GlStateManager.callList(ClientProxy.sphereIdInside);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
	    GlStateManager.popMatrix();
	    
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(x, y + entity.height / 2, z);
	    GlStateManager.scale(6F, 6F, 6F);
		GlStateManager.rotate(-entityYaw  * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate(-(-entity.rotationPitch + 90F) * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
			Minecraft.getMinecraft().getTextureManager().bindTexture(eocinside);
	    GlStateManager.callList(ClientProxy.sphereIdOutside);
	    GlStateManager.callList(ClientProxy.sphereIdInside);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
	    GlStateManager.popMatrix();
  	}
}
