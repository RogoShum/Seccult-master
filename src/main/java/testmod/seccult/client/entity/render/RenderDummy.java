package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.entity.EntityDummy;

@SideOnly(Side.CLIENT)
public class RenderDummy extends Render<EntityDummy>
{
	
	public RenderDummy(RenderManager renderManager) 
	{
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityDummy entity, double x, double y, double z, float entityYaw, float partialTicks) {
		RenderOrigin(entity, x, y, z, entityYaw, partialTicks);
	}
	
	private void RenderOrigin(EntityDummy entity, double x, double y, double z, float entityYaw, float partialTicks) {
	    if (entity.getEntity() != null) {
	        this.renderManager.getEntityRenderObject(entity.getEntity()).doRender(entity.getEntity(), x, y, z, entityYaw, partialTicks);
	    }
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityDummy entity)
	{
		return null;
	}
}
