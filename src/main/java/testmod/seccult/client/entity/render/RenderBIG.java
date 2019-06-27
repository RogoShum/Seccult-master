package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelBIG;
import testmod.seccult.entity.livings.EntityNotoriousBIG;

@SideOnly(Side.CLIENT)
public class RenderBIG extends RenderLiving<EntityNotoriousBIG>
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Seccult.MODID + ":textures/entity/big.png");
	private static final ResourceLocation TEXTURES1 = new ResourceLocation(Seccult.MODID + ":textures/entity/big1.png");
	private static final ResourceLocation TEXTURES2 = new ResourceLocation(Seccult.MODID + ":textures/entity/big2.png");
	
	public RenderBIG(RenderManager renderManager) 
	{
		super(renderManager, new ModelBIG(), 0.0F);
		this.shadowSize = 0F;
	}
	
	@Override
	protected void preRenderCallback(EntityNotoriousBIG entitylivingbaseIn, float partialTickTime) {
		GlStateManager.scale(entitylivingbaseIn.getMySize(), entitylivingbaseIn.getMySize(), entitylivingbaseIn.getMySize());
		super.preRenderCallback(entitylivingbaseIn, partialTickTime);
	}
	
	@Override
	public void doRender(EntityNotoriousBIG entity, double x, double y, double z, float entityYaw, float partialTicks) {
		this.shadowSize = entity.getMySize() * 0.5F;
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityNotoriousBIG entity)
	{
		if(entity.getTail())
			return TEXTURES;
		
		int a = 0;
		if(entity.getMySize() < 1)
			a = 0;
		if(entity.getMySize() > 1)
			a = 1;
		if(entity.getMySize() > 2)
			a = 2;
		switch(a) {
		case 0:
			return TEXTURES;
		case 1:
			return TEXTURES1;
		case 2:
			return TEXTURES2;
		}
		return TEXTURES;
	}
}
