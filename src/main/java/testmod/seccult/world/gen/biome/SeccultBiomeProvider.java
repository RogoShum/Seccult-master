package testmod.seccult.world.gen.biome;

import net.minecraft.world.biome.BiomeProvider;
import net.minecraft.world.gen.layer.GenLayer;
import net.minecraft.world.gen.layer.GenLayerVoronoiZoom;
import net.minecraft.world.gen.layer.GenLayerZoom;
import net.minecraft.world.storage.WorldInfo;
import testmod.seccult.ModReclection;
import testmod.seccult.world.gen.SeccultBiomeRegistries;

public class SeccultBiomeProvider extends BiomeProvider{

    public SeccultBiomeProvider(WorldInfo world, long seed) throws NoSuchFieldException, SecurityException, IllegalArgumentException, IllegalAccessException
    {
        super(world);

        getBiomesToSpawnIn().clear();
        
		GenLayer biomes = new GenLayerSeccultBiome(1000L);
		//biomes = new GenLayerSeccult(1001L, biomes);

		//biomes = new GenLayerZoom(1002L, biomes);
		//biomes = new GenLayerZoom(1003L, biomes);
		//biomes = new GenLayerZoom(1004L, biomes);
		//biomes = new GenLayerZoom(1005L, biomes);
		//biomes = new GenLayerZoom(1006L, biomes);

		GenLayer genlayervoronoizoom = new GenLayerVoronoiZoom(10L, biomes);

		biomes.initWorldGenSeed(seed);
		genlayervoronoizoom.initWorldGenSeed(seed);
		
		ModReclection.BiomeProvider_SetgenBiomes(this, biomes);
		ModReclection.BiomeProvider_SetbiomeIndexLayer(this, genlayervoronoizoom);
    }
}
