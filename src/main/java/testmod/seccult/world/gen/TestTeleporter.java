package testmod.seccult.world.gen;

import it.unimi.dsi.fastutil.objects.ObjectIterator;
import net.minecraft.block.state.IBlockState;
import net.minecraft.block.state.pattern.BlockPattern.PatternHelper;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing.Axis;
import net.minecraft.util.EnumFacing.AxisDirection;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.BlockPos.MutableBlockPos;
import net.minecraft.util.math.ChunkPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.Teleporter;
import net.minecraft.world.World;
import net.minecraft.world.WorldServer;
import testmod.seccult.blocks.BlockPortal;
import testmod.seccult.entity.EntityBorderCrosser;
import testmod.seccult.init.ModBlocks;

public class TestTeleporter extends Teleporter {
	
	public TestTeleporter(WorldServer world) {
		super(world);
	}

	@Override
	public void placeInPortal(Entity entity, float rotationYaw) {}
	
	@Override
	public boolean placeInExistingPortal(Entity entity, float rotationYaw) {return false;}
	
	@Override
	public boolean makePortal(Entity entity) {return true;}
	
	@Override
	public void removeStalePortalLocations(long worldTime) {
		if(worldTime % 100L == 0L) {
			long i = worldTime - 300L;
			ObjectIterator<Teleporter.PortalPosition> iterator = this.destinationCoordinateCache.values().iterator();
			
			while(iterator.hasNext()) {
				Teleporter.PortalPosition position = (Teleporter.PortalPosition)iterator.next();
				
				if(position == null || position.lastUpdateTime < i) iterator.remove();
			}
		}
	}
	
	@Override
	public void placeEntity(World world, Entity entity, float yaw) {
		BlockPos pos = entity.getPosition();
		double d5 = (double) pos.getX() + 0.5D,
				   d7 = (double) pos.getZ() + 0.5D;
			
			BlockPos LandPos = null;
			for(int c = pos.getY(); c < 256; c++)
			{
				BlockPos BPos = new BlockPos(d5, c, d7);
				if(!world.isAirBlock(BPos) && world.isAirBlock(BPos.up()) && world.isAirBlock(BPos.up().up()))
				{
					LandPos = BPos;
					continue;
				}
			}
			
			//System.out.println(world);
			
			if(LandPos == null)
				
			for(int c = pos.getY(); c > 0; c--)
			{
				BlockPos BPos = new BlockPos(d5, c, d7);
				if(!world.isAirBlock(BPos) && world.isAirBlock(BPos.up()) && world.isAirBlock(BPos.up().up()))
				{
					LandPos = BPos;
					continue;
				}
			}
			
			if(LandPos == null)
			{
				if(entity instanceof EntityPlayerMP) ((EntityPlayerMP) entity).connection.setPlayerLocation(d5, pos.getY(), d7, entity.rotationYaw, entity.rotationPitch);
				else entity.setLocationAndAngles(d5, pos.getY(), d7, entity.rotationYaw, entity.rotationPitch);
				EntityBorderCrosser crosser = new EntityBorderCrosser(entity.world, entity.dimension);
				crosser.tick = 101;
				crosser.setPosition(entity.posX, entity.posY, entity.posZ);
			}
			else
			{
				if(entity instanceof EntityPlayerMP) ((EntityPlayerMP) entity).connection.setPlayerLocation(d5, LandPos.getY()+1, d7, entity.rotationYaw, entity.rotationPitch);
				else entity.setLocationAndAngles(d5, LandPos.getY()+1, d7, entity.rotationYaw, entity.rotationPitch);
				EntityBorderCrosser crosser = new EntityBorderCrosser(entity.world, entity.dimension);
				crosser.tick = 101;
				crosser.setPosition(entity.posX, entity.posY, entity.posZ);
			}
	}
}
