package testmod.seccult.entity.ai;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import testmod.seccult.entity.EntityBorderCrosser;
import testmod.seccult.entity.livings.landCreature.EntityDreamPop;
import testmod.seccult.magick.implementation.ImplementationFocused;
import testmod.seccult.world.gen.DimensionMagic;

public class EntityAIMoveToMagick extends EntityAIBase {
	private EntityDreamPop pop;
	
	public EntityAIMoveToMagick(EntityDreamPop pop) {
		this.pop = pop;
	}
	
	@Override
	public boolean shouldExecute() {
		if(pop.getCrosser() != null)
			return true;
		return false;
	}

    @Override
    public void startExecuting() 
    {
    	if (pop.getCrosser().getDistance(pop) >= 32F || !this.pop.canEntityBeSeen(pop.getCrosser()))
        {
            this.pop.getNavigator().clearPath();
        }
        else
        {
            this.pop.getNavigator().tryMoveToEntityLiving(pop.getCrosser(), 1D);
        }

        this.pop.getLookHelper().setLookPositionWithEntity(pop.getCrosser(), 30.0F, 30.0F);
		
		super.startExecuting();
    }
}
