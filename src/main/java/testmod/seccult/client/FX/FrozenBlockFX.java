package testmod.seccult.client.FX;

import java.util.List;

import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.GLU;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class FrozenBlockFX extends Particle {
	private static ResourceLocation darkPTexture = new ResourceLocation("seccult:textures/entity/darktexture.png");
    Entity player;
    float height;
    float width;

    public FrozenBlockFX(World worldIn, double posXIn, double posYIn, double posZIn, float height, float width, float maxage) {
        super(worldIn, posXIn, posYIn, posZIn);
        this.height = height * 1.1F;
        this.width = width;
        this.posX = posXIn;
        this.posY = posYIn;
        this.posZ = posZIn;
        this.particleMaxAge = (int) maxage;
        this.particleScale = 20;
        this.setParticleTexture(LightFX.test2);
    }

    public FrozenBlockFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        int i = 15728880;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
        DrawChannel(
                (float) (entityIn.posX),
                (float) (entityIn.posY),
                (float) (entityIn.posZ),
                width, height);
        if(player == null)
        {
        	double dis = 2;
        	List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(null, this.getBoundingBox());
        	for(int c = 0; c < list.size(); c++)
        	{
        		double distance = list.get(c).getDistance(posX, posY, posZ);
        		if(distance < dis)
        			this.player = list.get(c);
        	}
        }
    }

    @Override
    public int getFXLayer() {
    	return 1;
    }
    
    public void setHeight(float height)
    {
    	this.height = height;
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
		if(this.player != null)
		GlStateManager.translate(0, this.player.height, 0);
		GlStateManager.rotate(90, 1, 0, 0);
		GlStateManager.rotate(45, 0, 0, 1);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
		Minecraft.getMinecraft().getTextureManager().bindTexture(darkPTexture);
	    GlStateManager.pushMatrix();
			GlStateManager.color(0, 0.4F, 0.4F, 0.2F);
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
	    GlStateManager.translate(0 - x1, 0 -y1,  0 - z1);
	    GlStateManager.translate(posX, posY,  posZ);
	    Cylinder(width, height);
	    GlStateManager.popMatrix();
	}
    
    @Override
    public void onUpdate() {
    	if(this.particleAge++ > this.particleMaxAge)
    		setExpired();
    	if(player == null)
    		return;
    	if(!player.isEntityAlive())
    		setExpired();

        this.posX = player.posX;
        this.posY = player.posY;
        this.posZ = player.posZ;
    }
}
