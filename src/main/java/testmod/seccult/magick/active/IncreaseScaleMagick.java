package testmod.seccult.magick.active;

import testmod.seccult.init.ModMagicks;

public class IncreaseScaleMagick extends Magick{

	public IncreaseScaleMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}

	@Override
	void toEntity() {
		entity.height = entity.height / strengh;
		entity.width = entity.width / strengh;
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
		return ModMagicks.InScaleMagickColor;
	}

	@Override
	public boolean doMagickNeedAtrribute() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean doMagickNeedStrength() {
		return true;
	}
}
