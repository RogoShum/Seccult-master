package testmod.seccult.magick.implementation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;

public class ImplementationGroup extends Implementation{

	public ImplementationGroup(String nbtName) {
		super(nbtName);
	}

	@Override
	public void getTarget() {
		if(getEntity() != null) 
		{
			List<Entity> eList = getEntity();
			List<Entity> newList = new ArrayList<>();
			List<Entity> e = null;
			for(int i = 0; i < eList.size(); i++)
			{	
				if(eList.get(i) != null) {
				e = eList.get(i).world.getEntitiesWithinAABBExcludingEntity(player, eList.get(i).getEntityBoundingBox().grow(radius));
				applyMagickRadius(eList.get(i).world, eList.get(i).posX, eList.get(i).posY, eList.get(i).posZ, radius);
				newList.addAll(e);
				}
			}
			setEntity(newList);
		}
	}

	private void applyMagickRadius(World world, double srcX, double srcY, double srcZ,float particles) 
	{
		double[] pos = new double[3], vec = new double[3];
		pos[0] = srcX;
		pos[1] = srcY;
		pos[2] = srcZ;
		vec[0] = 1 - 2*world.rand.nextFloat();
		vec[1] = 1 - 2*world.rand.nextFloat();
		vec[2] = 1 - 2*world.rand.nextFloat();
		float[] color = {this.color[0], this.color[1],this.color[2]};
		 NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, particles, 103));
	}
}
