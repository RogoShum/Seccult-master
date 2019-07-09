package testmod.seccult.magick.active;

import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;

public class FloatingMagick extends Magick{

	public FloatingMagick(String name, boolean hasDetailedText) {
		super(name, hasDetailedText);
		// TODO Auto-generated constructor stub
	}

	@Override
	void toEntity() {
			StateManager.setState(entity, StateManager.Floating, (int)(strengh + attribute));
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
}
