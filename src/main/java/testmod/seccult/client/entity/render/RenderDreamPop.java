package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelDreamPop;
import testmod.seccult.entity.livings.landCreature.EntityDreamPop;
import testmod.seccult.entity.livings.landCreature.EntityNightmarePop;

@SideOnly(Side.CLIENT)
public class RenderDreamPop extends RenderLiving<EntityDreamPop>
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Seccult.MODID + ":textures/entity/dream_pop.png");
	private static final ResourceLocation TEXTURES_N = new ResourceLocation(Seccult.MODID + ":textures/entity/night_pop.png");
	
	public RenderDreamPop(RenderManager renderManager) 
	{
		super(renderManager, new ModelDreamPop(), 0.6F);
		this.addLayer(new LayerDreamPop());
	}

	@Override
	protected void preRenderCallback(EntityDreamPop entitylivingbaseIn, float partialTickTime) {
		GlStateManager.translate(0, 0.8, 0);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityDreamPop entity)
	{
		if(entity instanceof EntityNightmarePop)
		return TEXTURES_N;
		
		return TEXTURES;
	}
}
