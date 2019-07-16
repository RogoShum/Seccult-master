package testmod.seccult.world.gen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import testmod.seccult.blocks.Leaf;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.util.handlers.TreeHandler;
import testmod.seccult.util.handlers.PlantsHandler;
import testmod.seccult.world.gen.plant.WorldGenSeccultTree;
import testmod.seccult.world.gen.plant.WorldGenEverythingTree;
import testmod.seccult.world.gen.plant.WorldGenPlants;

public class WorldGenCustomTrees implements IWorldGenerator
{
	private final WorldGenerator EVERYTHING = new WorldGenEverythingTree();
	private final WorldGenSeccultTree BLUETREE = new WorldGenSeccultTree(true, TreeHandler.EnumType.MANA_TREE_BLUE);
	private final WorldGenSeccultTree WHITETREE = new WorldGenSeccultTree(true, TreeHandler.EnumType.MANA_TREE_WHITE);
	private final WorldGenSeccultTree MAGICTREE = new WorldGenSeccultTree(true, TreeHandler.EnumType.MANA_TREE_MAGIC);
	private final WorldGenPlants LITTLE_FLOWER = new WorldGenPlants(ModBlocks.FLOWER, PlantsHandler.EnumType.Little);
	private final WorldGenPlants CATCH_SOUL = new WorldGenPlants(ModBlocks.FLOWER, PlantsHandler.EnumType.Catch_Soul);
	private final WorldGenPlants C = new WorldGenPlants(ModBlocks.FLOWER, PlantsHandler.EnumType.C);
	private final WorldGenPlants M = new WorldGenPlants(ModBlocks.FLOWER, PlantsHandler.EnumType.M);
	private final WorldGenPlants Y = new WorldGenPlants(ModBlocks.FLOWER, PlantsHandler.EnumType.Y);
	private final WorldGenPlants GRASS = new WorldGenPlants(ModBlocks.FLOWER, PlantsHandler.EnumType.Grass);
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		switch(world.provider.getDimension())
		{
		case 1:
			
			break;
			
		case DimensionMagic.MAGIC_ID:
			runGenerator(LITTLE_FLOWER, world, random, chunkX, chunkZ, 1, 40, 76);
			runGenerator(CATCH_SOUL, world, random, chunkX, chunkZ, 1, 0, 120);
			runGenerator(C, world, random, chunkX, chunkZ, 1, 40, 76);
			runGenerator(M, world, random, chunkX, chunkZ, 1, 40, 76);
			runGenerator(Y, world, random, chunkX, chunkZ, 1, 40, 76);
			runGenerator(GRASS, world, random, chunkX, chunkZ, 10, 40, 76);
			break;
			
		case -1:
			
		}
	}
	
	private void runGenerator(WorldGenerator generator, World world, Random random, int chunkX, int chunkZ, double chancesToSpawn, int minHeight, int maxHeight)
	{
		if(minHeight < 0 || maxHeight > 256 || minHeight > maxHeight) throw new IllegalArgumentException("Illegal Height Arguments for WorldGenerator");
		
		int heightDiff = maxHeight - minHeight + 1;
		for(int i = 0; i < chancesToSpawn; i++) {
			int x = chunkX * 16 + random.nextInt(4)+8;
			int y = minHeight + random.nextInt(heightDiff);
			int z = chunkZ * 16 + random.nextInt(4)+8;
			generator.generate(world, random, new BlockPos(x, y, z));
		}
	}
}