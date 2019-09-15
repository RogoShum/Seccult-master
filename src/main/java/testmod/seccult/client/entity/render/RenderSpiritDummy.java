package testmod.seccult.client.entity.render;

import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.entity.livings.EntitySpiritDummy;

@SideOnly(Side.CLIENT)
public class RenderSpiritDummy extends RenderLivingBase<EntitySpiritDummy>
{
	private static ResourceLocation darkPTexture = new ResourceLocation("seccult:textures/entity/darktexture.png");
	
	public RenderSpiritDummy(RenderManager renderManager) 
	{
		super(renderManager, new ModelSlime(0), 0);
	}
	
	@Override
	public void doRender(EntitySpiritDummy entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}

	@Override
	protected void renderEntityName(EntitySpiritDummy entityIn, double x, double y, double z, String name,
			double distanceSq) {

	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntitySpiritDummy entity)
	{
		return darkPTexture;
	}
}
