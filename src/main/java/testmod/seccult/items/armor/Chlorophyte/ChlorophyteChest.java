package testmod.seccult.items.armor.Chlorophyte;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;
import testmod.seccult.items.armor.ChlorophyteArmor;
import testmod.seccult.items.armor.MagickArmor.CoreType;

public class ChlorophyteChest extends ChlorophyteArmor{

	public ChlorophyteChest(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		setMagickAttribute(0.3F, 1, 1F);
	}

	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		addMagickCore(itemStack, CoreType.FlyingCore);
		if(!hasArmorSetItem(player))
			return;

		if(world.getLight(player.getPosition()) >= 7)
		{
			if(player.ticksExisted % 10 == 0)
			{
				player.heal(2);
				if(player.getFoodStats().getFoodLevel() < 20)
				player.getFoodStats().addStats(2, 2);
			}
		}
	}
}
