package testmod.seccult.world.gen;

import java.util.Random;

import net.minecraft.block.state.pattern.BlockMatcher;
import net.minecraft.entity.monster.EntitySkeleton;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.chunk.IChunkProvider;
import net.minecraft.world.gen.IChunkGenerator;
import net.minecraft.world.gen.feature.WorldGenMinable;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.common.IWorldGenerator;
import testmod.seccult.blocks.Ores;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.util.handlers.EnumHandler;
import testmod.seccult.world.gen.structures.SeccultStructure;

public class WorldGenStructures implements IWorldGenerator
{
	private SeccultStructure test = new SeccultStructure("test");
	
	public WorldGenStructures() 
	{
	}
	
	@Override
	public void generate(Random random, int chunkX, int chunkZ, World world, IChunkGenerator chunkGenerator,
			IChunkProvider chunkProvider) 
	{
		switch(world.provider.getDimension())
		{
		case 0:
		
			//runGenerator(test, world, random, chunkX, chunkZ, 10, 20, 25);
			break;
			
		case DimensionMagic.MAGIC_ID:
			
			break;
		}
	}
	
	private void runGenerator(WorldGenerator gen, World world, Random rand, int chunkX, int chunkZ, int chance, int minHeight, int maxHeight)
	{
		if(minHeight > maxHeight || minHeight < 0 || maxHeight > 256) throw new IllegalArgumentException("Ore generated out of bounds");

		int heightDiff = maxHeight - minHeight + 1;
		for(int i = 0; i < chance; i++)
		{
			int x = chunkX * 16 + rand.nextInt(8);
			int y = minHeight + rand.nextInt(heightDiff);
			int z = chunkZ * 16 + rand.nextInt(8);
			if(!world.isRemote)
			gen.generate(world, rand, new BlockPos(x, SeccultStructure.getGroundFromAbove(world, x, z),z));
		}
	}


}
