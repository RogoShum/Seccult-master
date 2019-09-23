package testmod.seccult.entity.projectile;

import java.util.List;

import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import testmod.seccult.items.TRprojectile.TRprojectileID;

public class TRprojectileBase extends Entity{
	private int Speed;
	private int Damage;
	private EntityPlayer player = null;
	private double x;
	private double y;
	private double z;
	private int id;
	protected boolean hasGravity;
	protected boolean makeDamage;
	protected int collisionTimes;
	private int collisionCold;
	protected boolean isStick;
	
    public double smotionX;
    public double smotionY;
    public double smotionZ;
	
	private static final DataParameter<Integer> PROID = EntityDataManager.<Integer>createKey(TRprojectileBase.class, DataSerializers.VARINT);
	
	public TRprojectileBase(World worldIn) {
		super(worldIn);
		this.setSize(1.0F, 1.0F);
	}

	public TRprojectileBase(World worldIn, int Damage, int speed, EntityPlayer player, int id) {
		super(worldIn);
		this.setSize(1.0F, 1.0F);
		this.Speed = speed;
		this.Damage = Damage;
		this.player = player;
		this.id = id;
		this.makeDamage = true;
	}
	
	@Override
    protected void entityInit()
    {
        this.dataManager.register(PROID, Integer.valueOf(5));
    }
	
    public int getRenderSkin()
    {
        return ((Integer)this.dataManager.get(PROID)).intValue();
    }

    public void setRender(int skinId)
    {
        this.dataManager.set(PROID, Integer.valueOf(skinId));
    }
	
	@Override
	public float getEyeHeight() {
		return this.height * 0.5F;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(!this.world.isRemote)
		setRender(this.id);
		Vec3d QAQ = this.getLookVec();
		this.x = QAQ.x;
		this.y = QAQ.y;
		this.z = QAQ.z;
		DamageThings();
		MoveingAttribute();
		realseDust();
		if(this.ticksExisted > 200)
			this.setDead();
        BlockPos base = this.getPosition();
        BlockPos x1 = base.add(0.4, 0, 0);
        BlockPos x2 = base.add(-0.4, 0, 0);
        BlockPos y1 = base.add(0, 0.4, 0);
        BlockPos y2 = base.add(0, -0.4, 0);
        BlockPos z1 = base.add(0, 0, 0.4);
        BlockPos z2 = base.add(0, 0, -0.4);
        if (!this.world.isRemote && (this.stick(x1) || this.stick(x2) || this.stick(y1) || this.stick(y2) || this.stick(z1) || this.stick(z2))) {
        	this.isStick = true;
        }
        else
        	this.isStick = false;
		
        this.collisionCold--;
        if(this.collisionCold < -100)
        	this.collisionCold = -100;
	}
	
	public boolean stick(BlockPos pos)
	{
		return !this.world.isAirBlock(pos) && !(this.world.getBlockState(pos).getBlock() instanceof BlockLiquid);
	}
	
	public void setAttribute(int Damage, int speed, EntityPlayer player, int id) {
		this.Speed = speed;
		this.Damage = Damage;
		this.player = player;
		this.id = id;
		this.makeDamage = true;
	}
	
	private void DamageThings() {
			Entity entity = null;
	       List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(0.25));
	       if ((list != null) && (list.size() > 0))
		    {
		      for (int j1 = 0; j1 < list.size(); ++j1)
		      {
		        entity = (Entity)list.get(j1);
		        
		        if (entity != null)
		        {
		        	Ref(entity);
		        }
		      }
		    }
	}
	
	private void Ref(Entity hitEntity) {
		if(hitEntity instanceof EntityLivingBase)
		{
			EntityLivingBase e = (EntityLivingBase) hitEntity;
			if(player != null && e != player && this.makeDamage) {
		    	if(attackEntityAsMob(e))
		    	{
		    		if(this.getRenderSkin() == TRprojectileID.Daybreak)
		    			this.makeDamage = false;
		    		else if(this.getRenderSkin() != TRprojectileID.Meowmere || this.getRenderSkin() != TRprojectileID.SolarEruption)
		    		this.setDead();
		    	}
			}
		}
	}
	
	/*private void Ref(MovingObjectPosition movingObjectPosition) {
		Entity hitEntity = movingObjectPosition.entityHit;
		if(hitEntity instanceof EntityLivingBase)
		{
			EntityLivingBase e = (EntityLivingBase) hitEntity;
			if(player != null && e != player && this.makeDamage) {
		    	  if(!attackEntityAsMob(e))
		    	  {
		    		  if(e.getHealth() > this.Damage) {
		    			  e.setHealth(e.getHealth() - this.Damage);
		    			  if(this.getRenderSkin() == 13)
		    				  this.makeDamage = false;
		    			  else if(this.getRenderSkin() != TRprojectileID.Meowmere || this.getRenderSkin() != TRprojectileID.SolarEruption)
		    			  this.setDead();
		    		  }
		    		  else{
		    			e.setHealth(0);
		    		  	e.onDeath(DamageSource.causeIndirectDamage(this, player));
		    		  	 if(this.getRenderSkin() != TRprojectileID.Meowmere || this.getRenderSkin() != TRprojectileID.SolarEruption)
		    		  	this.setDead();
		    		  }
		    	}
			}
		}
	}*/
	
	public int getID() {
		return this.id;
	}
	
	public int getDamage() {
		return this.Damage;
	}
	
	public EntityPlayer getPlayer() {
		return this.player;
	}
	
	public int getSpeed() {
		return this.Speed;
	}
	
	protected double lookX() {
		return this.getLookVec().x;
	}
	
	protected double lookY() {
		return this.getLookVec().y;
	}
	
	protected double lookZ() {
		return this.getLookVec().z;
	}
	
	protected void MoveingAttribute() {
		if(this.isStick && this.collisionCold <= 0)  {
            this.smotionX = this.motionX;
            this.smotionY = this.motionY;
            this.smotionZ = this.motionZ;
			this.collisionTimes++;
			this.collisionCold = 10;
			onStickinWall(this.id);
		}else if(this.collisionTimes <= 0){
			 this.motionX = this.x * (Speed / 10);
			 this.motionY = this.y * (Speed / 10);
			 this.motionZ = this.z * (Speed / 10);
		}
		
		 this.posX += this.motionX;
         this.posY += this.motionY;
         this.posZ += this.motionZ;
         
         this.setPosition(this.posX, this.posY, this.posZ);
	}
	
	private void onStickinWall(int id) {	
		switch(id) {
		case 10:
            int var1 = MathHelper.floor(this.posX);
            int var2 = MathHelper.floor(this.getEntityBoundingBox().minY);
            int var3 = MathHelper.floor(this.posZ);
            this.motionY -= (double)0.2F;
            if (this.smotionX > 0.0D && this.world.getBlockState(new BlockPos(var1 + 1, var2, var3)).getBlock() != Blocks.AIR)
            {
                this.motionX = this.smotionX = -this.smotionX;
            }
            else if (this.smotionX < 0.0D && this.world.getBlockState(new BlockPos(var1 - 1, var2, var3)).getBlock() != Blocks.AIR)
            {
                this.motionX = this.smotionX = -this.smotionX;
            }

            if (this.smotionY > 0.0D && this.world.getBlockState(new BlockPos(var1, var2 + 1, var3)).getBlock() != Blocks.AIR)
            {
                this.motionY = this.smotionY = -this.smotionY;
            }
            else if (this.smotionY < 0.0D && this.world.getBlockState(new BlockPos(var1, var2 - 1, var3)).getBlock() != Blocks.AIR)
            {
                this.motionY = this.smotionY = -this.smotionY;
            }

            if (this.smotionZ > 0.0D && this.world.getBlockState(new BlockPos(var1, var2, var3 + 1)).getBlock() != Blocks.AIR)
            {
                this.motionZ = this.smotionZ = -this.smotionZ;
            }
            else if (this.smotionZ < 0.0D && this.world.getBlockState(new BlockPos(var1, var2, var3 - 1)).getBlock() != Blocks.AIR)
            {
                this.motionZ = this.smotionZ = -this.smotionZ;
            }
			break;
		}
	}

	private void realseDust() {
		
	}
	
    public boolean attackEntityAsMob(Entity par1Entity)
    {
         boolean var4 = false;
         par1Entity.hurtResistantTime = -1;
         var4 = par1Entity.attackEntityFrom(DamageSource.causePlayerDamage(player), this.Damage);
         par1Entity.hurtResistantTime = -1;
         return var4;
    }
    
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		if(nbt.hasKey("Speed")) {
			this.Speed = nbt.getInteger("Speed");
			this.Damage = nbt.getInteger("Damage");
			this.id = nbt.getInteger("ProID");
			this.smotionX = nbt.getDouble("SMotionx");
			this.smotionY = nbt.getDouble("SMotiony");
			this.smotionZ = nbt.getDouble("SMotionz");
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		if(!nbt.hasKey("Speed")) {
			nbt.setInteger("Speed", this.Speed);
			nbt.setInteger("Damage", this.Damage);
			nbt.setInteger("ProID", this.id);
			nbt.setDouble("SMotionx", this.smotionX);
			nbt.setDouble("SMotiony", this.smotionY);
			nbt.setDouble("SMotionz", this.smotionZ);
		}
	}
}
