package testmod.seccult.init;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

import javax.annotation.Nullable;

import testmod.seccult.magick.ImplementationHandler;
import testmod.seccult.magick.active.*;
import testmod.seccult.magick.implementation.Implementation;

public class ModMagicks {

	public static final String DamageMagick = "damage";
	public static final String FlameMagick = "flame";
	public static final String FrozenMagick = "frozen";
	public static final String noClipMagick = "noclip";
	public static final String LoseMindMagick = "losemind";
	public static final String MoveMagick = "move";
	
	public static final int DamageMagickColor = 0xDC143C;
	public static final int FlameMagickColor = 0xFF4500;
	public static final int FrozenMagickColor = 0xE1FFFF;
	public static final int noClipMagickColor = 0x708090;
	public static final int LoseMindMagickColor = 0xB22222;
	public static final int MoveMagickColor = 0x40E0D0;
	
	public static Magick Damage;
	public static Magick Flame;
	public static Magick Frozen;
	public static Magick noClip;
	public static Magick LoseMind;
	public static Magick Move;
	

	private static Set<Magick> magicks = new HashSet<>();
	private static ArrayList<String> MagickList = new ArrayList<String>();

	public static void init()
	{
		ImplementationHandler.init();
		Damage = new DamageMagick(DamageMagick, true);
		Flame = new FlameMagick(FlameMagick, true);
		Frozen = new FrozenMagick(FrozenMagick, true);
		noClip = new NoClipMagick(noClipMagick, true);
		LoseMind = new LostMindMagick(LoseMindMagick, true);
		Move = new MoveMagick(MoveMagick, true);
	}

	public static void addMagick(Magick magick) {
		magicks.add(magick);
		MagickList.add(magick.getNbtName());
	}

	public static void addImplementation(Implementation magick) {
		MagickList.add(magick.getNbtName());
	}
	
	@Nullable
	public static Magick getAttributeFromName(String name) {
		for (Magick magick : magicks) {
			if (magick.getNbtName().equals(name)) return magick.clone();
		}
		return null;
	}
	
	public static String GetMagickStringByID(int id)
	{
			String magick = MagickList.get(id);
			if(magick != null)
				return magick;
			
			return null;
	}
	
	public static String getI18nIntroductionByID(int id)
	{
		String magick = MagickList.get(id);
		if(magick != null)
		{
			String I18n = "spell_" + magick + "_introduction";
			return I18n;
		}
		
		return null;
	}
	
	public static String getI18nNameByID(int id)
	{
		String magick = MagickList.get(id);
		if(magick != null)
		{
			String I18n = "spell_" + magick + "_name";
			return I18n;
		}
		
		return null;
	}
	
	public static int GetMagickIDByString(String s)
	{
		for(int i = 0; i < MagickList.size(); i++)
		{
			
			if(MagickList.get(i).equals(s)) {
				return i;
			}
		}
		
		return -1;
	}
}
