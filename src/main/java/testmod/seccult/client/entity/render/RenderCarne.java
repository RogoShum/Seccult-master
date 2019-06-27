package testmod.seccult.client.entity.render;

import net.minecraft.client.model.ModelBiped;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.entity.livings.EntityCarne;

@SideOnly(Side.CLIENT)
public class RenderCarne extends RenderLiving<EntityCarne>
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Seccult.MODID + ":textures/entity/carne.png");
	
	public RenderCarne(RenderManager renderManager) 
	{
		super(renderManager, new ModelBiped(), 0.7F);
        
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityCarne entity)
	{
		return TEXTURES;
	}
}
