package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;

@SideOnly(Side.CLIENT)
public class RenderLmr extends Render<Entity>
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Seccult.MODID + ":textures/entity/lmr.png");
	
	public RenderLmr(RenderManager renderManager) 
	{
		super(renderManager);
		this.shadowSize = 0F;
	}
	
	@Override
	protected ResourceLocation getEntityTexture(Entity entity)
	{
		return TEXTURES;
	}
}
