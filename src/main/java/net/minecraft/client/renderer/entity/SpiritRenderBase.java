package net.minecraft.client.renderer.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.client.FX.LightFX;
import testmod.seccult.entity.livings.EntitySpirit;
import testmod.seccult.potions.PotionAllSeeEye;

@SideOnly(Side.CLIENT)
public class SpiritRenderBase extends RenderLivingBase<EntityLivingBase>
{
	private RenderLivingBase<EntityLivingBase> renderer;
	private ModelBase model;
	private String tex;
	private static ResourceLocation darkPTexture = new ResourceLocation("seccult:textures/entity/darktexture.png");
	
	public SpiritRenderBase(RenderManager renderManager) 
	{
		super(renderManager, new ModelSlime(0), 0);
	}
	
	@Override
	public void doRender(EntityLivingBase entity, double x, double y, double z, float entityYaw, float partialTicks) {
		EntityPlayer player = Minecraft.getMinecraft().player;

		if(player == null || !PotionAllSeeEye.hasAllSee(player))
		{
			entity.width = 0;
			entity.height = 0;
			return;
		}
		
		EntitySpirit spirit = (EntitySpirit) entity;
		EntityLivingBase Ospirit = spirit.getEntity();
	    if (Ospirit != null) {
	    	Ospirit.setPositionAndRotation(x, y, z, entityYaw, entity.rotationPitch);
    		Ospirit.motionX = entity.motionX;
    		Ospirit.motionY = entity.motionY;
    		Ospirit.motionZ = entity.motionZ;

        	if(Ospirit instanceof EntityLivingBase) 
        	{
        		EntityLivingBase living = (EntityLivingBase) Ospirit;
        		living.setRenderYawOffset(entity.renderYawOffset);
        		living.setRotationYawHead(entity.rotationYawHead);
        		living.limbSwing = entity.limbSwing;
        		living.limbSwingAmount = entity.limbSwingAmount;
        	}
			
        	Render<Entity> render = this.renderManager.getEntityRenderObject(Ospirit);
        	if(render instanceof RenderLivingBase)
        	{
        		spirit.setIsDayTime(false);
        		@SuppressWarnings({ "unchecked", "rawtypes" })
        		RenderLivingBase<EntityLivingBase> livingRenderer = (RenderLivingBase) render;
	    		if(livingRenderer != this.renderer)
	    		{
	    				this.renderer = livingRenderer;
	    				this.tex = RendererHelper.getTexture(this.renderer, Ospirit);
	        	    	
	        	    	this.model = this.renderer.getMainModel();
	    		}
	    		
	    		super.doRender(entity, x, y, z, entityYaw, partialTicks);
	    		
	    	    if(this.renderer != null && this.model != null && this.tex != null)
	    	    {
	    	    	GlStateManager.pushMatrix();
	    	    	GlStateManager.depthMask(false);
	    	    	this.setBlurLayer();
	    	        GlStateManager.enableBlend();
	    	        GlStateManager.enableAlpha();
	    	        GlStateManager.blendFunc(SourceFactor.ONE_MINUS_SRC_COLOR, DestFactor.ONE_MINUS_SRC_COLOR);
	    			int i = 15728880;
	    			int j = i % 65536;
	    			int k = i / 65536;
	    			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
	    	    	this.renderer.doRender(Ospirit, x, y, z, Ospirit.rotationYaw, 1);
	    	    	GlStateManager.disableAlpha();
	    	    	GlStateManager.disableBlend();
	    	    	GlStateManager.depthMask(true);
	    	    	GlStateManager.popMatrix();
	    	    }
	    	    else
	    	    {
	    	    	
	    	    	GlStateManager.pushMatrix();
	    	    	GlStateManager.depthMask(false);
	    	    	this.setBlurLayer();
	    	        GlStateManager.enableBlend();
	    	        GlStateManager.enableAlpha();
	    	        GlStateManager.blendFunc(SourceFactor.ONE_MINUS_SRC_COLOR, DestFactor.ONE_MINUS_SRC_COLOR);
	    			int i = 15728880;
	    			int j = i % 65536;
	    			int k = i / 65536;
	    			OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
	    	    	GlStateManager.disableAlpha();
	    	    	GlStateManager.disableBlend();
	    	    	GlStateManager.depthMask(true);
	    	    	GlStateManager.popMatrix();
	    	    	
	    	    	LightFX par = new LightFX(entity.world, entity.posX, entity.posY + entity.height / 2, entity.posZ, 0, 0, 0, 2F);
        			par.setRBGColorF(0.5F, 0.7F, 1);
        			Minecraft.getMinecraft().effectRenderer.addEffect(par);
	    	    }
        	}
        	else
        	{
        		spirit.setIsDayTime(true);
        	}
	    }
	}
	
    protected void setBlurLayer()
    {
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_ALPHA, 770);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
        GlStateManager.color(0.2F, 0.25F, 1.0F, 1.0F);
        GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
        GlStateManager.disableTexture2D();
        GlStateManager.bindTexture(0);
        GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
	
	@Override
	protected boolean canRenderName(EntityLivingBase entity) {
		return false;
	}
	
	@Override
	protected void renderModel(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float ageInTicks, float netHeadYaw, float headPitch, float scaleFactor) {
        boolean flag = this.isVisible(entitylivingbaseIn);
        boolean flag1 = !flag && !entitylivingbaseIn.isInvisibleToPlayer(Minecraft.getMinecraft().player);
        
        if (flag || flag1)
        {
            if (flag1)
            {
                GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }
            
    	    /*if(this.renderer != null)
    	    {
    	    	EntitySpirit spirit = (EntitySpirit) entitylivingbaseIn;
    			EntityLivingBase Ospirit = spirit.getEntity();
    		    
    	    	GlStateManager.pushMatrix();
    	    	Minecraft mc = Minecraft.getMinecraft();
    	    	GlStateManager.color(0.2F, 0.25F, 1F, 0.75f);
    	        GlStateManager.enableBlend();
    	        GlStateManager.enableAlpha();
    	        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
    	    	if(Ospirit != null)
    	    	{
    	    		if(this.tex != null)
        	    	mc.getTextureManager().bindTexture(tex);
    	    		this.model.render(Ospirit, limbSwing, limbSwingAmount, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    	    		
    	            for (LayerRenderer<EntityLivingBase> layerrenderer : this.renderer.layerRenderers)
    	            {
    	    			GlStateManager.pushMatrix();
    	                layerrenderer.doRenderLayer(Ospirit, limbSwing, limbSwingAmount, 1, ageInTicks, netHeadYaw, headPitch, scaleFactor);
    	                GlStateManager.enableBlend();
    	    	        GlStateManager.enableAlpha();
    	                setBlurLayer();
    	                GlStateManager.color(0.2F, 0.25F, 1F, 0.75f);
    	                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
    	                GlStateManager.disableAlpha();
    	    	    	GlStateManager.disableBlend();
    	    	    	GlStateManager.popMatrix();
    	            }
    	    	}
    	    	GlStateManager.disableAlpha();
    	    	GlStateManager.disableBlend();
    	    	GlStateManager.popMatrix();
    	    }*/

            if (flag1)
            {
                GlStateManager.disableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            }
        }
	}

	@Override
    protected void unsetBrightness()
    {
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
        GlStateManager.enableTexture2D();
        GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, OpenGlHelper.defaultTexUnit);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PRIMARY_COLOR);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, OpenGlHelper.defaultTexUnit);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_ALPHA, OpenGlHelper.GL_PRIMARY_COLOR);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_ALPHA, 770);
        GlStateManager.setActiveTexture(OpenGlHelper.lightmapTexUnit);
        GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
        GlStateManager.color(0.2F, 0.25F, 1.0F, 1.0F);
        GlStateManager.setActiveTexture(OpenGlHelper.GL_TEXTURE2);
        GlStateManager.disableTexture2D();
        GlStateManager.bindTexture(0);
        GlStateManager.glTexEnvi(8960, 8704, OpenGlHelper.GL_COMBINE);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_RGB, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND1_RGB, 768);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_RGB, 5890);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE1_RGB, OpenGlHelper.GL_PREVIOUS);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_COMBINE_ALPHA, 8448);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_OPERAND0_ALPHA, 770);
        GlStateManager.glTexEnvi(8960, OpenGlHelper.GL_SOURCE0_ALPHA, 5890);
        GlStateManager.setActiveTexture(OpenGlHelper.defaultTexUnit);
    }
	
	@Override
	protected void renderLayers(EntityLivingBase entitylivingbaseIn, float limbSwing, float limbSwingAmount,
			float partialTicks, float ageInTicks, float netHeadYaw, float headPitch, float scaleIn) 
	{
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityLivingBase entity)
	{
		if(this.model == null || this.tex == null)
		{
			return darkPTexture;
		}
		return null;
	}
}
