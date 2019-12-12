package testmod.seccult.client.FX;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.entity.render.ParticleRender;

public class ParticleFX {
	protected float scale = 0;
	protected float scaleBase = 0;
	protected float blend = 0;
	protected float particleRed = 1;
	protected float particleGreen = 1;
	protected float particleBlue = 1;
	protected float particleMaxAge = 1;
	protected double prevPosX;
    protected double prevPosY;
    protected double prevPosZ;
    protected double posX;
    protected double posY;
    protected double posZ;
    protected double motionX;
    protected double motionY;
    protected double motionZ;
    protected int ticksExisted;
	public ParticleType type;
	protected boolean isDead;
	public Random rand = Seccult.rand;

	public final static float[] defaultColor = {1F, 1F, 1F};

	public ParticleFX(ParticleType type, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, float scale) {
		this.scaleBase = (this.rand.nextFloat() * 0.2F + scale) / 4F;
		this.motionX = xSpeedIn;
        this.motionY = ySpeedIn;
        this.motionZ = zSpeedIn;
        this.scale = this.scaleBase;
        this.setPosition(xCoordIn, yCoordIn, zCoordIn);
        this.particleMaxAge = (int)(30 * scale);
        if(particleMaxAge == 0)
        	particleMaxAge = 1;
        
        this.type = type;
	}
	
	public void setRBGColorF(float red, float green, float blue)
	{
		this.particleRed = red;
        this.particleGreen = green;
        this.particleBlue = blue;
	}

	public void setMaxAge(int age)
	{
		this.particleMaxAge = age;
	}
	
	public void onUpdate() {
		this.particleRed += (0.5 - rand.nextFloat()) *0.0005;
		this.particleGreen += (0.5 - rand.nextFloat()) *0.0005;
		this.particleBlue += (0.5 - rand.nextFloat()) *0.0005;
    	if(this.ticksExisted < this.particleMaxAge / 2)
    	{
    		float f = (float)this.ticksExisted / (float)this.particleMaxAge * 2;
    		f = 1.0F - f;
    		f = f * f;
    		f = 1.0F - f;
    		this.scale = this.scaleBase * f;
    	}
    	else if(this.ticksExisted >= this.particleMaxAge / 2 && scale > 0)
    	{
    		this.scale -= 0.01;
    	}
		
		//this.scale = 1F;
    	this.blend = (float)(particleMaxAge - ticksExisted) / (float)particleMaxAge;
    	//this.blend = 1F;
    	if(this.ticksExisted++ > this.particleMaxAge)
    	{
    		this.isDead = true;
    		return;
    	}
    	
    	this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;

		this.motionY -= 0.04D * (double)0;
		this.setPosition(this.posX + this.motionX, this.posY + this.motionY, this.posZ + this.motionZ);
		this.motionX *= 0.9800000190734863D;
		this.motionY *= 0.9800000190734863D;
		this.motionZ *= 0.9800000190734863D;
	}
	
	public void setPosition(double x, double y, double z) {
		this.posX = x;
		this.posY = y;
		this.posZ = z;
	}

	public void setDead()
	{
		this.isDead = true;
	}
	
	@SideOnly(Side.CLIENT)
	public void render(float partialTicks)
	{
		double x = this.prevPosX + (this.posX - this.prevPosX) * (double)partialTicks;
		double y = this.prevPosY + (this.posY - this.prevPosY) * (double)partialTicks;
		double z = this.prevPosZ + (this.posZ - this.prevPosZ) * (double)partialTicks;
		
		if(this.type == ParticleType.Light)
			ParticleRender.renderFX(ModFX.Light, x, y, z, this.scale, blend, this.getColor());
		else if(this.type == ParticleType.Pentagon)
			ParticleRender.renderFX(ModFX.Pentagon, x, y, z, this.scale, blend, this.getColor());
		else if(this.type == ParticleType.Star)
			ParticleRender.renderFX(ModFX.Star, x, y, z, this.scale, blend, this.getColor());
		else if(this.type == ParticleType.ATField)
			ParticleRender.renderFX(ModFX.ATField, x, y, z, this.scale, blend, this.getColor());
		else if(this.type == ParticleType.Rainbow)
			ParticleRender.renderFX(ModFX.Rainbow, x, y, z, this.scale, blend, this.getColor());
	}
	
    public float getMySize()
    {
        return this.scale;
    }

    public float getMyBlend()
    {
        return this.blend;
    }
    
    public float[] getColor()
    {
        return new float[] {particleRed, particleGreen, particleBlue};
    }

    public boolean isDead()
    {
        return this.isDead;
    }
    
	public enum ParticleType
	{
		Light, Pentagon, Star, ATField, Rainbow
	}
}
