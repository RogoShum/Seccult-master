package testmod.seccult.client.entity.render;
 
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelFish;
import testmod.seccult.entity.livings.water.EntityFish;

@SideOnly(Side.CLIENT)
public class RenderFish extends RenderLiving<EntityFish>
{
	private static final ResourceLocation TEXTURESD = new ResourceLocation(Seccult.MODID + ":textures/entity/fish_normal.png");
	
	public RenderFish(RenderManager renderManager) 
	{
		super(renderManager, new ModelFish(), 0.3F);
	}
	
	@Override
	public void doRender(EntityFish entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityFish entity)
	{
		return TEXTURESD;
	}
}
