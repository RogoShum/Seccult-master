package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelJellyFish;
import testmod.seccult.entity.livings.insect.EntityButterfly;
import testmod.seccult.entity.livings.water.EntityJellyfish;

@SideOnly(Side.CLIENT)
public class RenderJellyFish extends RenderLiving<EntityJellyfish>
{
	public RenderJellyFish(RenderManager renderManager) 
	{
		super(renderManager, new ModelJellyFish(), 0.3F);
		this.addLayer(new LayerJellyFish(this));
	}
	
	@Override
	public void doRender(EntityJellyfish entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	}
	
	@Override
	protected void preRenderCallback(EntityJellyfish e, float partialTickTime) {
		super.preRenderCallback(e, partialTickTime);
		float size = e.width;
		GlStateManager.scale(size * 2, size * 2, size * 2);
		this.shadowSize = size / 2;
	}
	
    protected void applyRotations(EntityJellyfish entityLiving, float p_77043_2_, float rotationYaw, float partialTicks)
    {
        float f = entityLiving.prevSquidPitch + (entityLiving.squidPitch - entityLiving.prevSquidPitch) * partialTicks;
        float f1 = entityLiving.prevSquidYaw + (entityLiving.squidYaw - entityLiving.prevSquidYaw) * partialTicks;
        GlStateManager.translate(0.0F, 0.1F, 0.0F);
        GlStateManager.rotate(180.0F - rotationYaw, 0.0F, 1.0F, 0.0F);
        GlStateManager.rotate(f, 1.0F, 0.0F, 0.0F);
        GlStateManager.rotate(f1, 0.0F, 1.0F, 0.0F);
        
        GlStateManager.translate(0.0F, -0.2F, 0.0F);
    }

    protected float handleRotationFloat(EntityJellyfish livingBase, float partialTicks)
    {
        return livingBase.lastTentacleAngle + (livingBase.tentacleAngle - livingBase.lastTentacleAngle) * partialTicks;
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityJellyfish entity)
	{
		return null;
	}
}
