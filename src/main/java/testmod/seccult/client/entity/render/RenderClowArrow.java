package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelArrow;
import testmod.seccult.entity.EntityClowCardArrow;

@SideOnly(Side.CLIENT)
public class RenderClowArrow extends Render<EntityClowCardArrow>
{
	public static final ResourceLocation ARROW = new ResourceLocation(Seccult.MODID + ":textures/entity/arrow.png");
	private static ModelBase arrow = new ModelArrow();
	
    public RenderClowArrow(RenderManager renderManagerIn)
    {
        super(renderManagerIn);
    }

    /**
     * Renders the desired {@code T} type Entity.
     */
    public void doRender(EntityClowCardArrow entity, double x, double y, double z, float entityYaw, float partialTicks)
    {
    	entity.height = 1;
    	entity.width = 1;
    	y -= 0.9F;
    	super.doRender(entity, x, y, z, entityYaw, partialTicks);
    	int zz = 15728880;
        int cc = zz % 65536;
        int k = zz / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)cc, (float)k);
        GlStateManager.pushMatrix();
        GlStateManager.translate((float)x, (float)y, (float)z);
        GlStateManager.scale(0.065F, 0.065F, 0.065F);
        GlStateManager.rotate((180-entityYaw) * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate(-entity.rotationPitch * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
        GlStateManager.enableBlend();
        GlStateManager.color(0.3F, 0.3F, 1.0F, 0.75F);
        Minecraft.getMinecraft().getTextureManager().bindTexture(ARROW);
        arrow.render(entity, 0, 0, 0, 0, 0, 1);
        GlStateManager.disableBlend();
        GlStateManager.popMatrix();
        
    }

	@Override
	protected ResourceLocation getEntityTexture(EntityClowCardArrow entity) {
		return null;
	}
}