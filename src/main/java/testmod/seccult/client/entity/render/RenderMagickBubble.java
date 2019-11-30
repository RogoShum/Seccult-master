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
import testmod.seccult.client.entity.model.ModelBubble;
import testmod.seccult.entity.projectile.EntityMagickBubble;

@SideOnly(Side.CLIENT)
public class RenderMagickBubble extends Render<EntityMagickBubble>
{   
	private static final ResourceLocation GLOW = new ResourceLocation(Seccult.MODID + ":textures/entity/bubble.png");
	private ModelBase model = new ModelBubble();
	private Minecraft mc = Minecraft.getMinecraft();
    public RenderMagickBubble(RenderManager manager) {
        super(manager);
        this.shadowSize = 0;
    }
    
    public void doRender(EntityMagickBubble par1Entity, double x, double y, double z, float entityYaw, float partialTicks) {
    	super.doRender(par1Entity, x, y, z, entityYaw, partialTicks);
    	GlStateManager.pushMatrix();
		GlStateManager.translate((float) x, (float) y - 1.85, (float) z);
		mc.getTextureManager().bindTexture(GLOW);
		int i = 15728880;
		int j = i % 65536;
		int k = i / 65536;
		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
		GlStateManager.enableNormalize();
		this.model.render(par1Entity, 0, 0, partialTicks, entityYaw, par1Entity.rotationPitch, 0.1F);
		GlStateManager.disableNormalize();
		GlStateManager.popMatrix();
    }
    
	@Override
    protected ResourceLocation getEntityTexture(EntityMagickBubble par1Entity) {
        return null;
    }
}