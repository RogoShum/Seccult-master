package testmod.seccult.items.tools.TRWeapon;

import testmod.seccult.items.TRprojectile.TRprojectileID;

public class Meowmere extends ItemWeaponBase{

	public Meowmere(String name) {
		super(name);
		ItemAttribute(true, 200, 0, 16, 12, "seccult:trblade", TRprojectileID.Meowmere);
	}
}
