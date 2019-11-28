package testmod.seccult.client.entity.render;

import org.lwjgl.BufferUtils;
import org.lwjgl.opengl.GL11;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.ClientProxy;
import testmod.seccult.Seccult;
import testmod.seccult.entity.livings.EntitySpaceGatorix;

@SideOnly(Side.CLIENT)
@Mod.EventBusSubscriber(modid = Seccult.MODID)
public class RenderGatorix extends Render<EntitySpaceGatorix>
{   
    public RenderGatorix(RenderManager manager) {
        super(manager);
        this.shadowSize = 0;
    }
    
    public void doRender(EntitySpaceGatorix entity, double x, double y, double z, float entityYaw, float partialTicks) {
    	int quality = 64;
    	
    	if(!RenderGatorixEvent.registerGatorix.containsKey(entity))
        {
            int newTextureId = GL11.glGenTextures();
            GlStateManager.bindTexture(newTextureId);
            GL11.glTexImage2D(GL11.GL_TEXTURE_2D, 0, GL11.GL_RGB, quality, quality, 0, GL11.GL_RGB, GL11.GL_UNSIGNED_BYTE, BufferUtils.createByteBuffer(3 * quality * quality));
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            RenderGatorixEvent.registerGatorix.put(entity, newTextureId);
            return;
        }
    
    	faceEntity(entity, Minecraft.getMinecraft().player, 360, 90);

        GlStateManager.pushMatrix();
        {
            GlStateManager.enableBlend();
            OpenGlHelper.glBlendFunc(770, 771, 1, 0);

            GlStateManager.disableLighting();
            GlStateManager.bindTexture(RenderGatorixEvent.registerGatorix.get(entity));

            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MIN_FILTER, GL11.GL_NEAREST);
            GL11.glTexParameteri(GL11.GL_TEXTURE_2D, GL11.GL_TEXTURE_MAG_FILTER, GL11.GL_NEAREST);

            GlStateManager.enableRescaleNormal();

            GlStateManager.translate(x, y + 0.2, z);
            GlStateManager.rotate((-Minecraft.getMinecraft().player.rotationYaw - 90)  * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
    	    GlStateManager.rotate(-90F * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
    	    GlStateManager.rotate(-Minecraft.getMinecraft().player.rotationPitch * 0.017453292F * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
    	    //GlStateManager.depthMask(false);
        	GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
        	float size = 20 - entity.ticksExisted;
        	size *= 0.1F;
        	if(size < 0.4F)
        		size = 0.4F;
        	
        	float blend = 1.0F;
        	
        	if(entity.ticksExisted > 200)
        	{
        		blend -= (float)(entity.ticksExisted - 200) / (float)100;
        	}
        	
        	if(blend < 0.12F)
        		blend = 0.12F;
        	
        	GlStateManager.pushMatrix();
        	GlStateManager.color(1.0F, 1.0F, 1.0F, blend);
        	GlStateManager.scale(size, size, size);
        	ClientProxy.callSphere();
        	GlStateManager.popMatrix();
        	size+=0.05F;
        	GlStateManager.pushMatrix();
        	GlStateManager.color(1.0F, 1.0F, 1.0F, blend / 2);
        	GlStateManager.scale(size, size, size);
        	ClientProxy.callSphere();
        	GlStateManager.popMatrix();
        	size+=0.05F;
        	GlStateManager.pushMatrix();
        	GlStateManager.color(1.0F, 1.0F, 1.0F, blend / 5);
        	GlStateManager.scale(size, size, size);
        	ClientProxy.callSphere();
        	GlStateManager.popMatrix();
        	size+=0.05F;
        	GlStateManager.pushMatrix();
        	GlStateManager.color(1.0F, 1.0F, 1.0F, blend / 7);
        	GlStateManager.scale(size, size, size);
        	ClientProxy.callSphere();
        	GlStateManager.popMatrix();

        	//GlStateManager.depthMask(true);
            GlStateManager.disableRescaleNormal();
            GlStateManager.disableBlend();
            GlStateManager.enableLighting();
        }
        GlStateManager.popMatrix();

        //ParticleRender.renderLightFX(this.renderManager, x, y, z, 2F, 0.5F, new float[] {0F, 0.5F, 0.8F});
        
         super.doRender(entity, x, y, z, entityYaw, partialTicks);
    }
    
    public void faceEntity(Entity entity, Entity entityIn, float maxYawIncrease, float maxPitchIncrease)
    {
        double d0 = entity.posX - entityIn.posX;
        double d2 = entity.posZ - entityIn.posZ;
        double d1;

        if (entity instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)entity;
            d1 = entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (entityIn.posY + (double)entityIn.getEyeHeight());
        }
        else
        {
            d1 = (entity.getEntityBoundingBox().minY + entity.getEntityBoundingBox().maxY) / 2.0D - (entityIn.posY + (double)entityIn.getEyeHeight());
        }

        double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
        float f = (float)(MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
        float f1 = (float)(-(MathHelper.atan2(d1, d3) * (180D / Math.PI)));

        entity.rotationPitch = this.updateRotation(entity.rotationPitch, f1, maxPitchIncrease);

        entity.rotationYaw = this.updateRotation(entity.rotationYaw, f, maxYawIncrease);
    }
    
    private float updateRotation(float angle, float targetAngle, float maxIncrease)
    {
        float f = MathHelper.wrapDegrees(targetAngle - angle);

        if (f > maxIncrease)
        {
            f = maxIncrease;
        }

        if (f < -maxIncrease)
        {
            f = -maxIncrease;
        }

        return angle + f;
    }
    
	@Override
    protected ResourceLocation getEntityTexture(EntitySpaceGatorix par1Entity) {
        return null;
    }
}