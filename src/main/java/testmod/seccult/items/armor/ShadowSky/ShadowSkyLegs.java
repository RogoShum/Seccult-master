package testmod.seccult.items.armor.ShadowSky;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor.ArmorMaterial;
import testmod.seccult.items.armor.ShadowSkyArmor;

public class ShadowSkyLegs extends  ShadowSkyArmor{

	public ShadowSkyLegs(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		setMagickAttribute(0.2F, 3, 2F);
	}
}
