package testmod.seccult.client.entity.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Sphere;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.ClientProxy;
import testmod.seccult.Seccult;
import testmod.seccult.entity.projectile.EntityAlterSpace;
import testmod.seccult.entity.projectile.EntityAlterSpace.AlterType;
import testmod.seccult.entity.projectile.EntitySpaceGatorix;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = Seccult.MODID)
public class RenderAlterSpace extends Render<EntityAlterSpace>
{   
	private static final ResourceLocation END_PORTAL_TEXTURE = new ResourceLocation("textures/entity/end_portal.png");
	private static final ResourceLocation END_SKY_TEXTURE = new ResourceLocation("textures/environment/end_sky.png");
	private static final ResourceLocation darkTexture = new ResourceLocation("seccult:textures/entity/darktexture.png");
	
    public RenderAlterSpace(RenderManager manager) {
        super(manager);
        this.shadowSize = 0;
    }
    
    public void doRender(EntityAlterSpace entity, double x, double y, double z, float entityYaw, float partialTicks) {

    	float size =  entity.height / 2;
    	float blend = 1.0F;

    	int i = 15728880;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
    	
        GlStateManager.pushMatrix();
        {
        	if(entity.getState() == AlterType.Void.getVaule())
        	{
        		entity.world.spawnParticle(EnumParticleTypes.SUSPENDED_DEPTH, entity.posX, entity.posY + 0.3, entity.posZ, 0, 0, 0);
    			Minecraft.getMinecraft().getTextureManager().bindTexture(END_PORTAL_TEXTURE);
    			GlStateManager.color(1.0F, 1.0F, 1.0F, blend * 0.7F);
        	}
    		
    		if(entity.getState() == AlterType.Barrier.getVaule())
    		{
    			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    			entity.world.spawnParticle(EnumParticleTypes.TOTEM, entity.posX, entity.posY + 0.3, entity.posZ, 0, 0, 0);
    			Minecraft.getMinecraft().getTextureManager().bindTexture(darkTexture);
    			GlStateManager.color(0.0F, 0.6F, 0.0F, blend * 0.5F);
    		}
    		
    		if(entity.getState() == AlterType.TerrainTrans.getVaule())
    		{
    			GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
    			entity.world.spawnParticle(EnumParticleTypes.PORTAL, entity.posX, entity.posY + 0.3, entity.posZ, 0, 0, 0);
    			Minecraft.getMinecraft().getTextureManager().bindTexture(darkTexture);
    			GlStateManager.color(0.65F, 0.1F, 0.6F, blend * 0.5F);
    		}
    		
            GlStateManager.translate(x, y + entity.height / 2, z);
    	    GlStateManager.depthMask(false);
    	    GlStateManager.rotate(entity.ticksExisted * 30, 0, 1, 1);
    	    GlStateManager.enableBlend();
        	
        	GlStateManager.pushMatrix();
        	GlStateManager.scale(size, size, size);
        	Sphere sphere = new Sphere();
        	sphere.draw(1, 4, 2);
        	GlStateManager.popMatrix();
        	
        	GlStateManager.pushMatrix();
        	GlStateManager.rotate(90, 1, 0, 1);
        	GlStateManager.scale(size, size, size);
        	Sphere sphere2 = new Sphere();
        	sphere2.draw(1, 4, 2);
        	GlStateManager.popMatrix();
        	GlStateManager.depthMask(true);
        	GlStateManager.disableBlend();
        }
        GlStateManager.popMatrix();

        //ParticleRender.renderLightFX(this.renderManager, x, y, z, 2F, 0.5F, new float[] {0F, 0.5F, 0.8F});
        
         super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
	@Override
    protected ResourceLocation getEntityTexture(EntityAlterSpace entity) {

        return darkTexture;
    }
}