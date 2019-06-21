package testmod.seccult.items.tools.TRWeapon;

import testmod.seccult.items.TRprojectile.TRprojectileID;

public class StarFury extends ItemWeaponBase{

	public StarFury(String name) {
		super(name);
		ItemAttribute(true, 22, 0, 19, 20, "seccult:trmagick", TRprojectileID.starfury);
	}
}
