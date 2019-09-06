package testmod.seccult.magick.active;

import testmod.seccult.entity.EntityClowCardArrow;
import testmod.seccult.init.ModMagicks;

public class ArrowClowCardMagick extends Magick{

	public ArrowClowCardMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
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

	@Override
	public boolean doMagickNeedAtrribute() {
		return true;
	}

}
