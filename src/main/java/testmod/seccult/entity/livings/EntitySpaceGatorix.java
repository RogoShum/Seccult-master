package testmod.seccult.entity.livings;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import testmod.seccult.entity.livings.landCreature.EntityDreamPop;
import testmod.seccult.init.ModDamage;
import testmod.seccult.init.ModSounds;

public class EntitySpaceGatorix extends EntityThrowable {
	private static final DataParameter<Float> GATORIX_STATE = EntityDataManager.<Float>createKey(EntitySpaceGatorix.class, DataSerializers.FLOAT);
	private float damage = 25;
	private EntityLivingBase victim;
	private int ExistedLimit;
	public EntitySpaceGatorix(World worldIn) {
		super(worldIn);
		this.setSize(0.4F, 0.4F);
		this.ExistedLimit = 300;
		this.damage = 25;
	}
	
	public EntitySpaceGatorix(World worldIn, EntityLivingBase throwerIn) {
		super(worldIn, throwerIn);
		this.setSize(0.4F, 0.4F);
		this.setNoGravity(true);
		this.ExistedLimit = 300;
		this.damage = 25;
	}

	public EntitySpaceGatorix(World worldIn, EntityLivingBase throwerIn, EntityLivingBase victim) {
		this(worldIn, throwerIn);
		this.victim = victim;
		this.setNoGravity(false);
		this.ExistedLimit = 300;
		this.damage = 25;
	}
	
	public void setCharged(float size)
	{
		this.setState(this.height += size);
	}
	
	@Override
	protected void entityInit() 
	{
		super.entityInit();
		this.dataManager.register(GATORIX_STATE, 0F);
	}
	
	protected void setState(float id) 
	{
		this.dataManager.set(GATORIX_STATE, id);
	}
	  
	protected float getState() 
	{
		return this.dataManager.get(GATORIX_STATE).floatValue();
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(this.getState() != 0)
		{
			this.setSize(this.getState(), this.getState());
			this.ExistedLimit = 1000;
		}
		
		if(this.ticksExisted == 1)
		{
			this.world.playSound((EntityPlayer)null, this.getPosition(), ModSounds.gatorix_spawn, SoundCategory.NEUTRAL, 2F, 1F);
		}
		
		if(this.ticksExisted == 15)
			this.world.playSound((EntityPlayer)null, this.getPosition(), ModSounds.gatorix_flying, SoundCategory.NEUTRAL, 0.2F, 0.5F);
		
		if(this.ticksExisted % 25 == 0)
			this.world.playSound((EntityPlayer)null, this.getPosition(), ModSounds.gatorix_flying, SoundCategory.NEUTRAL, 0.2F, 0.5F);
		
		if(victim != null)
		{
			Moveto(victim.posX, victim.posY, victim.posZ, 0.02);
		}
		
		if(this.ticksExisted > ExistedLimit)
			this.setDead();
		
		collideWithNearbyEntities();
	}
	
    protected void collideWithNearbyEntities()
    {
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(0.5D));

        if (!list.isEmpty())
        {
            for (int l = 0; l < list.size(); ++l)
            {
                Entity entity = list.get(l);
                if(entity instanceof EntitySpaceGatorix)
                	this.applyEntityCollision(entity);
                else if(!(entity instanceof EntityLivingBase) || entity instanceof EntityThrowable || (entity.height+entity.width) / 2 < 0.5F)
                {
                	entity.setDead();
                }
            }
        }
    }
	
    public void applyEntityCollision(Entity entityIn)
    {
                double d0 = entityIn.posX - this.posX;
                double d1 = entityIn.posZ - this.posZ;
                double d2 = MathHelper.absMax(d0, d1);

                if (d2 >= 0.009999999776482582D)
                {
                    d2 = (double)MathHelper.sqrt(d2);
                    d0 = d0 / d2;
                    d1 = d1 / d2;
                    double d3 = 1.0D / d2;

                    if (d3 > 1.0D)
                    {
                        d3 = 1.0D;
                    }

                    d0 = d0 * d3;
                    d1 = d1 * d3;
                    d0 = d0 * 0.05000000074505806D;
                    d1 = d1 * 0.05000000074505806D;
                    d0 = d0 * (double)(1.0F - this.entityCollisionReduction);
                    d1 = d1 * (double)(1.0F - this.entityCollisionReduction);

                    this.addVelocity(-d0, 0.0D, -d1);
                    entityIn.addVelocity(d0, 0.0D, d1);
                }
    }
    
	protected void Moveto(double x, double y, double z, double speed) {
	       this.motionX += (Math.signum(x - this.posX) - this.motionX) * speed;
	       this.motionY += (Math.signum(y - this.posY) - this.motionY) * speed;
	       this.motionZ += (Math.signum(z - this.posZ) - this.motionZ) * speed;
	       
	       this.posX += this.motionX;
	       this.posY += this.motionY;
	       this.posZ += this.motionZ;

	       this.setPosition(this.posX, this.posY, this.posZ);
	}
	
	@Override
	protected void onImpact(RayTraceResult result) 
	{
		if (!this.world.isRemote && result.entityHit != null)
        {
			if(result.entityHit instanceof EntityDreamPop)
				return;
			
			if(this.ExistedLimit < 500 && this.ticksExisted > 200)
				damage -= (ticksExisted - 200) / 10;
			
			if(this.ExistedLimit > 500)
			{
				 if(this.ticksExisted < 900)
					 damage += (0.6F + this.getState()) * damage;
				 else
					 damage -= (ticksExisted - 900) / 5;
			}
				
			
			result.entityHit.hurtResistantTime = -1;
			if(this.thrower != null)
				result.entityHit.attackEntityFrom(ModDamage.causeForbiddenEntityDamage(this, this.thrower), damage);
			else
				result.entityHit.attackEntityFrom(ModDamage.causeForbiddenEntityDamage(this), damage);
			if(result.entityHit instanceof Entity && (result.entityHit.height+result.entityHit.width) / 2 < 0.5F)
				result.entityHit.setDead();
			this.setDead();
			
			if(this.ExistedLimit > 500)
			this.world.createExplosion(this, this.posX, this.posY, this.posZ, (this.damage - 20), true);
        }
		
		if (!this.world.isRemote && result.getBlockPos() != null)
        {
			this.world.destroyBlock(result.getBlockPos(), true);
			if(this.ExistedLimit > 500)
			this.world.createExplosion(this, this.posX, this.posY, this.posZ, (this.damage - 20), true);
			this.setDead();
        }
	}

    protected float getGravityVelocity()
    {
        return -0.015F;
    }
	
	@Override
	public float getEyeHeight() {
		return this.height / 2;
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		compound.setFloat("GatorixDamage", damage);
        compound.setFloat("GatorixState", this.getState());
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		this.damage = compound.getFloat("GatorixDamage");
		this.setCharged(compound.getFloat("GatorixState"));
	}
}
