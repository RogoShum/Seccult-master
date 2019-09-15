package testmod.seccult.entity;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EntitySelectors;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import testmod.seccult.client.FX.LightFX;
import testmod.seccult.entity.livings.water.EntityRockShellLeviathan;
import testmod.seccult.magick.active.TeleportMagick;

public class EntityLightingThing extends Entity{
	
	protected UUID upperUUID = null;
	protected long uppermost = 0;
	protected long upperleast = 0;
	protected EntityLivingBase owner;
	protected EntityLivingBase prey;
	protected boolean getPrey;
	
	public EntityLightingThing(World worldIn) {
		super(worldIn);
        this.setSize(0.4F, 0.4F);
	}
	
	public EntityLightingThing(World worldIn, EntityLivingBase owner, EntityLivingBase prey) {
		super(worldIn);
        this.setSize(0.4F, 0.4F);
		this.owner = owner;
		this.upperUUID = owner.getUniqueID();
		this.upperleast = owner.getUniqueID().getLeastSignificantBits();
		this.uppermost = owner.getUniqueID().getMostSignificantBits();
		this.prey = prey;
	}
	
	public void setOwner(EntityLivingBase owner)
	{
		this.owner = owner;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		collideWithNearbyEntities();
		
		if(!this.world.isRemote && this.owner != null && !this.owner.isEntityAlive())
			this.owner = null;
		
		if(!this.world.isRemote && this.prey != null && !this.prey.isEntityAlive())
			this.prey = null;
		
		if(!this.world.isRemote && this.owner == null) loadUUID();
		else
		{
			if(this.prey != null && !getPrey) 
			{
				Moveto(this.prey.posX, this.prey.posY + (this.prey.height / 2), this.prey.posZ, 0.3F);
				this.faceEntity(prey, 50, 30);
			}
			else if(this.prey != null && this.owner != null)
			{
				Vec3d look = this.getLookVec();
			
				BlockPos pos = this.getPosition().add(look.x, -this.prey.height / 2, look.z);
				this.prey.setPosition(pos.getX(), pos.getY(), pos.getZ());
				TeleportMagick.setPlayerTP(this.prey, pos.getX(), pos.getY(), pos.getZ(), 0);
				Moveto(this.owner.posX, this.owner.posY + (this.owner.height / 2), this.owner.posZ, 0.1F);
				this.faceEntity(owner, 50, 30);
			}
			else if(this.prey == null)
			{
				findPrey();
			}
		}
	}

    public void faceEntity(Entity entityIn, float maxYawIncrease, float maxPitchIncrease)
    {
        double d0 = entityIn.posX - this.posX;
        double d2 = entityIn.posZ - this.posZ;
        double d1;

        if (entityIn instanceof EntityLivingBase)
        {
            EntityLivingBase entitylivingbase = (EntityLivingBase)entityIn;
            d1 = entitylivingbase.posY + (double)entitylivingbase.getEyeHeight() - (this.posY + (double)this.getEyeHeight());
        }
        else
        {
            d1 = (entityIn.getEntityBoundingBox().minY + entityIn.getEntityBoundingBox().maxY) / 2.0D - (this.posY + (double)this.getEyeHeight());
        }

        double d3 = (double)MathHelper.sqrt(d0 * d0 + d2 * d2);
        float f = (float)(MathHelper.atan2(d2, d0) * (180D / Math.PI)) - 90.0F;
        float f1 = (float)(-(MathHelper.atan2(d1, d3) * (180D / Math.PI)));
        this.rotationPitch = this.updateRotation(this.rotationPitch, f1, maxPitchIncrease);
        this.rotationYaw = this.updateRotation(this.rotationYaw, f, maxYawIncrease);
    }
	
    private float updateRotation(float angle, float targetAngle, float maxIncrease)
    {
        float f = MathHelper.wrapDegrees(targetAngle - angle);

        if (f > maxIncrease)
        {
            f = maxIncrease;
        }

        if (f < -maxIncrease)
        {
            f = -maxIncrease;
        }

        return angle + f;
    }
    
	@Override
	public void applyEntityCollision(Entity entity) {
		super.applyEntityCollision(entity);
		if (entity != null && this.prey != null && entity == this.prey)
      	{
      		this.getPrey = true;
      	}

		if (this.getPrey && entity != null && this.owner != null && entity == this.owner)
      	{
      		this.setDead();
      	}
	}
	
    protected void collideWithNearbyEntities()
    {
        List<Entity> list = this.world.getEntitiesInAABBexcluding(this, this.getEntityBoundingBox(), EntitySelectors.getTeamCollisionPredicate(this));

        if (!list.isEmpty())
        {
            for (int l = 0; l < list.size(); ++l)
            {
                Entity entity = list.get(l);
                this.applyEntityCollision(entity);
            }
        }
    }
	
	public void findPrey()
	{
		List<Entity> entity = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(40));
		
		for(int i = 0; i < entity.size(); ++i)
		{
			Entity e = entity.get(i);
			if(e instanceof EntityLivingBase && !(e instanceof EntityRockShellLeviathan)  && !(e instanceof EntityVillager) && this.canSeeTarget(e) && e.isEntityAlive())
			{
				boolean creat = false;
				EntityLivingBase living = (EntityLivingBase) e;
				if(living instanceof EntityPlayer)
				{
					EntityPlayer p = (EntityPlayer) living;
					if(p.capabilities.isCreativeMode)
						creat = true;
				}
				
				if(!creat)
				this.prey = (EntityLivingBase) e;
			}
		}
	}
	
	public boolean canSeeTarget(Entity e)
    {
    	return	canSeeTarget(e.posX, e.posY, e.posZ);
    }
	
    public boolean canSeeTarget(double pX, double pY, double pZ)
    {
    	return (this.world.rayTraceBlocks(new Vec3d(this.posX, this.posY + 0.55D, this.posZ), new Vec3d(pX, pY, pZ), false) == null);
    }
	
	private void onParticle() {
		Minecraft.getMinecraft().effectRenderer.addEffect(new LightFX(this.world, this.posX, this.posY, this.posZ, 0, 0, 0, 1));
	}
	
	protected void Moveto(double x, double y, double z, float speed) {
       this.motionX += (Math.signum(x - this.posX) - this.motionX) * speed;
       this.motionY += (Math.signum(y - this.posY) - this.motionY) * speed;
       this.motionZ += (Math.signum(z - this.posZ) - this.motionZ) * speed;
       
       float f = (float)(MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
       float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
   
       this.rotationYaw += f1;
       
       this.posX += this.motionX;
       this.posY += this.motionY;
       this.posZ += this.motionZ;

       this.motionX *= (double)0.8;
       this.motionY *= (double)0.8;
       this.motionZ *= (double)0.8;
       
       this.setPositionAndRotation(posX, posY, posZ, this.rotationYaw, this.rotationPitch);
	}
	
	private void loadUUID() {
        if(this.owner == null && this.upperUUID != null) {
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
        else
        	this.setDead();
	}
	
	@Override
	protected void entityInit() {}

	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("ParentUUIDMost") && nbt.hasKey("ParentUUIDLeast")) {
			this.uppermost = nbt.getLong("ParentUUIDMost");
			this.upperleast = nbt.getLong("ParentUUIDLeast");
            this.upperUUID = new UUID(nbt.getLong("ParentUUIDMost"), nbt.getLong("ParentUUIDLeast"));
        }
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		if(!nbt.hasKey("ParentUUIDMost") && !nbt.hasKey("ParentUUIDLeast")) {
			nbt.setLong("ParentUUIDMost", this.uppermost);
			nbt.setLong("ParentUUIDLeast", this.upperleast);
        }
	}

}
