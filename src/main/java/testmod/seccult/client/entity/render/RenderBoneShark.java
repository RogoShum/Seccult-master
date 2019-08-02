package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelBoneShark;
import testmod.seccult.entity.livings.water.EntityBoneShark;

@SideOnly(Side.CLIENT)
public class RenderBoneShark extends RenderLiving<EntityBoneShark>
{
    private static final ResourceLocation TEXTURESD = new ResourceLocation(Seccult.MODID + ":textures/entity/boneshark.png");
	public RenderBoneShark(RenderManager renderManager) 
	{
		super(renderManager, new ModelBoneShark(), 0.3F);
		this.addLayer(new LayerBoneShark());
	}
	
	@Override
	public void doRender(EntityBoneShark entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override
	protected void preRenderCallback(EntityBoneShark e, float partialTickTime) {
		super.preRenderCallback(e, partialTickTime);
		//GlStateManager.translate(0, 1, 0);
		float size = e.width;
		GlStateManager.scale(size * 1.5F, size * 1.5F, size * 1.5F);
		this.shadowSize = size / 2;
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityBoneShark entity)
	{
		return TEXTURESD;
	}
}
