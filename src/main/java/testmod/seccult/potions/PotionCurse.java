package testmod.seccult.potions;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.DamageSource;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import testmod.seccult.Seccult;
import testmod.seccult.magick.magickState.StateManager;

public class PotionCurse extends PotionMod{

	public PotionCurse() {
		super("curse", true, 0XFFAB4B, 0);
	}

	@SubscribeEvent
	public void onCurseUpdate(LivingUpdateEvent event) {
		EntityLivingBase living = event.getEntityLiving();
		if(!living.isPotionActive(ModPotions.curse) || living.getActivePotionEffect(ModPotions.curse).getDuration()<0 || !(living.ticksExisted % 100 == 0)) return;
		int rando = Seccult.rand.nextInt(10);
		int mult = living.getActivePotionEffect(ModPotions.curse).getAmplifier();
		List<Entity> entities = living.world.getEntitiesWithinAABBExcludingEntity(living, living.getEntityBoundingBox().grow(3 * mult));
		int randEntity = Seccult.rand.nextInt(entities.size());
		switch(rando)
		{
			case 0:
				living.attackEntityFrom(DamageSource.causeMobDamage(living), living.getHealth() / 3);
				break;
			case 1:
				living.addPotionEffect(new PotionEffect(MobEffects.POISON, 60 * mult, 6));
				break;
			case 2:
				living.addPotionEffect(new PotionEffect(MobEffects.WITHER, 60 * mult, 6));
				break;
			case 3:
				living.addPotionEffect(new PotionEffect(ModPotions.curse, living.getActivePotionEffect(ModPotions.curse).getDuration() + 200, Seccult.rand.nextInt(6) + 1));
				break;
			case 4:
				StateManager.setState(entities.get(randEntity), StateManager.LOST_MIND, 100 * mult, 5);
				break;
			case 5:
				if(entities.get(randEntity) instanceof EntityLivingBase)
				((EntityLivingBase) entities.get(randEntity)).addPotionEffect(new PotionEffect(ModPotions.curse, living.getActivePotionEffect(ModPotions.curse).getDuration() + 200, Seccult.rand.nextInt(6) + 1));
				break;
			case 6:
				StateManager.setState(entities.get(randEntity), StateManager.FROZEN, 100 * mult, 5);
				break;
			case 7:
				if(living instanceof EntityPlayer)
				{
					EntityPlayer player = (EntityPlayer) living;
					player.getFoodStats().addStats(-mult, -mult);
				}
				break;
			case 8:
				living.addPotionEffect(new PotionEffect(ModPotions.damage, living.getActivePotionEffect(ModPotions.curse).getDuration() + 200, Seccult.rand.nextInt(6) + 1));
				break;
		 	default:
		}
	}
	
}
