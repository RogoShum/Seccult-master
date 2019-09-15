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

public class EntityShieldFX extends Entity{

	protected UUID upperUUID = null;
	protected long uppermost = 0;
	protected long upperleast = 0;
	protected EntityLivingBase owner;
	private float Blend;
	private float Scale;
	private int time;
	private float scaleMult;
	
	private float OwnerHleath;
	
	private static final DataParameter<Float> IBlend = EntityDataManager.<Float>createKey(EntityShieldFX.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> IScale = EntityDataManager.<Float>createKey(EntityShieldFX.class, DataSerializers.FLOAT);
	private static final DataParameter<Boolean> Realease = EntityDataManager.<Boolean>createKey(EntityShieldFX.class, DataSerializers.BOOLEAN);
	
	public EntityShieldFX(World worldIn) {
		super(worldIn);
		this.setSize(0.5f, 0.5f);
		this.setNoGravity(true);
		this.noClip = true;
		this.collided = false;
		this.isImmuneToFire = true;
		this.setEntityInvulnerable(true);
	}

	public void setOwner(EntityLivingBase owner, int time, float scaleMult)
	{
		this.owner = owner;
		this.OwnerHleath = owner.getHealth();
		this.time = time * 20;
		this.scaleMult = scaleMult;
		this.upperUUID = owner.getUniqueID();
		this.uppermost = owner.getUniqueID().getMostSignificantBits();
		this.upperleast = owner.getUniqueID().getLeastSignificantBits();
	}
	
	public EntityLivingBase getOwner()
	{
		return this.owner;
	}
	
	@Override
	public void setDead() {
		this.isDead = false;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(!this.getRrelease())
			this.isDead = false;
		else
			this.isDead = true;
		
		if(this.Blend > 0)
			this.Blend -= 0.01;
		if(this.Blend < 0)
			this.Blend = 0.0F;
		
		if(!this.world.isRemote && this.ticksExisted % 20 == 0)
			System.out.println(this.time - this.ticksExisted);
		
		if(!this.world.isRemote && this.owner == null)
		{
			if(this.ticksExisted < 20)
				loadUUID();
			else
				this.setRrelease();
		}

		if(this.owner != null)
		{
			if(owner.getHealth() < this.OwnerHleath)
			{
				owner.isDead = false;
				owner.deathTime = -1;
				owner.setHealth(this.OwnerHleath);
			}
			
			if(!owner.isEntityAlive() || owner.isDead)
			{
				owner.isDead = false;
				owner.deathTime = -1;
				owner.setHealth(this.OwnerHleath);
				if(this.world.isRemote)
					this.world.spawnEntity(owner);
			}
			owner.addPotionEffect(new PotionEffect(ModPotions.shield, 10, 6, false, false));
			if(StateManager.CheckIfStatedSafe(owner, StateManager.Shield))
			{
				this.Blend = 0.5f;
			}
			
			if(this.time > this.ticksExisted)
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
				
				this.setScale(this.Scale * scaleMult);
				this.setSize(this.Scale * scaleMult, this.Scale * scaleMult);
			}
			else
			{
				this.Scale -= 0.01;
				this.setBlend(0.15F);
				this.setScale(this.Scale * scaleMult);
			}

			this.setPosition(owner.posX, owner.posY + owner.height / 2, owner.posZ);
			
			if(this.ticksExisted > 20 && this.getScale() < 0)
			{
				this.setRrelease();
			}
		}

		if(!this.world.isRemote)
			this.setBlend(this.Blend);
		
		collideWithNearbyEntities();
	}
	
	@Override
    protected void entityInit()
    {	
        this.dataManager.register(IBlend, Float.valueOf(0));
        this.dataManager.register(IScale, Float.valueOf(0));
        this.dataManager.register(Realease, Boolean.valueOf(false));
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
    
	public void setRrelease() {
		this.dataManager.set(Realease, true);
	}
    
    public boolean getRrelease()
    {
        return this.dataManager.get(Realease).booleanValue();
    }
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		this.Blend = 0.4F;
		return false;
	}
	
    protected void collideWithNearbyEntities()
    {
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(this.Scale * this.scaleMult));

        if (!list.isEmpty())
        {
            for (int l = 0; l < list.size(); ++l)
            {
                Entity entity = list.get(l);
                if(this.owner != null && entity != this.owner && !(entity instanceof EntityBloodBeam))
                {
                	this.applyEntityCollision(entity);
                	this.Blend = 0.2F;
                }
                
    			if(entity instanceof EntityShieldFX)
            	{
            		EntityShieldFX shield = (EntityShieldFX) entity;
            		if(shield.owner == this.owner)
            		{
            			if(shield.ticksExisted == this.ticksExisted)
            			{
            				this.ticksExisted -= this.rand.nextInt(10);
            			}
            			
            			if(shield.ticksExisted < this.ticksExisted)
            			{
            				this.time += shield.time;
            				this.scaleMult += shield.scaleMult / 10;
            				shield.setRrelease();
            			}
                		return;
            		}
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
                        entityIn.addVelocity(d0 * 5 * this.getScale(), 0.0D, d1 * 5 * this.getScale());
                    }
                }
	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("ParentUUIDMost") && nbt.hasKey("ParentUUIDLeast")) {
			this.uppermost = nbt.getLong("ParentUUIDMost");
			this.upperleast = nbt.getLong("ParentUUIDLeast");
            this.upperUUID = new UUID(nbt.getLong("ParentUUIDMost"), nbt.getLong("ParentUUIDLeast"));
        }
		
		if(nbt.hasKey("Time"))
			this.time = nbt.getInteger("Time");
		
		if(nbt.hasKey("scaleMult"))
			this.scaleMult = nbt.getFloat("scaleMult");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		if(!nbt.hasKey("ParentUUIDMost") && !nbt.hasKey("ParentUUIDLeast")) {
			nbt.setLong("ParentUUIDMost", this.uppermost);
			nbt.setLong("ParentUUIDLeast", this.upperleast);
        }

		nbt.setInteger("Time", this.time);
		nbt.setFloat("scaleMult", this.scaleMult);
	}
}
