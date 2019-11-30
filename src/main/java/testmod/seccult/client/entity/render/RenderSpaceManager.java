package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelSpaceManager;
import testmod.seccult.entity.livings.landCreature.EntitySpaceManager;

@SideOnly(Side.CLIENT)
public class RenderSpaceManager extends RenderLiving<EntitySpaceManager>
{
	private static final ResourceLocation TEXTURESD = new ResourceLocation(Seccult.MODID + ":textures/entity/space_manager.png");
	
	public RenderSpaceManager(RenderManager renderManager) 
	{
		super(renderManager, new ModelSpaceManager(), 0.0F);
	}

	@Override
	protected void preRenderCallback(EntitySpaceManager entitylivingbaseIn, float partialTickTime) {
		super.preRenderCallback(entitylivingbaseIn, partialTickTime);
		GlStateManager.translate(0, entitylivingbaseIn.height / 5, 0);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntitySpaceManager entity)
	{
		return TEXTURESD;
	}
}
