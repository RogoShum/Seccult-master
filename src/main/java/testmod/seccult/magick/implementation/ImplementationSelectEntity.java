package testmod.seccult.magick.implementation;

public class ImplementationSelectEntity extends Implementation{

	public ImplementationSelectEntity(String nbtName) {
		super(nbtName);
	}

	@Override
	public void getTarget() {
	}

	@Override
	public boolean doMagickNeedAtrribute() {
		return false;
	}
}
