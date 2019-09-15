package testmod.seccult.entity.livings.insect;

import net.minecraft.block.BlockBush;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import testmod.seccult.entity.livings.flying.EntityFlyable;

public class EntityDragonfly extends EntityFlyable implements IEntityInsect{

	private boolean isHovering;
	public EntityDragonfly(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 0.3F);
		this.hang = true;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(1.5);
	}
	
	@Override
	public void movingMode() 
	{
        if(this.posY > dropLine && this.ticksExisted % 20 == 0)
        	this.attackEntityFrom(DamageSource.STARVE, 0.5F);
		
		if(rand.nextBoolean() && rand.nextBoolean() && rand.nextBoolean() && rand.nextBoolean() && rand.nextBoolean() )
			isHovering = false;
		else
			isHovering = true;
		
        if (this.spawnPosition != null && (this.world.isAirBlock(this.spawnPosition) || this.spawnPosition.getY() < 1))
        {
            this.spawnPosition = null;
        }

        if (this.spawnPosition == null && this.rand.nextInt(30) == 0)
        {
            	BlockPos prePos = null;
            	Iterable<BlockPos> blocks = BlockPos.getAllInBox(this.getPosition().add(-7, -5, -7), this.getPosition().add(7, 5, 7));
            	for(BlockPos pos : blocks)
            	{
            		if(world.getBlockState(pos).getBlock() instanceof BlockBush || world.getBlockState(pos).getBlock() == Blocks.WATER)
            		{
            			if(prePos == null)
            				prePos = pos;
            			else if(this.getDistanceSq(pos) < this.getDistanceSq(prePos) && rand.nextInt(3) == 0)
            				prePos = pos;
            		}
            	}
            	
            		spawnPosition = prePos;
        }

        double d0, d2;
        
        if(spawnPosition != null)
        {
            d0 = (double)this.spawnPosition.getX() + 2D - this.posX;
            d2 = (double)this.spawnPosition.getZ() + 2D - this.posZ;
        }
        else
        {
        	d0 = this.posX + 1-(rand.nextFloat()*2) - this.posX;
        	d2 = this.posZ + 1-(rand.nextFloat()*2) - this.posZ;
        }
        
        if(!isHovering) {
        	this.motionX += (Math.signum(d0) * 2D - this.motionX) * 0.30000000149011612D;
        	this.motionY += (rand.nextFloat()*2.2 - 1)/3;
        	this.motionZ += (Math.signum(d2) * 2D - this.motionZ) * 0.30000000149011612D;
        }
        else if(this.motionY < 0)
        	this.motionY -= this.motionY;
        
        float f = (float)(MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
        float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
        this.moveForward = 0.5F;
        this.rotationYaw += f1;

        if (this.spawnPosition != null && this.rand.nextInt(10) == 0 && this.world.getBlockState(spawnPosition).getBlock() instanceof BlockBush && this.posY < spawnPosition.getY() + 0.5)
        {
            this.setIsBatHanging(true);
        }
	}

	@Override
	public void hangingMode() {
		 BlockPos blockpos = new BlockPos(this);
		if (this.world.getBlockState(blockpos).getBlock() instanceof BlockBush)
        {
            if (this.rand.nextInt(200) == 0)
            {
                this.rotationYawHead = (float)this.rand.nextInt(360);
            }

            if (this.world.getNearestPlayerNotCreative(this, 4.0D) != null)
            {
                this.setIsBatHanging(false);
            }
            
            if (this.rand.nextInt(50) == 0)
            {
            	this.setIsBatHanging(false);
            }
        }
        else
        {
            this.setIsBatHanging(false);
            this.world.playEvent((EntityPlayer)null, 1025, blockpos, 0);
        }
	}
}
