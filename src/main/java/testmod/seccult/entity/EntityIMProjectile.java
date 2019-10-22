package testmod.seccult.entity;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import testmod.seccult.client.FX.LightFX;
import testmod.seccult.client.FX.PentagonFX;
import testmod.seccult.magick.MagickCompiler;

public class EntityIMProjectile extends EntityThrowable{
	private  NBTTagCompound LoadMagick = new NBTTagCompound();
	private  NBTTagList LoadSelect = new NBTTagList();
	private float particleRedIn;
	private float particleGreenIn;
	private float particleBlueIn;
	
	private float scale;
	
	private Particle core;
	private boolean doBlock;
	private boolean doEntity;
	
    public EntityIMProjectile(World worldIn) {
		super(worldIn);
	}

	public void IMProjectile(double x, double y, double z)
    {
        this.setPosition(x, y, z);
    }
	
    public void IMProjectile(EntityLivingBase throwerIn, Entity pos)
    {
    	IMProjectile(pos.posX, pos.posY + (double)pos.getEyeHeight() - 0.10000000149011612D, pos.posZ);
        this.thrower = throwerIn;
    }
    
	public void setEntityIMProjectile(Entity throwerIn, Entity pos) {
		if(throwerIn instanceof EntityLivingBase)
			IMProjectile((EntityLivingBase)throwerIn, pos);
		else
			IMProjectile(pos.posX, pos.posY + (pos.height / 2), pos.posZ);
	}

	public void setData(NBTTagCompound magick, NBTTagList select, boolean doEntity, boolean doBlock)
	{
		this.LoadMagick = magick;
		this.LoadSelect = select;
		this.doEntity = doEntity;
		this.doBlock = doBlock;
	}
	
	public void setScale(float s)
	{
		scale = s;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(!this.world.isRemote) {
			for(int c = 0; c < 5; ++c)
			{
			double x = 0.5 - world.rand.nextFloat();
			double y = 0.5 - world.rand.nextFloat();
			double z = 0.5 - world.rand.nextFloat();

			Particle fx2 = new LightFX(this.world, this.posX, this.posY, this.posZ, x / 25, y / 25, z / 25, scale / 2);
			fx2.setRBGColorF(particleRedIn, particleGreenIn, particleBlueIn);
			Minecraft.getMinecraft().effectRenderer.addEffect(fx2);
			}
			if(core == null) {
			PentagonFX fx = new PentagonFX(this.world, this.posX, this.posY, this.posZ, 0, 0, 0, scale);
			fx.setRBGColorF(particleRedIn, particleGreenIn, particleBlueIn);
			fx.setTest(true);
			this.core = fx;
			Minecraft.getMinecraft().effectRenderer.addEffect(fx);
			}
			if(this.core != null)
			{
				this.core.setPosition(this.posX, this.posY, this.posZ);
			}
			}
	}
	
	@Override
	public boolean isEntityAlive() {
		if(this.isDead && !this.world.isRemote && core != null)
		{
			core.setExpired();
			core = null;
		}
        return !this.isDead;
	}
	
	@Override
	protected void onImpact(RayTraceResult result) {
		if (!this.world.isRemote && result.hitVec != null && !result.hitVec.equals(new Vec3d(0, 0, 0)))
        {
			NBTTagCompound newMagickNBT = LoadMagick.copy();
			newMagickNBT.setTag("BlockHit", this.newDoubleNBTList(result.hitVec.x, result.hitVec.y - 1, result.hitVec.z));
			MagickCompiler newMagick = new MagickCompiler();
			newMagick.pushMagickData(newMagickNBT, LoadSelect, this.thrower, this.doEntity, this.doBlock);
        }

		if (!this.world.isRemote && result.entityHit != null)
        {
			NBTTagCompound newMagickNBT = LoadMagick.copy();
			newMagickNBT.setUniqueId("EntityHit", result.entityHit.getUniqueID());
			MagickCompiler newMagick = new MagickCompiler();
			newMagick.pushMagickData(newMagickNBT, LoadSelect, this.thrower, this.doEntity, this.doBlock);
        }
		
        if (!this.world.isRemote)
        {
            this.world.setEntityState(this, (byte)3);
            this.setDead();
        }
	}

	public void setColor(float particleRedIn, float particleGreenIn, float particleBlueIn) {
		this.particleRedIn = particleRedIn;
		this.particleGreenIn = particleGreenIn;
		this.particleBlueIn = particleBlueIn;
	}

}
