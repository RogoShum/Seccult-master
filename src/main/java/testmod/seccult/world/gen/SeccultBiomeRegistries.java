package testmod.seccult.world.gen;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import testmod.seccult.Seccult;
import testmod.seccult.world.gen.biome.BiomeManaForest;

public class SeccultBiomeRegistries {
	public static Biome mana_froest_Mountain = new BiomeManaForest(BiomeManaForest.Type.NORMAL, new BiomeProperties("Mana_Forest_Mountain").setTemperature(0.95f).setRainfall(1.0f).setWaterColor(1113825)).setRegistryName(Seccult.MODID, "Mana_Forest_Mountain");
	public static Biome mana_froest_BIRCH = new BiomeManaForest(BiomeManaForest.Type.BIRCH, new BiomeProperties("Mana_Forest_Birch").setTemperature(0.95f).setRainfall(1.0f).setWaterColor(1113825)).setRegistryName(Seccult.MODID, "Mana_Forest_Birch");
	public static Biome mana_froest_FLOWER = new BiomeManaForest(BiomeManaForest.Type.FLOWER, new BiomeProperties("Mana_Forest_Flower").setTemperature(0.95f).setRainfall(1.0f).setWaterColor(1113825)).setRegistryName(Seccult.MODID, "Mana_Forest_Flower");
	public static Biome mana_froest_ROOFED = new BiomeManaForest(BiomeManaForest.Type.ROOFED, new BiomeProperties("Mana_Forest_Roofed").setTemperature(0.95f).setRainfall(1.0f).setWaterColor(1113825)).setRegistryName(Seccult.MODID, "Mana_Forest_Roofed");
	
	@Mod.EventBusSubscriber(modid = Seccult.MODID)
	public static class Register {
		
		@SubscribeEvent
		public static void registerBiomes(final RegistryEvent.Register<Biome> event) {
			final Biome[] biomes = {
					mana_froest_Mountain,
					mana_froest_BIRCH,
					mana_froest_FLOWER,
					mana_froest_ROOFED
			};
			
			event.getRegistry().registerAll(biomes);
			
			spawnBiomes();
		}
		
		private static void spawnBiomes() {
			BiomeDictionary.addTypes(mana_froest_Mountain, Type.FOREST);
			BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(mana_froest_Mountain, 50));
			BiomeManager.addSpawnBiome(mana_froest_Mountain);
			
			BiomeDictionary.addTypes(mana_froest_BIRCH, Type.FOREST);
			BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(mana_froest_BIRCH, 50));
			BiomeManager.addSpawnBiome(mana_froest_BIRCH);
			
			BiomeDictionary.addTypes(mana_froest_FLOWER, Type.FOREST);
			BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(mana_froest_FLOWER, 50));
			BiomeManager.addSpawnBiome(mana_froest_FLOWER);
			
			BiomeDictionary.addTypes(mana_froest_ROOFED, Type.FOREST);
			BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(mana_froest_ROOFED, 50));
			BiomeManager.addSpawnBiome(mana_froest_ROOFED);
		}
	}
}
