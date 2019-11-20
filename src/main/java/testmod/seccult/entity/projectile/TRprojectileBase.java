package testmod.seccult.entity.projectile;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
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
		this.setSize(0.4F, 0.4F);
	}

	public TRprojectileBase(World worldIn, int Damage, int speed, EntityPlayer player, int id) {
		super(worldIn);
		this.setSize(0.4F, 0.4F);
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
		DamageThings();
		updateMoveingAttribute();
		realseDust();
		if(this.ticksExisted > 200)
			this.setDead();

        if (!this.world.isRemote && this.stick(this.getEntityBoundingBox())) {
        	this.isStick = true;
        }
        else
        	this.isStick = false;
		
        this.collisionCold--;
        if(this.collisionCold < -100)
        	this.collisionCold = -100;
	}
	
	protected boolean stick(AxisAlignedBB p_70972_1_)
    {
        int i = MathHelper.floor(p_70972_1_.minX);
        int j = MathHelper.floor(p_70972_1_.minY);
        int k = MathHelper.floor(p_70972_1_.minZ);
        int l = MathHelper.floor(p_70972_1_.maxX);
        int i1 = MathHelper.floor(p_70972_1_.maxY);
        int j1 = MathHelper.floor(p_70972_1_.maxZ);
        boolean flag = false;
        
        for (int k1 = i; k1 <= l; ++k1)
        {
            for (int l1 = j; l1 <= i1; ++l1)
            {
                for (int i2 = k; i2 <= j1; ++i2)
                {
                    BlockPos blockpos = new BlockPos(k1, l1, i2);
                    IBlockState iblockstate = this.world.getBlockState(blockpos);
                    Block block = iblockstate.getBlock();

                    if (!block.isAir(iblockstate, this.world, blockpos) && iblockstate.getMaterial() != Material.FIRE)
                    {
                        {
                            flag = true;
                        }
                    }
                }
            }
        }

        return flag;
    }
	
	public void setAttribute(int Damage, int speed, EntityPlayer player, int id) {
		this.Speed = speed;
		this.Damage = Damage;
		this.player = player;
		this.id = id;
		this.makeDamage = true;
		
		Vec3d QAQ = this.getLookVec();
		this.x = QAQ.x;
		this.y = QAQ.y;
		this.z = QAQ.z;
		initMoveingAttribute();
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
	
	protected void initMoveingAttribute() {
		this.motionX = this.x * (Speed / 10);
		this.motionY = this.y * (Speed / 10);
		this.motionZ = this.z * (Speed / 10);
	}
	
	protected void updateMoveingAttribute() {
		if(this.isStick && this.collisionCold <= 0)  {
            this.smotionX = this.motionX;
            this.smotionY = this.motionY;
            this.smotionZ = this.motionZ;
			this.collisionTimes++;
			this.collisionCold = 10;
			onStickinWall(this.id);
		}
		
		if(this.id == 10)
			this.motionY -= 0.05;
		
		if(this.motionY < -0.7)
			this.motionY = -0.7;
		
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
