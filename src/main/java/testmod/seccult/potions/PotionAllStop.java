package testmod.seccult.potions;

import net.minecraftforge.common.MinecraftForge;

public class PotionAllStop extends PotionMod {

	public PotionAllStop() {
		super("allstop", false, 0X008B8B, 0);
		MinecraftForge.EVENT_BUS.register(this);
		setBeneficial();
	}
}
