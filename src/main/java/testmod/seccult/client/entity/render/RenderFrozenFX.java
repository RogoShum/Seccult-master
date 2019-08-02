package testmod.seccult.client.entity.render;

import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.GLU;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.entity.EntityFrozenFX;

@SideOnly(Side.CLIENT)
public class RenderFrozenFX extends Render<EntityFrozenFX>
{   
	private static ResourceLocation darkPTexture = new ResourceLocation("seccult:textures/entity/darktexture.png");
    public RenderFrozenFX(RenderManager manager) {
        super(manager);
        this.shadowSize = 0;
    }
    
    public void doRender(EntityFrozenFX par1Entity, double x, double y, double z, float entityYaw, float partialTicks) {
    	super.doRender(par1Entity, x, y, z, entityYaw, partialTicks);
    	 int i = 15728880;
         int j = i % 65536;
         int k = i / 65536;
         OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
         par1Entity.width = par1Entity.getMyWidth();
         par1Entity.height = par1Entity.getMyLength();
         DrawChannel(
                 (float) (x),
                 (float) (y),
                 (float) (z),
                 par1Entity.getMyWidth() + 0.5F, par1Entity.getMyLength() + 0.5F);
    }
 
	public void cylinderRender(float radius, float height) {
		Cylinder cylinder = new Cylinder();
		cylinder.setDrawStyle(GLU.GLU_FILL);		
		cylinder.setNormals(GLU.GLU_SMOOTH);
		cylinder.draw(radius, radius, height, 4, 1);
	}
	
	public void diskRender(float radius) {
		Disk cylinder = new Disk();
		cylinder.setDrawStyle(GLU.GLU_FILL);		
		cylinder.setNormals(GLU.GLU_SMOOTH);
		cylinder.draw(0, radius, 4, 1);
	}
	
	public void Cylinder(float radius, float height)
	{
		GlStateManager.pushMatrix();
		GlStateManager.translate(0, height, 0);
		GlStateManager.rotate(90, 1, 0, 0);
		GlStateManager.rotate(45, 0, 0, 1);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
		Minecraft.getMinecraft().getTextureManager().bindTexture(darkPTexture);
	    GlStateManager.pushMatrix();
			GlStateManager.color(0, 1F, 1F, 0.2F);
	    	cylinderRender(radius, height);
		    
			GlStateManager.pushMatrix();
			GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			diskRender(radius);
			GlStateManager.popMatrix();
			
			GlStateManager.pushMatrix();
			GlStateManager.translate(0, 0, height);
			diskRender(radius);
			GlStateManager.popMatrix();
			GlStateManager.popMatrix();
			
			
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
        GlStateManager.popMatrix();
	}
	
	public void DrawChannel(float x1, float y1, float z1, float width, float height){
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(x1, y1, z1);
	    Cylinder(width, height);
	    GlStateManager.popMatrix();
	}
    
	@Override
	protected ResourceLocation getEntityTexture(EntityFrozenFX entity) {
		return null;
	}
}