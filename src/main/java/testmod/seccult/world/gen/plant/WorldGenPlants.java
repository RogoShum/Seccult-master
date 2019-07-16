package testmod.seccult.world.gen.plant;

import java.util.Random;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import testmod.seccult.blocks.BlockPlant;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.util.handlers.PlantsHandler.EnumType;

public class WorldGenPlants extends WorldGenerator{
	private BlockPlant flower;
	private EnumType TYPE;
	
	public WorldGenPlants(BlockPlant flower, EnumType type) {
		this.flower = flower;
		this.TYPE = type;
	}
	
	@Override
	public boolean generate(World world, Random rand, BlockPos position) {
		for (int i = 0; i < 64; i++)
		{
			BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));
			
			if(world.isAirBlock(blockpos) && blockpos.getY() < 255 && ModBlocks.FLOWER.canBlockStay(world, blockpos, ModBlocks.FLOWER.getDefaultState()))
				world.setBlockState(blockpos, ModBlocks.FLOWER.getDefaultState().withProperty(BlockPlant.VARIANT, TYPE));
		}
		return false;
	}

}
