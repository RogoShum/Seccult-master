package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelRockShellLeviathan;
import testmod.seccult.entity.livings.water.EntityRockShellLeviathan;

@SideOnly(Side.CLIENT)
public class RenderRockShell extends RenderLiving<EntityRockShellLeviathan>
{
    private static final ResourceLocation TEXTURESD = new ResourceLocation(Seccult.MODID + ":textures/entity/rockshell.png");
	public RenderRockShell(RenderManager renderManager) 
	{
		super(renderManager, new ModelRockShellLeviathan(), 0.3F);
		this.addLayer(new LayerRockShell());
	}
	
	@Override
	public void doRender(EntityRockShellLeviathan entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		GlStateManager.pushMatrix();
		GlStateManager.rotate(-entityYaw, 0, 1, 0);
		GlStateManager.popMatrix();
	}
	
	@Override
	protected void preRenderCallback(EntityRockShellLeviathan e, float partialTickTime) {
		super.preRenderCallback(e, partialTickTime);
		//GlStateManager.translate(0, 1, 0);
		float size = e.width;
		GlStateManager.scale(size * 0.8, size * 0.8, size * 0.8);
		this.shadowSize = size / 2;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityRockShellLeviathan entity)
	{
		return TEXTURESD;
	}
}
