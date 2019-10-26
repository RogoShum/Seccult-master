package testmod.seccult.magick.implementation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.TransPoint;

public class ImplementationGroup extends Implementation{

	public ImplementationGroup(String nbtName) {
		super(nbtName);
	}

	@Override
	public void getTarget() {
		if(this.doEntity && getEntity() != null) 
		{
			List<Entity> eList = getEntity();
			List<Entity> newList = new ArrayList<>();
			List<Entity> e = null;
			for(int i = 0; i < eList.size(); i++)
			{	
				if(eList.get(i) != null) {
				e = eList.get(i).world.getEntitiesWithinAABBExcludingEntity(player, eList.get(i).getEntityBoundingBox().grow(base + addtion));
				applyMagickRadius(eList.get(i), eList.get(i).posX, eList.get(i).posY + (eList.get(i).height / 2), eList.get(i).posZ, base + addtion);
				for(int z = 0; z < e.size(); z++)
				{	
					if(!eList.contains(e.get(z)))
					newList.add(e.get(z));
				}
				}
			}
			setEntity(newList);
		}
		
		if(this.doBlock && getBlock() != null && getEntity() == null)
		{
			List<BlockPos> bList = getBlock();
			List<BlockPos> newList = new ArrayList<>();
			BlockPos block = null;
			List<Entity> e = null;
			for(int i = 0; i < bList.size(); i++)
			{
				if(bList.get(i) != null) {
					block = bList.get(i);
					AxisAlignedBB boundingBox = new AxisAlignedBB(block.getX(), block.getY(), block.getZ(), block.getX(), block.getY(), block.getZ());
					e = player.world.getEntitiesWithinAABBExcludingEntity(player, boundingBox.grow(base + addtion));
					applyMagickRadius(player, block.getX(), block.getY(), block.getZ(), base + addtion);
					int radius = base + addtion;
					Iterable<BlockPos> blocks = BlockPos.getAllInBox(block.add(-radius, -radius, -radius), block.add(radius, radius, radius));
					for(BlockPos pos: blocks)
					{
						newList.add(pos);
					}
				}
			}
			setBlock(newList);
			setEntity(e);
		}
	}

	private void applyMagickRadius(Entity entity, double srcX, double srcY, double srcZ,float particles) 
	{
		double[] pos = new double[3], vec = new double[3];
		pos[0] = srcX;
		pos[1] = srcY;
		pos[2] = srcZ;
		vec[0] = 1 - 2*entity.world.rand.nextFloat();
		vec[1] = 1 - 2*entity.world.rand.nextFloat();
		vec[2] = 1 - 2*entity.world.rand.nextFloat();
		float[] color = {this.color[0], this.color[1],this.color[2]};
		 NetworkHandler.sendToAllAround(new NetworkEffectData(pos, vec, color, particles, 103), 
					new TransPoint(entity.dimension, pos[0], pos[1], pos[2], 32), entity.world);
		 
	}

	@Override
	public boolean doMagickNeedAtrribute() {
		return false;
	}
	
	@Override
	public boolean doMagickNeedStrength() {
		return true;
	}
}
