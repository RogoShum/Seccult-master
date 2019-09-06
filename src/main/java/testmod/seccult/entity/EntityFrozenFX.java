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
import net.minecraft.world.World;
import testmod.seccult.magick.magickState.StateManager;

public class EntityFrozenFX extends Entity{

	protected UUID upperUUID = null;
	protected long uppermost = 0;
	protected long upperleast = 0;
	protected Entity owner;
	
	private static final DataParameter<Float> Length = EntityDataManager.<Float>createKey(EntityFrozenFX.class, DataSerializers.FLOAT);
	private static final DataParameter<Float> Width = EntityDataManager.<Float>createKey(EntityFrozenFX.class, DataSerializers.FLOAT);
	
	public EntityFrozenFX(World worldIn) {
		super(worldIn);
	}

	public void setOwner(Entity owner)
	{
		this.owner = owner;
		this.upperUUID = owner.getUniqueID();
		this.uppermost = owner.getUniqueID().getMostSignificantBits();
		this.upperleast = owner.getUniqueID().getLeastSignificantBits();
		this.setSize(owner.width, owner.height);
	}
	
	public Entity getOwner()
	{
		return this.owner;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();

		if(!this.world.isRemote && this.owner == null)
		{
			if(this.ticksExisted < 20)
				loadUUID();
			else
				this.setDead();
		}

		if(this.owner != null)
		{
			if(!this.owner.isEntityAlive() || !StateManager.CheckIfStatedSafe(this.owner, StateManager.FROZEN))
			{
				this.setDead();
				return;
			}
			this.setLength(owner.height);
			this.setWidth(owner.width);
			this.setPosition(owner.posX, owner.posY, owner.posZ);
		}
	}
	
	@Override
    protected void entityInit()
    {	
        this.dataManager.register(Length, Float.valueOf(0));
        this.dataManager.register(Width, Float.valueOf(0));
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
	
    public void setWidth(float width)
    {
        this.dataManager.set(Width, Float.valueOf(width));
    }
    
    public float getMyWidth()
    {
        return this.dataManager.get(Width).floatValue();
    }

    public void setLength(float width)
    {
        this.dataManager.set(Length, Float.valueOf(width));
    }
    
    public float getMyLength()
    {
        return this.dataManager.get(Length).floatValue();
    }
	
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
