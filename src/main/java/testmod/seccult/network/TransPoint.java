package testmod.seccult.network;

public class TransPoint {
    public final double x;
    public final double y;
    public final double z;
    public final double range;
    public final int dimension;
	
	public TransPoint(int dimension, double x, double y, double z, double range)
    {
        this.x = x;
        this.y = y;
        this.z = z;
        this.range = range;
        this.dimension = dimension;
    }

}
