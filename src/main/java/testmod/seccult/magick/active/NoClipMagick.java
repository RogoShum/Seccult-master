package testmod.seccult.magick.active;

import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;

public class NoClipMagick extends Magick{

	public NoClipMagick(String name, boolean hasDetailedText) {
		super(name, hasDetailedText);
		// TODO Auto-generated constructor stub
	}

	@Override
	void toEntity() {
			StateManager.setState(entity, StateManager.NOCLIP, (int)(strengh + attribute));
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
