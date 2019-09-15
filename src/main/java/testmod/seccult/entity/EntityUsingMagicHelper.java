package testmod.seccult.entity;

import testmod.seccult.entity.livings.EntityBase.DoYouGotMagickHand;
import testmod.seccult.magick.active.Magick;

public interface EntityUsingMagicHelper {
	
	public void getAttackingMagic(DoYouGotMagickHand hand);
	
	public void getDefenceMagic(DoYouGotMagickHand hand);
	
	public void getControlMagic(DoYouGotMagickHand hand);
	
	public Magick randomSwitchMagic(EnumType type);
	
	public static enum EnumType
	{
		Attacking,
		Defence,
		Control
	}
}
