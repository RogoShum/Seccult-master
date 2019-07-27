package testmod.seccult.blocks.tileEntity;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.world.gen.plant.WorldGenCave;

public class tileGenerator extends TileEntity implements ITickable {

		public tileGenerator() {
		}
		
		@Override
		public void update() {
			boolean Gen = true;
			Iterable<BlockPos> pp = BlockPos.getAllInBox(pos.add(60, 60, 60), pos.add(-60, -60, -60));
			for(BlockPos p : pp)
			{
				if(world.getBlockState(p).getBlock() == ModBlocks.Mush_Gen && !p.equals(pos))
				{
					Gen = false;
					world.setBlockState(p, Blocks.STONE.getDefaultState());
				}
			}

			if(Gen)
			{
				Random rand = new Random();
				WorldGenCave cave = new WorldGenCave(Blocks.AIR);
				world.setBlockState(pos, Blocks.AIR.getDefaultState());
				if(!world.isRemote)
				cave.generate(world, rand, pos);
			}
		}
		
		
	    @Override
	    public boolean shouldRefresh(World world, BlockPos pos, IBlockState oldState, IBlockState newState)
	    {
	        return oldState.getBlock() != newState.getBlock();
	    }
	}