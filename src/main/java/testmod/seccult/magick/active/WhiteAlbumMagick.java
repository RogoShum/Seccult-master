package testmod.seccult.magick.active;

import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;

public class WhiteAlbumMagick extends Magick{

	public WhiteAlbumMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}

	@Override
	void toEntity() 
	{
		StateManager.setState(entity, StateManager.WhiteAlbum, (int)(strengh), (int)attribute);
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

	@Override
	public boolean doMagickNeedAtrribute() {
		return true;
	}

}
