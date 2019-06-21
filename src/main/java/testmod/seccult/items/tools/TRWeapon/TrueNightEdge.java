package testmod.seccult.items.tools.TRWeapon;

import testmod.seccult.items.TRprojectile.TRprojectileID;

public class TrueNightEdge extends ItemWeaponBase{

	public TrueNightEdge(String name) {
		super(name);
		ItemAttribute(true, 90, 0, 25, 10, "seccult:trblade", TRprojectileID.TrueNightEdge);
	}
}
