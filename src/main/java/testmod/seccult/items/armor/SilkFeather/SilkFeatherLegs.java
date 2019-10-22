package testmod.seccult.items.armor.SilkFeather;

import net.minecraft.inventory.EntityEquipmentSlot;
import testmod.seccult.items.armor.SilkFeatherArmor;

public class SilkFeatherLegs extends SilkFeatherArmor{

	public SilkFeatherLegs(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		setMagickAttribute(0.2F, 1F, 1F);
	}

}
