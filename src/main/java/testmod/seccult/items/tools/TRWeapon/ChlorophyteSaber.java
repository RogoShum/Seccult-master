package testmod.seccult.items.tools.TRWeapon;

import testmod.seccult.items.TRprojectile.TRprojectileID;

public class ChlorophyteSaber extends ItemWeaponBase{

	public ChlorophyteSaber(String name) {
		super(name);
		ItemAttribute(true, 48, 0, 15, 8, "seccult:trmagick", TRprojectileID.ChlorophyteSaber);
	}
}
