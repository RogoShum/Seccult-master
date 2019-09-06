package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.client.FX.LightFX;
import testmod.seccult.entity.EntityBloodBeam;

@SideOnly(Side.CLIENT)
public class RenderBloodBeam extends Render<EntityBloodBeam>
{
	public RenderBloodBeam(RenderManager renderManager) 
	{
		super(renderManager);
		this.shadowSize = 0F;
	}
	
	@Override
	public void doRender(EntityBloodBeam entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		LightFX fx = new LightFX(entity.world, entity.posX, entity.posY, entity.posZ, 0, 0, 0, 1);
		fx.setRBGColorF(1, 0, 0);
		Minecraft.getMinecraft().effectRenderer.addEffect(fx);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityBloodBeam entity)
	{
		return null;
	}
}
