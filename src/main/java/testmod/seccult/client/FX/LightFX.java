package testmod.seccult.client.FX;

import net.minecraft.client.particle.IParticleFactory;
import net.minecraft.client.particle.Particle;
import net.minecraft.client.renderer.BufferBuilder;
import net.minecraft.client.renderer.texture.TextureAtlasSprite;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class LightFX extends Particle
{
    private final float lightParticleScale;

	public static TextureAtlasSprite test2;

    public LightFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn)
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
        this.particleScale = this.rand.nextFloat() * 0.2F + 0.7F;
        this.particleAlpha = 0.5F; // So MC renders us on the alpha layer, value not actually used
        this.lightParticleScale = this.particleScale;
        this.particleMaxAge = 10;
        this.particleRed = 1F;
        this.particleGreen = 0.9F;
        this.particleBlue = 0.5F;
        this.setParticleTexture(test2);
        this.setSize(0.1F, 0.1F);
    }
    
    public LightFX(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, int a)
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
        this.particleScale = this.rand.nextFloat() * 0.2F + 0.3F;
        this.particleAlpha = 0.5F; // So MC renders us on the alpha layer, value not actually used
        this.lightParticleScale = this.particleScale;
        this.particleMaxAge = 10;
        this.particleRed = 1F;
        this.particleGreen = 0.0F;
        this.particleBlue = 0.0F;
        this.setParticleTexture(test2);
        this.setSize(0.1F, 0.1F);
    }
    
    @Override
    public int getFXLayer()
    {
        return 1;
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
    	//Minecraft.getMinecraft().renderEngine.bindTexture(texture);
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

      // if you want the brightness to be the local illumination (from block light and sky light) you can just use
      //  Entity.getBrightnessForRender() base method, which contains:
      //    BlockPos blockpos = new BlockPos(this.posX, this.posY, this.posZ);
      //    return this.worldObj.isBlockLoaded(blockpos) ? this.worldObj.getCombinedLight(blockpos, 0) : 0;
    }

    /*public void onUpdate()
    {
        this.prevPosX = this.posX;
        this.prevPosY = this.posY;
        this.prevPosZ = this.posZ;
        float f = (float)this.particleAge / (float)this.particleMaxAge;
        float f1 = -f + f * f * 2.0F;
        float f2 = 1.0F - f1;
        this.posX = this.posX + this.motionX * (double)f2;
        this.posY = this.posY + this.motionY * (double)f2;
        this.posZ = this.posZ + this.motionZ * (double)f2;

        if (this.particleAge++ >= this.particleMaxAge)
        {
            this.setExpired();
        }
    }*/
    
    
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
                return new LightFX(worldIn, xCoordIn, yCoordIn, zCoordIn, xSpeedIn, ySpeedIn, zSpeedIn);
            }
        }
}