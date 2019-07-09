package testmod.seccult.world.gen.plant;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import testmod.seccult.blocks.BlockPlant;

public class WorldGenPlants extends WorldGenerator{
	private BlockPlant flower;
	
	public WorldGenPlants(BlockPlant flower) {
		this.flower = flower;
	}
	
	@Override
	public boolean generate(World world, Random rand, BlockPos position) {
		for (int i = 0; i < 64; i++)
		{
			BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
			
			if(world.isAirBlock(blockpos) && blockpos.getY() < 255 && this.flower.canBlockStay(world, blockpos, this.flower.getDefaultState()))
				world.setBlockState(blockpos, this.flower.getDefaultState());
		}
		return false;
	}

}
