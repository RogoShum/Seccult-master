package testmod.seccult.magick.active;

import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;

public class GratefulDeadMagick extends Magick{

	public GratefulDeadMagick(String nbtName, boolean hasDetailedText) {
		super(nbtName, hasDetailedText);
		// TODO Auto-generated constructor stub
	}

	@Override
	void toEntity() {
		StateManager.setState(entity, StateManager.GratefulDead, (int)strengh);
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

}
