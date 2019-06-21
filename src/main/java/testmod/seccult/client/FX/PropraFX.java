package testmod.seccult.client.FX;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class PropraFX extends Particle
{
    private final float lightParticleScale;
    private int ID;
    public PropraFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.motionX = xSpeedIn;
        this.motionY = ySpeedIn;
        this.motionZ = zSpeedIn;
        this.posX = xCoordIn;
        this.posY = yCoordIn;
        this.posZ = zCoordIn;
        this.width = 0.1F;
        this.height = 0.1F;
    	this.particleScale = this.rand.nextFloat() * 0.5F + 0.5F;
        this.particleAlpha = 0.5F; // So MC renders us on the alpha layer, value not actually used
        this.lightParticleScale = this.particleScale;
        this.particleMaxAge = 35;
        this.setSize(0.1F, 0.1F);
        this.setParticleTextureIndex((int)(Math.random() * 8.0D));
    }
    
    public PropraFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int id)
    {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
        this.motionX = xSpeedIn;
        this.motionY = ySpeedIn;
        this.motionZ = zSpeedIn;
        this.posX = xCoordIn;
        this.posY = yCoordIn;
        this.posZ = zCoordIn;
        this.width = 0.1F;
        this.height = 0.1F;
        this.ID = id;
        if(this.ID == 12)
        	this.particleScale = this.rand.nextFloat() * 0.2F + 0.1F;
        else
        	this.particleScale = this.rand.nextFloat() * 0.5F + 0.5F;
        this.particleAlpha = 0.5F; // So MC renders us on the alpha layer, value not actually used
        this.lightParticleScale = this.particleScale;
        this.particleMaxAge = 20;
        if(this.ID == 12)
        	this.particleMaxAge = 10;
        this.setSize(0.1F, 0.1F);
        this.setParticleTextureIndex((int)(Math.random() * 8.0D));
    }
    
    @Override
    public int getFXLayer()
    {
        return 0;
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
        float f = ((float)this.particleAge + partialTicks) / (float)this.particleMaxAge;
        f = 1.0F - f;
        f = f * f;
        f = 1.0F - f;
        this.particleScale = this.lightParticleScale * f;
        super.renderParticle(buffer, entityIn, partialTicks, rotationX, rotationZ, rotationYZ, rotationXY, rotationXZ);
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

		this.motionY -= 0.04D * (double)this.particleGravity;
		this.move(this.motionX, this.motionY, this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;
	}
	
    @SideOnly(Side.CLIENT)
    public static class Factory implements IParticleFactory
        {
            public Particle createParticle(int particleID, World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int... p_178902_15_)
            {
                return new PropraFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
            }
        }
}