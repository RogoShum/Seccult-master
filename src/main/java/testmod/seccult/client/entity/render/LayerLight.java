package testmod.seccult.client.entity.render;

import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.client.entity.model.ModelLight;
import testmod.seccult.entity.livings.EntityLight;

@SideOnly(Side.CLIENT)
public class LayerLight implements LayerRenderer<EntityLight>
{
    private final RenderLight LightRenderer;
    private final ModelBase lightModel = new ModelLight(1);

    public LayerLight(RenderLight slimeRendererIn)
    {
        this.LightRenderer = slimeRendererIn;
    }

    public void doRenderLayer(EntityLight entitylivingbaseIn, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!entitylivingbaseIn.isInvisible())
        {
            GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
            GlStateManager.enableNormalize();
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
            this.lightModel.setModelAttributes(this.LightRenderer.getMainModel());
            this.lightModel.render(entitylivingbaseIn, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
            GlStateManager.disableBlend();
            GlStateManager.disableNormalize();
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}