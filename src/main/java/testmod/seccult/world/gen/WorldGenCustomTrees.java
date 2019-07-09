package testmod.seccult.world.gen;

import java.util.Random;

import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenFlowers;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import testmod.seccult.blocks.Leaf;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.util.handlers.TreeHandler;
import testmod.seccult.world.gen.plant.WorldGenSeccultTree;
import testmod.seccult.world.gen.plant.WorldGenEverythingTree;
import testmod.seccult.world.gen.plant.WorldGenPlants;

public class WorldGenCustomTrees implements IWorldGenerator
{
	private final WorldGenerator EVERYTHING = new WorldGenEverythingTree();
	private final WorldGenSeccultTree BLUETREE = new WorldGenSeccultTree(true, TreeHandler.EnumType.MANA_TREE_BLUE);
	private final WorldGenSeccultTree WHITETREE = new WorldGenSeccultTree(true, TreeHandler.EnumType.MANA_TREE_WHITE);
	private final WorldGenSeccultTree MAGICTREE = new WorldGenSeccultTree(true, TreeHandler.EnumType.MANA_TREE_MAGIC);
	private final WorldGenPlants LITTLE_FLOWER = new WorldGenPlants(ModBlocks.LITTLE_FLOWER);
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator, IChunkProvider chunkProvider) 
	{
		switch(world.provider.getDimension())
		{
		case 1:
			
			break;
			
		case 0:
			int i = random.nextInt(4);
			if(i == 0)
			runGenerator(EVERYTHING, world, random, chunkX, chunkZ, 3, 0, 110);
			else if (i == 1)
			runGenerator(BLUETREE, world, random, chunkX, chunkZ, 3, 0, 256);
			else if (i == 2)
			runGenerator(WHITETREE, world, random, chunkX, chunkZ, 3, 0, 256);
			else if (i == 3)
			runGenerator(MAGICTREE, world, random, chunkX, chunkZ, 3, 0, 256);
			runGenerator(LITTLE_FLOWER, world, random, chunkX, chunkZ, 1, 40, 76);
			
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