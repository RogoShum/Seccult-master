package testmod.seccult.client.FX;

import java.util.ArrayList;

import org.lwjgl.util.glu.Cylinder;
import org.lwjgl.util.glu.GLU;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.OpenGlHelper;
import net.minecraft.entity.Entity;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class ThunderFX extends Particle
{
    private static ResourceLocation darkPTexture = new ResourceLocation("seccult:textures/entity/darktexture.png");

	private double TposX;
	private double TposY;
	private double TposZ;
	
	private float yaw;
	private float pitch;
	
	public static Cylinder cylinder = new Cylinder();
	
	private ArrayList<FXPoint> point = new ArrayList<FXPoint>();
	private float NewSpace;
	
    public ThunderFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double posx, double posy, double posz, float yaw, float pitch, float scale)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn);
        this.posX = xCoordIn;
        this.posY = yCoordIn;
        this.posZ = zCoordIn;
        this.TposX = posx;
        this.TposY = posy;
        this.TposZ = posz;
        this.yaw = yaw;
        this.pitch = pitch;
        this.particleScale = scale;
        this.particleAlpha = 1F;
        this.particleMaxAge = 10;
        this.setSize(1F, 1F);
        
        calculate();
    }
    
    protected void calculate()
    {
    	if(this.TposX != 0 && this.TposY != 0 && this.TposZ != 0)
    	{
    		float distance = getDis(this.posX, this.posY, this.posZ, this.TposX, this.TposY, this.TposZ);

            int space = (int)distance / 2; 
            if(space < 2)
            	space = 2;
            
            if(space > 15)
            	space = 15;
            
            NewSpace = distance / space;
            
            for(int s = 0; s < space; ++s)
            {
            	if(s == 0)
            	{
            		FXPoint point = new FXPoint(new Vec3d(this.posX, this.posY, this.posZ), yaw + (40 - this.rand.nextInt(80)), pitch + (30 - this.rand.nextInt(60)));
            		this.point.add(point);
            	}
            	else if(s == space - 1)
            	{
            		Vec3d vec = this.getVectorForRotation(this.point.get(s - 1).pitch, this.point.get(s - 1).yaw);
            		Vec3d pos = new Vec3d(this.point.get(s - 1).pos.x + vec.x * NewSpace, this.point.get(s - 1).pos.y + vec.y * NewSpace, this.point.get(s - 1).pos.z + vec.z * NewSpace);
            		FXPoint point = faceEntity(pos);
            		this.point.add(point);
            	}
            	else
            	{
            		Vec3d vec = this.getVectorForRotation(this.point.get(s - 1).pitch, this.point.get(s - 1).yaw);
            		Vec3d pos = new Vec3d(this.point.get(s - 1).pos.x + vec.x * NewSpace, this.point.get(s - 1).pos.y + vec.y * NewSpace, this.point.get(s - 1).pos.z + vec.z * NewSpace);
            		FXPoint point = new FXPoint(pos, yaw + (40 - this.rand.nextInt(80)), pitch + (30 - this.rand.nextInt(60)));
            		this.point.add(point);
            	}
            }
            
    	}
    }
    
    public ThunderFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn);
        this.posX = xCoordIn;
        this.posY = yCoordIn;
        this.posZ = zCoordIn;
        this.particleScale = 3.7F;
        this.particleAlpha = 1F;
        this.particleMaxAge = 20;
        this.setSize(1F, 1F);
    }
    
    @Override
    public int getFXLayer() {
    	return 3;
    }
    
	public void move(double x, double y, double z)
    {
        this.setBoundingBox(this.getBoundingBox().offset(x, y, z));
        this.resetPositionToBB();
    }

    /**
     * Renders the particle
     */
    public void renderParticle(BufferBuilder buffer, Entity entityIn, float partialTicks, float rotationX, float rotationZ, float rotationYZ, float rotationXY, float rotationXZ)
    {
    	int cc = 15728880;
        int j = cc % 65536;
        int k = cc / 65536;

        OpenGlHelper.setLightmapTextureCoords(OpenGlHelper.lightmapTexUnit, (float) j, (float) k);
            GlStateManager.pushMatrix();
            GlStateManager.depthMask(false);
            Minecraft.getMinecraft().getTextureManager().bindTexture(darkPTexture);
            GlStateManager.enableBlend();
            GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_CONSTANT_ALPHA);
            
            float blend = 0.5F - 0.5F / (this.particleMaxAge + 2 - this.particleAge) - 0.15F;
            
            GlStateManager.color(0.6F, 0.8F, 1F, blend);
            
			cylinder.setDrawStyle(GLU.GLU_FILL);		
			cylinder.setNormals(GLU.GLU_NONE);
            for(int i = 0; i < this.point.size(); ++i)
            {
            	if(i == 0)
            	{
            		Vec3d pos = this.point.get(i).pos;
            		GlStateManager.pushMatrix();
            		GlStateManager.translate(pos.x - entityIn.posX, pos.y - entityIn.posY, pos.z - entityIn.posZ);
            		GlStateManager.rotate(-this.point.get(i).yaw, 0, 1, 0);
            		GlStateManager.rotate(this.point.get(i).pitch, 1, 0, 0);
        			cylinder.draw(0, 0.015f * particleScale, NewSpace, 4, 1);
        			cylinder.draw(0, 0.035f * particleScale, NewSpace, 4, 1);
        			GlStateManager.popMatrix();
            	}
            	else if(i == this.point.size() - 1)
            	{
            		Vec3d pos = this.point.get(i).pos;
            		float finaldis = getDis(pos.x, pos.y, pos.z, this.TposX, this.TposY, this.TposZ);
            		
            		GlStateManager.pushMatrix();
            		GlStateManager.translate(pos.x - entityIn.posX, pos.y - entityIn.posY, pos.z - entityIn.posZ);
            		GlStateManager.rotate(-this.point.get(i).yaw, 0, 1, 0);
            		GlStateManager.rotate(this.point.get(i).pitch, 1, 0, 0);
        			cylinder.draw(0.015F * particleScale, 0, finaldis, 4, 1);
        			cylinder.draw(0.035F * particleScale, 0, finaldis, 4, 1);
        			GlStateManager.popMatrix();
            	}
            	else
            	{
            		Vec3d pos = this.point.get(i).pos;
            		GlStateManager.pushMatrix();
            		GlStateManager.translate(pos.x - entityIn.posX, pos.y - entityIn.posY, pos.z - entityIn.posZ);
            		GlStateManager.rotate(-this.point.get(i).yaw, 0, 1, 0);
            		GlStateManager.rotate(this.point.get(i).pitch, 1, 0, 0);
        			cylinder.draw(0.015F * particleScale, 0.015f * particleScale, NewSpace, 4, 1);
        			cylinder.draw(0.035F * particleScale, 0.035f * particleScale, NewSpace, 4, 1);
        			GlStateManager.popMatrix();
            	}
        	}
    	GlStateManager.disableBlend();
    	GlStateManager.depthMask(true);
    	GlStateManager.popMatrix();
    }
    
    protected FXPoint faceEntity(Vec3d vec)
    {
        double d0 = this.TposX - vec.x;
        double d2 = this.TposZ - vec.z;
        double d1 = this.TposY - vec.y;

        double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
        float f = (float)(MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90;
        float f1 = (float)(-(MathHelper.atan2(d1, d3) * (180D / Math.PI)));

        return new FXPoint(vec, f, f1);
    }
    
    protected float getDis(double posx1, double posy1, double posz1, double posx2, double posy2, double posz2)
    {
    	float f = (float)(posx1 - posx2);
        float f1 = (float)(posy1 - posy2);
        float f2 = (float)(posz1 - posz2);
        
        return MathHelper.sqrt(f * f + f1 * f1 + f2 * f2);
    }
    
    protected final Vec3d getVectorForRotation(float pitch, float yaw)
    {
        float f = MathHelper.cos(-yaw * 0.017453292F - (float)Math.PI);
        float f1 = MathHelper.sin(-yaw * 0.017453292F - (float)Math.PI);
        float f2 = -MathHelper.cos(-pitch * 0.017453292F);
        float f3 = MathHelper.sin(-pitch * 0.017453292F);
        return new Vec3d((double)(f1 * f2), (double)f3, (double)(f * f2));
    }
    
    @Override
    public int getBrightnessForRender(float partialTick)
    {
      final int FULL_BRIGHTNESS_VALUE = 0xf000f0;
      return FULL_BRIGHTNESS_VALUE;
    }
    
	@Override
	public void onUpdate() {
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		if (this.particleAge++ >= this.particleMaxAge)
		{
			this.setExpired();
		}
	}

    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory
        {
            public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_)
            {
                return new ThunderFX(worldIn, xCoordIn, yCoordIn, zCoordIn);
            }
        }
    
    public class FXPoint
    {
    	private Vec3d pos;
    	private float yaw;
    	private float pitch;
    	
    	public FXPoint(Vec3d pos, float yaw, float pitch) 
    	{
    		this.pos = pos;
    		this.yaw = yaw;
    		this.pitch = pitch;
		}
    }
}