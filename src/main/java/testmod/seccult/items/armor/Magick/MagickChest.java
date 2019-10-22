package testmod.seccult.items.armor.Magick;

import net.minecraft.inventory.EntityEquipmentSlot;
import testmod.seccult.items.armor.MagickArmor;

public class MagickChest extends MagickArmor{

	public MagickChest(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		setMagickAttribute(0.1F, 0.5F, 0.5F);
	}
	
}