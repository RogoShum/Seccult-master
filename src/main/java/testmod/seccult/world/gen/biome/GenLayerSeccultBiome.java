package testmod.seccult.world.gen.biome;

import java.util.Arrays;
import java.util.List;
import java.util.function.Supplier;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.IntCache;
import testmod.seccult.world.gen.SeccultBiomeRegistries;

public class GenLayerSeccultBiome extends GenLayer {
	private static final int RARE_BIOME_CHANCE = 50;
	private static final int TYPE_AMOUNT = 3;
	private static final int AREA = 24;
	
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

	public GenLayerSeccultBiome(long l, GenLayer genlayer) {
		super(l);
		parent = genlayer;
	}

	public GenLayerSeccultBiome(long l) {
		super(l);
	}

	@Override
	public int[] getInts(int x, int z, int width, int depth) {
		int xx = x;
		int zz = z;
		xx = Math.abs(xx);
		zz = Math.abs(zz);
		
		int xScale = 1;
		int zScale = 1;
		
		for(;(xScale * AREA) < xx;)
		{
			++xScale;
		}
		
		for(;(zScale * AREA) < zz;)
		{
			++zScale;
		}
		
		xx = xx - (AREA * (xScale - 1));
		zz = zz - (AREA * (zScale - 1));
		int type = 0;
		int start = AREA / TYPE_AMOUNT / 2;
		
		for(int i = 1; i < TYPE_AMOUNT+1; )
		{
			int whatever = i * start;
			if((xx >= whatever && xx <= AREA - whatever) && (zz >= i * start && zz <= AREA - whatever))
			{
				type = i;
			}
			
			++i;
		}
		
	int dest[] = IntCache.getIntCache(width * depth);

		for (int dz = 0; dz < depth; dz++) {
			for (int dx = 0; dx < width; dx++) {
				initChunkSeed(dx + x, dz + z);
				
				switch(type)
				{
				case 0:
					if (nextInt(RARE_BIOME_CHANCE) == 0) {
						dest[dx + dz * width] = Biome.getIdForBiome(getRandomBiome(ForestRare));
						//System.out.println("Rare Forest");
					} else {
						dest[dx + dz * width] = Biome.getIdForBiome(getRandomBiome(ForestCommon));
					}
					break;
				case 1:
					if (nextInt(RARE_BIOME_CHANCE) == 0) {
						dest[dx + dz * width] = Biome.getIdForBiome(getRandomBiome(MushroomRare));
						//System.out.println("Rare MushRoom");
					} else {
						dest[dx + dz * width] = Biome.getIdForBiome(getRandomBiome(MushroomCommon));
					}
					break;
				case 2:
					if (nextInt(3) == 0) {
						dest[dx + dz * width] = Biome.getIdForBiome(getRandomBiome(OceanRare));
						//System.out.println("Rare Ocean");
					} else {
						dest[dx + dz * width] = Biome.getIdForBiome(getRandomBiome(OceanCommon));
					}
					break;
				}
			}
		}

		return dest;
	}
	
	private Biome getRandomBiome(List<Supplier<Biome>> biomes) {
		return biomes.get(nextInt(biomes.size())).get();
	}
}
