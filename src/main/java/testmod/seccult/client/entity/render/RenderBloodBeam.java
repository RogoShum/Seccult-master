package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.ClientProxy;
import testmod.seccult.client.FX.LightFX;
import testmod.seccult.entity.EntityBloodBeam;

@SideOnly(Side.CLIENT)
public class RenderBloodBeam extends Render<EntityBloodBeam>
{
	private static ResourceLocation darkPTexture = new ResourceLocation("seccult:textures/entity/darktexture.png");
	public RenderBloodBeam(RenderManager renderManager) 
	{
		super(renderManager);
		this.shadowSize = 0F;
	}
	
	@Override
	public void doRender(EntityBloodBeam entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		renderCycle(x, y + entity.height / 2, z, 0.2F);
			LightFX fx = new LightFX(entity.world, entity.posX, entity.posY, entity.posZ, 0, 0, 0, 1);
			fx.setRBGColorF(1, 0, 0);
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);
	}
	
	public void renderCycle(double x, double y, double z, float scale)
	{
		GlStateManager.pushMatrix();
	    GlStateManager.translate(x, y, z);
        GlStateManager.enableBlend();
    	GlStateManager.depthMask(false);
    	GlStateManager.enableNormalize();
    	Minecraft.getMinecraft().getTextureManager().bindTexture(darkPTexture);
    	GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_COLOR);
    	GlStateManager.color(1.0F, 0.0F, 0.0F, 1F);
    	 GlStateManager.scale(scale, scale, scale);
    	ClientProxy.callSphere();
    	GlStateManager.disableNormalize();
    	GlStateManager.depthMask(true);
    	GlStateManager.disableBlend();
    	GlStateManager.popMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityBloodBeam entity)
	{
		return null;
	}
}
