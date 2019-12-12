package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.model.ModelSlime;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderLivingBase;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.entity.RendererHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.client.FX.ModFX;
import testmod.seccult.client.FX.ParticleFX;
import testmod.seccult.entity.livings.EntitySpiritContainer;

@SideOnly(Side.CLIENT)
public class RenderSpiritContainer extends RenderLivingBase<EntitySpiritContainer>
{
	private RenderLivingBase<EntityLivingBase> renderer;
	private ModelBase model;
	private String tex;
	private static ResourceLocation darkPTexture = new ResourceLocation("seccult:textures/entity/darktexture.png");
	
	public RenderSpiritContainer(RenderManager renderManager) 
	{
		super(renderManager, new ModelSlime(0), 0);
	}
	
	@Override
	public void doRender(EntitySpiritContainer entity, double x, double y, double z, float entityYaw, float partialTicks) {
		EntityLivingBase Ospirit = entity.getBody();
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
	    	    	this.renderer.doRender(Ospirit, x, y, z, Ospirit.rotationYaw, 1);
	    	    	GlStateManager.popMatrix();
	    	    }
	    	    else
	    	    {
	    	    	ParticleFX par = new ParticleFX(ParticleFX.ParticleType.Light, entity.posX, entity.posY + entity.height / 2, entity.posZ, 0, 0, 0, 2F);
        			par.setRBGColorF(0.5F, 0.7F, 1);
        			ModFX.addPar(par);
	    	    }
        	}
	    }
	}

	@Override
	protected void renderEntityName(EntitySpiritContainer entityIn, double x, double y, double z, String name,
			double distanceSq) {

	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntitySpiritContainer entity)
	{
		if(this.model == null || this.tex == null)
		{
			return darkPTexture;
		}
		return null;
	}
}
