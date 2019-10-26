package testmod.seccult.items.Accessories;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import testmod.seccult.Seccult;
import testmod.seccult.init.ModItems;

@Mod.EventBusSubscriber(modid = Seccult.MODID)
public class AAttackRefract extends ItemAccessories{

	public AAttackRefract(String name) {
		super(name);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
	}
	
	@SubscribeEvent
	public static void attackEvent(LivingAttackEvent event)
	{
		if(event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			if(hasAccessories(player, ModItems.Attack_REFRACT))
			{
				List<Entity> entity = player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(16));
				for(int i = 0; i < entity.size(); ++i)
				{
					if(entity.get(i) instanceof EntityLivingBase)
					{
						entity.get(i).attackEntityFrom(event.getSource(), event.getAmount());
						
						event.setCanceled(true);
						
						break;
					}
				}
			}
		}
	}
}
