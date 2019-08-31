package testmod.seccult.entity.livings;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityBase extends EntityLiving{
	protected float swing;
	protected boolean swingUp;	
	
	private static final DataParameter<Byte> Day = EntityDataManager.<Byte>createKey(EntityBase.class, DataSerializers.BYTE);
	private static final DataParameter<Byte> Sleep = EntityDataManager.<Byte>createKey(EntityBase.class, DataSerializers.BYTE);
	
	public int swingLimit;
	
	protected int tick = this.ticksExisted;
	public boolean isTRboss;
	
	protected EntityLivingBase target; 
	
	public EntityBase(World worldIn) {
		super(worldIn);
	}

	protected EntityPlayer findPlayerToAttack(Entity myentity) 
	{
	       Entity entity = null;
	       double effectiveBoundary = 128.0D;
	       List<Entity> list = myentity.world.getEntitiesWithinAABBExcludingEntity(myentity, myentity.getEntityBoundingBox().grow(effectiveBoundary, effectiveBoundary, effectiveBoundary));
	     
	       if ((list != null) && (list.size() > 0))
		    {
	    	   for (int j1 = 0; j1 < list.size(); ++j1)
	    	   {
	    		   entity = (Entity)list.get(j1);
		        
		       		if (entity != null)
		       		{
		       			if ((myentity.world.isRemote) || (entity instanceof EntityItemFrame) || (entity instanceof EntityPainting)) {
				          continue;
				        }
				        
				        EntityPlayer player = Aura(entity);
				        if(player != null)
				        	return player;
				   }    
		       }
	       }
		return null;
	}
	
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(Day, Byte.valueOf((byte)0));
        this.dataManager.register(Sleep, Byte.valueOf((byte)0));
    }
	
    public boolean getIsDayTime()
    {
        return (((Byte)this.dataManager.get(Day)).byteValue() & 1) != 0;
    }

    public void setIsDayTime(boolean isDay)
    {
        byte b0 = ((Byte)this.dataManager.get(Day)).byteValue();

        if (isDay)
        {
            this.dataManager.set(Day, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.dataManager.set(Day, Byte.valueOf((byte)(b0 & -2)));
        }
    }
    
    public boolean getIsSleeping()
    {
        return (((Byte)this.dataManager.get(Sleep)).byteValue() & 1) != 0;
    }

    public void setIsSleeping(boolean isSleep)
    {
        byte b0 = ((Byte)this.dataManager.get(Sleep)).byteValue();

        if (isSleep)
        {
            this.dataManager.set(Sleep, Byte.valueOf((byte)(b0 | 1)));
        }
        else
        {
            this.dataManager.set(Sleep, Byte.valueOf((byte)(b0 & -2)));
        }
    }
    
	@Override
	public void onUpdate() {
		super.onUpdate();
		doSwing();

	}
	
	public boolean canSeeTarget(Entity e)
    {
    	return	canSeeTarget(e.posX, e.posY, e.posZ);
    }
	
    public boolean canSeeTarget(double pX, double pY, double pZ)
    {
    	return (this.world.rayTraceBlocks(new Vec3d(this.posX, this.posY + 0.55D, this.posZ), new Vec3d(pX, pY, pZ), false) == null);
    }
	
	protected void doSwing()
	{
		float motion = (float) (Math.abs(this.motionX) + Math.abs(this.motionY) + Math.abs(this.motionZ));
		
		if(swingUp)
		{
			if(motion > 0)
				swing += (motion + 1) * 10;
			else if (this.world.isDaytime())
				swing++;
			else if (this.ticksExisted % 3 ==0)
				swing++;
		}
		else
		{
			if(motion > 0)
				swing -= (motion + 1) * 10;
			else if (this.world.isDaytime())
				swing--;
			else if (this.ticksExisted % 3 ==0)
				swing--;
		}
		
		if(swing > this.swingLimit)
			swingUp = false;
		else if(swing < -this.swingLimit)
			swingUp = true;
	}
	
	protected EntityPlayer Aura(Entity hitEntity) {
       if(hitEntity instanceof EntityPlayer) {
    	   EntityPlayer player = (EntityPlayer) hitEntity;
    	   return player;
       }
	return null;
	}
	
	protected void turn(int dis)
	{
		BlockPos pos = this.getPosition().add(LookX() * dis, LookY() * dis, LookZ() * dis);
		if(this.world.isAirBlock(pos) || this.world.getBlockState(pos).getBlock() == Blocks.LAVA)
		{
			this.rotationYaw += this.rand.nextInt(50);
		}
	}
	
	public boolean getIfCloestTarget(Entity self, Entity target, Entity prevTarget)
	{
		return getIfCloestTarget(self, target.posX, target.posY, target.posZ, prevTarget.posX, prevTarget.posY, prevTarget.posZ);
	}
	
	public boolean getIfCloestTarget(Entity self, BlockPos target, BlockPos prevTarget)
	{
		return getIfCloestTarget(self, target.getX(), target.getY(), target.getZ(), prevTarget.getX(), prevTarget.getY(), prevTarget.getZ());
	}
	
	public boolean getIfCloestTarget(Entity self, double x, double y, double z, double x2, double y2, double z2)
	{
		boolean var = false;
		if(self.getDistance(x, y, z) < self.getDistance(x2, y2, z2))
			var = true;
		return var;
	}
	
	public double LookX()
	{
		return this.getLookVec().x;
	}
	
	public double LookY()
	{
		return this.getLookVec().y;
	}
	
	public double LookZ()
	{
		return this.getLookVec().z;
	}
	
	public float getSwing()
	{
		return this.swing;
	}
	
	protected void Moveto(double x, double y, double z, float speed) {
        this.motionX += (Math.signum(x - this.posX) - this.motionX) * speed;
        this.motionY += (Math.signum(y - this.posY) - this.motionY) * speed;
        this.motionZ += (Math.signum(z - this.posZ) - this.motionZ) * speed;
	}
	
	//copy from dragon
	protected boolean destroyBlocksInAABB(AxisAlignedBB p_70972_1_)
    {
        int i = MathHelper.floor(p_70972_1_.minX);
        int j = MathHelper.floor(p_70972_1_.minY);
        int k = MathHelper.floor(p_70972_1_.minZ);
        int l = MathHelper.floor(p_70972_1_.maxX);
        int i1 = MathHelper.floor(p_70972_1_.maxY);
        int j1 = MathHelper.floor(p_70972_1_.maxZ);
        boolean flag = false;
        boolean flag1 = false;

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
                        if (!net.minecraftforge.event.ForgeEventFactory.getMobGriefingEvent(this.world, this))
                        {
                            flag = true;
                        }
                        else if (block.canEntityDestroy(iblockstate, this.world, blockpos, this) && net.minecraftforge.event.ForgeEventFactory.onEntityDestroyBlock(this, blockpos, iblockstate))
                        {
                            if (block != Blocks.COMMAND_BLOCK && block != Blocks.REPEATING_COMMAND_BLOCK && block != Blocks.CHAIN_COMMAND_BLOCK && block != Blocks.IRON_BARS && !(block instanceof BlockLiquid))
                            {
                                flag1 = this.world.setBlockToAir(blockpos) || flag1;
                            }
                            else
                            {
                                flag = true;
                            }
                        }
                        else
                        {
                            flag = true;
                        }
                    }
                }
            }
        }

        if (flag1)
        {
            double d0 = p_70972_1_.minX + (p_70972_1_.maxX - p_70972_1_.minX) * (double)this.rand.nextFloat();
            double d1 = p_70972_1_.minY + (p_70972_1_.maxY - p_70972_1_.minY) * (double)this.rand.nextFloat();
            double d2 = p_70972_1_.minZ + (p_70972_1_.maxZ - p_70972_1_.minZ) * (double)this.rand.nextFloat();
            this.world.spawnParticle(EnumParticleTypes.EXPLOSION_LARGE, d0, d1, d2, 0.0D, 0.0D, 0.0D);
        }

        return flag;
    }
}
