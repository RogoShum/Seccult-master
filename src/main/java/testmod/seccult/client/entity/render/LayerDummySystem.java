package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.layers.LayerRenderer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.entity.livings.EntitySpiritDummy;

@SideOnly(Side.CLIENT)
public class LayerDummySystem implements LayerRenderer<EntitySpiritDummy>
{
    private static final ResourceLocation BE = new ResourceLocation(Seccult.MODID + ":textures/entity/dummy_blueeye.png");
    private static final ResourceLocation WE = new ResourceLocation(Seccult.MODID + ":textures/entity/dummy_whiteeye.png");
    private static final ResourceLocation DE = new ResourceLocation(Seccult.MODID + ":textures/entity/dummy_defaulteye.png");
    private static final ResourceLocation S = new ResourceLocation(Seccult.MODID + ":textures/entity/dummy_soul.png");

    public final RenderDummySystem dummyRendererIn;
    
    public LayerDummySystem(RenderDummySystem dummyRendererIn)
    {
    	this.dummyRendererIn = dummyRendererIn;
    }

    public void doRenderLayer(EntitySpiritDummy entity, float limbSwing, float limbSwingAmount, float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scale)
    {
        if (!entity.isInvisible())
        {
        	int cc = 15728880;
            int j = cc % 65536;
            int k = cc / 65536;
    		GlStateManager.enableLighting();
            OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);

            
            
        	if(entity.hasSoulStone())
        	{
        		GlStateManager.pushMatrix();
            	GlStateManager.color(1.0F, 1.0F, 1.0F, 0.5F);
                GlStateManager.enableNormalize();
                GlStateManager.enableBlend();
                Minecraft.getMinecraft().getTextureManager().bindTexture(S);
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
                this.dummyRendererIn.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                GlStateManager.disableBlend();
                GlStateManager.disableNormalize();
            	GlStateManager.popMatrix();
            	GlStateManager.color(1.0F, 1.0F, 1.0F, 1F);
            	if(entity.getCanFly())
            	{
            		GlStateManager.pushMatrix();
            		GlStateManager.scale(1.01, 1.01, 1.01);
                    Minecraft.getMinecraft().getTextureManager().bindTexture(WE);
                    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
                    this.dummyRendererIn.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                	GlStateManager.popMatrix();
            	}
            	else if(entity.getCanSwim())
            	{
            		GlStateManager.pushMatrix();
            		GlStateManager.scale(1.01, 1.01, 1.01);
                    Minecraft.getMinecraft().getTextureManager().bindTexture(BE);
                    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
                    this.dummyRendererIn.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                 	GlStateManager.popMatrix();
            	}
            	else
            	{ 
            		GlStateManager.pushMatrix();
            		GlStateManager.scale(1.01, 1.01, 1.01);
                    Minecraft.getMinecraft().getTextureManager().bindTexture(DE);
                    GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
                    this.dummyRendererIn.getMainModel().render(entity, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scale);
                	GlStateManager.popMatrix();
            	}
        	}
        }
    }

    public boolean shouldCombineTextures()
    {
        return true;
    }
}