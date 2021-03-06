package testmod.seccult.magick.active;

import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;

public class FloatingMagick extends Magick implements ControllerMagic{

	public FloatingMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}

	@Override
	void toEntity() {
			StateManager.setState(entity, StateManager.Floating, (int)(strengh), (int)attribute);
	}

	@Override
	void toBlock() 
	{
		
	}

	@Override
	public int getColor() {
		return ModMagicks.FloatingMagickColor;
	}

	@Override
	void MagickFX() {
	}

	@Override
	public boolean doMagickNeedAtrribute() {
		return false;
	}
	
	@Override
	public boolean doMagickNeedStrength() {
		return true;
	}
}
