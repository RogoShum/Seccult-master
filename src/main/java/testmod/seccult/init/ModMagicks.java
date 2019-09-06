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
	public static final String FloatingMagick = "floating";
	public static final String InScaleMagick = "inscale";
	public static final String ArrowMagick = "arrow";
	public static final String AllSeeMagick = "allsee";
	public static final String ShieldMagick = "shield";
	public static final String ProtectMagick = "protect";
	public static final String LifeAbsorptionMagick = "lifeabsorption";
	public static final String BlackVelvetHellMagick = "blackvelvethell";
	public static final String CatchTheSoulMagick = "catchthesoul";
	
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
	public static final int FloatingMagickColor = 0xF08080;
	public static final int InScaleMagickColor = 0xFFFACD;
	public static final int ArrowMagickColor = 0x48D1CC;
	public static final int AllSeeMagickColor = 0xBA55D3;
	public static final int ShieldMagickColor = 0x7FFFAA;
	public static final int ProtectMagickColor = 0x7FFFAA;
	public static final int LifeAbsorptionMagickColor = 0xFF0000;
	public static final int BlackVelvetHellMagickColor = 0x4B0082;
	public static final int CatchTheSoulMagickColor = 0xB0C4DE;
	
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
	@SuppressWarnings("unused")
	private static Magick AllSee;
	@SuppressWarnings("unused")
	private static Magick Shield;
	@SuppressWarnings("unused")
	private static Magick Protect;
	@SuppressWarnings("unused")
	private static Magick LifeAbsorption;
	@SuppressWarnings("unused")
	private static Magick BlackVelvetHell;
	@SuppressWarnings("unused")
	private static Magick CatchTheSoul;

	private static Set<Magick> magicks = new HashSet<>();
	private static ArrayList<String> MagickList = new ArrayList<String>();

	public static void init()
	{
		ImplementationHandler.init();
		Damage = new DamageMagick(DamageMagick, true, 5, 1.2F);
		Flame = new FlameMagick(FlameMagick, true, 2, 1);
		Frozen = new FrozenMagick(FrozenMagick, true, 2, 1);
		noClip = new NoClipMagick(noClipMagick, true, 50, 0);
		LoseMind = new LostMindMagick(LoseMindMagick, true, 35, 2);
		Move = new MoveMagick(MoveMagick, true, 10, 1);
		WhiteAlbum = new WhiteAlbumMagick(WhiteAlbumMagick, true, 30, 0);
		GratefulDead = new GratefulDeadMagick(GratefulDeadMagick, true, 10, 2);
		Teleport = new TeleportMagick(TeleportMagick, true, 100, 1.3F);
		Copy = new CopyMagick(CopyMagick, true, 500, 1.2F);
		TheWorld = new TheWorldMagick(TheWorldMagick, true, 500, 2F);
		KraftWork = new KraftWorkMagick(KraftWorkMagick, true, 60, 3F);
		Floating = new FloatingMagick(FloatingMagick, true, 20, 1F);
		//InScale = new IncreaseScaleMagick(InScaleMagick, true, 20, 1F);
		Arrow = new ArrowClowCardMagick(ArrowMagick, true, 30, 1.5F);
		AllSee = new AllSeeMagick(AllSeeMagick, true, 120, 2F);
		Shield = new ShieldMagick(ShieldMagick, true, 120, 3F);
		Protect = new ProtectionMagick(ProtectMagick, true, 0.5F, 1.2F);
		LifeAbsorption = new LifeAbsorptionMagick(LifeAbsorptionMagick, true, 5, 3F);
		BlackVelvetHell = new BlackVelvetHellMagick(BlackVelvetHellMagick, true, 5000, 10F);
		CatchTheSoul = new CatchTheSoulMagick(CatchTheSoulMagick, true, 1000, 10F);
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
