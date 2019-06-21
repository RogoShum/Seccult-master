package testmod.seccult.items.tools.TRWeapon;

import testmod.seccult.items.TRprojectile.TRprojectileID;

public class StarWrath extends ItemWeaponBase{

	public StarWrath(String name) {
		super(name);
		ItemAttribute(true, 220, 0, 15, 8, "seccult:trmagick", TRprojectileID.StarWrath);
	}
}
