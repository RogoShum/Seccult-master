package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelLightingThing;
import testmod.seccult.entity.EntityLightingThing;

@SideOnly(Side.CLIENT)
public class RenderLightingThing extends Render<EntityLightingThing>
{
	private static final ResourceLocation TEXTURESD = new ResourceLocation(Seccult.MODID + ":textures/entity/lightingthing.png");
	private static final ResourceLocation GLOW = new ResourceLocation(Seccult.MODID + ":textures/entity/lightingthing_glow.png");
	private ModelBase model = new ModelLightingThing();
	private Minecraft mc = Minecraft.getMinecraft();
	
	public RenderLightingThing(RenderManager renderManager) 
	{
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityLightingThing entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y - 2.0, (float) z);
		mc.getTextureManager().bindTexture(TEXTURESD);
		int i = 15728880;
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
		GlStateManager.enableNormalize();
		this.model.render(entity, 0, 0, partialTicks, entityYaw, entity.rotationPitch, 0.1F);
		GlStateManager.disableNormalize();
		GlStateManager.popMatrix();
		
		GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y - 2.0, (float) z);
		mc.getTextureManager().bindTexture(GLOW);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
		GlStateManager.enableNormalize();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_COLOR);
		this.model.render(entity, 0, 0, partialTicks, entityYaw, entity.rotationPitch, 0.1F);
		GlStateManager.disableBlend();
		GlStateManager.disableNormalize();
		GlStateManager.popMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityLightingThing entity)
	{
		return null;
	}
}
