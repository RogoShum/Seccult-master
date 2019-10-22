package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.ClientProxy;
import testmod.seccult.entity.EntityShieldFX;

@SideOnly(Side.CLIENT)
public class RenderShieldFX extends Render<EntityShieldFX>
{   
	private static ResourceLocation darkPTexture = new ResourceLocation("seccult:textures/entity/darktexture.png");
    public RenderShieldFX(RenderManager manager) {
        super(manager);
        this.shadowSize = 0;
    }
    
    public void doRender(EntityShieldFX entity, double x, double y, double z, float entityYaw, float partialTicks) {
    	super.doRender(entity, x, y, z, entityYaw, partialTicks);
    	 int i = 15728880;
         int j = i % 65536;
         int k = i / 65536;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
     
         GlStateManager.pushMatrix();
         GlStateManager.translate(x, y, z);
         float scale = entity.getScale();
         float blend = entity.getBlend();
         renderShield(scale * 2.5F, blend);
         GlStateManager.popMatrix();
    }
    
    @SideOnly(Side.CLIENT)
    public static void renderShield(float scale, float blend)
    {
    	GlStateManager.enableBlend();
    	GlStateManager.depthMask(false);
    	GlStateManager.enableNormalize();
    	Minecraft.getMinecraft().getTextureManager().bindTexture(darkPTexture);
    	GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_COLOR);
    	GlStateManager.color(0.4F, 1F, 0.6F, blend);
    	GlStateManager.scale(scale, scale, scale);
    	ClientProxy.callSphere();
    	GlStateManager.disableNormalize();
    	GlStateManager.depthMask(true);
    	GlStateManager.disableBlend();
    }
    
	@Override
	protected ResourceLocation getEntityTexture(EntityShieldFX entity) {
		return null;
	}
}