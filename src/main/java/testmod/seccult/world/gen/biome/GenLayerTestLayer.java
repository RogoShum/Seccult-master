package testmod.seccult.world.gen.biome;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import testmod.seccult.world.gen.SeccultBiomeRegistries;

public class GenLayerTestLayer extends GenLayer {
	private static final int RARE_BIOME_CHANCE = 100;
	char[][] preset = new char[9][9];

	public GenLayerTestLayer(long seed) {
		super(seed);
		initPresets();
	}

	protected static final List<Supplier<Biome>> ForestRare = Arrays.asList(
			() -> SeccultBiomeRegistries.mana_froest_MACICAL
	);
	
	protected static final List<Supplier<Biome>> ForestCommon = Arrays.asList(
			() -> SeccultBiomeRegistries.mana_froest_ROOFED,
			() -> SeccultBiomeRegistries.mana_froest_FLOWER,
			() -> SeccultBiomeRegistries.mana_froest_Mountain
	);
	
	protected static final List<Supplier<Biome>> MushroomRare = Arrays.asList(
			() -> SeccultBiomeRegistries.mana_Mushroom_Cave
	);
	
	protected static final List<Supplier<Biome>> MushroomCommon = Arrays.asList(
			() -> SeccultBiomeRegistries.mana_Mushroom_Island,
			() -> SeccultBiomeRegistries.mana_Mushroom_Normal
	);
	
	protected static final List<Supplier<Biome>> OceanRare = Arrays.asList(
			() -> SeccultBiomeRegistries.mana_DeepOcean
	);
	
	protected static final List<Supplier<Biome>> OceanCommon = Arrays.asList(
			() -> SeccultBiomeRegistries.mana_Ocean,
			() -> SeccultBiomeRegistries.mana_OceanSide
	);
	
	@Override
	public int[] getInts(int x, int z, int width, int depth) {

		int dest[] = IntCache.getIntCache(width * depth);

		for (int dz = 0; dz < depth; dz++) {
			for (int dx = 0; dx < width; dx++) {

				int sx = x + dx + 4;
				int sz = z + dz + 4;
				
				sx = sx % 9;
				sz = sz % 9;
				
				if (sx >= 0 && sx < 8 && sz >= 0 && sz < 8) {
					dest[dx + dz * width] = Biome.getIdForBiome(getBiomeFor(preset[sx][sz]));
				} else {
					dest[dx + dz * width] = Biome.getIdForBiome(SeccultBiomeRegistries.mana_OceanSide);
				}
			}
		}

		return dest;
	}

	private void initPresets() {
		char[][] map = {
				{'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'},
				{'P', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'P'},
				{'P', 'M', 'R', 'R', 'C', 'R', 'R', 'M', 'P'},
				{'P', 'M', 'R', 'S', 'S', 'S', 'R', 'M', 'P'},
				{'P', 'M', 'C', 'S', 'D', 'S', 'C', 'M', 'P'},
				{'P', 'M', 'R', 'S', 'S', 'S', 'R', 'M', 'P'},
				{'P', 'M', 'R', 'R', 'C', 'R', 'R', 'M', 'P'},
				{'P', 'M', 'M', 'M', 'M', 'M', 'M', 'M', 'P'},
				{'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P', 'P'}};

		for (int x = 0; x < map.length; x++) {
			for (int z = 0; z < map[x].length; z++) {
				preset[x][z] = map[z][x];
			}
		}
	}

	protected final Biome getBiomeFor(char c) {
		switch (c) {
			default:
				return SeccultBiomeRegistries.mana_OceanSide;
//		case 'P' :
//			return Biome.plains;
			case 'R':
				if (nextInt(RARE_BIOME_CHANCE) == 0) {
					return getRandomBiome(MushroomRare);
				} else {
					return getRandomBiome(MushroomCommon);
				}
			case 'M':
				if (nextInt(RARE_BIOME_CHANCE) == 0) {
					return getRandomBiome(ForestRare);
				} else {
					return getRandomBiome(ForestCommon);
				}
			case 'S':
				if (nextInt(RARE_BIOME_CHANCE) == 0) {
					return getRandomBiome(OceanRare);
				} else {
					return getRandomBiome(OceanCommon);
				}
			case 'D':
				return SeccultBiomeRegistries.mana_DeepOcean;
			case 'C':
				return SeccultBiomeRegistries.mana_Mushroom_Cave;
		}
	}
	private Biome getRandomBiome(List<Supplier<Biome>> biomes) {
		return biomes.get(nextInt(biomes.size())).get();
	}
}
