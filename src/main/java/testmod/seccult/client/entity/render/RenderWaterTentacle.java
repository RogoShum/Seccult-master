package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelAirTentacle;
import testmod.seccult.entity.livings.water.EntityWaterTentacle;

@SideOnly(Side.CLIENT)
public class RenderWaterTentacle extends RenderLiving<EntityWaterTentacle>
{
    private static final ResourceLocation TEXTURESD = new ResourceLocation(Seccult.MODID + ":textures/entity/airtentacle.png");
	public RenderWaterTentacle(RenderManager renderManager) 
	{
		super(renderManager, new ModelAirTentacle(), 0.3F);
		this.addLayer(new LayerWaterTentacle(this));
	}
	
	@Override
	public void doRender(EntityWaterTentacle entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override
	protected void preRenderCallback(EntityWaterTentacle e, float partialTickTime) {
		super.preRenderCallback(e, partialTickTime);
		if(e.getIsSleeping())
			GlStateManager.translate(0, 5F, 0);
		else
			GlStateManager.translate(0, 5.5F, 0);
		//GlStateManager.translate(e.LookX() * 10, 0, e.LookZ() * 10);
		//GlStateManager.translate(-e.LookX() * 4, 0, -e.LookZ() * 4);
		//GlStateManager.rotate(e.rotationPitch - 90, 1, 0, 0);
		float size = e.width;
		this.shadowSize = size / 2;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityWaterTentacle entity)
	{
		return TEXTURESD;
	}
}
