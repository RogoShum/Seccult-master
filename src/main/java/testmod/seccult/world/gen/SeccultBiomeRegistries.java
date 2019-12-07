package testmod.seccult.world.gen;

import java.util.Iterator;
import java.util.Set;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLiving.SpawnPlacementType;
import net.minecraft.entity.EntitySpawnPlacementRegistry;
import net.minecraft.entity.EnumCreatureType;
import net.minecraft.init.Biomes;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.Biome.BiomeProperties;
import net.minecraft.world.biome.Biome.SpawnListEntry;
import net.minecraftforge.common.BiomeDictionary;
import net.minecraftforge.common.BiomeDictionary.Type;
import net.minecraftforge.common.BiomeManager;
import net.minecraftforge.common.BiomeManager.BiomeType;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.EntityRegistry;
import testmod.seccult.Seccult;
import testmod.seccult.entity.livings.landCreature.EntityDreamPop;
import testmod.seccult.entity.livings.water.EntityBoneShark;
import testmod.seccult.entity.livings.water.EntityFish;
import testmod.seccult.entity.livings.water.EntityJellyfish;
import testmod.seccult.entity.livings.water.EntityRockShellLeviathan;
import testmod.seccult.entity.livings.water.EntityWaterTentacle;
import testmod.seccult.world.gen.biome.BiomeManaForest;
import testmod.seccult.world.gen.biome.BiomeManaMushroom;
import testmod.seccult.world.gen.biome.BiomeManaOcean;
import testmod.seccult.world.gen.biome.BiomeManaSpirit;

public class SeccultBiomeRegistries {
	//.
	//.setBaseHeight(3.5F)
	//.setBaseHeight(3.5F)
	//.setBaseHeight(3.5F)
	
	//.setBaseHeight(3.0F)
	//.setBaseHeight(3.5F)
	//.setBaseHeight(3.5F)
	
	//.setBaseHeight(3.0F)
	//.setBaseHeight(3.5F)
	//.setBaseHeight(3.5F)
	
	//.setBaseHeight(3.0F)
	//.setBaseHeight(1.1F)
	//.setBaseHeight(-1.5F)

	public static Biome mana_froest_Mountain = new BiomeManaForest(BiomeManaForest.Type.NORMAL, new BiomeProperties("Mana_Forest_Mountain")
			.setTemperature(0.95f).setRainfall(1.0f).setWaterColor(1).setHeightVariation(0.3F).setBaseHeight(1.8F)).setRegistryName(Seccult.MODID, "Mana_Forest_Mountain");
	public static Biome mana_froest_MACICAL = new BiomeManaForest(BiomeManaForest.Type.Magical, new BiomeProperties("Mana_Forest_Magical")
			.setTemperature(0.95f).setRainfall(1.0f).setWaterColor(1).setHeightVariation(0.2F).setBaseHeight(1.8F)).setRegistryName(Seccult.MODID, "Mana_Forest_Magical");
	public static Biome mana_froest_FLOWER = new BiomeManaForest(BiomeManaForest.Type.FLOWER, new BiomeProperties("Mana_Forest_Flower")
			.setTemperature(0.95f).setRainfall(1.0f).setWaterColor(1).setHeightVariation(0.2F).setBaseHeight(1.8F)).setRegistryName(Seccult.MODID, "Mana_Forest_Flower");
	public static Biome mana_froest_ROOFED = new BiomeManaForest(BiomeManaForest.Type.ROOFED, new BiomeProperties("Mana_Forest_Roofed")
			.setTemperature(0.95f).setRainfall(1.0f).setWaterColor(1).setHeightVariation(0.2F).setBaseHeight(1.8F)).setRegistryName(Seccult.MODID, "Mana_Forest_Roofed");
	
	public static Biome mana_Mushroom_Cave = new BiomeManaMushroom(BiomeManaMushroom.Type.CAVE, new BiomeProperties("Mana_Mushroom_Cave")
			.setTemperature(1.25f).setRainfall(1.0f).setWaterColor(1).setHeightVariation(0.3F).setBaseHeight(2F)).setRegistryName(Seccult.MODID, "Mana_Mushroom_Cave");
	public static Biome mana_Mushroom_Island = new BiomeManaMushroom(BiomeManaMushroom.Type.ISLAND, new BiomeProperties("Mana_Mushroom_Island")
			.setTemperature(1.25f).setRainfall(1.0f).setWaterColor(1).setHeightVariation(0.2F).setBaseHeight(1.8F)).setRegistryName(Seccult.MODID, "Mana_Mushroom_Island");
	public static Biome mana_Mushroom_Normal = new BiomeManaMushroom(BiomeManaMushroom.Type.NORMAL, new BiomeProperties("Mana_Mushroom_Normal")
			.setTemperature(1.25f).setRainfall(1.0f).setWaterColor(1).setHeightVariation(0.2F).setBaseHeight(1.8F)).setRegistryName(Seccult.MODID, "Mana_Mushroom_Normal");
	
	public static Biome mana_OceanSide = new BiomeManaOcean(BiomeManaOcean.Type.SIDE, new BiomeProperties("Mana_OceanSide")
			.setTemperature(1.25f).setRainfall(1.0f).setWaterColor(1).setBaseHeight(-0.4F).setHeightVariation(0.3F)).setRegistryName(Seccult.MODID, "Mana_OceanSide");
	public static Biome mana_Ocean = new BiomeManaOcean(BiomeManaOcean.Type.NORMAL,new BiomeProperties("Mana_Ocean")
			.setTemperature(1.25f).setRainfall(1.0f).setWaterColor(1).setBaseHeight(-1.5F).setHeightVariation(0.3F)).setRegistryName(Seccult.MODID, "Mana_Ocean");
	public static Biome mana_DeepOcean = new BiomeManaOcean(BiomeManaOcean.Type.DEEP,new BiomeProperties("Mana_DeepOcean")
			.setTemperature(1.25f).setRainfall(1.0f).setWaterColor(1).setBaseHeight(-2.5F)).setRegistryName(Seccult.MODID, "Mana_DeepOcean");
	
	public static Biome Oblivion = new BiomeManaSpirit(BiomeManaSpirit.Type.NORMAL, new BiomeProperties("Oblivion")
			.setTemperature(0.95f).setRainfall(1.0f).setWaterColor(1).setBaseHeight(1.2F)).setRegistryName(Seccult.MODID, "Oblivion");
	public static Biome Oblivion_Skyrim = new BiomeManaSpirit(BiomeManaSpirit.Type.SKYTIM, new BiomeProperties("Oblivion_Skyrim")
			.setTemperature(0.95f).setRainfall(1.0f).setWaterColor(1).setBaseHeight(-2F)).setRegistryName(Seccult.MODID, "Oblivion_Skyrim");
	public static Biome Oblivion_Summerest = new BiomeManaSpirit(BiomeManaSpirit.Type.SUMMEREST, new BiomeProperties("Oblivion_Summerest")
			.setTemperature(0.95f).setRainfall(1.0f).setWaterColor(1).setBaseHeight(1.1F)).setRegistryName(Seccult.MODID, "Oblivion_Summerest");
	
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
					mana_Ocean,
					mana_OceanSide,
					mana_DeepOcean,
					Oblivion,
					Oblivion_Skyrim,
					Oblivion_Summerest
			};
			
			event.getRegistry().registerAll(biomes);
			
			spawnBiomes();
		}
		
		private static void spawnBiomes() {
			BiomeDictionary.addTypes(mana_froest_Mountain, Type.FOREST);
			//BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(mana_froest_Mountain, 10));
			
			BiomeDictionary.addTypes(mana_froest_MACICAL, Type.FOREST);
			//BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(mana_froest_MACICAL, 10));
			
			BiomeDictionary.addTypes(mana_froest_FLOWER, Type.FOREST);
			//BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(mana_froest_FLOWER, 10));
			
			BiomeDictionary.addTypes(mana_froest_ROOFED, Type.FOREST);
			//BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(mana_froest_ROOFED, 10));
			
			BiomeDictionary.addTypes(mana_Mushroom_Cave, Type.MUSHROOM);
			BiomeDictionary.addTypes(mana_Mushroom_Island, Type.MUSHROOM);
			BiomeDictionary.addTypes(mana_Mushroom_Normal, Type.MUSHROOM);
			BiomeDictionary.addTypes(mana_OceanSide, Type.BEACH);
			BiomeDictionary.addTypes(mana_Ocean, Type.OCEAN);
			BiomeDictionary.addTypes(mana_DeepOcean, Type.OCEAN);
			
			BiomeDictionary.addTypes(Oblivion, Type.END);
			BiomeDictionary.addTypes(Oblivion_Skyrim, Type.VOID);
			BiomeDictionary.addTypes(Oblivion_Summerest, Type.END);
			
			//BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(mana_Mushroom_Cave, 5));
			//BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(mana_Mushroom_Island, 5));
			//BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(mana_Mushroom_Normal, 5));
			
			//BiomeManager.addBiome(BiomeType.COOL, new BiomeManager.BiomeEntry(mana_OceanSide, 50));
			//BiomeManager.addBiome(BiomeType.COOL, new BiomeManager.BiomeEntry(mana_Ocean, 50));
			//BiomeManager.addBiome(BiomeType.COOL, new BiomeManager.BiomeEntry(mana_DeepOcean, 100));
			//BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(Oblivion, 50));
			//BiomeManager.addBiome(BiomeType.WARM, new BiomeManager.BiomeEntry(Oblivion_Skyrim, 50));
			
			registerCreatureSpawn();
		}
		
		private static void registerCreatureSpawn()
		{
			EntitySpawnPlacementRegistry.setPlacementType(EntityFish.class, SpawnPlacementType.IN_WATER);
			EntitySpawnPlacementRegistry.setPlacementType(EntityJellyfish.class, SpawnPlacementType.IN_WATER);
			EntitySpawnPlacementRegistry.setPlacementType(EntityBoneShark.class, SpawnPlacementType.IN_WATER);
			EntitySpawnPlacementRegistry.setPlacementType(EntityWaterTentacle.class, SpawnPlacementType.IN_WATER);
			EntitySpawnPlacementRegistry.setPlacementType(EntityRockShellLeviathan.class, SpawnPlacementType.IN_WATER);
			
			EntityRegistry.addSpawn(EntityFish.class, 5, 1, 2, EnumCreatureType.WATER_CREATURE, SeccultBiomeRegistries.mana_froest_Mountain);
			EntityRegistry.addSpawn(EntityFish.class, 5, 2, 4, EnumCreatureType.WATER_CREATURE, SeccultBiomeRegistries.mana_froest_MACICAL);
			EntityRegistry.addSpawn(EntityFish.class, 5, 1, 2, EnumCreatureType.WATER_CREATURE, SeccultBiomeRegistries.mana_froest_FLOWER);
			EntityRegistry.addSpawn(EntityFish.class, 5, 1, 2, EnumCreatureType.WATER_CREATURE, SeccultBiomeRegistries.mana_froest_ROOFED);
			
			EntityRegistry.addSpawn(EntityFish.class, 5, 1, 2, EnumCreatureType.WATER_CREATURE, SeccultBiomeRegistries.mana_Mushroom_Island);
			EntityRegistry.addSpawn(EntityFish.class, 5, 1, 2, EnumCreatureType.WATER_CREATURE, SeccultBiomeRegistries.mana_Mushroom_Normal);
			
			EntityRegistry.addSpawn(EntityFish.class, 20, 4, 10, EnumCreatureType.WATER_CREATURE, SeccultBiomeRegistries.mana_OceanSide);
			EntityRegistry.addSpawn(EntityJellyfish.class, 5, 1, 2, EnumCreatureType.WATER_CREATURE, SeccultBiomeRegistries.mana_OceanSide);
			
			EntityRegistry.addSpawn(EntityFish.class, 20, 6, 15, EnumCreatureType.WATER_CREATURE, SeccultBiomeRegistries.mana_Ocean);
			EntityRegistry.addSpawn(EntityJellyfish.class, 5, 3, 5, EnumCreatureType.WATER_CREATURE, SeccultBiomeRegistries.mana_Ocean);
			
			EntityRegistry.addSpawn(EntityFish.class, 20, 10, 20, EnumCreatureType.WATER_CREATURE, SeccultBiomeRegistries.mana_DeepOcean);
			EntityRegistry.addSpawn(EntityJellyfish.class, 10, 4, 10, EnumCreatureType.WATER_CREATURE, SeccultBiomeRegistries.mana_DeepOcean);
			
			EntityRegistry.addSpawn(EntityBoneShark.class, 5, 2, 4, EnumCreatureType.WATER_CREATURE, SeccultBiomeRegistries.mana_Ocean);
			EntityRegistry.addSpawn(EntityBoneShark.class, 5, 1, 1, EnumCreatureType.WATER_CREATURE, SeccultBiomeRegistries.mana_DeepOcean);
			
			EntityRegistry.addSpawn(EntityWaterTentacle.class, 2, 1, 2, EnumCreatureType.WATER_CREATURE, SeccultBiomeRegistries.mana_DeepOcean);
			EntityRegistry.addSpawn(EntityRockShellLeviathan.class, 1, 1, 1, EnumCreatureType.WATER_CREATURE, SeccultBiomeRegistries.mana_DeepOcean);
			
			BiomeDictionary.Type[] types = {
					BiomeDictionary.Type.BEACH,
					//BiomeDictionary.Type.COLD,
					//BiomeDictionary.Type.CONIFEROUS,
					//BiomeDictionary.Type.DEAD,
					//BiomeDictionary.Type.DENSE,
					//BiomeDictionary.Type.DRY,
					BiomeDictionary.Type.FOREST,
					//BiomeDictionary.Type.HILLS,
					//BiomeDictionary.Type.HOT,
					BiomeDictionary.Type.JUNGLE,
					//BiomeDictionary.Type.LUSH,
					BiomeDictionary.Type.MAGICAL,
					//BiomeDictionary.Type.MESA,
					//BiomeDictionary.Type.MOUNTAIN,
					//BiomeDictionary.Type.OCEAN,
					//BiomeDictionary.Type.PLAINS,
					//BiomeDictionary.Type.RARE,
					//BiomeDictionary.Type.RIVER,
					//BiomeDictionary.Type.SANDY,
					//BiomeDictionary.Type.SAVANNA,
					//BiomeDictionary.Type.SNOWY,
					//BiomeDictionary.Type.SPARSE,
					//BiomeDictionary.Type.SPOOKY,
					//BiomeDictionary.Type.SWAMP,
					//BiomeDictionary.Type.VOID,
					//BiomeDictionary.Type.WASTELAND,
					//BiomeDictionary.Type.WATER,
					//BiomeDictionary.Type.WET
			};
			
			SpawnListEntry spawnPop = new SpawnListEntry(EntityDreamPop.class, 1, 1, 2);

			for (BiomeDictionary.Type t : types) {
				Set<Biome> biomes = BiomeDictionary.getBiomes(t);
				
				Iterator biomeIterator = biomes.iterator();
				
				while(biomeIterator.hasNext()) {
					Biome currentBiome = (Biome) biomeIterator.next();
					
					currentBiome.getSpawnableList(EnumCreatureType.AMBIENT).add(spawnPop);
				}
			}
		}
	}
}
