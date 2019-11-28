package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import testmod.seccult.ClientProxy;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelBorderCrosser;
import testmod.seccult.client.entity.model.ModelLightingThing;
import testmod.seccult.entity.EntityBorderCrosser;

public class RenderBorderCrosser extends Render<EntityBorderCrosser>{
	private static final ResourceLocation TEX_1 = new ResourceLocation(Seccult.MODID + ":textures/entity/bordercrosser_1.png");
	private static final ResourceLocation TEX_2 = new ResourceLocation(Seccult.MODID + ":textures/entity/bordercrosser_2.png");
	private static final ResourceLocation TEX_3 = new ResourceLocation(Seccult.MODID + ":textures/entity/bordercrosser_3.png");
	private final ModelBase model = new ModelBorderCrosser();
	private final ModelBase model_ = new ModelBorderCrosser();
	private Minecraft mc = Minecraft.getMinecraft();

	public RenderBorderCrosser(RenderManager manager) {
        super(manager);
        this.shadowSize = 0;
	}

	@Override
	public void doRender(EntityBorderCrosser entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);

    	GlStateManager.pushMatrix();
    	GlStateManager.translate(x, y + entity.height / 1.3, z);
    	float size = (float)entity.ticksExisted / 90F;
    	if(size > 1)
    		size = 1F;
    	//GlStateManager.rotate(180, 0, 0, size);
    	size = size * 0.08F;
    	
    	if(entity.getTICK()>=100)
    	{
    		size = (120 - entity.getTICK()) / 20 * 0.08F;
    		GlStateManager.rotate((entity.getTICK() - 100) * 8, 0, 0, 1);
    	}
    	
		if(entity.ticksExisted % 3 == 0)
			mc.getTextureManager().bindTexture(TEX_3);
		else if(entity.ticksExisted % 2 == 0)
			mc.getTextureManager().bindTexture(TEX_2);
		else 
			mc.getTextureManager().bindTexture(TEX_1);
			
		int i = 15728880;
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
		GlStateManager.enableBlend();
    	GlStateManager.depthMask(false);
		GlStateManager.enableNormalize();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_COLOR);
		ModelBase mo_del_ = new ModelBorderCrosser();
		ModelBase mo_d_el_ = new ModelBorderCrosser();
		
		GlStateManager.pushMatrix();
			GlStateManager.color(0.0F, 0.0F, 1.0F, (float)entity.ticksExisted / 100F * 0.5F);
			mo_del_.render(entity, 0, 0, partialTicks, entityYaw, entity.rotationPitch, size);
		GlStateManager.popMatrix();
		
		GlStateManager.pushMatrix();
		GlStateManager.rotate(180, 0, 0, 1);
			GlStateManager.color(1.0F, 0.0F, 1.0F, (float)entity.ticksExisted / 100F * 0.5F);
			mo_d_el_.render(entity, 0, 0, partialTicks, entityYaw, entity.rotationPitch, -size);
		GlStateManager.popMatrix();
		
		//model_.render(entity, 0, 0, partialTicks, entityYaw, entity.rotationPitch, -size);
		GlStateManager.disableNormalize();
		GlStateManager.depthMask(true);
    	GlStateManager.disableBlend();
		GlStateManager.popMatrix();
    	
		
		/*GlStateManager.pushMatrix();
		GlStateManager.translate(x, y + entity.height / 2, z);
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
		GlStateManager.enableNormalize();
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_COLOR);
		model.render(entity, 0, 0, partialTicks, entityYaw, entity.rotationPitch, 0.065F);
		GlStateManager.disableBlend();
		GlStateManager.disableNormalize();
		GlStateManager.popMatrix();*/
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityBorderCrosser entity) {
		return null;
	}
}
