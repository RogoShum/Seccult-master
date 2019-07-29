package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelButterFly;
import testmod.seccult.entity.livings.insect.EntityButterfly;

@SideOnly(Side.CLIENT)
public class RenderButterFly extends RenderLiving<EntityButterfly>
{
	private static final ResourceLocation TEXTURESD = new ResourceLocation(Seccult.MODID + ":textures/entity/butterfly_normal.png");
	
	public RenderButterFly(RenderManager renderManager) 
	{
		super(renderManager, new ModelButterFly(), 0.1F);
	}
	
	@Override
	protected void preRenderCallback(EntityButterfly e, float partialTickTime) {
		super.preRenderCallback(e, partialTickTime);
		float size = e.width;
		GlStateManager.scale(size * 2, size * 2, size * 2);
		this.shadowSize = size / 2;
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityButterfly entity)
	{
		return TEXTURESD;
	}
}
