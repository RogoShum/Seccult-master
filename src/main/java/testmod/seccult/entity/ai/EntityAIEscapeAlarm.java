package testmod.seccult.entity.ai;

import net.minecraft.entity.EntityCreature;

public class EntityAIEscapeAlarm extends net.minecraft.entity.ai.EntityAIHurtByTarget {

	public EntityAIEscapeAlarm(EntityCreature creatureIn, boolean entityCallsForHelpIn,
			Class<?>[] excludedReinforcementTypes) {
		super(creatureIn, entityCallsForHelpIn, excludedReinforcementTypes);
	}

}
