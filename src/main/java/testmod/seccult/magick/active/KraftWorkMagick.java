package testmod.seccult.magick.active;

import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;

public class KraftWorkMagick extends Magick implements ControllerMagic{

	public KraftWorkMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}

	@Override
	void toEntity() {
			StateManager.setState(entity, StateManager.KraftWork, (int)(strengh), (int)attribute);
	}

	@Override
	void toBlock() 
	{
		
	}

	@Override
	public int getColor() {
		return ModMagicks.KraftWorkMagickColor;
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
