package testmod.seccult.world.gen.biome;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import testmod.seccult.world.gen.SeccultBiomeRegistries;

public class GenLayerSeccult extends GenLayer {

	public GenLayerSeccult(long l, GenLayer genlayer) {
		super(l);
		parent = genlayer;
	}

	/**
	 * If we are next to one of the 4 "key" biomes, we randomly turn into a companion biome for that center biome
	 */
	@Override
	public int[] getInts(int x, int z, int width, int depth) {

		int nx = x - 1;
		int nz = z - 1;
		int nwidth = width + 2;
		int ndepth = depth + 2;
		int input[] = parent.getInts(nx, nz, nwidth, ndepth);
		int output[] = IntCache.getIntCache(width * depth);

		int fireSwamp        = Biome.getIdForBiome(SeccultBiomeRegistries.mana_froest_ROOFED);
		int swamp            = Biome.getIdForBiome(SeccultBiomeRegistries.mana_froest_BIRCH);
		int glacier          = Biome.getIdForBiome(SeccultBiomeRegistries.mana_froest_Mountain);
		int snowyForest      = Biome.getIdForBiome(SeccultBiomeRegistries.mana_froest_FLOWER);

		for (int dz = 0; dz < depth; dz++) {
			for (int dx = 0; dx < width; dx++) {

				int right  = input[dx + 0 + (dz + 1) * nwidth];
				int left   = input[dx + 2 + (dz + 1) * nwidth];
				int up     = input[dx + 1 + (dz + 0) * nwidth];
				int down   = input[dx + 1 + (dz + 2) * nwidth];
				int center = input[dx + 1 + (dz + 1) * nwidth];

				if (isKey(fireSwamp, center, right, left, up, down)) {
					output[dx + dz * width] = swamp;
				} else if (isKey(glacier, center, right, left, up, down)) {
					output[dx + dz * width] = snowyForest;
				} else {
					output[dx + dz * width] = center;
				}
			}
		}

		return output;
	}

	/**
	 * Returns true if any of the surrounding biomes is the specified biome
	 */
	boolean isKey(int biome, int center, int right, int left, int up, int down) {

		return center != biome && (right == biome || left == biome || up == biome || down == biome);
	}
}
