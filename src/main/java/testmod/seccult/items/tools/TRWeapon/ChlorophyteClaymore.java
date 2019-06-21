package testmod.seccult.items.tools.TRWeapon;

import testmod.seccult.items.TRprojectile.TRprojectileID;

public class ChlorophyteClaymore extends ItemWeaponBase{

	public ChlorophyteClaymore(String name) {
		super(name);
		ItemAttribute(true, 75, 0, 25, 8, "seccult:trmagick", TRprojectileID.ChlorophyteClaymore);
	}
}
