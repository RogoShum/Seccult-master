package testmod.seccult.entity.livings.water;

import net.minecraft.entity.passive.IAnimals;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import testmod.seccult.Seccult;
import testmod.seccult.entity.livings.EntityBase;

public abstract class EntityWaterCreature extends EntityBase implements IAnimals {

	public EntityWaterCreature(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
		if(this.inWater)
			waterMoving();
		else
			GroundThing();
	}
	
	public abstract void GroundThing();

	public abstract void waterMoving();
	
	public static void onGroundJump(EntityWaterCreature water, float height, int frequency, float distance)
	{
		if(water.ticksExisted % frequency == 0)
		{
			water.motionX += (water.LookX() + 1 - water.rand.nextDouble() * 2) / 10 * distance;
			water.motionZ += (water.LookZ() + 1 - water.rand.nextDouble() * 2) / 10 * distance;
			
			float f = (float)(MathHelper.atan2(water.motionZ, water.motionX) * (180D / Math.PI)) - 90.0F;
			float f1 = MathHelper.wrapDegrees(f - water.rotationYaw);
        
			water.rotationYaw += f1;
			
			water.motionY += height * water.rand.nextFloat();
		}
	}
	
	public static void inWaterWalk(EntityBase water, float height, int frequency, float distance)
	{
		if(water.ticksExisted % frequency == 0)
		{
			
			double randRotation = (0.125 - Seccult.rand.nextDouble() * 0.25);
			if(water.ticksExisted % 20 == 0)
			{
				randRotation = (0.5 - Seccult.rand.nextDouble());
			}
			else
			if(water.ticksExisted % 5 == 0)
			{
				randRotation = (0.25 - Seccult.rand.nextDouble() * 0.5);
			}
			water.motionX += (water.LookX() + randRotation) / 10 * distance;
			water.motionY += (Seccult.rand.nextFloat()*4 - 2)/3 * height;
			water.motionZ += (water.LookZ() + randRotation) / 10 * distance;
			
			float f = (float)(MathHelper.atan2(water.motionZ, water.motionX) * (180D / Math.PI)) - 90.0F;
			float f1 = MathHelper.wrapDegrees(f - water.rotationYaw);
        
			water.rotationYaw += f1;
			
			if(water.isInWater())
			{
            for (int i = 0; i < 8; ++i)
            {
                float f2 = Seccult.rand.nextFloat() - Seccult.rand.nextFloat();
                float f3 = Seccult.rand.nextFloat() - Seccult.rand.nextFloat();
                float f4 = Seccult.rand.nextFloat() - Seccult.rand.nextFloat();
                water.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, water.posX + (double)f2, water.posY + (double)f4, water.posZ + (double)f3, water.motionX, water.motionY, water.motionZ);
            }
			}
		}
	}
	
    public boolean canBreatheUnderwater()
    {
        return true;
    }
    
    public void onEntityUpdate()
    {
        int i = this.getAir();
        super.onEntityUpdate();

        if (this.isEntityAlive() && !this.isInWater())
        {
            --i;
            this.setAir(i);

            if (this.getAir() == -20)
            {
                this.setAir(0);
                this.attackEntityFrom(DamageSource.DROWN, 2.0F);
            }
        }
        else
        {
            this.setAir(300);
        }
    }
    
    public boolean isPushedByWater()
    {
        return false;
    }
}
