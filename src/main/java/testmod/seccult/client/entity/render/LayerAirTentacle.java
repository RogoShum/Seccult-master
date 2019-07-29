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
import testmod.seccult.entity.livings.flying.EntityAirTentacle;

@SideOnly(Side.CLIENT)
public class LayerAirTentacle implements LayerRenderer<EntityAirTentacle>
{
    private final ModelBase lightModel = new ModelAirTentacle();
    private static final ResourceLocation TEXTURESD = new ResourceLocation(Seccult.MODID + ":textures/entity/airtentacle_glow.png");

    public LayerAirTentacle(RenderAirTentacle slimeRendererIn)
    {
    }

    public void doRenderLayer(EntityAirTentacle entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
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