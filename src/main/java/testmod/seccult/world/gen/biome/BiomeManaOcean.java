package testmod.seccult.world.gen.biome;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.biome.BiomeOcean;

public class BiomeManaOcean extends BiomeOcean{

	public BiomeManaOcean(BiomeProperties properties) {
		super(properties);
		//topBlock = Blocks.WATER.getDefaultState();
		// fillerBlock = Blocks.WATER.getDefaultState();

		/*this.decorator = new BiomeDecorator()
				{
			
				};*/
	}
	
	@Override
	public int getWaterColorMultiplier() {
		return  -16711681;
	}
	
	@Override
	public int getGrassColorAtPos(BlockPos pos) {
		return  -16711681;
	}
	
	@Override
	public int getFoliageColorAtPos(BlockPos pos) {
		return  -16711681;
	}
}
