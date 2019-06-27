package testmod.seccult.magick.active;

import net.minecraft.util.math.Vec3d;
import testmod.seccult.init.ModMagicks;

public class MoveMagick extends Magick{

	public MoveMagick(String name, boolean hasDetailedText) {
		super(name, hasDetailedText);
	}

	@Override
	void toEntity() {
		Vec3d QAQ = player.getLookVec();
		entity.motionX = QAQ.scale(0.5).x *(strengh + attribute);
		entity.motionY = QAQ.scale(0.5).y *(strengh + attribute);
		entity.motionZ = QAQ.scale(0.5).z *(strengh + attribute);
	}

	@Override
	void toBlock() 
	{
		
	}

	@Override
	public int getColor() {
		return ModMagicks.MoveMagickColor;
	}

	@Override
	void MagickFX() {
	}
}
