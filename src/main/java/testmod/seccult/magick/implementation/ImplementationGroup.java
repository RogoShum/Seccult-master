package testmod.seccult.magick.implementation;

import net.minecraft.entity.Entity;

public class ImplementationGroup extends Implementation{

	public ImplementationGroup(String nbtName, String shortName) {
		super(nbtName, shortName);
		// TODO Auto-generated constructor stub
	}

	@Override
	public void getTarget(Entity player) {
		player.world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(1));
	}

}
