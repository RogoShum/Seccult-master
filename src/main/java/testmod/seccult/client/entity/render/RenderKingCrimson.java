package testmod.seccult.client.entity.render;

import net.minecraft.client.model.ModelPlayer;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.entity.livings.EntityKingCrimson;

@SideOnly(Side.CLIENT)
public class RenderKingCrimson extends RenderLiving<EntityKingCrimson>
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Seccult.MODID + ":textures/entity/king_crimson.png");
	
	public RenderKingCrimson(RenderManager renderManager) 
	{
		super(renderManager, new ModelPlayer(0, false), 0.7F);
        
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityKingCrimson entity)
	{
		return TEXTURES;
	}
}
