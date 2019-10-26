package testmod.seccult.entity;

import java.util.List;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.projectile.ProjectileHelper;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.client.FX.LightFX;
import testmod.seccult.entity.livings.EntityStand;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;

public class EntityLmr extends Entity
{
    public EntityLivingBase shootingEntity;
    private int ticksInAir;
    public double accelerationX;
    public double accelerationY;
    public double accelerationZ;
    
    public boolean isLaser; 
    
    public EntityLmr(World worldIn)
    {
        super(worldIn);
        this.setSize(0.2F, 0.2F);
        this.setNoGravity(true);
    }

    protected void entityInit()
    {
    }

    /**
     * Checks if the entity is in range to render.
     */
    @SideOnly(Side.CLIENT)
    public boolean isInRangeToRenderDist(double distance)
    {
        return true;
    }

    public EntityLmr(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ)
    {
        super(worldIn);
        this.setSize(0.2F, 0.2F);
        this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
        this.setPosition(x, y, z);
        double d0 = (double)MathHelper.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
        this.accelerationX = accelX / d0 * 0.1D;
        this.accelerationY = accelY / d0 * 0.1D;
        this.accelerationZ = accelZ / d0 * 0.1D;
        isLaser = false;
    }

    public EntityLmr(World worldIn, double x, double y, double z, double accelX, double accelY, double accelZ, boolean islaser, EntityLivingBase shooter)
    {
        super(worldIn);
        this.shootingEntity = shooter;
        this.setSize(0.2F, 0.2F);
        this.setLocationAndAngles(x, y, z, this.rotationYaw, this.rotationPitch);
        this.setPosition(x, y, z);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        isLaser = islaser;
        this.accelerationX = accelX;
        this.accelerationY = accelY;
        this.accelerationZ = accelZ;
    }
    
    public EntityLmr(World worldIn, EntityLivingBase shooter, double accelX, double accelY, double accelZ)
    {
        super(worldIn);
        this.shootingEntity = shooter;
        accelX = shooter.getLookVec().x;
        accelY = shooter.getLookVec().y;
        accelZ = shooter.getLookVec().z;
        this.setSize(0.2F, 0.2F);
        this.setLocationAndAngles(shooter.posX, shooter.posY + shooter.getEyeHeight(), shooter.posZ, shooter.rotationYaw, shooter.rotationPitch);
        this.setPosition(this.posX, this.posY, this.posZ);
        this.motionX = 0.0D;
        this.motionY = 0.0D;
        this.motionZ = 0.0D;
        double d0 = (double)MathHelper.sqrt(accelX * accelX + accelY * accelY + accelZ * accelZ);
        this.accelerationX = accelX / d0 * 0.1D;
        this.accelerationY = accelY / d0 * 0.1D;
        this.accelerationZ = accelZ / d0 * 0.1D;
        isLaser = false;
    }

    /**
     * Called to update the entity's position/logic.
     */
    public void onUpdate()
    {
        if(!isLaser) {
        	super.onUpdate();
        	notLaser();
        }
        else {
        	super.onUpdate();
        	isLaser();
        }
    }

    private void isLaser() {
        Entity entity = null;

        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(0.5));

	      for (int j1 = 0; j1 < list.size(); ++j1)
	      {
	        entity = (Entity)list.get(j1);
	        
	        if (entity != null)
	        {
	        	Ref(entity);
	        }
	        
	      }      
        
        if(this.ticksExisted > 20) {
        	this.setDead();
        }
		
	}

	protected void water_bubble()
	{
		 this.world.spawnParticle(EnumParticleTypes.SMOKE_NORMAL, this.posX - this.motionX * 0.25D, this.posY - this.motionY * 0.25D, this.posZ - this.motionZ * 0.25D, this.motionX, this.motionY, this.motionZ);
	}

    /**
     * Called when this EntityFireball hits a block or entity.
     */

    protected void notLaser() {
    	if (this.world.isRemote || (this.shootingEntity == null || !this.shootingEntity.isDead) && this.world.isBlockLoaded(new BlockPos(this)))
        {	
            Entity entity = null;
            
            ++this.ticksInAir;
            
            this.posX += this.motionX;
            this.posY += this.motionY;
            this.posZ += this.motionZ;
            ProjectileHelper.rotateTowardsMovement(this, 0.2F);

            if (this.isInWater())
            {
                for (int i = 0; i < 4; ++i)
                {
                	water_bubble();
                }
            }

            this.motionX += this.accelerationX;
            this.motionY += this.accelerationY;
            this.motionZ += this.accelerationZ;
            
            this.world.spawnEntity(new EntityLmr(this.world, this.posX, this.posY, this.posZ, this.accelerationX, this.accelerationY, this.accelerationZ, true, shootingEntity));
            
            pret();
            
            this.setPosition(this.posX, this.posY, this.posZ);            
            List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(0.5));

    	      for (int j1 = 0; j1 < list.size(); ++j1)
    	      {
    	        entity = (Entity)list.get(j1);
    	        
    	        if (entity != null)
    	        {
    	        	Ref(entity);
    	        }
    	       
    	     }      

            if(this.ticksExisted > 200) {
            	this.setDead();
            }
        }
        else
        {
            this.setDead();
        }
    }

    protected void pret() {
    	double[] pos2 = {this.lastTickPosX, this.lastTickPosY, this.lastTickPosZ};
    	
    	double[] vec = {this.accelerationX, this.accelerationY, this.accelerationZ};
		double[] pos = {this.posX, this.posY, this.posZ};
		float[] color = {1, 0.9F, 0.5F};
        NetworkHandler.getNetwork().sendToAllAround(new NetworkEffectData(pos, vec, color, this.rand.nextFloat() * 0.2F + 0.7F, 0),
        		new TargetPoint(dimension, pos[0], pos[1], pos[2], 32));
        NetworkHandler.getNetwork().sendToAllAround(new NetworkEffectData(pos2, vec, color, this.rand.nextFloat() * 0.2F + 0.7F, 0),
        		new TargetPoint(dimension, pos[0], pos[1], pos[2], 32));
    }
    
    protected void Ref(Entity hitEntity)
    {
      Entity shooter = this.shootingEntity;
      if(!(hitEntity == shooter) && hitEntity instanceof EntityLivingBase)
      {
    	  EntityLivingBase living = (EntityLivingBase) hitEntity;
    	  if(living instanceof EntityStand) {
    		  EntityStand stand = (EntityStand) living;
    		  stand.setDead();
    	  }
    	  
    	  if(!attackEntityAsMob(living))
    	  {
    		  if(living.getHealth() > 10F)
    		  living.setHealth(living.getHealth() - 10F);
    		  else {
    			  living.setHealth(0);
    			  living.onDeath(DamageSource.causeIndirectDamage(this, shootingEntity));
    		  }
    	  }	  
      }
      else if(!(hitEntity == shooter)){
    	  hitEntity.setDead();
      }
    }
    
    public boolean attackEntityAsMob(Entity par1Entity)
    {
         boolean var4 = false;
             var4 = par1Entity.attackEntityFrom(DamageSource.causeIndirectDamage(this, shootingEntity), 10F);
         return var4;
    }
	/**
     * Returns true if other Entities should be prevented from moving through this Entity.
     */
    public boolean canBeCollidedWith()
    {
        return true;
    }

    public float getCollisionBorderSize()
    {
        return 1.0F;
    }

    /**
     * Called when the entity is attacked.
     */
    public boolean attackEntityFrom(DamageSource source, float amount)
    {
        if (this.isEntityInvulnerable(source))
        {
            return false;
        }
        else
        {
            this.markVelocityChanged();

            if (source.getTrueSource() != null)
            {
                Vec3d vec3d = source.getTrueSource().getLookVec();

                if (vec3d != null)
                {
                    this.motionX = vec3d.x;
                    this.motionY = vec3d.y;
                    this.motionZ = vec3d.z;
                    this.accelerationX = this.motionX * 0.1D;
                    this.accelerationY = this.motionY * 0.1D;
                    this.accelerationZ = this.motionZ * 0.1D;
                }

                if (source.getTrueSource() instanceof EntityLivingBase)
                {
                    this.shootingEntity = (EntityLivingBase)source.getTrueSource();
                }

                return true;
            }
            else
            {
                return false;
            }
        }
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness()
    {
        return 900.0F;
    }

    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		
	}
}