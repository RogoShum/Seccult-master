package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelSoC;
import testmod.seccult.entity.livings.EntitySoC;

@SideOnly(Side.CLIENT)
public class RenderSoC extends RenderLiving<EntitySoC>
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Seccult.MODID + ":textures/entity/soc.png");
	
	public RenderSoC(RenderManager renderManager) 
	{
		super(renderManager, new ModelSoC(), 0.2F);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntitySoC entity)
	{
		return TEXTURES;
	}
}
