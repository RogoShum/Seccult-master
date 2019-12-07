package testmod.seccult.world.gen.biome;

import java.util.Random;

import net.minecraft.block.BlockFlower;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeEndDecorator;
import net.minecraft.world.biome.BiomeVoidDecorator;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.entity.livings.flying.EntityBird;
import testmod.seccult.entity.livings.insect.EntityButterfly;
import testmod.seccult.entity.livings.water.EntityFish;
import testmod.seccult.init.ModBlocks;

public class BiomeManaSpirit extends Biome {
    private final BiomeManaSpirit.Type type;

    public BiomeManaSpirit(BiomeManaSpirit.Type typeIn, Biome.BiomeProperties properties)
    {
        super(properties);
        this.type = typeIn;
        this.spawnableCreatureList.clear();
        
        this.decorator = new BiomeDecorator()
        		{
        			@Override
        			protected void genDecorations(Biome biomeIn, World worldIn, Random random) {

        			}
        		};
        
        this.fillerBlock = ModBlocks.Unkonw.getDefaultState();
        this.topBlock =ModBlocks.Unkonw.getDefaultState();
    }

    @Override
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
    	generateMushRoomTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
    
    public final void generateMushRoomTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        int l = x & 15;
        int i1 = z & 15;

        if(this.type == Type.SKYTIM)
        {
            for (int j1 = 255; j1 >= 0; --j1)
            {
                    IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);

                    if (iblockstate2.getBlock() == Blocks.STONE)
                    {
                        chunkPrimerIn.setBlockState(i1, j1, l, Blocks.AIR.getDefaultState());
                    }
            }
        }
        else
        {
            for (int j1 = 255; j1 >= 0; --j1)
            {
                if (j1 <= rand.nextInt(5))
                {
                    chunkPrimerIn.setBlockState(i1, j1, l, BEDROCK);
                }
                else
                {
                    IBlockState iblockstate2 = chunkPrimerIn.getBlockState(i1, j1, l);

                    if (iblockstate2.getBlock() == Blocks.STONE)
                    {
                        chunkPrimerIn.setBlockState(i1, j1, l, ModBlocks.Unkonw.getDefaultState());
                    }
                }
            }
        }
    }
    
    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random rand) {
    	return null;
    }

    @Override
    public void plantFlower(World world, Random rand, BlockPos pos) {
    	
    }

    @Override
    public int getWaterColorMultiplier() {
    	return 10092512;
    }
    
    public Class <? extends Biome > getBiomeClass()
    {
        return BiomeManaSpirit.class;
    }

    @Override
    public int getSkyColorByTemp(float currentTemperature) {
    	return 10092512;
    }
    
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos)
    {
        //int i = super.getGrassColorAtPos(pos);
        //return (i & 14221165) + 2634762 >> 1;
            return 682628;
    }

    public static enum Type
    {
        NORMAL,
        SKYTIM,
        SUMMEREST;
    }
}
