package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.ClientProxy;
import testmod.seccult.entity.EntityProtectionShieldFX;

@SideOnly(Side.CLIENT)
public class RenderProtectionShieldFX extends Render<EntityProtectionShieldFX>
{   
	private static ResourceLocation shield_1 = new ResourceLocation("seccult:textures/entity/shield_1.png");
	private static ResourceLocation shield_2 = new ResourceLocation("seccult:textures/entity/shield_2.png");
	private static ResourceLocation shield_3 = new ResourceLocation("seccult:textures/entity/shield_3.png");
	private static ResourceLocation shield_4 = new ResourceLocation("seccult:textures/entity/shield_4.png");
	private static ResourceLocation shield_5 = new ResourceLocation("seccult:textures/entity/shield_5.png");
	private static ResourceLocation shield_6 = new ResourceLocation("seccult:textures/entity/shield_6.png");
	
	private static TextureManager texM = Minecraft.getMinecraft().getTextureManager();
	
    public RenderProtectionShieldFX(RenderManager manager) {
        super(manager);
        this.shadowSize = 0;
    }
    
    public void doRender(EntityProtectionShieldFX entity, double x, double y, double z, float entityYaw, float partialTicks) {
    	super.doRender(entity, x, y, z, entityYaw, partialTicks);
    	 int i = 15728880;
         int j = i % 65536;
         int k = i / 65536;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
         GlStateManager.pushMatrix();
         float scale = entity.getScale();
         GlStateManager.translate(x + entity.getLookVec().x * scale / 2, y + entity.height / 2, z + entity.getLookVec().z * scale / 2);
         scale = scale * 2;
         float blend = entity.getBlend();
         GlStateManager.rotate(-entityYaw  * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
 	    GlStateManager.rotate(-(-entity.rotationPitch + 90F) * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
         ResourceLocation tex = null;
     	if(blend>=0.1F && blend < 0.2F)
    		tex=shield_1;
    	if(blend>=0.2F && blend < 0.3F)
    		tex=shield_2;
    	if(blend>=0.3F && blend < 0.4F)
    		tex=shield_3;
    	if(blend>=0.4F && blend < 0.5F)
    		tex=shield_4;
    	if(blend>=0.5F && blend < 0.6F)
    		tex=shield_5;
    	if(blend>=0.6F)
    		tex=shield_6;
         
         renderShield(scale, blend, tex);
         GlStateManager.popMatrix();
    }
    
    @SideOnly(Side.CLIENT)
    public static void renderShield(float scale, float blend, ResourceLocation res)
    {
    	GlStateManager.enableBlend();
    	GlStateManager.depthMask(false);
    	GlStateManager.enableNormalize();
    	
    	if(res != null)
    	texM.bindTexture(res);
    	GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_COLOR);
    	GlStateManager.color(0.4F, 0.6F, 1F, blend * 2);
    	GlStateManager.scale(scale, scale, scale);
    	ClientProxy.callSphere();
    	GlStateManager.disableNormalize();
    	GlStateManager.depthMask(true);
    	GlStateManager.disableBlend();
    }
    
	@Override
	protected ResourceLocation getEntityTexture(EntityProtectionShieldFX entity) {
		ResourceLocation tex = null;

		return tex;
	}
}