package testmod.seccult.entity.livings;

import java.util.List;

import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import testmod.seccult.entity.EntityFlyable;
import testmod.seccult.entity.livings.insect.IEntityInsect;

public class EntityBird extends EntityFlyable
{
	protected Entity target;
	private int onGroundTime;
	private int flyingTime;
	public EntityBird(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 0.6F);
	}

	@Override
    public boolean canBeLeashedTo(EntityPlayer player)
    {
        return true;
    }
	
	@Override
	public void onUpdate() {
        super.onUpdate();
        
        if (!this.getIsBatHanging())
        {
            this.motionY *= 0.6000000238418579D;
        }
	}
	
	@Override
	public void movingMode() {
		if(!this.world.isDaytime())
			return;
		
		flyingTime++;
        
        List<Entity> entity = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(10));
        for(Entity e : entity)
        {
        	if(e instanceof IEntityInsect && (target == null || this.getDistance(target.posX, target.posY, target.posZ) > this.getDistance(e)))
        	{
        		target = e;
        	}
        }
        
        List<Entity> entity1 = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(1));
        for(Entity e : entity1)
        {
        	if(e instanceof IEntityInsect)
        	{
        		this.faceEntity(e, 40, 40);
        		e.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
        	}
        }
        
        if(target!=null && !target.isEntityAlive())
        	this.target = null;
        
        if (this.target != null)
        {
            this.Moveto(target.posX, target.posY, target.posZ, 0.5F);
        }
        else
        {
        if(rand.nextInt(100) == 0 || this.ticksExisted % 600 == 0)
        	this.onGroundTime = rand.nextInt(800);
        
        this.motionX = this.getLookVec().x / 2;
        
        if(flyingTime < 40 && rand.nextFloat() < 0.6)
        	this.motionY = rand.nextFloat() / 2;
        else 
        	this.motionY = (rand.nextFloat()*2.2 - 1)/3;
        
        this.motionZ = this.getLookVec().z / 2;

        if (onGroundTime > 0)
        {
            this.setIsBatHanging(true);
        }
        }
	}
	
	@Override
	public void hangingMode() {
		
		
		
		 BlockPos blockpos = new BlockPos(this).down();
		 int y = (int)(this.posY - 0.2);
		 if((this.world.getBlockState(blockpos).getBlock() instanceof BlockLiquid))
		 {
			this.onGroundTime = 0;
         	this.setIsBatHanging(false); 
		 }
		 BlockPos blockpos1 = new BlockPos(blockpos.getX(), y, blockpos.getZ());
		 if ((this.world.getBlockState(blockpos1).getBlock() instanceof BlockAir))
	     {
			 this.motionY = -0.100049;
			 this.motionX = this.getLookVec().x / 3;
			 this.motionZ = this.getLookVec().z / 3;
		 }
		 
		 if ((this.world.getBlockState(blockpos).getBlock() instanceof BlockLiquid))
	     {
			 this.motionY = -0.100049;
			 this.motionX = 0.5 - this.rand.nextFloat();
			 this.motionZ = 0.5 - this.rand.nextFloat();
			 
			 float f = (float)(MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
			 float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
	        
			 this.rotationYaw += f1;
		 }
		 
		 this.onGroundTime--;
		 flyingTime = 0;
		if (!(this.world.getBlockState(blockpos).getBlock() instanceof BlockLiquid) && !(this.world.getBlockState(blockpos).getBlock() instanceof BlockAir))
        {

			 if(rand.nextInt(10) == 0)
			 {
		         this.rotationYaw = this.rotationYaw + 20 - this.rand.nextInt(40);
				 this.motionX += this.getLookVec().x / 5;
				 this.motionZ += this.getLookVec().z / 5;
			 }
		
            if (this.rand.nextInt(200) == 0)
            {
                this.rotationYawHead = (float)this.rand.nextInt(360);
            }

            if (this.world.getNearestPlayerNotCreative(this, 4.0D) != null)
            {
            	this.onGroundTime = 0;
                this.setIsBatHanging(false);
            }
            
            if (this.rand.nextInt(100) == 0 || onGroundTime <= 0)
            {
            	this.onGroundTime = 0;
            	this.setIsBatHanging(false);
            }
        }
	}
}
