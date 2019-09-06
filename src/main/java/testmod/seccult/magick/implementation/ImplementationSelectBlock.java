package testmod.seccult.magick.implementation;

public class ImplementationSelectBlock extends Implementation{

	public ImplementationSelectBlock(String nbtName) {
		super(nbtName);
	}

	@Override
	public void getTarget() {}

	@Override
	public boolean doMagickNeedAtrribute() {
		return false;
	}
}
