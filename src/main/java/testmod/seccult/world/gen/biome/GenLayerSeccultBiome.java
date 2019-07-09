package testmod.seccult.world.gen.biome;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import testmod.seccult.world.gen.SeccultBiomeRegistries;

public class GenLayerSeccultBiome extends GenLayer {
	private static final int RARE_BIOME_CHANCE = 15;

	protected static final List<Supplier<Biome>> commonBiomes = Arrays.asList(
			() -> SeccultBiomeRegistries.mana_froest_BIRCH
	);
	protected static final List<Supplier<Biome>> rareBiomes = Arrays.asList(
			() -> SeccultBiomeRegistries.mana_froest_ROOFED,
			() -> SeccultBiomeRegistries.mana_froest_FLOWER,
			() -> SeccultBiomeRegistries.mana_froest_Mountain
	);

	public GenLayerSeccultBiome(long l, GenLayer genlayer) {
		super(l);
		parent = genlayer;
	}

	public GenLayerSeccultBiome(long l) {
		super(l);
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth) {

		int dest[] = IntCache.getIntCache(width * depth);

		for (int dz = 0; dz < depth; dz++) {
			for (int dx = 0; dx < width; dx++) {
				initChunkSeed(dx + x, dz + z);
				if (nextInt(RARE_BIOME_CHANCE) == 0) {
					// make rare biome
					dest[dx + dz * width] = Biome.getIdForBiome(getRandomBiome(rareBiomes));
				} else {
					// make common biome
					dest[dx + dz * width] = Biome.getIdForBiome(getRandomBiome(commonBiomes));
				}
			}
		}

		return dest;
	}

	private Biome getRandomBiome(List<Supplier<Biome>> biomes) {
		return biomes.get(nextInt(biomes.size())).get();
	}
}
