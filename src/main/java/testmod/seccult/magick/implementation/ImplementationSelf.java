package testmod.seccult.magick.implementation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;

public class ImplementationSelf extends Implementation{

	public ImplementationSelf(String nbtName) {
		super(nbtName);
	}

	@Override
	public void getTarget() {
		List<Entity> pl = new ArrayList<>();
		pl.add(player);
		setEntity(pl);
	}
}
