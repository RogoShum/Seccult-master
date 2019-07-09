package testmod.seccult.magick.active;

import testmod.seccult.init.ModMagicks;

public class IncreaseScaleMagick extends Magick{

	public IncreaseScaleMagick(String nbtName, boolean hasDetailedText) {
		super(nbtName, hasDetailedText);
		// TODO Auto-generated constructor stub
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

}
