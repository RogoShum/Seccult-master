package testmod.seccult.items.armor.Magick;

import net.minecraft.inventory.EntityEquipmentSlot;
import testmod.seccult.items.armor.MagickArmor;

public class MagickBoots extends MagickArmor{

	public MagickBoots(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		setMagickAttribute(1, 50, 50);
	}
	
}