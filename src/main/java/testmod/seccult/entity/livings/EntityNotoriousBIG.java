package testmod.seccult.entity.livings;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntitySnowball;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;

public class EntityNotoriousBIG extends EntityStand{
	private static final DataParameter<Float> Size = EntityDataManager.<Float>createKey(EntityNotoriousBIG.class, DataSerializers.FLOAT);
	private static final DataParameter<Boolean> Tail = EntityDataManager.<Boolean>createKey(EntityNotoriousBIG.class, DataSerializers.BOOLEAN);

	private EntityNotoriousBIG Body;
	private EntityNotoriousBIG MyTail;
	private boolean isTail;
	private float swing;
	private boolean swinglock;
	public boolean HeisDead;
	private Entity myTarget;
	private double targetSpeed;
	private float MySize;
	
	public EntityNotoriousBIG(World worldIn) {
		super(worldIn);
		this.setSize(0.5F, 0.5F);
	}

	@Override
	public boolean isEntityAlive() {
		thisSise();
		if(getStandEnergy() > -10)
			this.isDead = false;
		return getStandEnergy() > -10;
	}
	
	@Override
	public void setDead() {
		setStandEnergy(getStandEnergy() - 10);
			if(getStandEnergy() <= -10)
				this.isDead = true;
	}
	
	public void setTail() {
		this.isTail = true;
	}
	
	public void setBody(EntityNotoriousBIG b) {
		this.Body = b;
	}
	
	public void setMyTail(EntityNotoriousBIG b) {
		this.MyTail = b;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(this.isTail) {
			TailDoThings();
			return;
		}
		
		/*if(this.world.isRemote && this.getMySize() > 0.1F) {
			this.isDead = false;
		}*/
		
		catchUpTheEntity();
		swing();
		onMoveing();
		
		if(this.myTarget == null)
		 return;
		if(!this.myTarget.isEntityAlive() || this.getDistance(myTarget) > 20) {
			this.myTarget = null;
			this.targetSpeed = 0;
			return;
		}
		
		if(this.ticksExisted % 100 == 0) {
			onUpdateTarget();
		}
	}

	private void TailDoThings() {
		if(!this.world.isRemote && this.Body != null) {
			this.setisTail(true);
			if(!this.Body.isEntityAlive()) {
				this.Body = null;
				return;
			}
			faceEntity(Body, 360, 360);
		}else if(!this.world.isRemote && this.Body == null){
			this.setisTail(false);
			this.isTail = false;
			return;
		}
		
		if(!this.world.isRemote && this.MyTail != null) {
			if(!this.MyTail.isEntityAlive()) {
				this.MyTail = null;
			}
		}
		
		if(!this.world.isRemote && !Body.isTail) {
			this.setRender(Body.getMySize() * 0.5F);
			float pitch = Body.rotationPitch;
			if(pitch > 15)
				pitch = 15;
			if(pitch < -15)
				pitch = -15;
			Vec3d qaq = onLook(Body.getVectorForRotation(pitch, Body.rotationYaw), Body.getPositionVector(), -(Body.getMySize() * 0.7));
			this.posX = qaq.x;
			this.posY = qaq.y;
			this.posZ = qaq.z;
		}
		
		if(!this.world.isRemote && Body.isTail && Body.getMySize() > 0.3F) {
			this.setRender(Body.getMySize() * 0.7F);
		}
		
		this.setSize(this.getMySize(), this.getMySize());
		
		if(getStandEnergy() > 50) {
			setStandEnergy(getStandEnergy() - 1);
			Body.setStandEnergy(getStandEnergy() + 1);
		}
		
		
		if(Body.isTail) {
		Vec3d pos;
		
		pos = new Vec3d(this.Body.posX, this.Body.posY, this.Body.posZ);
		
		double segmentDistance = this.getMySize() / 2D;
		
		if(this.posX - pos.x > segmentDistance)
			this.posX = pos.x + segmentDistance;
		else if(this.posX - pos.x < -segmentDistance)
			this.posX = pos.x - segmentDistance;
		
		if(this.posY - pos.y > segmentDistance)
			this.posY = pos.y + segmentDistance;
		else if(this.posY - pos.y < -(segmentDistance))
			this.posY = pos.y - segmentDistance;
		
		if(this.posZ - pos.z > segmentDistance)
			this.posZ = pos.z + segmentDistance;
		else if(this.posZ - pos.z < -segmentDistance)
			this.posZ = pos.z - segmentDistance;
		}
		
		if(!this.world.isRemote && this.MyTail == null && this.getMySize() >= 0.3F) {
			EntityCreator.createEntity(this, 0);
		}
		
		if(!this.world.isRemote && this.getMySize() < 0.2 && getStandEnergy() >= 0) {
			this.Body.setStandEnergy(this.getStandEnergy() + 10);
			this.setStandEnergy(-5000);
		}

        Entity entity = null;
        
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(1));
        
        if ((list != null) && (list.size() > 0))
	    {
	      for (int j1 = 0; j1 < list.size(); ++j1)
	      {
	        entity = (Entity)list.get(j1);

	        Ref2(entity);
	       }      
	    }
	}
	
	public void swing() {
		if(this.ticksExisted % 1 ==0) {
		if(swing > 45)
			swinglock = true;
		if(swing < - 45)
			swinglock = false;
	
		if(swinglock)
			swing++;
		else
			swing--;
		}
	} 

	public float getSwing() {
		return this.swing;
	}
	
	private void thisSise() {
		if(!this.world.isRemote) {
			MySize = sizeeee(getStandEnergy());
			if(MySize > 25)
				MySize = 25;
			setRender(MySize);
		
		this.setSize(getMySize(), getMySize());
		}
		
		/*if(!this.world.isRemote && this.MyTail == null && this.getMySize() >= 0.7) {
			EntityCreator.createEntity(this, 0);
		}
		
		if(!this.world.isRemote && this.MyTail != null) {
			if(!this.MyTail.isEntityAlive()) {
				this.MyTail = null;
			}
		}*/
	}

    protected void initEntityAI()
    {
        this.tasks.addTask(8, new EntityAIWatchClosest(this, EntityPlayer.class, 32.0F));
        this.tasks.addTask(8, new EntityAILookIdle(this));
    }
	
	@Override
    protected void entityInit()
    {	
		super.entityInit();
        this.dataManager.register(Size, Float.valueOf(5));
        this.dataManager.register(Tail, Boolean.valueOf(false));
    }
	
	public boolean getTail() 
	{
		return this.dataManager.get(Tail).booleanValue();
	}
	
    public float getMySize()
    {
        return this.dataManager.get(Size).floatValue();
    }

    public void setisTail(boolean b) {
    	this.dataManager.set(Tail, b);
    }
    
    public void setRender(float size)
    {
        this.dataManager.set(Size, Float.valueOf(size));
    }
    
	@Override
	protected void EnergyCalculate() {
		if(this.Owner != null && this.Owner.isEntityAlive()) {
		super.EnergyCalculate();
		return;
		}else if(!HeisDead){
			this.HeisDead = true;
			setStandEnergy(getStandEnergy() + 10);
			this.Owner = null;
		}

			if(getStandEnergy() < 50 && getStandEnergy() > 25){
				MySpeed = MyMaxSpeed * 0.75;
				MyStrength = MyMaxStrength * 0.75;
				MyDurability = MyMaxDurability * 0.65;
				MyRange = MyMaxRange * 0.65;
			}else if(getStandEnergy() < 25) {
				MySpeed = MyMaxSpeed * 0.35;
				MyStrength = MyMaxStrength * 0.35;
				MyDurability = MyMaxDurability * 0.25;
				MyRange = MyMaxRange * 0.25;
			}
	}
	
	private void onUpdateTarget() {
		 double sx = this.myTarget.motionX;
	      double sy = this.myTarget.motionY;
	      double sz = this.myTarget.motionZ;
	      if(sx < 0)
	    	  sx = -sx;
	      if(sy < 0)
	    	  sy = -sy;
	      if(sz < 0)
	    	  sz = -sz;
	      double speed = sx + sy + sz;
	    	  this.targetSpeed = speed;
	    	  this.MySpeed = Max(sx, sy, sz);	
	}

	private void onMoveing() {
		if(!this.world.isRemote && myTarget != null) {
	        Moveto(this.myTarget.posX, this.myTarget.posY, this.myTarget.posZ, this.MySpeed);
			this.faceEntity(myTarget, 360, 360);
		}
	}
	
	protected void Moveto(double x, double y, double z, double d) {
        this.motionX += (Math.signum(x - this.posX) - this.motionX) * d;
        this.motionY += (Math.signum(y - this.posY) - this.motionY) * d;
        this.motionZ += (Math.signum(z - this.posZ) - this.motionZ) * d;
	}
	
	protected Vec3d onLook(Vec3d look, Vec3d AP, double dist) {
		AP.addVector(0, this.height / 5, 0);
		return AP.addVector(look.x * dist, look.y * dist, look.z * dist);
	}
	
	private void catchUpTheEntity() {
        Entity entity = null;
        
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(128));

	      for (int j1 = 0; j1 < list.size(); ++j1)
	      {
	        entity = (Entity)list.get(j1);
	        
	        Ref(entity);
	       }      

        List<Entity> list1 = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(1));
        
	      for (int j1 = 0; j1 < list1.size(); ++j1)
	      {
	        entity = (Entity)list1.get(j1);
	        
	        Ref1(entity);
	         
	     }      
	}
	
    protected void Ref(Entity hitEntity)
    {
      if(hitEntity instanceof EntityNotoriousBIG) {
    	  return;
      }
      
      double sx = hitEntity.motionX;
      double sy = hitEntity.motionY;
      double sz = hitEntity.motionZ;
      if(sx < 0)
    	  sx = -sx;
      if(sy < 0)
    	  sy = -sy;
      if(sz < 0)
    	  sz = -sz;
      double speed = sx + sy + sz;
      
      if(hitEntity instanceof EntityPlayer) {
    	  EntityPlayer player = (EntityPlayer) hitEntity;
    	  if(player.limbSwingAmount != player.prevLimbSwingAmount) {
    		  speed = 2;
    	  }
    	  return;
      }
      
      if(speed > this.targetSpeed) {
    	  this.myTarget = hitEntity;
    	  this.targetSpeed = speed;
    	  this.MySpeed = Max(sx, sy, sz);
      }
    }
    
    protected void Ref1(Entity hitEntity)
    {
      if(!(hitEntity instanceof EntityLivingBase)) {
    	  if(hitEntity instanceof EntitySnowball) {
    		  setStandEnergy(getStandEnergy() + 10);
    	  }
    		  hitEntity.setDead();
    		  setStandEnergy(getStandEnergy() + 5);
    	  return;
      }
      
      EntityLivingBase living = (EntityLivingBase) hitEntity;
      if(living instanceof EntityNotoriousBIG) {
    	 EntityNotoriousBIG big = (EntityNotoriousBIG) living;
    	 if(big.isTail)
    		 return;
    	 if(big.getStandEnergy() < this.getStandEnergy()) {
    		this.setStandEnergy(this.getStandEnergy() + big.getStandEnergy());
    		big.setStandEnergy(-100);
    		 return;
    	 }
    	 else if(big.getStandEnergy() == this.getStandEnergy())
    	 {
    		 this.setStandEnergy(this.getStandEnergy() + this.rand.nextFloat());
    		 return;
    	 }
      }
      
      if(living.getHealth() >= 0 && this.ticksExisted % (26 - (int)this.MySize) == 0) {
      	  float size = sizeeee(this.getStandEnergy());
      	  if(living.getHealth() >= (size)) {
      	  living.setHealth(living.getHealth() - (size));
      	  setStandEnergy(getStandEnergy() + (size));
      	  }
    	  else {
    		  setStandEnergy(getStandEnergy() + living.getHealth());
    		  living.setHealth(0);
    	  }
      }
    }
    
    protected void Ref2(Entity hitEntity)
    {
        if(!(hitEntity instanceof EntityLivingBase)) {
        	float siize = (hitEntity.width + hitEntity.height) / 2;
      	  if(hitEntity instanceof EntitySnowball) {
      		  setStandEnergy(getStandEnergy() + 10);
      	  }
      		  hitEntity.setDead();
      		setStandEnergy(getStandEnergy() + siize);
      	  return;
        }
        
        EntityLivingBase living = (EntityLivingBase) hitEntity;
        if(living instanceof EntityNotoriousBIG) {
      	 return;
        }
        
        if(living.getHealth() >= 0 && this.ticksExisted % (26 - (int)this.MySize) == 0) {
      	  float size = sizeeee(getStandEnergy());
      	  if(living.getHealth() >= size) {
      	  living.setHealth(living.getHealth() - size);
      	setStandEnergy(getStandEnergy() + size);
      	  }
      	  else {
      		setStandEnergy(getStandEnergy() + living.getHealth());
      		  living.setHealth(0);
      	  }
        }
    }
    
    private float sizeeee(float energy) {
  	  		float size = 0.1F;
			float add = 0;
			if(energy > 0)
				add = energy * 0.01F;
			return size + add;
    }
    
    private boolean isSuitableTarget(Entity par1EntityLiving)
    {
      if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL && par1EntityLiving instanceof EntityPlayer) 
    	  return false;

      if (par1EntityLiving instanceof EntityPlayer)
      {
     	 EntityPlayer pl = (EntityPlayer) par1EntityLiving;
     	 if(pl.isCreative())
        return false;
     	 else
     	return true;
      }
      
      if (!(par1EntityLiving.isEntityAlive()))
      {
        return false;
      }

      return true;
    }
    
    @Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
    	nbt.setFloat("StandEnergy", getStandEnergy());
    }
    
    @Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
    	if(nbt.hasKey("StandEnergy"))
    		setStandEnergy(nbt.getFloat("StandEnergy"));
    }
    
    public double Max(double a, double b, double c) {
    	double d = Math.max(a, b);
    	d = Math.max(d, c);
		return d;
    }
}
