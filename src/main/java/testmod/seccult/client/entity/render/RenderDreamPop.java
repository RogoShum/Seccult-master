package testmod.seccult.client.entity.render;

import org.lwjgl.util.glu.Disk;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderLiving;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.ModelDreamPop;
import testmod.seccult.entity.livings.landCreature.EntityDreamPop;
import testmod.seccult.entity.livings.landCreature.EntityNightmarePop;
import testmod.seccult.events.EntityRenderHandler;

@SideOnly(Side.CLIENT)
public class RenderDreamPop extends RenderLiving<EntityDreamPop>
{
	private static final ResourceLocation TEXTURES = new ResourceLocation(Seccult.MODID + ":textures/entity/dream_pop.png");
	private static final ResourceLocation TEXTURES_N = new ResourceLocation(Seccult.MODID + ":textures/entity/night_pop.png");
	private static ResourceLocation sphere = new ResourceLocation("seccult:textures/spell/sphere.png");
	private static ResourceLocation moon = new ResourceLocation("seccult:textures/spell/moon.png");
	private static ResourceLocation ball = new ResourceLocation("seccult:textures/spell/ball.png");
	private static ResourceLocation star = new ResourceLocation("seccult:textures/spell/star.png");
	private static ResourceLocation square = new ResourceLocation("seccult:textures/spell/square.png");
	
	public RenderDreamPop(RenderManager renderManager) 
	{
		super(renderManager, new ModelDreamPop(), 0.6F);
		this.addLayer(new LayerDreamPop());
	}

	@Override
	public void doRender(EntityDreamPop entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		if(entity.getState() == 1)
		{
			TextureManager tex = Minecraft.getMinecraft().getTextureManager();
			GlStateManager.pushMatrix();
			GlStateManager.disableCull();
			GlStateManager.disableLighting();

			float red = 1;
	        float green = 0;
	        float blue = 0;
	        float s1 = 0.5F;
	        GlStateManager.translate(x, y + 0.01, z);
			GlStateManager.rotate(90F, 1F, 0F, 0F);
		    GlStateManager.rotate(entity.ticksExisted, 0, 0, -1);
	        
			GlStateManager.pushMatrix();
		    GlStateManager.enableBlend();
		    GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
		    GlStateManager.color(red, green, blue);
		    tex.bindTexture(sphere);
		    Disk disk = new Disk();
		    disk.setTextureFlag(true);
		    disk.draw(0, s1 * 1.5F, 16, 16);    
		    GlStateManager.popMatrix();
		    
			GlStateManager.pushMatrix();
		    GlStateManager.enableBlend();
		    GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
		    GlStateManager.color(red, green, blue);
		    GlStateManager.translate(-s1 * 1.2F, 0, 0);
		    tex.bindTexture(moon);
		    disk.draw(0, s1, 16, 16);
		    GlStateManager.popMatrix();
		    
			GlStateManager.pushMatrix();
		    GlStateManager.enableBlend();
		    GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
		    GlStateManager.color(red, green, blue);
		    GlStateManager.translate(s1 * 1.2F, 0, 0);
		    tex.bindTexture(ball);
		    disk.draw(0, s1, 16, 16);
		    GlStateManager.popMatrix();
		    
			GlStateManager.pushMatrix();
		    GlStateManager.enableBlend();
		    GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
		    GlStateManager.color(red, green, blue);
		    tex.bindTexture(star);
		    disk.draw(0, s1, 16, 16);
		    GlStateManager.popMatrix();
		    
		    //for(int i = 0; i < 3; ++i)
		    //{
		    	GlStateManager.pushMatrix();
		    	//GlStateManager.rotate(60 * i, 0, 0, 1);
		    	GlStateManager.enableBlend();
		    	GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
		    	GlStateManager.color(red, green, blue);
		    	tex.bindTexture(square);
		    	disk.draw(0, s1 * 1.1F, 16, 16);
		    	GlStateManager.popMatrix();
		    //}
		    
			GlStateManager.enableCull();
			GlStateManager.popMatrix();
		}
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
