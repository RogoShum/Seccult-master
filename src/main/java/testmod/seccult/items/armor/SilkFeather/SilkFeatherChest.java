package testmod.seccult.items.armor.SilkFeather;

import net.minecraft.inventory.EntityEquipmentSlot;
import testmod.seccult.items.armor.SilkFeatherArmor;

public class SilkFeatherChest extends SilkFeatherArmor{

	public SilkFeatherChest(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		setMagickAttribute(0.3F, 1F, 1F);
	}

}
