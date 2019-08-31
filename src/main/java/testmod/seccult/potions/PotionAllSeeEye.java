package testmod.seccult.potions;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.potion.PotionEffect;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.armor.ArmorBase;

public class PotionAllSeeEye extends PotionMod{
	
	public PotionAllSeeEye() {
		super("allsee", false, 0XFF0000, 0);
		MinecraftForge.EVENT_BUS.register(this);
		setBeneficial();
	}
	
	@SubscribeEvent
	public void onMobUpdate(LivingUpdateEvent event) {
		if(event.getEntityLiving() instanceof EntityLivingBase){
			EntityLivingBase mob = (EntityLivingBase) event.getEntityLiving();
			if(mob.isPotionActive(ModPotions.allsee)){
				PotionEffect effect = mob.getActivePotionEffect(ModPotions.allsee); 
				float amount = effect.getAmplifier();
				if(amount > 1)
				{
					mob.removePotionEffect(ModPotions.allsee);
					mob.addPotionEffect(new PotionEffect(ModPotions.allsee, effect.getDuration(), 1));
				}
			}
	}
	}
	
	public static boolean hasAllSee(EntityPlayer mob)
	{
		if(ArmorBase.hasArmorSetItem(mob, 0, ModItems.SPA_HELMET))
			return true;
		
		return false;
	}
}
