package testmod.seccult.world.gen.biome;

import java.util.Random;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.BlockFlower;
import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockOldLeaf;
import net.minecraft.block.BlockOldLog;
import net.minecraft.block.BlockPlanks;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigMushroom;
import net.minecraft.world.gen.feature.WorldGenMegaJungle;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.util.handlers.TreeHandler;
import testmod.seccult.world.gen.WorldGenSeccultMushroom;
import testmod.seccult.world.gen.plant.WorldGenSeccultTree;

public class BiomeManaForest extends Biome {
    protected static final WorldGenSeccultTree SUPER_BIRCH_TREE = new WorldGenSeccultTree(true, TreeHandler.EnumType.MANA_TREE_MAGIC);
    protected static final WorldGenSeccultTree BIRCH_TREE = new WorldGenSeccultTree(true, TreeHandler.EnumType.MANA_TREE_WHITE);
    protected static final WorldGenSeccultTree ROOF_TREE = new WorldGenSeccultTree(true, TreeHandler.EnumType.MANA_TREE_BLUE);
    private final BiomeManaForest.Type type;

    public BiomeManaForest(BiomeManaForest.Type typeIn, Biome.BiomeProperties properties)
    {
        super(properties);
        this.type = typeIn;
        this.decorator.treesPerChunk = 9;
        this.decorator.grassPerChunk = 2;

        if (this.type == BiomeManaForest.Type.FLOWER)
        {
            this.decorator.treesPerChunk = -999;
            this.decorator.flowersPerChunk = 100;
            this.decorator.grassPerChunk = 1;
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityRabbit.class, 4, 2, 3));
        }

        if (this.type == BiomeManaForest.Type.NORMAL)
        {
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityWolf.class, 5, 4, 4));
        }

        if (this.type == BiomeManaForest.Type.ROOFED)
        {
            this.decorator.treesPerChunk = 4;
        }

        if (this.type == BiomeManaForest.Type.FLOWER) //Needs to be done here so we have access to this.type
        {
            this.flowers.clear();
            for (BlockFlower.EnumFlowerType type : BlockFlower.EnumFlowerType.values())
            {
                if (type.getBlockType() == BlockFlower.EnumFlowerColor.YELLOW) continue;
                if (type == BlockFlower.EnumFlowerType.BLUE_ORCHID) type = BlockFlower.EnumFlowerType.POPPY;
                addFlower(net.minecraft.init.Blocks.RED_FLOWER.getDefaultState().withProperty(net.minecraft.init.Blocks.RED_FLOWER.getTypeProperty(), type), 10);
            }
        }
    }

    public WorldGenAbstractTree getRandomTreeFeature(Random rand)
    {
        if (this.type == BiomeManaForest.Type.ROOFED && rand.nextInt(3) > 0)
        {
            return ROOF_TREE;
        }
        else if (this.type == BiomeManaForest.Type.Magical && rand.nextInt(5) != 0)
        {
            return SUPER_BIRCH_TREE;
        }
        else
        {
            return BIRCH_TREE;
        }
    	//return new WorldGenMegaJungle(false, 1, 2, Blocks.LOG.getDefaultState().withProperty(BlockOldLog.VARIANT, BlockPlanks.EnumType.JUNGLE)
    			//, Blocks.LEAVES.getDefaultState().withProperty(BlockOldLeaf.VARIANT, BlockPlanks.EnumType.JUNGLE).withProperty(BlockLeaves.CHECK_DECAY, Boolean.valueOf(false)));
    }

    public BlockFlower.EnumFlowerType pickRandomFlower(Random rand, BlockPos pos)
    {
        if (this.type == BiomeManaForest.Type.FLOWER)
        {
            double d0 = MathHelper.clamp((1.0D + GRASS_COLOR_NOISE.getValue((double)pos.getX() / 48.0D, (double)pos.getZ() / 48.0D)) / 2.0D, 0.0D, 0.9999D);
            BlockFlower.EnumFlowerType blockflower$enumflowertype = BlockFlower.EnumFlowerType.values()[(int)(d0 * (double)BlockFlower.EnumFlowerType.values().length)];
            return blockflower$enumflowertype == BlockFlower.EnumFlowerType.BLUE_ORCHID ? BlockFlower.EnumFlowerType.POPPY : blockflower$enumflowertype;
        }
        else
        {
            return super.pickRandomFlower(rand, pos);
        }
    }

    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
        if (this.type == BiomeManaForest.Type.ROOFED)
        {
            this.addMushrooms(worldIn, rand, pos);
        }

        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, rand, new net.minecraft.util.math.ChunkPos(pos), net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS))
        { // no tab for patch
        int i = rand.nextInt(5) - 3;

        if (this.type == BiomeManaForest.Type.FLOWER)
        {
            i += 2;
        }

        this.addDoublePlants(worldIn, rand, pos, i);
        }
        super.decorate(worldIn, rand, pos);
    }

    public void addMushrooms(World p_185379_1_, Random p_185379_2_, BlockPos p_185379_3_)
    {

        for (int i = 0; i < 4; ++i)
        {
            for (int j = 0; j < 4; ++j)
            {
                int k = i * 4 + 1 + 8 + p_185379_2_.nextInt(3);
                int l = j * 4 + 1 + 8 + p_185379_2_.nextInt(3);
                BlockPos blockpos = p_185379_1_.getHeight(p_185379_3_.add(k, 0, l));

                if (p_185379_2_.nextInt(20) == 0 && net.minecraftforge.event.terraingen.TerrainGen.decorate(p_185379_1_, p_185379_2_, new net.minecraft.util.math.ChunkPos(p_185379_3_), blockpos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM))
                {
                    WorldGenSeccultMushroom worldgenbigmushroom = new WorldGenSeccultMushroom();
                    worldgenbigmushroom.generate(p_185379_1_, p_185379_2_, blockpos);
                }
                else if (net.minecraftforge.event.terraingen.TerrainGen.decorate(p_185379_1_, p_185379_2_, new net.minecraft.util.math.ChunkPos(p_185379_3_), blockpos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE))
                {
                    WorldGenAbstractTree worldgenabstracttree = this.getRandomTreeFeature(p_185379_2_);
                    worldgenabstracttree.setDecorationDefaults();
                    if (worldgenabstracttree.generate(p_185379_1_, p_185379_2_, blockpos))
                    {
                        worldgenabstracttree.generateSaplings(p_185379_1_, p_185379_2_, blockpos);
                    }
                }
            }
        }
    }

    public void addDoublePlants(World p_185378_1_, Random p_185378_2_, BlockPos p_185378_3_, int p_185378_4_)
    {
        for (int i = 0; i < p_185378_4_; ++i)
        {
            int j = p_185378_2_.nextInt(3);

            if (j == 0)
            {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.SYRINGA);
            }
            else if (j == 1)
            {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.ROSE);
            }
            else if (j == 2)
            {
                DOUBLE_PLANT_GENERATOR.setPlantType(BlockDoublePlant.EnumPlantType.PAEONIA);
            }

            for (int k = 0; k < 5; ++k)
            {
                int l = p_185378_2_.nextInt(16) + 8;
                int i1 = p_185378_2_.nextInt(16) + 8;
                int j1 = p_185378_2_.nextInt(p_185378_1_.getHeight(p_185378_3_.add(l, 0, i1)).getY() + 32);

                if (DOUBLE_PLANT_GENERATOR.generate(p_185378_1_, p_185378_2_, new BlockPos(p_185378_3_.getX() + l, j1, p_185378_3_.getZ() + i1)))
                {
                    break;
                }
            }
        }
    }

    @Override
    public int getWaterColorMultiplier() {
    	return 10092512;
    }
    
    public Class <? extends Biome > getBiomeClass()
    {
        return BiomeManaForest.class;
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
        FLOWER,
        Magical,
        ROOFED;
    }
}
