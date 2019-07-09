package testmod.seccult.entity.livings;

import java.util.Iterator;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumDifficulty;
import net.minecraft.world.World;
import testmod.seccult.magick.magickState.StateManager;
import testmod.seccult.util.ChunkCoordinates;
import testmod.seccult.util.MathHelper.MathHelper;
import testmod.seccult.util.MathHelper.MovingObjectPosition;

public class EntityEoW extends EntityBase{
	private static final DataParameter<Integer> EOW_VARIANT = EntityDataManager.<Integer>createKey(EntityEoW.class, DataSerializers.VARINT);
	ResourceLocation EOWres = new ResourceLocation("seccult:eaterofworlds");
	
	///////////////////////////////////////////////////////////////////////////
	/////////////                Identifying                ///////////////////
	///////////////////////////////////////////////////////////////////////////
	
	protected boolean isHead;
	protected boolean isTail;
	protected boolean isBody;
	protected boolean isSingle;

	protected boolean open;
	protected boolean up;

	protected UUID upperUUID = null;
	protected long uppermost = 0;
	protected long upperleast = 0;
    protected EntityEoW upper;

    protected UUID followerUUID = null;
	protected long followermost = 0;
	protected long followerleast = 0;
    protected EntityEoW follower;
	
	
	protected ChunkCoordinates currentFlightTarget = null;
	protected EntityLivingBase findtoattack = null;
	protected boolean hasAttacked;
	protected int CooldownTime = 60;
	protected double eneX;
	protected double eneY;
	protected double eneZ;
	protected float thisYaw = 0;
	protected float thisPitch = 0;

	protected int FirstSpawn;
	public EntityEoW(World worldIn) {
		super(worldIn);
		this.FirstSpawn = 0;
		this.isBody = true;
		this.setSize(1.6F, 1.6F);
		this.eneX = this.posX;
		this.eneY = this.posY;
		this.eneZ = this.posZ;
		this.setNoGravity(true);
		this.noClip = true;
		this.isTRboss = true;
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("Part")) {
		String part = nbt.getString("Part");
		switch(part) {
		case "isHead" :
			this.isHead = true;
			break;
		case "isTail" :
			this.isTail = true;
			break;
		default :
			this.isBody = true;
		}	
		}
		
		if(nbt.hasKey("ParentUUIDMost") && nbt.hasKey("ParentUUIDLeast")) {
			this.uppermost = nbt.getLong("ParentUUIDMost");
			this.upperleast = nbt.getLong("ParentUUIDLeast");
            this.upperUUID = new UUID(nbt.getLong("ParentUUIDMost"), nbt.getLong("ParentUUIDLeast"));
        }
		
		if(nbt.hasKey("followerUUIDMost") && nbt.hasKey("followerUUIDLeast")) {
			this.followermost = nbt.getLong("followerUUIDMost");
			this.followerleast = nbt.getLong("followerUUIDLeast");
            this.followerUUID = new UUID(nbt.getLong("followerUUIDMost"), nbt.getLong("followerUUIDLeast"));
        }
		
		super.readEntityFromNBT(nbt);
	}
	
	@Override
    public void setDead()
    {
        this.isDead = true;
    }
	
	@Override
    public boolean isNonBoss()
    {
        return true;
    }
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
			if(isBody) {
				nbt.setString("Part", "isBody");
			}
			else if(isTail) {
				nbt.setString("Part", "isTail");
			}
			else if(isHead) {
				nbt.setString("Part", "isHead");
			}

    	if(this.uppermost != 0 && this.upperleast != 0) {
    		nbt.setLong("ParentUUIDMost", this.uppermost);
    		nbt.setLong("ParentUUIDLeast", this.upperleast);
    	}
    	
    	if(this.followermost != 0 && this.followerleast != 0) {
    		nbt.setLong("followerUUIDMost", this.followermost);
    		nbt.setLong("followerUUIDLeast", this.followerleast);
    	}
    	super.writeEntityToNBT(nbt);
	}
	
    protected void applyEntityAttributes()
    {
        super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(220.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
    }
	
    @Override
    public void onUpdate()
    {
	   super.onUpdate();
	   
	   if(this.ticksExisted < 30) {
		   if(this.isHead)
			   this.setHealth(65);
		   if(this.isBody)
			   this.setHealth(150);
		   if(this.isTail)
			   this.setHealth(220);
	   }
	   
	   MovingObjectPosition movingObjectPosition = new MovingObjectPosition(this);
       Entity entity = null;	    
       boolean pass = false;

       List<Entity> list2 = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(128));
       
       if ((list2 != null) && (list2.size() > 0))
	    {
	      for (int j1 = 0; j1 < list2.size(); ++j1)
	      {
	        entity = (Entity)list2.get(j1);
	        
	        if (entity != null)
	        {
	          movingObjectPosition = new MovingObjectPosition(entity);
	        }

	        if ((this.world.isRemote) || (movingObjectPosition == null) || (movingObjectPosition.entityHit instanceof EntityItemFrame) || (movingObjectPosition.entityHit instanceof EntityPainting)) {
	          continue;
	        }

	        pass = false;
	        if (pass)
	          continue;
	        RefB(movingObjectPosition);            	
	          }      
         }
       loadUUID();
       isUpperDead();
       followUp();
       onCheck();
       if(!this.isHead)
    	   return;
       Attack();
       CooldownTime--;
    }
    
    private void Attack() {
        int xdir = 1;
        int zdir = 1;
        
        EntityLivingBase e = null;

         if (this.currentFlightTarget == null) {
        	this.currentFlightTarget = new ChunkCoordinates((int)this.posX, (int)this.posY, (int)this.posZ);
        	this.currentFlightTarget.set((int)this.posX + xdir, (int)this.posY + this.rand.nextInt(20) - 10, (int)this.posZ + zdir);
        	}
        
    	     
        if ((this.rand.nextInt(7) == 2) && (this.world.getDifficulty() != EnumDifficulty.PEACEFUL))
             {
               e = findPlayerToAttack(this);
               if (e != null && isSuitableTarget(e) && CooldownTime <= 0 && !hasAttacked)
               {
            	   this.faceEntity(e, 3600, 3600);
            	   this.thisYaw = this.rotationYaw;
            	   this.thisPitch = this.rotationPitch;
            	   Vec3d QAQ = onLook(this.getLookVec(), this.getPositionVector(), e.getPositionVector());
            	   this.eneX = QAQ.x; this.eneY = QAQ.y; this.eneZ = QAQ.z;
            	   CooldownTime = 150;
            	   hasAttacked = true;
               }
               else {
            	   if(findtoattack != null && findtoattack.posY > this.posY)
            	   this.moveHelper.setMoveTo(eneX + this.rand.nextInt(3), eneY - this.rand.nextInt(5), eneZ + this.rand.nextInt(3), 0.2);
            	   else if(findtoattack != null && findtoattack.posY < this.posY)
            		   this.moveHelper.setMoveTo( + this.rand.nextInt(3), eneY + this.rand.nextInt(5), eneZ + this.rand.nextInt(3), 0.2);
            	   hasAttacked = false;
            	   CooldownTime = 0;
               }
            	   
             }else {
                 float var7 = (float)(Math.atan2(this.motionZ, this.motionX) * 180.0D / 3.141592653589793D) - 90.0F;
                 float var8 = MathHelper.wrapAngleTo180_float(var7 - this.rotationYaw);
                 this.moveForward = 0.75F;
                 this.rotationYaw += var8 / 4.0F;
             }
        
        if(this.CooldownTime <= 0) {
        this.rotationYaw = this.thisYaw;
        this.rotationPitch = this.thisPitch;
        this.motionX += (Math.signum(this.eneX - this.posX) - this.motionX);
        this.motionY += (Math.signum(this.eneY - this.posY) - this.motionY);
        this.motionZ += (Math.signum(this.eneZ - this.posZ) - this.motionZ);
        StateManager.setPlayerMove(this, (Math.signum(this.eneX - this.posX) - this.motionX), (Math.signum(this.eneY - this.posY) - this.motionY), (Math.signum(this.eneZ - this.posZ) - this.motionZ), 1);
        	//this.moveHelper.setMoveTo(eneX, eneY, eneZ, 1);
        }
	}

	protected Vec3d onLook(Vec3d look, Vec3d AP, Vec3d LP) {
		int rand = this.rand.nextInt(20) + 20;
		AP.addVector(0, this.height / 2, 0);
		double distance = AP.distanceTo(LP);
		double dis = distance + rand;
		return AP.addVector(look.x * dis, look.y * dis, look.z * dis);
	}

	private void isUpperDead() {
    	if(!this.getEntityWorld().isRemote) {
		if(this.upper != null && !this.upper.isEntityAlive()) {
			this.upper = null;
			this.upperleast = 0;
			this.uppermost = 0;
			this.upperUUID = null;
		}
		
		if(this.follower != null && !this.follower.isEntityAlive()) {
			this.follower = null;
			this.followerleast = 0;
			this.followermost = 0;
			this.followerUUID = null;
		 }
    	}
	}

	private void loadUUID() {
        if(!this.getEntityWorld().isRemote && this.upper == null && this.upperUUID != null) {
	        double range = 128D;
	        List<EntityEoW> connections = this.getEntityWorld().getEntitiesWithinAABB(EntityEoW.class, this.getEntityBoundingBox().grow(range, range, range));
	        Iterator<EntityEoW> possibleConnections = connections.iterator();
	        while(possibleConnections.hasNext()) {
	        	EntityEoW possibleConnection = (EntityEoW)possibleConnections.next();
	            if(possibleConnection != this && possibleConnection.getUniqueID().equals(this.upperUUID)) {
	            	this.upper = possibleConnection;
	            	break;
	            }
	        }
        }
        
        if(!this.getEntityWorld().isRemote && this.follower == null && this.followerUUID != null) {
	        double range = 128D;
	        List<EntityEoW> connections = this.getEntityWorld().getEntitiesWithinAABB(EntityEoW.class, this.getEntityBoundingBox().grow(range, range, range));
	        Iterator<EntityEoW> possibleConnections = connections.iterator();
	        while(possibleConnections.hasNext()) {
	        	EntityEoW possibleConnection = (EntityEoW)possibleConnections.next();
	            if(possibleConnection != this && possibleConnection.getUniqueID().equals(this.followerUUID)) {
	            	this.upper = possibleConnection;
	            	break;
	            }
	        }
        }
	}

	private void onCheck() {
		switch(onTurn()) {
		case 1:
    		this.isBody = true;
    		this.isHead = false;
    		this.isTail = false;
    		if(this.getHealth() > 150)
    			this.setHealth(150);
    		this.setRender(2);
    		break;
    	
    	
		case 0:
    		this.isHead = true;
    		this.isBody = false;
    		this.isTail = false;
    		if(this.getHealth() > 65)
    			this.setHealth(65);
    		this.setRender(1);
    		break;
    	
		case 2:
    		this.isTail = true;
    		this.isHead = false;
    		this.isBody = false;
    		this.setRender(3);
    		break;
    	
		case 3:
    		this.isSingle = true;
    		this.isHead = true;
    		this.isTail = true;
    		this.setRender(4);
    		break;
		}
	}

	
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(EOW_VARIANT, Integer.valueOf(5));
    }
	
    public int getRenderSkin()
    {
        return ((Integer)this.dataManager.get(EOW_VARIANT)).intValue();
    }

    public void setRender(int skinId)
    {
        this.dataManager.set(EOW_VARIANT, Integer.valueOf(skinId));
    }
	
	protected void isTail() {
		this.isTail = true;
	}
	
	protected void setupper(EntityEoW eow) {
		this.upper = eow;
	}
	 
	protected void setfollower(EntityEoW eow) {
		this.follower = eow;
	}
	
	protected void setupperUUIDL(long upper) {
		this.upperleast = upper;
	}
	
	protected void setupperUUIDM(long upper) {
		this.uppermost = upper;
	}
	
	protected void setfollowerUUIDL(long upper) {
		this.followerleast = upper;
	}
	
	protected void setfollowerUUIDM(long upper) {
		this.followermost = upper;
	}
	
	protected void setFUUID(UUID f) {
		this.followerUUID = f;
	}
	
	protected void setUUUID(UUID u) {
		this.upperUUID = u;
	}
	
	public boolean getHead() {
		return isHead;
	}
	
	public boolean getBody() {
		return isBody;
	}
	public boolean getTail() {
		return isTail;
	}
	public boolean getSingle() {
		return isSingle;
	}
	
	protected int onTurn() {
		if(!this.world.isRemote) {
		boolean A1 = this.upper != null;
		boolean A2 = this.upperleast != 0 && this.uppermost != 0;
		boolean A3 = this.upperUUID != null;
		boolean B1 = this.follower != null;
		boolean B2 = this.followerleast != 0 && this.followermost != 0;
		boolean B3 = this.followerUUID != null;
		
		if(open && up)
		{
			return 3;
		}
		
    	if((A1 || A2 || A3) && (B1 || B2 || B3)){
        
        if(A1 && !upper.isEntityAlive()){
        	this.open = true;
        	return 0;
        }
        
    	if(B1 && !follower.isEntityAlive()) {
        	this.up = true;
        	return 2;
    		}
    		
        	return 1;
    	}

        if((B1 || B2 || B3) && !(A1 || A2 || A3)) {
        	this.open = true;
        	return 0;
        }
        	
        if((A1 || A2 || A3) && !(B1 || B2 || B3)) {
        	this.up = true;	
        	return 2;
        }
        	
    	if (!A1 && !A2 && !A3){
        	this.open = true;
        	return 0;
    	}
    	
		if (!B1 && !B2 && !B3){
    		this.up = true;	
    		return 2;
		}
		
		}
		return 4;
	}
    
    private void RefB(MovingObjectPosition movingObjectPosition) {
        Entity hitEntity = movingObjectPosition.entityHit;
        if(hitEntity instanceof EntityEoW && hitEntity.getUniqueID().equals(this.upperUUID))
        	upper = (EntityEoW) hitEntity;
        if(hitEntity instanceof EntityEoW && hitEntity.getUniqueID().equals(this.followerUUID))
        	follower = (EntityEoW) hitEntity;
        if(!(hitEntity instanceof EntityEoW) && this.isHead && hitEntity.getDistanceSq(this) < 1.3) {
        	hitEntity.attackEntityFrom(DamageSource.causeMobDamage(this), 22);
        }
        
        if(!(hitEntity instanceof EntityEoW) && this.isBody && hitEntity.getDistanceSq(this) < 1.3) {
        	hitEntity.attackEntityFrom(DamageSource.causeMobDamage(this), 13);
        }
        
        if(!(hitEntity instanceof EntityEoW) && this.isTail && hitEntity.getDistanceSq(this) < 1.3) {
        	hitEntity.attackEntityFrom(DamageSource.causeMobDamage(this), 11);
        }
	}
    
    @Override
    public void fall(float distance, float damageMultiplier) {
    	super.fall(0, 0);
    }
    
    private void followUp() {
    	if(this.upper != null) {
    		this.faceEntity(this.upper, 360, 360);
    		this.renderYawOffset = this.rotationYaw;
            this.motionX += (Math.signum(upper.posX - this.posX) - this.motionX) * 0.6F;
            this.motionY += (Math.signum(upper.posY - this.posY) - this.motionY) * 0.6F;
            this.motionZ += (Math.signum(upper.posZ - this.posZ) - this.motionZ) * 0.6F;
            StateManager.setPlayerMove(this, (Math.signum(upper.posX - this.posX) - this.motionX) * 0.6F, (Math.signum(upper.posY - this.posY) - this.motionY) * 0.6, (Math.signum(upper.posZ - this.posZ) - this.motionZ) * 0.6, 1);
    		//this.moveHelper.setMoveTo(upper.posX, upper.posY, upper.posZ, 1);
    		double segmentDistance = 0.8D;
    		Vec3d pos;
    		if(this.upper instanceof EntityEoW)
    			pos = ((EntityEoW)this.upper).getFacingPositionDouble(this.upper.posX, this.upper.posY, this.upper.posZ, -0.25D, 0);
    		else
				pos = new Vec3d(this.upper.posX, this.upper.posY, this.upper.posZ);
    		
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
	}
    
    public Vec3d getFacingPositionDouble(double x, double y, double z, double distance, double angle) {
    	if(distance == 0) {
			distance = 1;
		}
        angle = Math.toRadians(angle);
        double xAmount = -Math.sin(angle);
        double zAmount = Math.cos(angle);
        return new Vec3d(x + (distance * xAmount), y, z + (distance * zAmount));
    }
    
    private boolean isSuitableTarget(EntityLivingBase par1EntityLiving)
    {
      if (this.world.getDifficulty() == EnumDifficulty.PEACEFUL && par1EntityLiving instanceof EntityPlayer) return false;

      if (par1EntityLiving instanceof EntityPlayer)
      {
     	 EntityPlayer pl = (EntityPlayer) par1EntityLiving;
     	 if(pl.isCreative())
        return true;
     	 else
     	return true;
      }
      
      if (!(par1EntityLiving.isEntityAlive()))
      {
        return false;
      }

      return true;
    }
}
