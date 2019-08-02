package testmod.seccult.client.entity.render;

import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.GLU;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.entity.EntityLaserBeamBase;

@SideOnly(Side.CLIENT)
public class RenderLaser extends Render<EntityLaserBeamBase>
{
	private static ResourceLocation darkPTexture = new ResourceLocation("seccult:textures/entity/darktexture.png");

    float angle;
    float red;
    float green;
    float blue;
    
	public RenderLaser(RenderManager renderManager) {
		super(renderManager);
	}
	
	@Override
	public void doRender(EntityLaserBeamBase entity, double x, double y, double z, float yaw, float partialTicks) {
		super.doRender(entity, x, y, z, yaw, partialTicks);
        angle+=10;
        if(angle >=360)
        	angle = 0;

        entity.width = entity.getMyLength() / 2;
        entity.height = entity.getMyWidth();
		DrawChannel(x, y - 0.5, z, entity.getMyWidth(), entity.getMyLength(), yaw, entity.rotationPitch);
	}
	
	public void cylinderRender(float radius, float height) {
		Cylinder cylinder = new Cylinder();
		cylinder.setDrawStyle(GLU.GLU_FILL);		
		cylinder.setNormals(GLU.GLU_NONE);
		cylinder.draw(radius, radius, height, 4, 1);
	}
	
	public void diskRender(float radius) {
		Disk cylinder = new Disk();
		cylinder.setDrawStyle(GLU.GLU_FILL);		
		cylinder.setNormals(GLU.GLU_NONE);
		cylinder.draw(0, radius, 4, 1);
	}
	
	public void Cylinder(float radius, float height)
	{
		GlStateManager.pushMatrix();
		GlStateManager.rotate(angle, 0, 0, 1);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
		Minecraft.getMinecraft().getTextureManager().bindTexture(darkPTexture);
		float space = radius / 18;
	    for(float r = radius / 3; r < radius;)
	    {
	    	GlStateManager.pushMatrix();
			    
			    if(r < radius / 1.3F)
			    	GlStateManager.color(0, 1, 1, 1F / 5F);
			    else
			    	GlStateManager.color(0, 1, 1, 1F / 10F);
			    if(r == radius / 3)
			    	GlStateManager.color(1.0F, 1F, 1.0F, 1F);

	    	cylinderRender(r, height);
		    r += space;
		    
			GlStateManager.pushMatrix();
			GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			diskRender(r - 0.01F);
			GlStateManager.popMatrix();
			
			GlStateManager.pushMatrix();
			GlStateManager.translate(0, 0, height);
			diskRender(r - 0.01F);
			GlStateManager.popMatrix();
			GlStateManager.popMatrix();
	    }
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
        GlStateManager.popMatrix();
	}
	
	public void DrawChannel(double x1, double y1, double z1, float width, float lengh, float yaw, float pitch){
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(x1,y1,z1); 
	    GlStateManager.rotate(-yaw  * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate(pitch * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
	    Cylinder(width / 2, lengh);
	    GlStateManager.popMatrix();
	}

	@Override
	protected ResourceLocation getEntityTexture(EntityLaserBeamBase entity) {
		return null;
	}
}
