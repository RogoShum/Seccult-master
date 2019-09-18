package testmod.seccult.items.armor;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import testmod.seccult.init.ModItems;

public class SilkFeatherArmor extends ArmorBase {

	public SilkFeatherArmor(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}

	public static boolean hasArmorSetItem(EntityPlayer player) {
		return hasArmorSetItem(player, 0, ModItems.SILK_FEATHER_HELMET) && 
				hasArmorSetItem(player, 1, ModItems.SILK_FEATHER_CHEST) && 
				hasArmorSetItem(player, 2, ModItems.SILK_FEATHER_LEGGINGS) && 
				hasArmorSetItem(player, 3, ModItems.SILK_FEATHER_BOOTS);
	}
}
