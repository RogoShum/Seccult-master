package testmod.seccult.entity.ai;

import java.util.List;

import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.monster.IMob;

public class EntityAIFindCloestMonster<T extends EntityLivingBase> extends EntityAINearestAttackableTarget<EntityLivingBase> {

	@SuppressWarnings("unchecked")
	public EntityAIFindCloestMonster(EntityCreature creature, Class<T> classTarget, boolean checkSight) {
		super(creature, (Class<EntityLivingBase>) classTarget, checkSight);
	}

	@Override
	public boolean shouldExecute() {
        List<EntityLivingBase> list = this.taskOwner.world.<EntityLivingBase>getEntitiesWithinAABB(EntityLivingBase.class, this.getTargetableArea(this.getTargetDistance()), this.targetEntitySelector);

        if(this.taskOwner.getAttackingEntity() != null)
        {
        	if(this.taskOwner.getAttackingEntity().isDead)
        		this.taskOwner.setAttackTarget(null);
        }

        {
        	EntityLivingBase living = null;
        	
        	if(this.taskOwner.getAttackingEntity() != null)
        		living = this.taskOwner.getAttackingEntity();
            for(int i = 0; i < list.size(); i++)
            {
            	EntityLivingBase entity = list.get(i);
            	if(entity instanceof IMob && (living == null || taskOwner.getDistance(living) > taskOwner.getDistance(entity)))
            	{
            		living = entity;
            	}
            }
            if(living != null)
            {
            	this.targetEntity = living;
            	return true;
            }
            else
            	return false;
        }
   }
}
