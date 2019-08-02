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
import testmod.seccult.client.entity.model.ModelBoneShark;
import testmod.seccult.entity.livings.water.EntityBoneShark;

@SideOnly(Side.CLIENT)
public class LayerBoneShark implements LayerRenderer<EntityBoneShark>
{
    private final ModelBase lightModel = new ModelBoneShark();
    private static final ResourceLocation BLUEEYE = new ResourceLocation(Seccult.MODID + ":textures/entity/boneshark_blueeye.png");
    private static final ResourceLocation REDEYE = new ResourceLocation(Seccult.MODID + ":textures/entity/boneshark_redeye.png");

    public LayerBoneShark()
    {
    }

    public void doRenderLayer(EntityBoneShark entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!entity.isInvisible())
        {
        	if(entity.getIsSleeping())
        	{
        		int i = 15728880;
        		int j = i % 65536;
        		int k = i / 65536;
        		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        		GlStateManager.enableNormalize();
        		GlStateManager.enableBlend();
        		Minecraft.getMinecraft().getTextureManager().bindTexture(REDEYE);
        		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_COLOR);
        		this.lightModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        		GlStateManager.disableBlend();
        		GlStateManager.disableNormalize();
        	}
        	else
        	{
        		GlStateManager.enableNormalize();
        		GlStateManager.enableBlend();
        		Minecraft.getMinecraft().getTextureManager().bindTexture(BLUEEYE);
        		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.DST_COLOR);
        		this.lightModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        		GlStateManager.disableBlend();
        		GlStateManager.disableNormalize();
        	}
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}