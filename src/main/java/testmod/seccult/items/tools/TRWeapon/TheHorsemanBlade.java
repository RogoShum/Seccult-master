package testmod.seccult.items.tools.TRWeapon;

import testmod.seccult.items.TRprojectile.TRprojectileID;

public class TheHorsemanBlade extends ItemWeaponBase{

	public TheHorsemanBlade(String name) {
		super(name);
		ItemAttribute(true, 75, 0, 25, 12, "seccult:trblade", TRprojectileID.HorseMan);
	}
}
