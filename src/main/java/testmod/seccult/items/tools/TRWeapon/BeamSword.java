package testmod.seccult.items.tools.TRWeapon;

import testmod.seccult.items.TRprojectile.TRprojectileID;

public class BeamSword extends ItemWeaponBase{

	public BeamSword(String name) {
		super(name);
		ItemAttribute(true, 52, 0, 14, 11, "seccult:trblade", TRprojectileID.BeamSword);
	}
}
