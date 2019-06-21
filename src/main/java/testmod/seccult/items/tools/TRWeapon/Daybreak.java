package testmod.seccult.items.tools.TRWeapon;

import testmod.seccult.items.TRprojectile.TRprojectileID;

public class Daybreak extends ItemWeaponBase{

	public Daybreak(String name) {
		super(name);
		ItemAttribute(true, 150, 0, 15, 15, "seccult:trblade", TRprojectileID.Daybreak);
	}
}
