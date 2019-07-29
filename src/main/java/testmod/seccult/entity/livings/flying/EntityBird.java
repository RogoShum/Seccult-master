package testmod.seccult.entity.livings.flying;

import java.util.List;

import net.minecraft.block.BlockAir;
import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import testmod.seccult.entity.livings.insect.IEntityInsect;

public class EntityBird extends EntityFlyable
{
	protected Entity target;
	protected BlockPos targetPos;
	private int onGroundTime;
	private int flyingTime;
	public float headRotationX;
	private int escapeTime;
	
	public EntityBird(World worldIn) {
		super(worldIn);
		float size = rand.nextFloat();
		if(size > 0.55F) size = 0.55F;
		if(size < 0.3F) size = 0.3F;
		this.setSize(size * 0.8F, size);
		
		this.swingLimit = 120;
		this.setIsBatHanging(true);
		this.onGroundTime = this.rand.nextInt(250);
	}

	@Override
    public boolean canBeLeashedTo(EntityPlayer player)
    {
        return true;
    }
	
	@Override
	public void onUpdate() {
        super.onUpdate();

        if(escapeTime > 0)
        	escapeTime--;
        
        if (!this.getIsBatHanging())
        {
            this.motionY *= 0.6000000238418579D;
        }
        
        if(!this.world.isRemote && this.getIsDayTime() != this.world.isDaytime())
        	this.setIsDayTime(this.world.isDaytime());
        
        if(!this.world.isRemote)
        {
        	if(!this.world.isDaytime() && this.onGround && this.rand.nextInt(20) == 0)
        		this.setIsSleeping(true);
        	else if(this.world.isDaytime() || !this.onGround)
        		this.setIsSleeping(false);
        }
        
        if(this.getIsSleeping())
        {
        	if(headRotationX < 130 * 0.017453292F)
        		headRotationX += 0.04;
        	else
        		headRotationX = 130 * 0.017453292F;
        }
        else
        {
        	headRotationX =  0.5462880558742251F;
        }
	}
	
	@Override
	protected void doSwing() {

		if(this.getIsSleeping())
		{
			swing = -2;
			return;
		}
		
		if(swingUp)
		{
			if(this.inWater)
				swing+=55;
			
			if(!this.onGround)
				swing += 45;
		}
		else
		{
			if(this.inWater)
				swing-=55;
			
			if(!this.onGround)
				swing -= 45;
		}
		
		if(swing > this.swingLimit)
			swingUp = false;
		else if(swing < 0)
			swingUp = true;
	}
	
    public float getEyeHeight()
    {
        return this.height / 1.2F;
    }
	
	@Override
	public void movingMode() {
		if(!this.getIsDayTime() && escapeTime <= 0)
		{
			this.onGroundTime = 200;
			this.setIsBatHanging(true);
			return;
		}
		
		flyingTime++;
        
		findInsect();
        
        if(target!=null && !target.isEntityAlive())
        	this.target = null;
        
        if (this.target != null)
        {
        	this.faceEntity(this.target, 40, 40);
            this.Moveto(target.posX, target.posY, target.posZ, 0.1F);
            if(this.getDistance(this.target) < 1)
            	this.target.attackEntityFrom(DamageSource.causeMobDamage(this), 2);
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
        turn(4);
        this.rotationYaw = this.rotationYawHead;
        
        if (onGroundTime > 0 && targetPos != null)
        {
        	this.Moveto(targetPos.getX(), targetPos.getY(), targetPos.getZ(), 0.1F);
        	if(this.getPosition() == targetPos || this.onGround)
        	{
        		this.setIsBatHanging(true);
        		targetPos = null;
        	}
        }
        else if(targetPos == null)
        {
        	BlockPos prePos = this.getPosition().add(LookX() * this.rand.nextInt(10), LookY() * this.rand.nextInt(10) - this.rand.nextInt(5) - 5, LookZ() * this.rand.nextInt(10));
        	this.targetPos = prePos;
        }
        
        }
	}
	
	@Override
	public void hangingMode() {
		 BlockPos blockpos = new BlockPos(this).down();
		 
		 if ((this.world.getBlockState(blockpos).getBlock() instanceof BlockAir))
	     {
			 this.motionY = -0.0700049;
		 }
		 
		 if (this.inWater)
	     {
			 this.motionY = 0.125 - this.rand.nextFloat() * 0.25;
			 this.motionX = 0.125 - this.rand.nextFloat() * 0.25;
			 this.motionZ = 0.125 - this.rand.nextFloat() * 0.25;
			 this.rotationYaw += this.rand.nextFloat();
		 }
		 else if((this.world.getBlockState(blockpos).getBlock() instanceof BlockLiquid))
		 {
	 
				this.onGroundTime = 0;
	         	this.setIsBatHanging(false); 
		 }
		 
		 this.onGroundTime--;
		 flyingTime = 0;

		if (this.onGround)
        {
			 if(rand.nextInt(20) == 0 && !this.getIsSleeping())
			 {
		         this.rotationYaw = this.rotationYaw + 20 - this.rand.nextInt(40);
				 this.motionX += this.getLookVec().x / 7;
				 this.motionZ += this.getLookVec().z / 7;
				 turn(1);
				 
				 findInsect();
				 if(this.target != null)
				 {
					 this.onGroundTime = 0;
	                 this.setIsBatHanging(false);
				 }
			 }
		
            if (!this.getIsSleeping() && this.rand.nextInt(200) == 0)
            {
                this.rotationYawHead = (float)this.rand.nextInt(360);
            }

            if(this.getIsSleeping())
            {
            	if (this.world.getNearestPlayerNotCreative(this, 1.0D) != null)
                {
                	this.onGroundTime = 0;
                	this.escapeTime += 200;
                    this.setIsBatHanging(false);
                }
            }
            else
            {
            	if (this.world.getNearestPlayerNotCreative(this, 4.0D) != null)
                {
                	this.onGroundTime = 0;
                    this.setIsBatHanging(false);
                }
            }
            
            if (onGroundTime <= 0)
            {
            	this.onGroundTime = 0;
            	this.setIsBatHanging(false);
            }
        }
	}
	
	public void findInsect()
	{
        List<Entity> entity = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(10));
        for(Entity e : entity)
        {
        	if(e instanceof IEntityInsect && (target == null || this.getDistance(target.posX, target.posY, target.posZ) > this.getDistance(e)))
        	{
        		if(this.canSeeTarget(e))
        		target = e;
        	}
        }
	}
}
