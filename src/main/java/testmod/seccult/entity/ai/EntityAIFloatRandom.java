package testmod.seccult.entity.ai;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.monster.EntityVex;
import net.minecraft.util.math.BlockPos;
import testmod.seccult.entity.EntityBorderCrosser;
import testmod.seccult.entity.livings.landCreature.EntityDreamPop;
import testmod.seccult.magick.implementation.ImplementationFocused;
import testmod.seccult.world.gen.DimensionMagic;

public class EntityAIFloatRandom extends EntityAIBase {
	private EntityCreature living;
	
	public EntityAIFloatRandom(EntityCreature living)
    {
		this.living = living;
        this.setMutexBits(1);
    }

    /**
     * Returns whether the EntityAIBase should begin execution.
     */
    public boolean shouldExecute()
    {
        return !living.getMoveHelper().isUpdating() && living.getRNG().nextInt(7) == 0;
    }

    /**
     * Returns whether an in-progress EntityAIBase should continue executing
     */
    public boolean shouldContinueExecuting()
    {
        return false;
    }

    /**
     * Keep ticking a continuous task that has already been started
     */
    public void updateTask()
    {
        BlockPos blockpos = new BlockPos(living);


        for (int i = 0; i < 3; ++i)
        {
            BlockPos blockpos1 = blockpos.add(living.getRNG().nextInt(15) - 7, living.getRNG().nextInt(11) - 5, living.getRNG().nextInt(15) - 7);

            if (living.world.isAirBlock(blockpos1))
            {
                living.getMoveHelper().setMoveTo((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, living.getAIMoveSpeed());

                if (living.getAttackTarget() == null)
                {
                    living.getLookHelper().setLookPosition((double)blockpos1.getX() + 0.5D, (double)blockpos1.getY() + 0.5D, (double)blockpos1.getZ() + 0.5D, 180.0F, 20.0F);
                }

                break;
            }
        }
    }
}
