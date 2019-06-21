package testmod.seccult.items.tools.TRWeapon;

import testmod.seccult.items.TRprojectile.TRprojectileID;

public class SolarEruption extends ItemWeaponBase{

	public SolarEruption(String name) {
		super(name);
		ItemAttribute(true, 105, 0, 19, 24, "seccult:solar", TRprojectileID.SolarEruption_Handle);
	}
}
