package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelLight;
import testmod.seccult.entity.livings.EntityLight;

@SideOnly(Side.CLIENT)
public class RenderLight extends RenderLiving<EntityLight>
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Seccult.MODID + ":textures/entity/light.png");
	
	public RenderLight(RenderManager renderManager) 
	{
		super(renderManager, new ModelLight(0), 0.0F);
		this.addLayer(new LayerLight(this));
        
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityLight entity)
	{
		return TEXTURES;
	}
}
