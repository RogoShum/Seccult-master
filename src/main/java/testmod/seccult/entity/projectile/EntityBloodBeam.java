package testmod.seccult.entity.projectile;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.MoverType;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.client.FX.LightFX;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.TransPoint;

public class EntityBloodBeam extends Entity{
	private static final DataParameter<Integer> ID = EntityDataManager.<Integer>createKey(EntityBloodBeam.class, DataSerializers.VARINT);
	protected UUID upperUUID = null;
	protected long uppermost = 0;
	protected long upperleast = 0;
	protected EntityLivingBase owner;
	protected float Myblood;
	
	public EntityBloodBeam(World worldIn) {
		super(worldIn);
        this.setSize(0.2F, 0.2F);
		this.setNoGravity(true);
		this.noClip = true;
		this.collided = false;
		this.setNoGravity(true);
		this.isImmuneToFire = true;
		this.setEntityInvulnerable(true);
	}
	
	public EntityBloodBeam(World worldIn, EntityLivingBase o, float d) {
		super(worldIn);
        this.setSize(0.2F, 0.2F);
		this.owner = o;
		this.upperUUID = o.getUniqueID();
		this.upperleast = o.getUniqueID().getLeastSignificantBits();
		this.uppermost = o.getUniqueID().getMostSignificantBits();
		this.Myblood = d;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(this.owner != null) {
			setOwnerID(this.owner.getEntityId());
			Moveto(this.owner.posX, this.owner.posY + (this.owner.height / 2), this.owner.posZ, 0.1D);
			onChargeBlood();
		}
		else
			loadUUID();

		if(this.world.isRemote && this.owner == null)
		{
			if(this.getOwnerID() != 0)
			{
			Entity living = this.world.getEntityByID(this.getOwnerID());
			
			if(living != null && living instanceof EntityLivingBase)
			{
				this.owner = (EntityLivingBase) living;
			}
			}
		}
		
		if(!this.world.isRemote && this.ticksExisted > 20 && this.owner == null)
		{
			this.setDead();
		}
	}

	private void onChargeBlood() {
        Entity entity = null;
        
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(0.5));
        
	      for (int j1 = 0; j1 < list.size(); ++j1)
	      {
	        entity = (Entity)list.get(j1);
	        
	      	if (entity != null && entity instanceof EntityLivingBase && entity.getUniqueID().equals(this.owner.getUniqueID()))
	      	{
	      		EntityLivingBase living = (EntityLivingBase) entity;
	      		living.setHealth(living.getHealth() + this.Myblood);
	      		this.setDead();
	      	}
	      }
	}
	
	protected void Moveto(double x, double y, double z, double speed) {
       this.motionX += (Math.signum(x - this.posX) - this.motionX) * speed;
       this.motionY += (Math.signum(y - this.posY) - this.motionY) * speed;
       this.motionZ += (Math.signum(z - this.posZ) - this.motionZ) * speed;
       
       this.posX += this.motionX;
       this.posY += this.motionY;
       this.posZ += this.motionZ;

       /*if(!this.world.isRemote)
       {
    	   double[] Bpos = {this.posX, this.posY, this.posZ};
   			double[] vec = {0, 0, 0};
			float[] color = {1F, 0.0F, 0.0F};
    	   NetworkHandler.sendToAllAround(new NetworkEffectData(Bpos, vec, color, 1, 0), 
   				new TransPoint(-12450, Bpos[0], Bpos[1], Bpos[2], 32), this.world);

       }*/
       
       this.setPosition(this.posX, this.posY, this.posZ);
	}
	
	private void loadUUID() {
        if(!this.getEntityWorld().isRemote && this.owner == null && this.upperUUID != null) {
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
        else if(!this.getEntityWorld().isRemote)
        	this.setDead();
	}
    
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness()
    {
        return 5.0F;
    }
	
    @Override
	protected void entityInit() {
        this.dataManager.register(ID, Integer.valueOf(0));
	}
	
    public int getOwnerID()
    {
        return this.dataManager.get(ID).intValue();
    }
    
    private void setOwnerID(int size)
    {
        this.dataManager.set(ID, Integer.valueOf(size));
    }

	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("MyBlood")) {
			this.Myblood = nbt.getFloat("MyBlood");
		}
		
		if(nbt.hasKey("ParentUUIDMost") && nbt.hasKey("ParentUUIDLeast")) {
			this.uppermost = nbt.getLong("ParentUUIDMost");
			this.upperleast = nbt.getLong("ParentUUIDLeast");
            this.upperUUID = new UUID(nbt.getLong("ParentUUIDMost"), nbt.getLong("ParentUUIDLeast"));
        }
	}

	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		if(!nbt.hasKey("MyBlood")) {
			nbt.setFloat("MyBlood", this.Myblood);
		}
		
		if(!nbt.hasKey("ParentUUIDMost") && !nbt.hasKey("ParentUUIDLeast")) {
			nbt.setLong("ParentUUIDMost", this.uppermost);
			nbt.setLong("ParentUUIDLeast", this.upperleast);
        }
		
	}
}
