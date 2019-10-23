package testmod.seccult.entity.livings.insect;

import net.minecraft.block.BlockBush;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import testmod.seccult.entity.livings.flying.EntityFlyable;
import testmod.seccult.init.ModItems;

public class EntityButterfly extends EntityFlyable implements IEntityInsect{
	
	public EntityButterfly(World worldIn) {
		super(worldIn);
		float size = rand.nextFloat();
		if(size > 0.6F) size = 0.6F;
		this.setSize(size, size / 2);
		this.hang = true;
		
		this.swingLimit = 45;
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5.0D);
        this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(1);
	}
	
	@Override
	public void movingMode() {
        if(this.posY > dropLine && this.ticksExisted % 20 == 0)
        	this.attackEntityFrom(DamageSource.STARVE, 0.5F);
		
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
            		if(world.getBlockState(pos).getBlock() instanceof BlockBush)
            		{
            			if(prePos == null)
            				prePos = pos;
            			else if(this.getDistanceSq(pos) < this.getDistanceSq(prePos) && rand.nextInt(3) == 0)
            				prePos = pos;
            		}
            	}
            	
            	if(prePos != null)
            		spawnPosition = prePos;
        }

        double d0, d2;
        
        if(spawnPosition != null)
        {
            d0 = (double)this.spawnPosition.getX() + 0.5D - this.posX;
            d2 = (double)this.spawnPosition.getZ() + 0.5D - this.posZ;
            
            this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
            this.motionY += (rand.nextFloat()*2.2 - 1)/3;
            this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
        }
        else
        {
        	double randRotation = (0.125 - this.rand.nextDouble() * 0.25);
    		if(this.ticksExisted % 20 == 0)
    		{
    			randRotation = (0.5 - this.rand.nextDouble());
    		}
    		else
    		if(this.ticksExisted % 5 == 0)
    		{
    			randRotation = (0.25 - this.rand.nextDouble() * 0.5);
    		}
            
            this.motionX = (this.LookX() + randRotation) / 10 * 2;
            if(this.world.getBlockState(this.getPosition().down()).getBlock() instanceof BlockLiquid)
            	this.motionY += this.rand.nextFloat();
            else
            	this.motionY += (this.rand.nextFloat()*4 - 2)/3 * 0.6;
            this.motionZ = (this.LookZ() + randRotation) / 10 * 2;
        }
        
        
        
        
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
	protected Item getDropItem() {
		return ModItems.Wing;
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
