package testmod.seccult.world.gen;

import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import testmod.seccult.Seccult;
import testmod.seccult.world.gen.biome.BiomeManaForest;
import testmod.seccult.world.gen.biome.BiomeManaMushroom;
import testmod.seccult.world.gen.biome.BiomeManaOcean;

public class SeccultBiomeRegistries {
	public static Biome mana_froest_Mountain = new BiomeManaForest(BiomeManaForest.Type.NORMAL, new BiomeProperties("Mana_Forest_Mountain").setTemperature(0.95f).setRainfall(1.0f).setWaterColor(1)).setRegistryName(Seccult.MODID, "Mana_Forest_Mountain");
	public static Biome mana_froest_MACICAL = new BiomeManaForest(BiomeManaForest.Type.Magical, new BiomeProperties("Mana_Forest_Magical").setTemperature(0.95f).setRainfall(1.0f).setWaterColor(1)).setRegistryName(Seccult.MODID, "Mana_Forest_Magical");
	public static Biome mana_froest_FLOWER = new BiomeManaForest(BiomeManaForest.Type.FLOWER, new BiomeProperties("Mana_Forest_Flower").setTemperature(0.95f).setRainfall(1.0f).setWaterColor(1)).setRegistryName(Seccult.MODID, "Mana_Forest_Flower");
	public static Biome mana_froest_ROOFED = new BiomeManaForest(BiomeManaForest.Type.ROOFED, new BiomeProperties("Mana_Forest_Roofed").setTemperature(0.95f).setRainfall(1.0f).setWaterColor(1)).setRegistryName(Seccult.MODID, "Mana_Forest_Roofed");
	
	public static Biome mana_Mushroom_Cave = new BiomeManaMushroom(BiomeManaMushroom.Type.CAVE, new BiomeProperties("Mana_Mushroom_Cave").setTemperature(1.25f).setRainfall(1.0f).setWaterColor(1)).setRegistryName(Seccult.MODID, "Mana_Mushroom_Cave");
	public static Biome mana_Mushroom_Island = new BiomeManaMushroom(BiomeManaMushroom.Type.ISLAND, new BiomeProperties("Mana_Mushroom_Island").setTemperature(1.25f).setRainfall(1.0f).setWaterColor(1)).setRegistryName(Seccult.MODID, "Mana_Mushroom_Island");
	public static Biome mana_Mushroom_Normal = new BiomeManaMushroom(BiomeManaMushroom.Type.NORMAL, new BiomeProperties("Mana_Mushroom_Normal").setTemperature(1.25f).setRainfall(1.0f).setWaterColor(1)).setRegistryName(Seccult.MODID, "Mana_Mushroom_Normal");
	
	public static Biome mana_Ocean = new BiomeManaOcean(new BiomeProperties("Mana_Ocean").setTemperature(1.25f).setRainfall(1.0f).setWaterColor(1).setBaseHeight(-1.8F).setHeightVariation(0.1F)).setRegistryName(Seccult.MODID, "Mana_Ocean");
	
	@Mod.EventBusSubscriber(modid = Seccult.MODID)
	public static class Register {
		
		@SubscribeEvent
		public static void registerBiomes(final RegistryEvent.Register<Biome> event) {
			final Biome[] biomes = {
					mana_froest_Mountain,
					mana_froest_MACICAL,
					mana_froest_FLOWER,
					mana_froest_ROOFED,
					mana_Mushroom_Cave,
					mana_Mushroom_Island,
					mana_Mushroom_Normal,
					mana_Ocean
			};
			
			event.getRegistry().registerAll(biomes);
			
			spawnBiomes();
		}
		
		private static void spawnBiomes() {
			BiomeDictionary.addTypes(mana_froest_Mountain, Type.FOREST);
			//BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(mana_froest_Mountain, 50));
			
			BiomeDictionary.addTypes(mana_froest_MACICAL, Type.FOREST);
			//BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(mana_froest_BIRCH, 50));
			
			BiomeDictionary.addTypes(mana_froest_FLOWER, Type.FOREST);
			//BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(mana_froest_FLOWER, 50));
			
			BiomeDictionary.addTypes(mana_froest_ROOFED, Type.FOREST);
			//BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(mana_froest_ROOFED, 50));
			
			BiomeDictionary.addTypes(mana_Mushroom_Cave, Type.MUSHROOM);
			BiomeDictionary.addTypes(mana_Mushroom_Island, Type.MUSHROOM);
			BiomeDictionary.addTypes(mana_Mushroom_Normal, Type.MUSHROOM);
			BiomeDictionary.addTypes(mana_Ocean, Type.OCEAN);
		}
	}
}
