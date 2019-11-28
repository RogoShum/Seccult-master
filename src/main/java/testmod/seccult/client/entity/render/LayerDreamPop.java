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
import testmod.seccult.client.entity.model.ModelDreamPop;
import testmod.seccult.entity.livings.landCreature.EntityDreamPop;
import testmod.seccult.entity.livings.landCreature.EntityNightmarePop;
import testmod.seccult.entity.livings.water.EntityBoneShark;

@SideOnly(Side.CLIENT)
public class LayerDreamPop implements LayerRenderer<EntityDreamPop>
{
    private final ModelBase lightModel = new ModelDreamPop();
	private static final ResourceLocation TEXTURES = new ResourceLocation(Seccult.MODID + ":textures/entity/dream_pop_layer.png");
	private static final ResourceLocation TEXTURES_N = new ResourceLocation(Seccult.MODID + ":textures/entity/night_pop_layer.png");

    public LayerDreamPop()
    {
    }

    public void doRenderLayer(EntityDreamPop entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!entity.isInvisible())
        {
        	if(entity instanceof EntityNightmarePop)
        	{
        		int i = 15728880;
        		int j = i % 65536;
        		int k = i / 65536;
        		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        		GlStateManager.enableNormalize();
        		GlStateManager.enableBlend();
        		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURES_N);
        		GlStateManager.color(1F, 0F, 0F, 0.5F);
        		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_COLOR);
        		this.lightModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        		GlStateManager.disableBlend();
        		GlStateManager.disableNormalize();
        	}
        	else
        	{
        		GlStateManager.enableNormalize();
        		GlStateManager.enableBlend();
        		Minecraft.getMinecraft().getTextureManager().bindTexture(TEXTURES);
        		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_COLOR);
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