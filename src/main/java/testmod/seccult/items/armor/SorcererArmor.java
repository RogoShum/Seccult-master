package testmod.seccult.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import testmod.seccult.init.ModItems;

public class SorcererArmor extends MagickArmor {

	public SorcererArmor(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}

	public static boolean hasArmorSetItem(EntityPlayer player) {
		return hasArmorSetItem(player, 0, ModItems.SORCERER_HELMET) && 
				hasArmorSetItem(player, 1, ModItems.SORCERER_CHEST) && 
				hasArmorSetItem(player, 2, ModItems.SORCERER_LEGGINGS) && 
				hasArmorSetItem(player, 3, ModItems.SORCERER_BOOTS);
	}
}
