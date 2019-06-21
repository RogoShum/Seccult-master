package testmod.seccult.items.tools.TRWeapon;

import testmod.seccult.items.TRprojectile.TRprojectileID;

public class EnchantedSword extends ItemWeaponBase{

	public EnchantedSword(String name) {
		super(name);
		ItemAttribute(true, 40, 0, 17, 10, "seccult:trblade", TRprojectileID.Enchant);
	}
}
