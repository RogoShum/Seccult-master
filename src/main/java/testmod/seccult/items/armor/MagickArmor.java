package testmod.seccult.items.armor;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.EntityEquipmentSlot;
import testmod.seccult.init.ModItems;

public class MagickArmor extends ArmorBase{

	public MagickArmor(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
	}
	
	public static boolean hasArmorSetItem(EntityPlayer player) {
		return hasArmorSetItem(player, 0, ModItems.SPA_HELMET) && 
				hasArmorSetItem(player, 1, ModItems.SPA_CHEST) && 
				hasArmorSetItem(player, 2, ModItems.SPA_LEGGINGS) && 
				hasArmorSetItem(player, 3, ModItems.SPA_BOOTS);
	}
}
