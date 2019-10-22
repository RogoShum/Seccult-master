package testmod.seccult.items.armor.SilkFeather;

import net.minecraft.inventory.EntityEquipmentSlot;
import testmod.seccult.items.armor.SilkFeatherArmor;

public class SilkFeatherBoot extends SilkFeatherArmor{

	public SilkFeatherBoot(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		setMagickAttribute(0.1F, 1F, 1F);
	}

}
