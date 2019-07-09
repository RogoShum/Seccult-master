package testmod.seccult.client.entity.render;

import org.lwjgl.util.glu.Disk;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import testmod.seccult.entity.EntityIMCircle;

public class RenderIMCircle extends Render<EntityIMCircle>{
	private static ResourceLocation darkTexture = new ResourceLocation("seccult:textures/particle/whitefield.png");
	private float angle;
	private float red, green, blue;
	public RenderIMCircle(RenderManager manager) {
        super(manager);
        this.shadowSize = 0;
	}

	@Override
	public void doRender(EntityIMCircle entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		
        int i = 15728880;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
		entity.height = 0.0F;
		entity.width = entity.getMySize();
		float size = ((float)entity.ticksExisted * entity.getMySize()) / 10F;
		if(size > entity.getMySize() / 2)
			size = entity.getMySize() / 2;
		Minecraft mc = Minecraft.getMinecraft();
	    	red = entity.getColorRed();
	    	green = entity.getColorGreen();
	    	blue = entity.getColorBlue();
	    GlStateManager.pushMatrix();
	    GlStateManager.enableBlend();
	    GlStateManager.blendFunc(SourceFactor.SRC_COLOR, DestFactor.ONE_MINUS_CONSTANT_ALPHA);
	    GlStateManager.color(red, green, blue);
	    GlStateManager.translate(x, y + 0.01, z);
	    mc.getTextureManager().bindTexture(darkTexture);
	    GlStateManager.rotate(90, 1, 0, 0);
	    GlStateManager.rotate(entity.ticksExisted * size, 0, 0, -1);
	    GlStateManager.pushMatrix();
	    Disk disk = new Disk();
	    disk.setTextureFlag(true);
	    disk.draw(0, size, 16, 16);
	    GlStateManager.rotate(180, 0, 1, 0);
	    Disk disk2 = new Disk();
	    disk2.setTextureFlag(true);
	    disk2.draw(0, size, 16, 16);
	    GlStateManager.popMatrix();
	    
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(0, size / 2, -0.1);
	    GlStateManager.rotate(entity.ticksExisted * size * 4, 0, 0, 1);
	    Disk disk3 = new Disk();
	    disk3.setTextureFlag(true);
	    disk3.draw(0, size / 2F, 16, 16);
	    GlStateManager.rotate(180, 0, 1, 0);
	    Disk disk4 = new Disk();
	    disk4.setTextureFlag(true);
	    disk4.draw(0, size / 2F, 16, 16);
	    GlStateManager.popMatrix();
	    
	    angle += 0.01;
	    float pos = angle / size;
	    if(pos > size * 0.8)
	    	pos = -size * 0.8F;
	    if(pos < -size * 0.8)
	    	pos = size * 0.8F;
	    if(angle > size * 120)
	    	angle = -size * 120;
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(pos * 0.8, pos * 0.8, -0.1);
	    GlStateManager.rotate(-entity.ticksExisted * 20, 0, 0, 1);
	    Disk disk5 = new Disk();
	    disk5.setTextureFlag(true);
	    disk5.draw(0, size / 4F, 16, 16);
	    GlStateManager.rotate(180, 0, 1, 0);
	    Disk disk6 = new Disk();
	    disk6.setTextureFlag(true);
	    disk6.draw(0, size / 4F, 16, 16);
	    GlStateManager.popMatrix();
	    
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(-pos * 0.6, -pos * 0.6, -0.1);
	    GlStateManager.rotate(-entity.ticksExisted * 20, 0, 0, 1);
	    Disk disk7 = new Disk();
	    disk7.setTextureFlag(true);
	    disk7.draw(0, pos / 2F, 16, 16);
	    GlStateManager.rotate(180, 0, 1, 0);
	    Disk disk8 = new Disk();
	    disk8.setTextureFlag(true);
	    disk8.draw(0, pos / 2F, 16, 16);
	    GlStateManager.popMatrix();
	    GlStateManager.disableBlend();
	    GlStateManager.popMatrix();
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityIMCircle entity) {
		return null;
	}
}
