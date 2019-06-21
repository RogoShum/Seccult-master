package testmod.seccult.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

public class PotionDamage extends PotionMod{
	
	public PotionDamage() {
		super("damage", false, 0XFF0000, 0);
		MinecraftForge.EVENT_BUS.register(this);
		setBeneficial();
	}
	
	@SubscribeEvent
	public void onMobUpdate(LivingUpdateEvent event) {
		if(event.getEntityLiving() instanceof EntityLivingBase){
			EntityLivingBase mob = (EntityLivingBase) event.getEntityLiving();
			if(mob.isPotionActive(ModPotions.damage) && mob.getActivePotionEffect(ModPotions.damage).getDuration()>0 && mob.ticksExisted % 20 == 0){
				PotionEffect effect = mob.getActivePotionEffect(ModPotions.damage); 
				float amount = effect.getAmplifier() + 1;
				if(!(mob.isPotionActive(ModPotions.reflect))) {
				if (amount < mob.getHealth()) 
				    onDamage(mob, mob.getHealth(), amount);
				else 
					onKill(mob);
				}
				else {
					if (amount < mob.getHealth()) 
					    onDamage(mob, mob.getHealth(), amount);
					else 
						mob.setHealth(0);
				}
			}
		}
		else return;
	}

	public float onDamage(EntityLivingBase entity, float a, float b) {
		
		entity.setHealth(a - b);
		return entity.getHealth();
	}
	
	public void onKill(EntityLivingBase entity) {
			entity.attackEntityFrom(DamageSource.causeMobDamage(entity).setDamageAllowedInCreativeMode().setDamageBypassesArmor().setDamageIsAbsolute(), entity.getHealth() * 10);
			if(entity.getHealth() > 0) {
				entity.setDropItemsWhenDead(true);
				entity.setHealth(0);
				entity.onDeath(DamageSource.causeMobDamage(entity).setDamageAllowedInCreativeMode().setDamageBypassesArmor().setDamageIsAbsolute().setMagicDamage());
			}
	}
}
