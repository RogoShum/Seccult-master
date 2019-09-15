package testmod.seccult.world.gen.biome;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import testmod.seccult.world.gen.SeccultBiomeRegistries;

public class GenLayerSpiritBiome extends GenLayer {
	
	protected static final List<Supplier<Biome>> SpiritRare = Arrays.asList(
			() -> SeccultBiomeRegistries.Oblivion
	);
	
	protected static final List<Supplier<Biome>> SpiritCommon = Arrays.asList(
			() -> SeccultBiomeRegistries.Oblivion_Skyrim,
			() -> SeccultBiomeRegistries.Oblivion_Summerest
	);

	public GenLayerSpiritBiome(long l, GenLayer genlayer) {
		super(l);
		parent = genlayer;
	}

	public GenLayerSpiritBiome(long l) {
		super(l);
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth) {

	int dest[] = IntCache.getIntCache(width * depth);

		for (int dz = 0; dz < depth; dz++) {
			for (int dx = 0; dx < width; dx++) {
				initChunkSeed(dx + x, dz + z);

					if (nextInt(10) == 0) {
						dest[dx + dz * width] = Biome.getIdForBiome(getRandomBiome(SpiritRare));
					} else {
						dest[dx + dz * width] = Biome.getIdForBiome(getRandomBiome(SpiritCommon));
					}
				}
			}
		
		return dest;
	}
	
	private Biome getRandomBiome(List<Supplier<Biome>> biomes) {
		return biomes.get(nextInt(biomes.size())).get();
	}
}
