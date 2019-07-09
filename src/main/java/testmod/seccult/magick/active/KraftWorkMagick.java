package testmod.seccult.magick.active;

import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;

public class KraftWorkMagick extends Magick{

	public KraftWorkMagick(String name, boolean hasDetailedText) {
		super(name, hasDetailedText);
		// TODO Auto-generated constructor stub
	}

	@Override
	void toEntity() {
			StateManager.setState(entity, StateManager.KraftWork, (int)(strengh + attribute));
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
}
