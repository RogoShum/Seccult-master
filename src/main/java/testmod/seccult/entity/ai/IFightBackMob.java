package testmod.seccult.entity.ai;

import net.minecraft.entity.EntityLivingBase;

public interface IFightBackMob {
    /**
     * Fight back the specified entity.
     */
    void fightEntityBack(EntityLivingBase target, float distanceFactor);
}
