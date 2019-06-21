package testmod.seccult.entity;

public class LaserLengthChecker {
	protected double[] position = new double[3];
	
	public LaserLengthChecker() {}
	
	protected void setPosition(double[] pos)
	{
		position = pos;
	}
	
	protected double[] getPosition()
	{
		return position;
	}
}
