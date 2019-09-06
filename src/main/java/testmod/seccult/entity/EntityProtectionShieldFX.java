package testmod.seccult.entity;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import testmod.seccult.magick.magickState.StateManager;
import testmod.seccult.potions.ModPotions;

public class EntityProtectionShieldFX extends Entity{

	protected UUID upperUUID = null;
	protected long uppermost = 0;
	protected long upperleast = 0;
	protected EntityLivingBase owner;
	private float Blend;
	private float Scale;
	private int strengh;
	
	private static final DataParameter<Float> IBlend = EntityDataManager.<Float>createKey(EntityProtectionShieldFX.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> IScale = EntityDataManager.<Float>createKey(EntityProtectionShieldFX.class, DataSerializers.FLOAT);
	
	public EntityProtectionShieldFX(World worldIn) {
		super(worldIn);
		this.setSize(0.5f, 0.5f);
		this.setNoGravity(true);
		this.collided = false;
		this.isImmuneToFire = true;
		this.setEntityInvulnerable(true);
	}

	public void setOwner(EntityLivingBase owner, int strengh)
	{
		this.owner = owner;
		this.strengh = strengh;
		this.upperUUID = owner.getUniqueID();
		this.uppermost = owner.getUniqueID().getMostSignificantBits();
		this.upperleast = owner.getUniqueID().getLeastSignificantBits();
		adjustSize();
		this.setSize(this.getScale() * 0.8F, this.getScale());
	}
	
	public EntityLivingBase getOwner()
	{
		return this.owner;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(this.Blend > 0.3)
			this.Blend -= 0.01;
		if(this.Blend < 0.3)
			this.Blend = 0.3F;
		
		if(!this.world.isRemote && this.owner == null)
		{
			if(this.ticksExisted < 20)
				loadUUID();
			else
				this.setDead();
		}

		if(this.owner != null)
		{
			owner.addPotionEffect(new PotionEffect(ModPotions.protection, 10, strengh, false, false));
			if(StateManager.CheckIfStatedSafe(owner, StateManager.Protection))
			{
				this.Blend = 0.6f;
			}
				adjustSize();
				
				double lookX = owner.getLookVec().x;
				double lookZ = owner.getLookVec().z;
				
			this.setPositionAndRotation(owner.posX + lookX * this.getScale() / 8, owner.posY + owner.height / 2, owner.posZ + lookZ * this.getScale() / 8, owner.rotationYaw, owner.rotationPitch);
		}

		this.setSize(this.getScale() * 0.8F, this.getScale());
		
		if(!this.world.isRemote)
			this.setBlend(this.Blend);
		
		collideWithNearbyEntities();
		
		if(this.ticksExisted > 5)
		this.setDead();
	}
	
	protected void adjustSize()
	{
		float scale;
	if(owner.height > owner.width)
		scale = owner.height;
	else if(owner.height < owner.width)
		scale = owner.width;
	else
		scale =owner.height;
	
		if(this.Scale < scale)
			this.Scale += 0.05;
		else
			this.Scale -= 0.01;
		
		if(this.Scale - scale < 0.1F)
			this.Scale = scale;
		
		this.setScale(this.Scale);
	}
	
	@Override
    protected void entityInit()
    {	
        this.dataManager.register(IBlend, Float.valueOf(0));
        this.dataManager.register(IScale, Float.valueOf(0));
    }

	private void loadUUID() {
        if(this.upperUUID != null) {
	        double range = 128D;
	        List<EntityLivingBase> connections = this.getEntityWorld().getEntitiesWithinAABB(EntityLivingBase.class, this.getEntityBoundingBox().grow(range, range, range));
	        Iterator<EntityLivingBase> possibleConnections = connections.iterator();
	        while(possibleConnections.hasNext()) {
	        	EntityLivingBase possibleConnection = (EntityLivingBase)possibleConnections.next();
	            if(possibleConnection.getUniqueID().equals(this.upperUUID)) {
	            	this.owner = possibleConnection;
	            	break;
	            }
	        }
        }
	}
	
    public void setScale(float scale)
    {
        this.dataManager.set(IScale, Float.valueOf(scale));
    }
    
    public float getScale()
    {
        return this.dataManager.get(IScale).floatValue();
    }

    public void setBlend(float blend)
    {
        this.dataManager.set(IBlend, Float.valueOf(blend));
    }
    
    public float getBlend()
    {
        return this.dataManager.get(IBlend).floatValue();
    }
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		this.Blend = 0.5F;
		return false;
	}
	
    protected void collideWithNearbyEntities()
    {
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(this.Scale * 1.5F));

        if (!list.isEmpty())
        {
            for (int l = 0; l < list.size(); ++l)
            {
                Entity entity = list.get(l);
                if(this.owner != null && entity != this.owner)
                {
                	this.applyEntityCollision(entity);
                	this.Blend = 0.4F;
                }
            }
        }
    }
	
	@Override
	public void applyEntityCollision(Entity entityIn) {
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

                    if (!entityIn.isBeingRidden())
                    {
                        entityIn.addVelocity(d0 * 5, 0.0D, d1 * 5);
                    }
                }
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {}
}
