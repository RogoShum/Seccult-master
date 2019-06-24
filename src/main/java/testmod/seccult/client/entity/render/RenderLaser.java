package testmod.seccult.client.entity.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.GLU;

import net.minecraft.block.BlockBeacon;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.ClientProxy;
import testmod.seccult.Seccult;
import testmod.seccult.client.FX.SuperLaserBeamFX;
import testmod.seccult.client.entity.model.*;
import testmod.seccult.entity.EntityLaserBeamBase;

@SideOnly(Side.CLIENT)
public class RenderLaser extends Render<EntityLaserBeamBase>
{
	private static ResourceLocation TEXTURES = new ResourceLocation(Seccult.MODID + ":textures/entity/laserbeam.png");
	private static ResourceLocation darkPTexture = new ResourceLocation("seccult:textures/entity/darktexture.png");
	private final ModelBase laser = new ModelLaser();
	private final ModelBase laserLayer = new ModelLaserLayer();
	private SuperLaserBeamFX laserbeam;
	
	public int cylinderIdOutside;
	public int cylinderIdInside;
	
	public RenderLaser(RenderManager renderManager)
	{
		super(renderManager);
		this.shadowSize = 0F;
		this.laserbeam = null;
	}
	
	@Override
	public void doRender(EntityLaserBeamBase entity, double x, double y, double z, float yaw, float partialTicks) {
		super.doRender(entity, x, y, z, yaw, partialTicks);
		/*if(entity.getMyWidth() == 1) {
			 GlStateManager.pushMatrix();
				GlStateManager.rotate(-yaw  * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
			    GlStateManager.rotate(entity.rotationPitch * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
			    GlStateManager.rotate(entity.getMyLength() * 36 * 0.017453292F * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
		     renderOffsetAABB(entity.getEntityBoundingBox(), x - entity.lastTickPosX, y - entity.lastTickPosY, z - entity.lastTickPosZ);
		     GlStateManager.popMatrix();
			return;
		}
		GlStateManager.pushMatrix();
		this.bindEntityTexture(entity);
	    GlStateManager.translate((float)x, (float)y + (entity.height / 2), (float)z);
        GlStateManager.enableRescaleNormal();
	    GlStateManager.scale(1F, 1F, 1F);
		GlStateManager.rotate(-yaw  * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate(entity.rotationPitch * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
	    GlStateManager.rotate(entity.getMyLength() * 36 * 0.017453292F * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
		GlStateManager.disableLighting();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.laser.render(entity, 0, 0, 0, yaw, entity.rotationPitch, 0.065F);
		GlStateManager.enableLighting();
		GlStateManager.popMatrix();
		
		GlStateManager.pushMatrix();
		this.bindEntityTexture(entity);
	    GlStateManager.translate((float)x, (float)y + (entity.height / 2), (float)z);
        GlStateManager.enableRescaleNormal();
	    GlStateManager.scale(1F, 1F, 1F);
		GlStateManager.rotate(-yaw  * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate(entity.rotationPitch * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
	    GlStateManager.rotate(entity.getMyLength() * 36 * 0.017453292F * (180F / (float)Math.PI), 0.0F, 0.0F, 1.0F);
		GlStateManager.disableLighting();
	        int i = (int) (entity.getMyLength() / 25);
	        int j = EnumDyeColor.values().length;
	        int k = i % j;
	        int l = (i + 1) % j;
	        float f = ((float)(entity.getMyLength() % 25) + partialTicks) / 25.0F;
	        float[] afloat1 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(k));
	        float[] afloat2 = EntitySheep.getDyeRgb(EnumDyeColor.byMetadata(l));
	        GlStateManager.color(afloat1[0] * (1.0F - f) + afloat2[0] * f, afloat1[1] * (1.0F - f) + afloat2[1] * f, afloat1[2] * (1.0F - f) + afloat2[2] * f);
        GlStateManager.enableNormalize();
        GlStateManager.depthMask(false);
		GlStateManager.enableBlend();
		GlStateManager.blendFunc(GL11.GL_SRC_ALPHA, GL11.GL_ONE_MINUS_SRC_ALPHA);
        this.laserLayer.render(entity, 0, 0, 0, yaw, entity.rotationPitch, 0.065F);
		GlStateManager.enableLighting();
	    GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
		GlStateManager.popMatrix();
		*/
		
		if(!(entity.getMyWidth() == 1)) return;
		if(!entity.getMyTag().hasKey("X")) return;
		double x2 = entity.getMyTag().getDouble("X");
		double y2 = entity.getMyTag().getDouble("Y");
		double z2 = entity.getMyTag().getDouble("Z");
		float dx = (float) (x2 - x);
	    float dy = (float) (y2 - y);
		float dz = (float) (z2 - z);
		float distance = (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
		
		Vec3d lok = entity.getLookVec();
		Vec3d position = new Vec3d(x,y,z);
	 	Vec3d QAQ = position.addVector(lok.x * 2, lok.y * 5, lok.z * 2);
		//DrawChannel(-(float)x, -(float)y + (entity.height/2), -(float)z, -(float)(x2+x), -(float)(y2+y) + (entity.height/2), -(float)(z2+z), yaw, entity.rotationPitch);
	 	//System.out.println(x);
	 	
	 	if(laserbeam == null || (laserbeam != null && !laserbeam.isAlive()))
	 	{
	 		//System.out.println("QAQ???");
	 		createLaser(entity.world, QAQ.x, QAQ.y, QAQ.z, entity, distance - 1);
	 	}
	 	else if(laserbeam != null)
	 	{
	 		//System.out.println(laserbeam);
	 		//System.out.println("QAQ???");
	 		//System.out.println(laserbeam.isAlive());
	 		laserbeam.setHeight(distance - 1);
	 	}
	 	
	}
	
	public void createLaser(World worldIn, double posXIn, double posYIn, double posZIn, Entity player, float height)
	{
		//System.out.println("QAQ");
			SuperLaserBeamFX laser =  new SuperLaserBeamFX(worldIn, posXIn, posYIn, posZIn, player, height);
			Minecraft.getMinecraft().effectRenderer.addEffect(laser);
			this.laserbeam = (SuperLaserBeamFX) laser;
	}
	
	public void cylinderRender(float radius, float height) {
		Cylinder cylinder = new Cylinder();
		cylinder.setDrawStyle(GLU.GLU_FILL);		
		cylinder.setNormals(GLU.GLU_NONE);
		//cylinder.setTextureFlag(true);
		cylinder.draw(radius, radius, height, 32, 32);
		//System.out.println(radius);
	}
	
	public void diskRender(float radius) {
		Disk cylinder = new Disk();
		cylinder.setDrawStyle(GLU.GLU_FILL);		
		cylinder.setNormals(GLU.GLU_NONE);
		//cylinder.setTextureFlag(true);
		cylinder.draw(0, radius, 32, 32);
	}
	
	public void Cylinder(float radius, float height, float alpha, float red, float green, float blue)
	{
        GlStateManager.enableNormalize();
        //GlStateManager.depthMask(true);
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
		Minecraft.getMinecraft().getTextureManager().bindTexture(darkPTexture);
	    for(float r = radius / 3; r < radius;)
	    {
	    	GlStateManager.pushMatrix();
		    if(r == radius / 3)
		    	GlStateManager.color(1.0F, 1F, 1.0F, alpha);
		    else
		    	GlStateManager.color(red, green, blue, 0.1001F);
	    	cylinderRender(r, height);
		    r += 0.01F;
		    
			GlStateManager.pushMatrix();
			GlStateManager.rotate(180, 0.0F, 1.0F, 0.0F);
			diskRender(r - 0.01F);
			GlStateManager.popMatrix();
			GlStateManager.popMatrix();
	    }
		//GlStateManager.depthMask(false);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
	}
	
	public void DrawChannel(float x1, float y1, float z1, float x2, float y2, float z2, float yaw, float pitch){
		float dx = x2 - x1;
	    float dy = y2 - y1;
		float dz = z2 - z1;
		float distance = (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
	    
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(x1,y1,z1); 
	    GlStateManager.rotate(-yaw  * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate(pitch * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
	    Cylinder(0.3F,distance, 0.7F, 1F, 0.5F, 0F);
	    GlStateManager.popMatrix();
	    
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(x1 + 1,y1 + 1,z1 + 1); 
	    GlStateManager.rotate(-yaw  * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate(pitch * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
	    Cylinder(0.25F,distance, 0.7F, 0F,0F,0.5F);
	    GlStateManager.popMatrix();
	    
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(x1 + 2,y1 -1,z1 + 2); 
	    GlStateManager.rotate(-yaw  * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate(pitch * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
	    Cylinder(0.2F,distance, 0.7F, 0F,0.5F,0.5F);
	    GlStateManager.popMatrix();
	    
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(x1 + 3,y1 -1,z1 + 2); 
	    GlStateManager.rotate(-yaw  * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate(pitch * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
	    Cylinder(0.2F,distance, 0.7F, 1F,0.0F,0.5F);
	    GlStateManager.popMatrix();
	    
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(x1 - 3,y1 -1,z1 + 2); 
	    GlStateManager.rotate(-yaw  * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate(pitch * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
	    Cylinder(0.25F,distance, 0.7F, 1F,0.0F,0.0F);
	    GlStateManager.popMatrix();
	    
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(x1 - 2,y1 +2,z1 + 2); 
	    GlStateManager.rotate(-yaw  * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate(pitch * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
	    Cylinder(0.3F,distance, 0.7F, 1F,0.0F,1.0F);
	    GlStateManager.popMatrix();
	}
	
    public static void renderOffsetAABB(AxisAlignedBB boundingBox, double x, double y, double z)
    {
        GlStateManager.disableTexture2D();
        Tessellator tessellator = Tessellator.getInstance();
        BufferBuilder bufferbuilder = tessellator.getBuffer();
        GlStateManager.color(1.0F, 1.0F, 1.0F, 1.0F);
        bufferbuilder.setTranslation(x, y, z);
        bufferbuilder.begin(7, DefaultVertexFormats.POSITION_NORMAL);
        bufferbuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
        bufferbuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
        bufferbuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
        bufferbuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).normal(0.0F, 0.0F, -1.0F).endVertex();
        bufferbuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
        bufferbuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
        bufferbuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
        bufferbuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 0.0F, 1.0F).endVertex();
        bufferbuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).normal(0.0F, -1.0F, 0.0F).endVertex();
        bufferbuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).normal(0.0F, -1.0F, 0.0F).endVertex();
        bufferbuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, -1.0F, 0.0F).endVertex();
        bufferbuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).normal(0.0F, -1.0F, 0.0F).endVertex();
        bufferbuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).normal(0.0F, 1.0F, 0.0F).endVertex();
        bufferbuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.maxZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
        bufferbuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.maxZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
        bufferbuilder.pos(boundingBox.minX, boundingBox.maxY, boundingBox.minZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
        bufferbuilder.pos(boundingBox.minX, boundingBox.minY, boundingBox.minZ).normal(-1.0F, 0.0F, 0.0F).endVertex();
        bufferbuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.minZ).normal(1.0F, 0.0F, 0.0F).endVertex();
        bufferbuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.minZ).normal(1.0F, 0.0F, 0.0F).endVertex();
        bufferbuilder.pos(boundingBox.maxX, boundingBox.maxY, boundingBox.maxZ).normal(1.0F, 0.0F, 0.0F).endVertex();
        bufferbuilder.pos(boundingBox.maxX, boundingBox.minY, boundingBox.maxZ).normal(1.0F, 0.0F, 0.0F).endVertex();
        tessellator.draw();
        bufferbuilder.setTranslation(0.0D, 0.0D, 0.0D);
        GlStateManager.enableTexture2D();
    }
	
	@Override
	protected ResourceLocation getEntityTexture(EntityLaserBeamBase entity)
	{
		return TEXTURES;
	}
}
