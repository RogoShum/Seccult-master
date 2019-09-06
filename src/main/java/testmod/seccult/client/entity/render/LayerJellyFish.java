package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelJellyFish;
import testmod.seccult.entity.livings.water.EntityJellyfish;

@SideOnly(Side.CLIENT)
public class LayerJellyFish implements LayerRenderer<EntityJellyfish>
{
    private final ModelBase lightModel = new ModelJellyFish();
    private static final ResourceLocation TEXTURESD = new ResourceLocation(Seccult.MODID + ":textures/entity/jellyfish_normal.png");

    public LayerJellyFish(RenderJellyFish slimeRendererIn)
    {
    }

    public void doRenderLayer(EntityJellyfish entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!entity.isInvisible())
        {
        	float blend = entity.getSwing() / 90F;
        	if(blend < 0.12F)
        		blend = 0.12F;
            GlStateManager.color(1.0F, 1.0F, 1.0F, blend);
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURESD);
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
            this.lightModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}