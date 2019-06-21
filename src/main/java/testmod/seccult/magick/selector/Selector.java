package testmod.seccult.magick.selector;

public class Selector {
	private final String nbtName;
	private final String shortName;
	private boolean entity;
	private boolean block;
	
	public Selector(String nbtName, String shortName) {
		this.nbtName = nbtName;
		this.shortName = shortName;
	}
	
	public String getShortName() {
		return shortName;
	}

	public String getNbtName() {
		return nbtName;
	}

	public boolean doEntity() 
	{
		return entity;
	}
	
	public boolean doBlock() 
	{
		return block;
	}
}
