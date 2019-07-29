package testmod.seccult.client.entity.render;
 
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelBird;
import testmod.seccult.entity.livings.flying.EntityBird;

@SideOnly(Side.CLIENT)
public class RenderBird extends RenderLiving<EntityBird>
{
	private static final ResourceLocation TEXTURESD = new ResourceLocation(Seccult.MODID + ":textures/entity/bird_normal.png");
	
	public RenderBird(RenderManager renderManager) 
	{
		super(renderManager, new ModelBird(), 0.3F);
	}
	
	@Override
	protected void preRenderCallback(EntityBird e, float partialTickTime) {
		super.preRenderCallback(e, partialTickTime);
		float size = e.width;
		GlStateManager.scale(size * 2, size * 2, size * 2);
		this.shadowSize = size / 2;
		//GlStateManager.rotate(e.rotationYaw, 0, 1, 0);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityBird entity)
	{
		return TEXTURESD;
	}
}
