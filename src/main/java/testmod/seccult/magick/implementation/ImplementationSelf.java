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
		for(int i = 0; i < getEntity().size(); i++)
		{
				pl.add(getEntity().get(i));
		}
		setEntity(pl);
	}
}
