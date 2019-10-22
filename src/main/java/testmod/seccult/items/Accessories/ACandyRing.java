package testmod.seccult.items.Accessories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class ACandyRing extends ItemAccessories{

	public ACandyRing(String name) {
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
	
}
