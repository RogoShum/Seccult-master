package testmod.seccult.magick;

import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import testmod.seccult.magick.active.*;

public class ActiveHandler {

	public static final String DamageMagick = "damage";
	public static final String FlameMagick = "flame";
	
	public static final int DamageMagickColor = 0xDC143C;
	public static final int FlameMagickColor = 0xFF4500;
	
	public final Magick Damage;
	public final Magick Flame;
	

	private Set<Magick> magicks = new HashSet<>();

	public ActiveHandler()
	{
		Damage = new DamageMagick(DamageMagick, true);
		Flame = new FlameMagick(FlameMagick, true);
		addMagick(Damage);
		addMagick(Flame);
	}

	public void addMagick(Magick magick) {
		magicks.add(magick);
	}

	@Nullable
	public Magick getAttributeFromName(String name) {
		for (Magick magick : magicks) {
			if (magick.getNbtName().equals(name) || magick.getShortName().equals(name)) return magick;
		}
		return null;
	}
}
