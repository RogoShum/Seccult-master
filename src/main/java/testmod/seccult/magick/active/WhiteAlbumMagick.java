package testmod.seccult.magick.active;

import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;

public class WhiteAlbumMagick extends Magick{

	public WhiteAlbumMagick(String nbtName, boolean hasDetailedText) {
		super(nbtName, hasDetailedText);
	}

	@Override
	void toEntity() 
	{
		StateManager.setState(entity, StateManager.WhiteAlbum, 2 * (int)strengh);
	}

	@Override
	void toBlock() {
	}

	@Override
	void MagickFX() {
	}

	@Override
	public int getColor() {
		return ModMagicks.WhiteAlbumMagickColor;
	}

}
