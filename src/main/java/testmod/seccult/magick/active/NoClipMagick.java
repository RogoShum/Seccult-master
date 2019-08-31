package testmod.seccult.magick.active;

import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;

public class NoClipMagick extends Magick{

	public NoClipMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}

	@Override
	void toEntity() {
			StateManager.setState(entity, StateManager.NOCLIP, (int)(strengh), (int)attribute);
	}

	@Override
	void toBlock() 
	{
		
	}

	@Override
	public int getColor() {
		return ModMagicks.noClipMagickColor;
	}

	@Override
	void MagickFX() {
	}
}
