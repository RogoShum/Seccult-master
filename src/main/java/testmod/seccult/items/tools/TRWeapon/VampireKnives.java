package testmod.seccult.items.tools.TRWeapon;

import testmod.seccult.items.TRprojectile.TRprojectileID;

public class VampireKnives extends ItemWeaponBase{

	public VampireKnives(String name) {
		super(name);
		ItemAttribute(true, 31, 0, 15, 15, "seccult:trblade", TRprojectileID.VampireKnives);
	}
}
