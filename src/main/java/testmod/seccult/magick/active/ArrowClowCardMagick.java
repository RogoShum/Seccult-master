package testmod.seccult.magick.active;

import testmod.seccult.entity.EntityClowCardArrow;
import testmod.seccult.init.ModMagicks;

public class ArrowClowCardMagick extends Magick{

	public ArrowClowCardMagick(String nbtName, boolean hasDetailedText) {
		super(nbtName, hasDetailedText);
	}

	@Override
	void toEntity() {
		EntityClowCardArrow newArrow = new EntityClowCardArrow(entity.world, entity, (int)strengh, strengh, (int)attribute + 1);
		newArrow.setPositionAndRotation(entity.posX, entity.posY + (entity.height * 0.7), entity.posZ, entity.rotationYaw, entity.rotationPitch);
		entity.world.spawnEntity(newArrow);
	}

	@Override
	void toBlock() 
	{
	}

	@Override
	void MagickFX() 
	{
	}

	@Override
	public int getColor() {
		return ModMagicks.ArrowMagickColor;
	}

}
