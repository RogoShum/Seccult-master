package testmod.seccult.entity.ai;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.util.math.BlockPos;
import testmod.seccult.entity.EntityBorderCrosser;
import testmod.seccult.entity.livings.landCreature.EntityDreamPop;
import testmod.seccult.magick.implementation.ImplementationFocused;
import testmod.seccult.world.gen.DimensionMagic;

public class EntityAIFindBorderCrosser extends EntityAIBase {
	private EntityDreamPop pop;
	private int coolDown;
	
	public EntityAIFindBorderCrosser(EntityDreamPop pop) {
		this.pop = pop;
	}
	
	@Override
	public boolean shouldExecute() {
		if(pop.getHealth() < 12)
			return true;
		return false;
	}
	
	@Override
    public void updateTask()
    {
		if(coolDown > 1)
		coolDown--;
		
		if(pop.getCrosser() == null)
		{
			List<Entity> list = pop.world.getEntitiesWithinAABBExcludingEntity(pop, pop.getEntityBoundingBox().grow(32));
			
			for(int i = 0; i < list.size(); ++i)
			{
				if(list.get(i) instanceof EntityBorderCrosser)
				{
					pop.setCrosser((EntityBorderCrosser) list.get(i));
					return;
				}
			}
			
			if(coolDown <= 0)
			{
				BlockPos pos = ImplementationFocused.getBlockLookedAt(pop, 20);
				EntityBorderCrosser crosser = null;
				crosser = new EntityBorderCrosser(pop.world, DimensionMagic.MAGIC_ID);
				crosser.setPosition(pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5);
				if(pop.dimension != DimensionMagic.MAGIC_ID)
					pop.world.spawnEntity(crosser);
				coolDown=300;
			}
			return;
		}
    }
    
    @Override
    public void startExecuting() 
    {
		if(pop.getCrosser() == null)
		{
			List<Entity> list = pop.world.getEntitiesWithinAABBExcludingEntity(pop, pop.getEntityBoundingBox().grow(32));
			
			for(int i = 0; i < list.size(); ++i)
			{
				if(list.get(i) instanceof EntityBorderCrosser)
				{
					pop.setCrosser((EntityBorderCrosser) list.get(i));
					return;
				}
			}
			
			if(coolDown <= 0)
			{
				BlockPos pos = ImplementationFocused.getBlockLookedAt(pop, 20);
				EntityBorderCrosser crosser = null;
				crosser = new EntityBorderCrosser(pop.world, DimensionMagic.MAGIC_ID);
				crosser.setPosition(pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5);
				if(pop.dimension != DimensionMagic.MAGIC_ID)
					pop.world.spawnEntity(crosser);
				coolDown=300;
			}
			return;
		}

		super.startExecuting();
    }
}
