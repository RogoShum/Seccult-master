package net.minecraft.client.particle;

public class particleHelper {
	Particle particle;
	public particleHelper() {
		
	}
	
	public static double getPosX(Particle p) {
		return p.posX;
	}
	
	public static double getPosY(Particle p) {
		return p.posY;
	}
	
	public static double getPosZ(Particle p) {
		return p.posZ;
	}
	
	public static double getPrevPosX(Particle p) {
		return p.prevPosX;
	}
	
	public static double getPrevPosY(Particle p) {
		return p.prevPosY;
	}
	
	public static double getPrevPosZ(Particle p) {
		return p.prevPosZ;
	}
}
