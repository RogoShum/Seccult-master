package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.ClientProxy;
import testmod.seccult.entity.EntityBloodBeam;

@SideOnly(Side.CLIENT)
public class RenderBloodBeam extends Render<EntityBloodBeam>
{
	private static ResourceLocation bloodbeam = new ResourceLocation("seccult:textures/entity/bloodbeam.png");
	
	public RenderBloodBeam(RenderManager renderManager) 
	{
		super(renderManager);
		this.shadowSize = 0F;
	}
	
	@Override
	public void doRender(EntityBloodBeam entity, double x, double y, double z, float entityYaw, float partialTicks) {
		// TODO Auto-generated method stub
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		  GlStateManager.pushMatrix();
		    GlStateManager.translate(x, y + entity.height / 2, z);
		    GlStateManager.scale(0.07F, 0.07F, 0.07F);
	        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
	        GlStateManager.enableNormalize();
	        GlStateManager.enableBlend();
	        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
				Minecraft.getMinecraft().getTextureManager().bindTexture(bloodbeam);
		    GlStateManager.callList(ClientProxy.sphereIdOutside);
		    GlStateManager.callList(ClientProxy.sphereIdInside);
	        GlStateManager.disableBlend();
	        GlStateManager.disableNormalize();
		    GlStateManager.popMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityBloodBeam entity)
	{
		return null;
	}
}
