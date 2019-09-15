package testmod.seccult.client.entity.render;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.FX.LightFX;
import testmod.seccult.client.entity.model.ModelDummySystem;
import testmod.seccult.entity.livings.EntitySpiritDummy;
import testmod.seccult.magick.active.Magick;

@SideOnly(Side.CLIENT)
public class RenderDummySystem extends RenderLiving<EntitySpiritDummy>
{
	private static final ResourceLocation TEXTURE = new ResourceLocation(Seccult.MODID + ":textures/entity/dummysystem_default.png");

	public RenderDummySystem(RenderManager renderManager) 
	{
		super(renderManager, new ModelDummySystem(), 0.5F);
		this.addLayer(new LayerDummySystem(this));
	}
	
	@Override
	public void doRender(EntitySpiritDummy entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y + 0.5F, z, entityYaw, partialTicks);
		
		if(entity.getLeftHandMagick() != null)
		{
			Magick magick = entity.getLeftHandMagick();
			Vec3d handVec = entity.getLookVec().rotateYaw(0.45F);
			Vec3d left = entity.getPositionVector().addVector(handVec.x, handVec.y + entity.getEyeHeight(), handVec.z);
			LightFX light = new LightFX(entity.world, left.x, left.y, left.z, 0, 0.02D, 0, 1.5F);
			light.setRBGColorF(magick.getRGB()[0], magick.getRGB()[1], magick.getRGB()[2]);
			Minecraft.getMinecraft().effectRenderer.addEffect(light);
		}
		
		if(entity.getRightHandMagick() != null)
		{
			Magick magick = entity.getRightHandMagick();
			Vec3d handVec = entity.getLookVec().rotateYaw(-0.45F);
			Vec3d left = entity.getPositionVector().addVector(handVec.x, handVec.y + entity.getEyeHeight(), handVec.z);
			LightFX light = new LightFX(entity.world, left.x, left.y, left.z, 0, 0.02D, 0, 1.5F);
			light.setRBGColorF(magick.getRGB()[0], magick.getRGB()[1], magick.getRGB()[2]);
			Minecraft.getMinecraft().effectRenderer.addEffect(light);
		}
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntitySpiritDummy entity)
	{
		return TEXTURE;
	}
}
