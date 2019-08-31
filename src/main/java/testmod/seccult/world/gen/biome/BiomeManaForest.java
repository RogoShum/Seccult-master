package testmod.seccult.world.gen.biome;

import java.util.Random;

import net.minecraft.block.BlockDoublePlant;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityOcelot;
import net.minecraft.entity.passive.EntityRabbit;
import net.minecraft.entity.passive.EntityWolf;
import net.minecraft.init.Biomes;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.biome.BiomeForest;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenerator;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.blocks.BlockPlant;
import testmod.seccult.entity.livings.flying.EntityBird;
import testmod.seccult.entity.livings.insect.EntityButterfly;
import testmod.seccult.entity.livings.water.EntityFish;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.util.handlers.PlantsHandler;
import testmod.seccult.util.handlers.TreeHandler;
import testmod.seccult.world.gen.WorldGenSeccultMushroom;
import testmod.seccult.world.gen.plant.WorldGenSeccultTree;

public class BiomeManaForest extends Biome {
    protected static final WorldGenSeccultTree SUPER_BIRCH_TREE = new WorldGenSeccultTree(true, TreeHandler.EnumType.MANA_TREE_MAGIC);
    protected static final WorldGenSeccultTree BIRCH_TREE = new WorldGenSeccultTree(true, TreeHandler.EnumType.MANA_TREE_WHITE);
    protected static final WorldGenSeccultTree ROOF_TREE = new WorldGenSeccultTree(true, TreeHandler.EnumType.MANA_TREE_BLUE);
    
	private int manaFlowersPerChunk = 2;
	private int manaGrassPerChunk = 1;
    private final BiomeManaForest.Type type;

    public BiomeManaForest(BiomeManaForest.Type typeIn, Biome.BiomeProperties properties)
    {
        super(properties);
        this.type = typeIn;
        this.spawnableCreatureList.clear();
        this.decorator = new BiomeDecorator()
        {
            public WorldGenManaFlowers flowerGen = new WorldGenManaFlowers(ModBlocks.FLOWER, PlantsHandler.EnumType.Grass);
        	
        	@Override
        	protected void genDecorations(Biome biomeIn, World worldIn, Random random) {
        	super.genDecorations(biomeIn, worldIn, random);
        	net.minecraft.util.math.ChunkPos forgeChunkPos = new net.minecraft.util.math.ChunkPos(chunkPos);
            if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.FLOWERS))
                for (int l2 = 0; l2 < manaFlowersPerChunk; ++l2)
                {
                    int i7 = random.nextInt(16) + 8;
                    int l10 = random.nextInt(16) + 8;
                    int j14 = worldIn.getHeight(this.chunkPos.add(i7, 0, l10)).getY() + 32;

                    if (j14 > 0)
                    {
                        int k17 = random.nextInt(j14);
                        BlockPos blockpos1 = this.chunkPos.add(i7, k17, l10);
                        PlantsHandler.EnumType blockflower$enumflowertype = pickRandomManaFlower(random, blockpos1);

                        this.flowerGen.setGeneratedBlock(ModBlocks.FLOWER, blockflower$enumflowertype);
                        this.flowerGen.generate(worldIn, random, blockpos1);
                    }
                }
            
            if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS))
                for (int i3 = 0; i3 < manaGrassPerChunk; ++i3)
                {
                    int j7 = random.nextInt(16) + 8;
                    int i11 = random.nextInt(16) + 8;
                    int k14 = worldIn.getHeight(this.chunkPos.add(j7, 0, i11)).getY() * 2;

                    if (k14 > 0)
                    {
                        int l17 = random.nextInt(k14);
                        biomeIn.getRandomWorldGenForGrass(random).generate(worldIn, random, this.chunkPos.add(j7, l17, i11));
                    }
                }
        	}

        };
        this.decorator.treesPerChunk = 1;
        this.manaFlowersPerChunk = 2;
        this.decorator.flowersPerChunk = -999;
        if (this.type == BiomeManaForest.Type.FLOWER)
        {
        	this.manaFlowersPerChunk = 10;
            this.decorator.treesPerChunk = -999;
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityRabbit.class, 4, 2, 3));
        	this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityButterfly.class, 50, 4, 4));
        }

        if (this.type == BiomeManaForest.Type.NORMAL)
        {
            this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityWolf.class, 5, 4, 4));
        	this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityBird.class, 40, 4, 4));
        	this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityButterfly.class, 50, 4, 4));
        	this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(EntityFish.class, 50, 4, 4));
        }

        if (this.type == BiomeManaForest.Type.ROOFED)
        {
        	this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityBird.class, 40, 4, 4));
        	this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityButterfly.class, 50, 4, 4));
        	this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(EntityFish.class, 50, 4, 4));
        	this.manaFlowersPerChunk = 3;
        }

        if (this.type == BiomeManaForest.Type.Magical)
        {
        	this.manaFlowersPerChunk = 4;
        	this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityOcelot.class, 5, 4, 4));
        	this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityBird.class, 40, 4, 4));
        	this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityButterfly.class, 50, 4, 4));
        	this.spawnableWaterCreatureList.add(new Biome.SpawnListEntry(EntityFish.class, 50, 4, 4));
        }
        
        if (this.type == BiomeManaForest.Type.FLOWER) //Needs to be done here so we have access to this.type
        {

            this.flowers.clear();
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

    @Override
    public WorldGenerator getRandomWorldGenForGrass(Random rand) {
    	return new WorldGenManaGrass(PlantsHandler.EnumType.Grass);
    }
    
    public PlantsHandler.EnumType pickRandomManaFlower(Random rand, BlockPos pos)
    {
    	int i = rand.nextInt(5);
    	switch(i)
    	{
    	case 0:
    		return rand.nextInt(3) > 0 ? PlantsHandler.EnumType.C : PlantsHandler.EnumType.Little;
    	case 1:
    		return rand.nextInt(3) > 0 ? PlantsHandler.EnumType.M : PlantsHandler.EnumType.Little;
    	case 2:
    		return rand.nextInt(3) > 0 ? PlantsHandler.EnumType.Y : PlantsHandler.EnumType.Little;
    	case 3:
    		return rand.nextInt(3) > 0 ? PlantsHandler.EnumType.C : PlantsHandler.EnumType.Catch_Soul;
    	case 4:
    		return rand.nextInt(3) > 0 ? PlantsHandler.EnumType.M : PlantsHandler.EnumType.Catch_Soul;
    	}
    	
    	return rand.nextInt(3) > 0 ? PlantsHandler.EnumType.C : PlantsHandler.EnumType.Catch_Soul;
    }

    public void decorate(World worldIn, Random rand, BlockPos pos)
    {
        if (this.type == BiomeManaForest.Type.ROOFED)
        {
            this.addMushrooms(worldIn, rand, pos);
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

    @Override
    public void plantFlower(World world, Random rand, BlockPos pos) {
    	
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
    
    public class WorldGenManaFlowers extends WorldGenerator
    {
        private BlockPlant flower;
        private IBlockState state;

        public WorldGenManaFlowers(BlockPlant flowerIn, PlantsHandler.EnumType type)
        {
            this.setGeneratedBlock(flowerIn, type);
        }

        public void setGeneratedBlock(BlockPlant flowerIn, PlantsHandler.EnumType typeIn)
        {
            this.flower = flowerIn;
            this.state = flowerIn.getDefaultState().withProperty(BlockPlant.VARIANT, typeIn);
        }

        public boolean generate(World worldIn, Random rand, BlockPos position)
        {
            for (int i = 0; i < 64; ++i)
            {
                BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

                if (worldIn.isAirBlock(blockpos) && (!worldIn.provider.isNether() || blockpos.getY() < 255) && this.flower.canBlockStay(worldIn, blockpos, this.state))
                {
                    worldIn.setBlockState(blockpos, this.state, 2);
                }
            }

            return true;
        }
    }
    
    public class WorldGenManaGrass extends WorldGenerator
    {
        private final IBlockState state;

        public WorldGenManaGrass(PlantsHandler.EnumType type)
        {
        	this.state = ModBlocks.FLOWER.getDefaultState().withProperty(BlockPlant.VARIANT, type);
        }

        public boolean generate(World worldIn, Random rand, BlockPos position)
        {
            for (IBlockState iblockstate = worldIn.getBlockState(position); (iblockstate.getBlock().isAir(iblockstate, worldIn, position) || iblockstate.getBlock().isLeaves(iblockstate, worldIn, position)) && position.getY() > 0; iblockstate = worldIn.getBlockState(position))
            {
                position = position.down();
            }

            for (int i = 0; i < 128; ++i)
            {
                BlockPos blockpos = position.add(rand.nextInt(8) - rand.nextInt(8), rand.nextInt(4) - rand.nextInt(4), rand.nextInt(8) - rand.nextInt(8));

                if (worldIn.isAirBlock(blockpos) && ModBlocks.FLOWER.canBlockStay(worldIn, blockpos, this.state))
                {
                    worldIn.setBlockState(blockpos, this.state, 2);
                }
            }

            return true;
        }
    }
}
