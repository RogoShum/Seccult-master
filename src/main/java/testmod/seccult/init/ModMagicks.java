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
	public static final String WhiteAlbumMagick = "whitealbum";
	public static final String GratefulDeadMagick = "gratefuldead";
	public static final String TeleportMagick = "teleport";
	public static final String CopyMagick = "copy";
	public static final String TheWorldMagick = "theworld";
	public static final String KraftWorkMagick = "kraftwork";
	public static final String FloatingMagick = "Floating";
	public static final String InScaleMagick = "InScale";
	public static final String ArrowMagick = "Arrow";
	
	public static final int DamageMagickColor = 0xDC143C;
	public static final int FlameMagickColor = 0xFF0000;
	public static final int FrozenMagickColor = 0xE1FFFF;
	public static final int noClipMagickColor = 0x708090;
	public static final int LoseMindMagickColor = 0xB22222;
	public static final int MoveMagickColor = 0x40E0D0;
	public static final int WhiteAlbumMagickColor = 0x00FFFF;
	public static final int GratefulDeadMagickColor = 0xFF4500;
	public static final int TeleportMagickColor = 0x00FA9A;
	public static final int CopyMagickColor = 0xDDA0DD;
	public static final int TheWorldMagickColor = 0xFFF5EE;
	public static final int KraftWorkMagickColor = 0xDEB887;
	public static final int FloatingMagickColor = 0xFFFACD;
	public static final int InScaleMagickColor = 0xFFFACD;
	public static final int ArrowMagickColor = 0xFFFACD;
	
	@SuppressWarnings("unused")
	private static Magick Damage;
	@SuppressWarnings("unused")
	private static Magick Flame;
	@SuppressWarnings("unused")
	private static Magick Frozen;
	@SuppressWarnings("unused")
	private static Magick noClip;
	@SuppressWarnings("unused")
	private static Magick LoseMind;
	@SuppressWarnings("unused")
	private static Magick Move;
	@SuppressWarnings("unused")
	private static Magick WhiteAlbum;
	@SuppressWarnings("unused")
	private static Magick GratefulDead;
	@SuppressWarnings("unused")
	private static Magick Teleport;
	@SuppressWarnings("unused")
	private static Magick Copy;
	@SuppressWarnings("unused")
	private static Magick TheWorld;
	@SuppressWarnings("unused")
	private static Magick KraftWork;
	@SuppressWarnings("unused")
	private static Magick Floating;
	@SuppressWarnings("unused")
	private static Magick InScale;
	@SuppressWarnings("unused")
	private static Magick Arrow;
	

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
		WhiteAlbum = new WhiteAlbumMagick(WhiteAlbumMagick, true);
		GratefulDead = new GratefulDeadMagick(GratefulDeadMagick, true);
		Teleport = new TeleportMagick(TeleportMagick, true);
		Copy = new CopyMagick(CopyMagick, true);
		TheWorld = new TheWorldMagick(TheWorldMagick, true);
		KraftWork = new KraftWorkMagick(KraftWorkMagick, true);
		Floating = new FloatingMagick(FloatingMagick, true);
		InScale = new IncreaseScaleMagick(InScaleMagick, true);
		Arrow = new ArrowClowCardMagick(ArrowMagick, true);
	}

	public static void addMagick(Magick magick) {
		magicks.add(magick);
		MagickList.add(magick.getNbtName());
	}

	public static void addImplementation(Implementation magick) {
		MagickList.add(magick.getNbtName());
	}
	
	@Nullable
	public static Magick getMagickFromName(String name) {
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
	
	public static ArrayList<String> GetAllMagickID()
	{
		ArrayList<String> newList = new ArrayList<>();
		for(int i = 0; i < MagickList.size(); i++)
		{
			newList.add(MagickList.get(i));
		}
		
		return newList;
	}
}
