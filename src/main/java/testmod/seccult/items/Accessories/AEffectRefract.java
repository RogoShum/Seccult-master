package testmod.seccult.items.Accessories;

import java.util.Iterator;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.PotionEffect;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.event.entity.living.LivingEvent.LivingUpdateEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import testmod.seccult.Seccult;
import testmod.seccult.init.ModItems;

@Mod.EventBusSubscriber(modid = Seccult.MODID)
public class AEffectRefract extends ItemAccessories{

	public AEffectRefract(String name) {
		super(name);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		if(!hasAccessories(player, itemStack)) return;
		{
			if(player.getFoodStats().getFoodLevel() < 20)
				player.getFoodStats().setFoodLevel(20);
			
			if(player.getFoodStats().getSaturationLevel() < 20)
				player.getFoodStats().setFoodSaturationLevel(20);
		}
	}
	
	@SubscribeEvent
	public static void attackEvent(LivingUpdateEvent event)
	{
		if(event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();

			if(hasAccessories(player, ModItems.EFFECT_REFRACT))
			{
				
		        Iterator<PotionEffect> iterator = player.getActivePotionEffects().iterator();

		        while (iterator.hasNext())
		        {
		            PotionEffect effect = iterator.next();
		            if(effect.getPotion().isBadEffect())
		            {
		            	{
		            		List<Entity> entity = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(16));
		        			for(int i = 0; i < entity.size(); ++i)
		        			{
		        				if(entity.get(i) instanceof EntityLivingBase)
		        				{
		        					((EntityLivingBase)entity.get(i)).addPotionEffect(new PotionEffect(effect.getPotion(), effect.getDuration(), effect.getAmplifier()));

		        					onFinishedPotionEffect(player, effect);
		    		            	iterator.remove();
		        					break;
		        				}
		        			}
		            	}
		            }
		        }
			}
		}
	}

	protected static void onFinishedPotionEffect(EntityPlayer player, PotionEffect effect)
	{
		effect.getPotion().removeAttributesModifiersFromEntity(player, player.getAttributeMap(), effect.getAmplifier());
	}
}
