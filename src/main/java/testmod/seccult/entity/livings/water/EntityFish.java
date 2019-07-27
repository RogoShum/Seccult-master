package testmod.seccult.entity.livings.water;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;
import testmod.seccult.entity.EntityWaterCreature;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.active.Magick;

public class EntityFish extends EntityWaterCreature{

	private int swing;
	private boolean swingUp;
	
	private int swimingTime;
	
	private int warningTime;
	
	public EntityFish(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 0.4F);
		this.swimingTime += 35;
	}

	@Override
	public void waterMoving() 
	{
		this.setNoGravity(true);
		if(!this.world.isRemote && this.world.isDaytime()) {
            if (this.world.getNearestPlayerNotCreative(this, 5.0D) != null)
            {
            	EntityWaterCreature.inWaterWalk(this, 0.3F, 1, 16);
            }
			
            if(this.rand.nextInt(100) == 0)
            	swimingTime += this.rand.nextInt(200) + 20;
            if(swimingTime > 0)
            {
            	EntityWaterCreature.inWaterWalk(this, 0.1F, 5, 1.5F);
            	if(this.rand.nextInt(3) == 0)
            	{
            		EntityWaterCreature.inWaterWalk(this, 0.2F, 5, 1.5F);
            		if(this.rand.nextInt(2) == 0)
            		{
            			Magick m = ModMagicks.getMagickFromName(ModMagicks.TeleportMagick);
            			m.setMagickAttribute(this, null, null, 2 + this.rand.nextInt(8), 0);
            		}
            	}
            	swimingTime--;
            }
		}
		else if(!this.world.isRemote)
		{
            if (this.world.getNearestPlayerNotCreative(this, 2.0D) != null || warningTime > 0)
            {
            	EntityWaterCreature.inWaterWalk(this, 0.3F, 1, this.rand.nextInt(8) + 8);
    			if(this.rand.nextInt(3) == 0)
    			{
    				EntityWaterCreature.inWaterWalk(this, 0.3F, 1, this.rand.nextInt(8) + 8);
    			}
    			swimingTime += 35;
    			
    			warningOthers();
            }
		}
		
		if(warningTime > 0)
			warningTime--;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
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
		
		if(swing > 15)
			swingUp = false;
		else if(swing < -15)
			swingUp = true;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		boolean attack = super.attackEntityFrom(source, amount);
		if(attack)
		{
			this.warningTime += 1;
			warningOthers();
		}
		return attack;
	}
	
	private void warningOthers()
	{
		List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(4));
		
		for(Entity e : entities)
		{
			if(e instanceof EntityFish)
			{
				EntityFish fish = (EntityFish) e;
				fish.warningTime += 1;
			}
		}
	}
	
	public int getSwing()
	{
		return this.swing;
	}
	
	@Override
	public void GroundThing() 
	{
		this.setNoGravity(false);
		EntityWaterCreature.onGroundJump(this, 0.5F, 20, 3);
	}
}
