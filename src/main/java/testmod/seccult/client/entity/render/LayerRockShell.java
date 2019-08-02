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
import testmod.seccult.client.entity.model.ModelRockShellLeviathan;
import testmod.seccult.entity.livings.water.EntityRockShellLeviathan;

@SideOnly(Side.CLIENT)
public class LayerRockShell implements LayerRenderer<EntityRockShellLeviathan>
{
    private final ModelBase lightModel = new ModelRockShellLeviathan();
    private static final ResourceLocation BODY = new ResourceLocation(Seccult.MODID + ":textures/entity/rockshell.png");
    private static final ResourceLocation EYE = new ResourceLocation(Seccult.MODID + ":textures/entity/rockshell_eye.png");
    private static final ResourceLocation SEAWAVE = new ResourceLocation(Seccult.MODID + ":textures/entity/rockshell_seaweed.png");

    public LayerRockShell()
    {
    }

    public void doRenderLayer(EntityRockShellLeviathan entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!entity.isInvisible())
        {
        	GlStateManager.pushMatrix();
    		GlStateManager.enableNormalize();
    		GlStateManager.enableBlend();
    		GlStateManager.translate(0, -entity.height * 0.005, 0);
    		Minecraft.getMinecraft().getTextureManager().bindTexture(SEAWAVE);
    		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    		this.lightModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale * 1.02F);
    		GlStateManager.disableBlend();
    		GlStateManager.disableNormalize();
    		GlStateManager.popMatrix();
    		
        	if(entity.getIsSleeping() || entity.getIsDayTime())
        	{

        		GlStateManager.pushMatrix();
        		int i = 15728880;
        		int j = i % 65536;
        		int k = i / 65536;
        		GlStateManager.color(1, 1, 1, 0.6F);
        		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        		GlStateManager.enableNormalize();
        		GlStateManager.enableBlend();
        		Minecraft.getMinecraft().getTextureManager().bindTexture(EYE);
        		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_COLOR);
        		this.lightModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        		GlStateManager.disableBlend();
        		GlStateManager.disableNormalize();
        		GlStateManager.popMatrix();
        		
        		GlStateManager.pushMatrix();
        		GlStateManager.color(1, 1, 1, 0.2F);
        		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        		GlStateManager.enableNormalize();
        		GlStateManager.enableBlend();
        		Minecraft.getMinecraft().getTextureManager().bindTexture(EYE);
        		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        		this.lightModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        		GlStateManager.disableBlend();
        		GlStateManager.disableNormalize();
        		GlStateManager.popMatrix();
        		
        		GlStateManager.pushMatrix();
        		OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
        		GlStateManager.enableNormalize();
        		GlStateManager.enableBlend();
        		Minecraft.getMinecraft().getTextureManager().bindTexture(BODY);
        		GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        		this.lightModel.render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
        		GlStateManager.disableBlend();
        		GlStateManager.disableNormalize();
        		GlStateManager.popMatrix();
        	}
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}