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
        getBiomesToSpawnIn().add(SeccultBiomeRegistries.mana_froest_Mountain);
        getBiomesToSpawnIn().add(SeccultBiomeRegistries.mana_froest_FLOWER);
        getBiomesToSpawnIn().add(SeccultBiomeRegistries.mana_froest_MACICAL);
        getBiomesToSpawnIn().add(SeccultBiomeRegistries.mana_froest_ROOFED);
        getBiomesToSpawnIn().add(SeccultBiomeRegistries.mana_Mushroom_Cave);
        getBiomesToSpawnIn().add(SeccultBiomeRegistries.mana_Mushroom_Normal);
        getBiomesToSpawnIn().add(SeccultBiomeRegistries.mana_Mushroom_Island);
        
        
		GenLayer biomes = new GenLayerSeccultBiome(1L);
		//biomes = new GenLayerSeccult(1000L, biomes);

		biomes = new GenLayerZoom(1001, biomes);
		biomes = new GenLayerZoom(1002, biomes);
		biomes = new GenLayerZoom(1003, biomes);
		biomes = new GenLayerZoom(1004, biomes);
		biomes = new GenLayerZoom(1005, biomes);

		GenLayer genlayervoronoizoom = new GenLayerVoronoiZoom(10L, biomes);

		biomes.initWorldGenSeed(seed);
		genlayervoronoizoom.initWorldGenSeed(seed);
		
		ModReclection.BiomeProvider_SetgenBiomes(this, biomes);
		ModReclection.BiomeProvider_SetbiomeIndexLayer(this, genlayervoronoizoom);
    }
}
