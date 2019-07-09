package testmod.seccult.magick.active;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;

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
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			StateManager.setPlayerMove(player, QAQ.scale(0.5).x *(strengh + attribute), QAQ.scale(0.5).y *(strengh + attribute), QAQ.scale(0.5).z *(strengh + attribute), 1);
		}
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
