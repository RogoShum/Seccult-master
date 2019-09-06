package testmod.seccult.magick.active;

import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;

public class GratefulDeadMagick extends Magick{

	public GratefulDeadMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}

	@Override
	void toEntity() {
		StateManager.setState(entity, StateManager.GratefulDead, (int)(strengh), (int)attribute);
	}

	@Override
	void toBlock() {
		// TODO Auto-generated method stub
		
	}

	@Override
	void MagickFX() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getColor() {
		return ModMagicks.GratefulDeadMagickColor;
	}

	@Override
	public boolean doMagickNeedAtrribute() {
		return true;
	}

}
