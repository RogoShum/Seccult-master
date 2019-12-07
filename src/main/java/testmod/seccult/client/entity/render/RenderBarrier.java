package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelRenderer;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.entity.EntityVoid;

@SideOnly(Side.CLIENT)
public class RenderBarrier extends Render<Entity>
{
	private static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
	private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
	private static ResourceLocation darkTexture = new ResourceLocation("seccult:textures/entity/darktexture.png");
	private Minecraft mc = Minecraft.getMinecraft();
	private ModelBase model = new ModelBase() 
	{
	    ModelRenderer block;

	    public void render(Entity entityIn, float limbSwing, float limbSwingAmount, float ageInTicks, float netHeadYaw, float headPitch, float scale)
	    {
	    	this.block = new ModelRenderer(this, 0, 0);
	        this.block.addBox(-4.0F, 16.0F, -4.0F, 16, 16, 16);
	        this.block.render(scale);
	    }
	};
	
	public RenderBarrier(RenderManager renderManager) 
	{
		super(renderManager);
	}
	
	@Override
	public void doRender(Entity entity, double x, double y, double z, float entityYaw,
			float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);

		int i = 15728880;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
		
		GlStateManager.pushMatrix();
		double offset = entity.width * -0.25;
		GlStateManager.translate(x + offset, y - entity.height - 0.2, z + offset);
		GlStateManager.enableNormalize();
		GlStateManager.depthMask(false);
		GlStateManager.enableBlend();
			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_COLOR);
			GlStateManager.color(0.5F, 1.0F, 0.6F, 0.12F);
			mc.getTextureManager().bindTexture(darkTexture);
		GlStateManager.scale(entity.width, entity.height, entity.width);
		model.render(entity, 0, 0, 0, 0, 0, 0.065F);
		GlStateManager.disableBlend();
		GlStateManager.depthMask(true);
		GlStateManager.disableNormalize();
        GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return null;
	}
}
