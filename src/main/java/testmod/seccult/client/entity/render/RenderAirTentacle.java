package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelAirTentacle;
import testmod.seccult.entity.livings.flying.EntityAirTentacle;

@SideOnly(Side.CLIENT)
public class RenderAirTentacle extends RenderLiving<EntityAirTentacle>
{
    private static final ResourceLocation TEXTURESD = new ResourceLocation(Seccult.MODID + ":textures/entity/airtentacle.png");
	public RenderAirTentacle(RenderManager renderManager) 
	{
		super(renderManager, new ModelAirTentacle(), 0.3F);
		this.addLayer(new LayerAirTentacle(this));
	}
	
	@Override
	public void doRender(EntityAirTentacle entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override
	protected void preRenderCallback(EntityAirTentacle e, float partialTickTime) {
		super.preRenderCallback(e, partialTickTime);
		GlStateManager.translate(0, 1, 0);
		float size = e.width;
		this.shadowSize = size / 2;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityAirTentacle entity)
	{
		return TEXTURESD;
	}
}
