package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.entity.livings.EntityChangeling;

@SideOnly(Side.CLIENT)
public class RenderChangeling extends Render<EntityChangeling>
{
	private ResourceLocation texture;
	
	public RenderChangeling(RenderManager renderManager) 
	{
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityChangeling entity, double x, double y, double z, float entityYaw, float partialTicks) {
	    if (entity.getEntity() != null) {
	    		this.renderManager.getEntityRenderObject(entity.getEntity()).doRender(entity.getEntity(), x, y, z, entity.getEntity().rotationYaw, partialTicks);
	    		//entity.getEntity().setPositionAndRotationDirect(x, y, z, entity.getEntity().rotationYaw, entity.getEntity().rotationPitch, 3, false);
	    		entity.getEntity().setPositionAndRotation(x, y, z, entityYaw, entity.rotationPitch);
	    		entity.getEntity().motionX = entity.motionX;
	    		entity.getEntity().motionY = entity.motionY;
	    		entity.getEntity().motionZ = entity.motionZ;

	        	if(entity.getEntity() instanceof EntityLivingBase) 
	        	{
	        		EntityLivingBase living = (EntityLivingBase) entity.getEntity();
	        		living.setRenderYawOffset(entity.renderYawOffset);
	        		living.setRotationYawHead(entity.rotationYawHead);
	        		living.limbSwing = entity.limbSwing;
	        		living.limbSwingAmount = entity.limbSwingAmount;
	        	}
	    }
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityChangeling entity)
	{
		return texture;
	}
}
