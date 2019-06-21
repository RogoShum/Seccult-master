package testmod.seccult.items.tools.TRWeapon;

import testmod.seccult.items.TRprojectile.TRprojectileID;

public class InfluxWaver extends ItemWeaponBase{

	public InfluxWaver(String name) {
		super(name);
		ItemAttribute(true, 110, 0, 19, 11, "seccult:trblade", TRprojectileID.InfluxWaver);
	}
}
