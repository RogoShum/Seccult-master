package testmod.seccult.entity.projectile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.client.FX.ModFX;
import testmod.seccult.client.FX.ParticleFX;
import testmod.seccult.entity.EntityIMCircle;
import testmod.seccult.magick.MagickCompiler;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.TransPoint;

public class EntityIMProjectile extends EntityThrowable{
	private static final DataParameter<Float> Size = EntityDataManager.<Float>createKey(EntityIMProjectile.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> red = EntityDataManager.<Float>createKey(EntityIMProjectile.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> green = EntityDataManager.<Float>createKey(EntityIMProjectile.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> blue = EntityDataManager.<Float>createKey(EntityIMProjectile.class, DataSerializers.FLOAT);
	
	private  NBTTagCompound LoadMagick = new NBTTagCompound();
	private  NBTTagList LoadSelect = new NBTTagList();

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
	
	@Override
	protected void entityInit() {
		this.dataManager.register(Size, Float.valueOf(1));
		this.dataManager.register(red, Float.valueOf(1));
		this.dataManager.register(green, Float.valueOf(1));
		this.dataManager.register(blue, Float.valueOf(1));
	}

	public float getMySize()
    {
        return this.dataManager.get(Size).floatValue();
    }
    
	public void setRender(float size)
    {
        this.dataManager.set(Size, Float.valueOf(size));
    }
	
    public float getColorRed()
    {
        return this.dataManager.get(red).floatValue();
    }
    
    public float getColorBlue()
    {
        return this.dataManager.get(green).floatValue();
    }
    
    public float getColorGreen()
    {
        return this.dataManager.get(blue).floatValue();
    }
    
    public void setColor(float particleRedIn, float particleGreenIn, float particleBlueIn)
    {
        this.dataManager.set(red, Float.valueOf(particleRedIn));
        this.dataManager.set(green, Float.valueOf(particleGreenIn));
        this.dataManager.set(blue, Float.valueOf(particleBlueIn));
    }
    
}
