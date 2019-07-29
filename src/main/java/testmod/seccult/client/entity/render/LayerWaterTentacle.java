package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelAirTentacle;
import testmod.seccult.entity.livings.water.EntityWaterTentacle;

@SideOnly(Side.CLIENT)
public class LayerWaterTentacle implements LayerRenderer<EntityWaterTentacle>
{
    private final ModelBase lightModel = new ModelAirTentacle();
    private static final ResourceLocation TEXTURESD = new ResourceLocation(Seccult.MODID + ":textures/entity/airtentacle_glow_blue.png");

    public LayerWaterTentacle(RenderWaterTentacle slimeRendererIn)
    {
    }

    public void doRenderLayer(EntityWaterTentacle entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!entity.isInvisible())
        {
            int i = 15728880;
            int j = i % 65536;
            int k = i / 65536;
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
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