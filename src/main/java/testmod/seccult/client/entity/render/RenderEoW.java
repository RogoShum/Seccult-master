package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelEoW;
import testmod.seccult.entity.livings.EntityEoW;
@SideOnly(Side.CLIENT)
public class RenderEoW extends RenderLiving<EntityEoW>
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Seccult.MODID + ":textures/entity/eow.png");
	private static final ResourceLocation TEXTURESB = new ResourceLocation(Seccult.MODID + ":textures/entity/eowhead.png");
	private static final ResourceLocation TEXTURESC = new ResourceLocation(Seccult.MODID + ":textures/entity/eowtail.png");
	private static final ResourceLocation TEXTURESD = new ResourceLocation(Seccult.MODID + ":textures/entity/eowentre.png");
	
	public RenderEoW(RenderManager renderManager) 
	{
		super(renderManager, new ModelEoW(), 0.0F);
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityEoW entity)
	{
		switch (entity.getRenderSkin())
        {	
            case 1:
                return TEXTURESB;
            case 2:
                return TEXTURES;
            case 3:
            	return TEXTURESC;
            case 4 :
            	return TEXTURESD;
        }
		return TEXTURESB;
	}
}
