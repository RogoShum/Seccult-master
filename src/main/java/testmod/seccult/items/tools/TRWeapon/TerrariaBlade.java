package testmod.seccult.items.tools.TRWeapon;

import testmod.seccult.items.TRprojectile.TRprojectileID;

public class TerrariaBlade extends ItemWeaponBase{

	public TerrariaBlade(String name) {
		super(name);
		ItemAttribute(true, 95, 0, 15, 12, "seccult:trblade", TRprojectileID.Terraria);
	}
}
