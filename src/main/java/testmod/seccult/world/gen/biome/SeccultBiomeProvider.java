package testmod.seccult.world.gen.biome;

import java.util.List;
import java.util.function.Supplier;

import net.minecraft.world.WorldServer;
import net.minecraft.world.WorldType;
import net.minecraft.world.biome.Biome;
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
        allowedBiomes.clear();
        getBiomesToSpawnIn().clear();

		//ModReclection.BiomeProvider_SetgenBiomes(this, biomes);
		//ModReclection.BiomeProvider_SetbiomeIndexLayer(this, genlayervoronoizoom);
    }
    
    @Override
    public GenLayer[] getModdedBiomeGenerators(WorldType worldType, long seed, GenLayer[] original) {
    	GenLayer biomes = new GenLayerSeccultBiome(1000L);
    	//GenLayer biomes = new GenLayerTestLayer(1000L);
		biomes = new GenLayerSeccult(1001L, biomes);

		biomes = new GenLayerZoom(1002L, biomes);
		biomes = new GenLayerZoom(1003L, biomes);
		biomes = new GenLayerZoom(1004L, biomes);
		biomes = new GenLayerZoom(1005L, biomes);
		biomes = new GenLayerZoom(1006L, biomes);

		GenLayer biomeIndexLayer = new GenLayerVoronoiZoom(10L, biomes);

		biomes.initWorldGenSeed(seed);
		biomeIndexLayer.initWorldGenSeed(seed);

        return new GenLayer[]{
                biomes,
                biomeIndexLayer
        };
    }
}
