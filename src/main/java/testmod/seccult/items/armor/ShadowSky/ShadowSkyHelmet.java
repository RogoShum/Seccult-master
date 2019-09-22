package testmod.seccult.items.armor.ShadowSky;

import net.minecraft.inventory.EntityEquipmentSlot;
import testmod.seccult.items.armor.ShadowSkyArmor;

public class ShadowSkyHelmet extends  ShadowSkyArmor{

	public ShadowSkyHelmet(String name, ArmorMaterial materialIn, int renderIndexIn,
			EntityEquipmentSlot equipmentSlotIn) {
		super(name, materialIn, renderIndexIn, equipmentSlotIn);
		setMagickAttribute(0.2F, 3, 2F);
	}

}
