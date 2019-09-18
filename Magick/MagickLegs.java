package testmod.seccult.items.armor.Magick;

import net.minecraft.inventory.EntityEquipmentSlot;
import testmod.seccult.items.armor.MagickArmor;

public class MagickLegs extends MagickArmor{

	public MagickLegs(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		setMagickAttribute(1, 50, 50);
	}
	
}