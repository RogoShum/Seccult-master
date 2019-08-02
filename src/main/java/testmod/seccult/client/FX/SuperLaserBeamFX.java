package testmod.seccult.client.FX;

import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.Disk;
import org.lwjgl.util.glu.GLU;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import testmod.seccult.entity.EntityLaserBeamBase;

public class SuperLaserBeamFX extends Particle {
	private static ResourceLocation darkPTexture = new ResourceLocation("seccult:textures/entity/darktexture.png");
    Entity player;
    Vec3d vec3d;
    float yaw;
    float piatch;
    float height;
    float angle;
	private EnumHand hand = EnumHand.MAIN_HAND;
    public SuperLaserBeamFX(World worldIn, double posXIn, double posYIn, double posZIn, Entity player, float height) {
        super(worldIn, posXIn, posYIn, posZIn);
        this.player=player;
   
        vec3d =new Vec3d(player.getLookVec().x,
                player.getLookVec().y,
                player.getLookVec().z);
        yaw=player.rotationYaw;
        piatch=player.rotationPitch;
        this.height = height;
        this.posX = player.posX;
        this.posY = player.posY;
        this.posZ = player.posZ;
        this.setParticleTexture(LightFX.test2);
    }

    public SuperLaserBeamFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
    }

    @Override
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ) {
        int i = 15728880;
        int j = i % 65536;
        int k = i / 65536;
        angle+=10;
        if(angle >=360)
        	angle = 0;
        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
        GlStateManager.pushMatrix();
        GlStateManager.translate(posX - entityIn.posX, posY - entityIn.posY, posZ - entityIn.posZ);
        DrawChannel(
                (float) (posX),
                (float) (posY + (entityIn.height / 2)),
                (float) (posZ),
                (float) (vec3d.x*height+posX),
                (float) (vec3d.y*height+posY + (entityIn.height / 2)) ,
                (float) (vec3d.z*height+posZ), player.rotationYaw,player.rotationPitch);
        GlStateManager.popMatrix();
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
			    	GlStateManager.color(0, 1, 1, particleAlpha / 5);
			    else
			    	GlStateManager.color(0, 1, 1, particleAlpha / 10);
			    if(r == radius / 3)
			    	GlStateManager.color(1.0F, 1F, 1.0F, particleAlpha);

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
	
	public void DrawChannel(float x1, float y1, float z1, float x2, float y2, float z2, float yaw, float pitch){
		float dx = x2 - x1;
	    float dy = y2 - y1;
		float dz = z2 - z1;
		float distance = (float) Math.sqrt(dx*dx + dy*dy + dz*dz);
	    
		Vec3d QAQ = new Vec3d(0, 0, 0);
		
		if(player instanceof EntityLaserBeamBase)
		{
			EntityLaserBeamBase laser = (EntityLaserBeamBase) player;
			if(laser.getOwner() instanceof EntityPlayer)
			{
				Vec3d handVec = player.getLookVec().rotateYaw(hand == EnumHand.MAIN_HAND ? -0.4F : 0.4F);
				Vec3d lok = player.getLookVec();
				Vec3d position = new Vec3d(handVec.x, handVec.y + 1, handVec.z);
				QAQ = position.addVector(lok.x * 1, lok.y * 1, lok.z * 1);
			}
		}

	    GlStateManager.pushMatrix();
	    GlStateManager.translate(QAQ.x,QAQ.y,QAQ.z); 
	    GlStateManager.rotate(-yaw  * 0.017453292F * (180F / (float)Math.PI), 0.0F, 1.0F, 0.0F);
	    GlStateManager.rotate(pitch * 0.017453292F * (180F / (float)Math.PI), 1.0F, 0.0F, 0.0F);
	    Cylinder((float)particleMaxAge / 10, distance);
	    GlStateManager.popMatrix();
	}
    
    @Override
    public void onUpdate() {
    	if(player == null)
    		return;
    	if(!player.isEntityAlive())
    		setExpired();
    	
        this.posX = player.posX;
        this.posY = player.posY;
        this.posZ = player.posZ;
    }
}
