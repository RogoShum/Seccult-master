package testmod.seccult.client.entity.render;

import org.lwjgl.opengl.GL11;
import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;

import net.minecraft.client.Minecraft;
import net.minecraft.client.model.ModelBase;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.client.renderer.Tessellator;
import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.vertex.DefaultVertexFormats;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.ClientProxy;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.model.*;
import testmod.seccult.entity.EntityLaserBeamBase;

@SideOnly(Side.CLIENT)
public class RenderLaser extends Render<EntityLaserBeamBase>
{
	private static ResourceLocation TEXTURES = new ResourceLocation(Seccult.MODID + ":textures/entity/laserbeam.png");
	private static ResourceLocation darkPTexture = new ResourceLocation("seccult:textures/entity/darktexture.png");
	private final ModelBase laser = new ModelLaser();
	private final ModelBase laserLayer = new ModelLaserLayer();
	
	public int cylinderIdOutside;
	public int cylinderIdInside;
	
	public RenderLaser(RenderManager renderManager)
	{
		super(renderManager);
		this.shadowSize = 0F;
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
		
		
		if(entity.getMyWidth() == 1) {
		if(entity.getMyTag().hasKey("X")) {
		double x2 = entity.getMyTag().getDouble("X");
		double y2 = entity.getMyTag().getDouble("Y");
		double z2 = entity.getMyTag().getDouble("Z");
		Vec3d lok = entity.getLookVec();
		 	Vec3d position = new Vec3d(x,y,z);
		 	Vec3d QAQ = position.addVector(lok.x * 2, lok.y * 1, lok.z * 1);
		GlStateManager.pushMatrix();
	    GlStateManager.translate((float)QAQ.x, (float)QAQ.y + (entity.height / 3), (float)QAQ.z);
	    int i = 15728880;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float)j, (float)k);
		DrawChannel(-(float)x, -(float)y + (entity.height/2), -(float)z, -(float)(x2+x), -(float)(y2+y) + (entity.height/2), -(float)(z2+z), yaw, entity.rotationPitch);
		GlStateManager.popMatrix();
		}
		}
	}
	
	public void cylinderRender(float radius, float height) {
		Cylinder Cylinder = new Cylinder();
		//Set up paramters that are common to both outside and inside.

		//GLU_FILL as a solid.
		Cylinder.setDrawStyle(GLU.GLU_FILL);
		//GLU_SMOOTH will try to smoothly apply lighting
		Cylinder.setNormals(GLU.GLU_NONE);
		Cylinder.setTextureFlag(true);
		//First make the call list for the outside of the sphere

		Cylinder.setOrientation(GLU.GLU_OUTSIDE);

		cylinderIdOutside = GlStateManager.glGenLists(1);
		//Create a new list to hold our sphere data.
		GlStateManager.glNewList(cylinderIdOutside, GL11.GL_COMPILE);
		//binds the texture 
		//The drawing the sphere is automatically doing is getting added to our list. Careful, the last 2 variables
		//control the detail, but have a massive impact on performance. 32x32 is a good balance on my machine.s
		Cylinder.draw(radius, radius, height, 64, 64);
		GlStateManager.glEndList();

		//Now make the call list for the inside of the sphere
		Cylinder.setOrientation(GLU.GLU_INSIDE);
		cylinderIdInside = GlStateManager.glGenLists(1);
		//Create a new list to hold our sphere data.
		GlStateManager.glNewList(cylinderIdInside, GL11.GL_COMPILE);
		Cylinder.draw(radius, radius, height, 64, 64);
		GlStateManager.glEndList();
	}
	
	public void Cylinder(float radius, float height, float alpha)
	{
		cylinderRender(radius, height);
	    GlStateManager.pushMatrix();
	    if(alpha == 1)
	    	GlStateManager.color(1.0F, 1F, 1.0F, alpha);
	    else
        GlStateManager.color(0.0F, 1.0F, 0.0F, alpha);
        GlStateManager.enableNormalize();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		Minecraft.getMinecraft().getTextureManager().bindTexture(darkPTexture);
	    GlStateManager.callList(cylinderIdOutside);
	    GlStateManager.callList(cylinderIdInside);
	    GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
	    GlStateManager.popMatrix();
	}
	
	public void Sphere(float sizee)
	{
	    GlStateManager.pushMatrix();
	    GlStateManager.scale(sizee, sizee, sizee);
	    GlStateManager.color(0.0F, 1F, 0.0F, 0.5F);
        GlStateManager.enableNormalize();
        GlStateManager.depthMask(false);
        GlStateManager.enableBlend();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA);
		Minecraft.getMinecraft().getTextureManager().bindTexture(darkPTexture);
	    GlStateManager.callList(ClientProxy.sphereIdOutside);
	    GlStateManager.callList(ClientProxy.sphereIdInside);
	    GlStateManager.depthMask(true);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
	    GlStateManager.popMatrix();
	}
	
	public void DrawChannel(float x1, float y1, float z1, float x2, float y2, float z2, float yaw, float pitch){
		//System.out.println(x1+" "+y1+" "+z1+" "+x2+" "+y2+" "+z2);
		float dx = x2 - x1;
	    float dy = y2 - y1;
		float dz = z2 - z1;
		float distance = (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
	    
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(x1,y1,z1); 
	    GlStateManager.rotate(-yaw  * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate(pitch * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
	    GlStateManager.pushMatrix();
	    Cylinder(0.1F,distance, 1F);
	    Cylinder(0.18F,distance, 0.1001F);
	    Cylinder(0.22F,distance, 0.1001F);
	    Cylinder(0.25F,distance, 0.1001F);
	    Cylinder(0.28F,distance, 0.1001F);
	    Cylinder(0.31F,distance, 0.1001F);
	    //Cylinder(0.34F,distance, 0.1001F);
	    //Cylinder(0.37F,distance, 0.1001F);
	    //Cylinder(0.4F,distance, 0.1001F);
	    GlStateManager.popMatrix();
	    GlStateManager.popMatrix();
	    
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(x1,y1,z1);   
	    Sphere(0.6F);
	    GlStateManager.popMatrix();
	    
	   
	    

	    GlStateManager.pushMatrix();
	    GlStateManager.translate(x2,y2,z2);   
	    Sphere(0.8F);       
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
