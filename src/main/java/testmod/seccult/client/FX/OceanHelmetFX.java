package testmod.seccult.client.FX;

import org.lwjgl.util.glu.GLU;
import org.lwjgl.util.glu.Sphere;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class OceanHelmetFX  extends Particle {
	private static ResourceLocation darkPTexture = new ResourceLocation("seccult:textures/entity/darktexture.png");
    Entity player;
    float yaw;
    float piatch;
    float height;
    public OceanHelmetFX(World worldIn, double posXIn, double posYIn, double posZIn, Entity player) {
        super(worldIn, posXIn, posYIn, posZIn);
        this.player=player;
        yaw=player.rotationYaw;
        piatch=player.rotationPitch;
        this.posX = player.posX;
        this.posY = player.posY;
        this.posZ = player.posZ;
        this.setParticleTexture(LightFX.test2);
    }

    public OceanHelmetFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        int i = 15728880;
        int j = i % 65536;
        int k = i / 65536;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
        if(player != null)
        DrawChannel(yaw,piatch);
    }

    @Override
    public int getFXLayer() {
    	return 1;
    }
    
    public void setHeight(float height)
    {
    	this.height = height;
    }
    
	public void SphereRender(float radius) {
		Sphere sphere = new Sphere();
		sphere.setDrawStyle(GLU.GLU_FILL);
		sphere.setNormals(GLU.GLU_SMOOTH);
		sphere.setTextureFlag(true);
		sphere.draw(radius, 4, 4);
	}
	
	public void Sphere(float radius)
	{
		GlStateManager.pushMatrix();
		GlStateManager.rotate(45, 0, 0, 1);
		GlStateManager.color(0, 0.8F, 1, 0.2F);
        GlStateManager.enableNormalize();
        GlStateManager.enableBlend();
        GlStateManager.enableAlpha();
        GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
		Minecraft.getMinecraft().getTextureManager().bindTexture(darkPTexture);
		SphereRender(radius);
        GlStateManager.disableBlend();
        GlStateManager.disableNormalize();
        GlStateManager.popMatrix();
	}
	
	public void DrawChannel(float yaw, float pitch){
	    GlStateManager.pushMatrix();
	    GlStateManager.translate(0,1.65,0); 
	    GlStateManager.rotate(-yaw  * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate(pitch * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
	    Sphere(0.4F);
	    GlStateManager.popMatrix();
	}
    
	public void setPlayer()
	{
		this.player = null;
	}
	
    @Override
    public void onUpdate() {
    	if(player == null)
    	{
    		setExpired();
    		return;
    	}
    	if(!player.isEntityAlive())
    		setExpired();
    	
    	this.yaw = player.rotationYaw;
    	this.piatch = player.rotationPitch;
        this.posX = player.posX;
        this.posY = player.posY;
        this.posZ = player.posZ;
    }
}