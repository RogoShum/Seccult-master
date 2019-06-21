package testmod.seccult.api;

import java.util.ArrayList;

import testmod.seccult.magick.ActiveHandler;
import testmod.seccult.magick.ImplemrntationHandler;

public class ModMagicks {
	private static ArrayList<String> MagickList = new ArrayList<String>();
	
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
	
	public static void init()
	{
		addMagick(ImplemrntationHandler.FocuseI);
		addMagick(ImplemrntationHandler.ProjectileI);
		addMagick(ImplemrntationHandler.CircleI);
		addMagick(ImplemrntationHandler.GroupI);
		addMagick(ImplemrntationHandler.SelfI);
		addMagick(ActiveHandler.DamageMagick);
		addMagick(ActiveHandler.FlameMagick);
	}
	
	private static void addMagick(String s)
	{
		MagickList.add(s);
	}
}
