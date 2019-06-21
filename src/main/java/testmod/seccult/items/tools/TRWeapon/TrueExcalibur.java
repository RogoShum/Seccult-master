package testmod.seccult.items.tools.TRWeapon;

import testmod.seccult.items.TRprojectile.TRprojectileID;

public class TrueExcalibur extends ItemWeaponBase{

	public TrueExcalibur(String name) {
		super(name);
		ItemAttribute(true, 66, 0, 15, 11, "seccult:trblade", TRprojectileID.TExcalibur);
	}
}
