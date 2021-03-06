package testmod.seccult.world.gen.biome;

import java.util.Random;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.passive.EntityMooshroom;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.biome.Biome;
import net.minecraft.world.biome.BiomeDecorator;
import net.minecraft.world.chunk.ChunkPrimer;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBush;
import net.minecraft.world.gen.feature.WorldGenDeadBush;
import net.minecraft.world.gen.feature.WorldGenLiquids;
import net.minecraft.world.gen.feature.WorldGenPumpkin;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.entity.livings.EntityCarne;
import testmod.seccult.entity.livings.EntityChangeling;
import testmod.seccult.entity.livings.flying.EntityBird;
import testmod.seccult.entity.livings.water.EntityFish;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.world.gen.WorldGenSeccultMushroom;

public class BiomeManaMushroom extends Biome{
	private final BiomeManaMushroom.Type type;
	
    public BiomeManaMushroom( Type type, Biome.BiomeProperties properties)
    {
        super(properties);
        this.type = type;
        this.decorator = new BiomeDecorator() {
        	@Override
        	protected void genDecorations(Biome biomeIn, World worldIn, Random random) {
        		 net.minecraft.util.math.ChunkPos forgeChunkPos = new net.minecraft.util.math.ChunkPos(chunkPos); // actual ChunkPos instead of BlockPos, used for events
        	        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Pre(worldIn, random, forgeChunkPos));
        	        this.generateOres(worldIn, random);

        	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SAND))
        	        for (int i = 0; i < this.sandPatchesPerChunk; ++i)
        	        {
        	            int j = random.nextInt(16) + 8;
        	            int k = random.nextInt(16) + 8;
        	            this.sandGen.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(j, 0, k)));
        	        }

        	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CLAY))
        	        for (int i1 = 0; i1 < this.clayPerChunk; ++i1)
        	        {
        	            int l1 = random.nextInt(16) + 8;
        	            int i6 = random.nextInt(16) + 8;
        	            this.clayGen.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(l1, 0, i6)));
        	        }

        	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SAND_PASS2))
        	        for (int j1 = 0; j1 < this.gravelPatchesPerChunk; ++j1)
        	        {
        	            int i2 = random.nextInt(16) + 8;
        	            int j6 = random.nextInt(16) + 8;
        	            this.gravelGen.generate(worldIn, random, worldIn.getTopSolidOrLiquidBlock(this.chunkPos.add(i2, 0, j6)));
        	        }

        	        int k1 = this.treesPerChunk;

        	        if (random.nextFloat() < this.extraTreeChance)
        	        {
        	            ++k1;
        	        }

        	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.TREE))
        	        for (int j2 = 0; j2 < k1; ++j2)
        	        {
        	            int k6 = random.nextInt(16) + 8;
        	            int l = random.nextInt(16) + 8;
        	            WorldGenAbstractTree worldgenabstracttree = biomeIn.getRandomTreeFeature(random);
        	            worldgenabstracttree.setDecorationDefaults();
        	            BlockPos blockpos = worldIn.getHeight(this.chunkPos.add(k6, 0, l));

        	            if (worldgenabstracttree.generate(worldIn, random, blockpos))
        	            {
        	                worldgenabstracttree.generateSaplings(worldIn, random, blockpos);
        	            }
        	        }

        	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.BIG_SHROOM))
        	        for (int k2 = 0; k2 < this.bigMushroomsPerChunk; ++k2)
        	        {
        	            int l6 = random.nextInt(16) + 8;
        	            int k10 = random.nextInt(16) + 8;
        	            new WorldGenSeccultMushroom().generate(worldIn, random, worldIn.getHeight(this.chunkPos.add(l6, 0, k10)));
        	        }

        	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.GRASS))
        	        for (int i3 = 0; i3 < this.grassPerChunk; ++i3)
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

        	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.DEAD_BUSH))
        	        for (int j3 = 0; j3 < this.deadBushPerChunk; ++j3)
        	        {
        	            int k7 = random.nextInt(16) + 8;
        	            int j11 = random.nextInt(16) + 8;
        	            int l14 = worldIn.getHeight(this.chunkPos.add(k7, 0, j11)).getY() * 2;

        	            if (l14 > 0)
        	            {
        	                int i18 = random.nextInt(l14);
        	                (new WorldGenDeadBush()).generate(worldIn, random, this.chunkPos.add(k7, i18, j11));
        	            }
        	        }

        	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LILYPAD))
        	        for (int k3 = 0; k3 < this.waterlilyPerChunk; ++k3)
        	        {
        	            int l7 = random.nextInt(16) + 8;
        	            int k11 = random.nextInt(16) + 8;
        	            int i15 = worldIn.getHeight(this.chunkPos.add(l7, 0, k11)).getY() * 2;

        	            if (i15 > 0)
        	            {
        	                int j18 = random.nextInt(i15);
        	                BlockPos blockpos4;
        	                BlockPos blockpos7;

        	                for (blockpos4 = this.chunkPos.add(l7, j18, k11); blockpos4.getY() > 0; blockpos4 = blockpos7)
        	                {
        	                    blockpos7 = blockpos4.down();

        	                    if (!worldIn.isAirBlock(blockpos7))
        	                    {
        	                        break;
        	                    }
        	                }

        	                this.waterlilyGen.generate(worldIn, random, blockpos4);
        	            }
        	        }

        	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.SHROOM))
        	        {
        	        for (int l3 = 0; l3 < this.mushroomsPerChunk; ++l3)
        	        {
        	            if (random.nextInt(4) == 0)
        	            {
        	                int i8 = random.nextInt(16) + 8;
        	                int l11 = random.nextInt(16) + 8;
        	                BlockPos blockpos2 = worldIn.getHeight(this.chunkPos.add(i8, 0, l11));
        	                this.mushroomBrownGen.generate(worldIn, random, blockpos2);
        	            }

        	            if (random.nextInt(8) == 0)
        	            {
        	                int j8 = random.nextInt(16) + 8;
        	                int i12 = random.nextInt(16) + 8;
        	                int j15 = worldIn.getHeight(this.chunkPos.add(j8, 0, i12)).getY() * 2;

        	                if (j15 > 0)
        	                {
        	                    int k18 = random.nextInt(j15);
        	                    BlockPos blockpos5 = this.chunkPos.add(j8, k18, i12);
        	                    this.mushroomRedGen.generate(worldIn, random, blockpos5);
        	                }
        	            }
        	        }

        	        if (random.nextInt(4) == 0)
        	        {
        	            int i4 = random.nextInt(16) + 8;
        	            int k8 = random.nextInt(16) + 8;
        	            int j12 = worldIn.getHeight(this.chunkPos.add(i4, 0, k8)).getY() * 2;

        	            if (j12 > 0)
        	            {
        	                int k15 = random.nextInt(j12);
        	                this.mushroomBrownGen.generate(worldIn, random, this.chunkPos.add(i4, k15, k8));
        	            }
        	        }

        	        if (random.nextInt(8) == 0)
        	        {
        	            int j4 = random.nextInt(16) + 8;
        	            int l8 = random.nextInt(16) + 8;
        	            int k12 = worldIn.getHeight(this.chunkPos.add(j4, 0, l8)).getY() * 2;

        	            if (k12 > 0)
        	            {
        	                int l15 = random.nextInt(k12);
        	                this.mushroomRedGen.generate(worldIn, random, this.chunkPos.add(j4, l15, l8));
        	            }
        	        }
        	        } // End of Mushroom generation
        	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.REED))
        	        {
        	        for (int k4 = 0; k4 < this.reedsPerChunk; ++k4)
        	        {
        	            int i9 = random.nextInt(16) + 8;
        	            int l12 = random.nextInt(16) + 8;
        	            int i16 = worldIn.getHeight(this.chunkPos.add(i9, 0, l12)).getY() * 2;

        	            if (i16 > 0)
        	            {
        	                int l18 = random.nextInt(i16);
        	                this.reedGen.generate(worldIn, random, this.chunkPos.add(i9, l18, l12));
        	            }
        	        }

        	        for (int l4 = 0; l4 < 10; ++l4)
        	        {
        	            int j9 = random.nextInt(16) + 8;
        	            int i13 = random.nextInt(16) + 8;
        	            int j16 = worldIn.getHeight(this.chunkPos.add(j9, 0, i13)).getY() * 2;

        	            if (j16 > 0)
        	            {
        	                int i19 = random.nextInt(j16);
        	                this.reedGen.generate(worldIn, random, this.chunkPos.add(j9, i19, i13));
        	            }
        	        }
        	        } // End of Reed generation
        	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.PUMPKIN))
        	        if (random.nextInt(32) == 0)
        	        {
        	            int i5 = random.nextInt(16) + 8;
        	            int k9 = random.nextInt(16) + 8;
        	            int j13 = worldIn.getHeight(this.chunkPos.add(i5, 0, k9)).getY() * 2;

        	            if (j13 > 0)
        	            {
        	                int k16 = random.nextInt(j13);
        	                (new WorldGenPumpkin()).generate(worldIn, random, this.chunkPos.add(i5, k16, k9));
        	            }
        	        }

        	        if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CACTUS))
        	        for (int j5 = 0; j5 < this.cactiPerChunk; ++j5)
        	        {
        	            int l9 = random.nextInt(16) + 8;
        	            int k13 = random.nextInt(16) + 8;
        	            int l16 = worldIn.getHeight(this.chunkPos.add(l9, 0, k13)).getY() * 2;

        	            if (l16 > 0)
        	            {
        	                int j19 = random.nextInt(l16);
        	                this.cactusGen.generate(worldIn, random, this.chunkPos.add(l9, j19, k13));
        	            }
        	        }

        	        if(grassPerChunk == -12450)
    	            {
        	        	if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.CUSTOM))
        	        	{
        	                int i10 = random.nextInt(16) + 8;
        	                int l13 = random.nextInt(16) + 8;
        	                int i17 = random.nextInt(60) + 20;

        	                int k19 = random.nextInt(i17);
        	                BlockPos blockpos6 = this.chunkPos.add(i10, k19, l13);
        	                
        	                worldIn.setBlockState(blockpos6, ModBlocks.Mush_Gen.getDefaultState());
        	        	}
    	            }
        
        	        
        	        if (this.generateFalls)
        	        {
        	            if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LAKE_WATER))
        	            for (int k5 = 0; k5 < 50; ++k5)
        	            {
        	                int i10 = random.nextInt(16) + 8;
        	                int l13 = random.nextInt(16) + 8;
        	                int i17 = random.nextInt(248) + 8;

        	                if (i17 > 0)
        	                {
        	                    int k19 = random.nextInt(i17);
        	                    BlockPos blockpos6 = this.chunkPos.add(i10, k19, l13);
        	                    (new WorldGenLiquids(Blocks.FLOWING_WATER)).generate(worldIn, random, blockpos6);
        	                }
        	            }

        	            if(net.minecraftforge.event.terraingen.TerrainGen.decorate(worldIn, random, forgeChunkPos, net.minecraftforge.event.terraingen.DecorateBiomeEvent.Decorate.EventType.LAKE_LAVA))
        	            for (int l5 = 0; l5 < 20; ++l5)
        	            {
        	                int j10 = random.nextInt(16) + 8;
        	                int i14 = random.nextInt(16) + 8;
        	                int j17 = random.nextInt(random.nextInt(random.nextInt(240) + 8) + 8);
        	                BlockPos blockpos3 = this.chunkPos.add(j10, j17, i14);
        	                (new WorldGenLiquids(Blocks.FLOWING_LAVA)).generate(worldIn, random, blockpos3);
        	            }
        	        }
        	        net.minecraftforge.common.MinecraftForge.EVENT_BUS.post(new net.minecraftforge.event.terraingen.DecorateBiomeEvent.Post(worldIn, random, forgeChunkPos));
        	}
        };
        this.decorator.mushroomRedGen = new WorldGenBush(ModBlocks.LittleRedMush);
        this.decorator.mushroomBrownGen = new WorldGenBush(ModBlocks.LittleMush);
        this.decorator.treesPerChunk = -100;
        this.decorator.flowersPerChunk = -100;
        this.decorator.grassPerChunk = -100;
        this.decorator.mushroomsPerChunk = 1;
        this.decorator.bigMushroomsPerChunk = 2;
        if(this.type == Type.CAVE)
        {
        	this.decorator.mushroomsPerChunk = 200;
        	this.decorator.grassPerChunk = -12450;
        	this.decorator.bigMushroomsPerChunk = 0;
        	this.spawnableCaveCreatureList.clear();
        }
        this.topBlock =	ModBlocks.Hypha.getDefaultState();
        this.fillerBlock = ModBlocks.Hypha.getDefaultState();
        this.spawnableMonsterList.clear();
        this.spawnableCreatureList.clear();
        this.spawnableWaterCreatureList.clear();
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityMooshroom.class, 8, 4, 8));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityChangeling.class, 4, 4, 8));
        this.spawnableCreatureList.add(new Biome.SpawnListEntry(EntityCarne.class, 1, 1, 1));
    }
    
    @Override
    public int getFoliageColorAtPos(BlockPos pos) {
    	return 524543;
    }
    
    @Override
    public int getSkyColorByTemp(float currentTemperature) {
    	return 524543;
    }
    
    @Override
    public void genTerrainBlocks(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal) {
    	this.generateMushRoomTerrain(worldIn, rand, chunkPrimerIn, x, z, noiseVal);
    }
    
    public final void generateMushRoomTerrain(World worldIn, Random rand, ChunkPrimer chunkPrimerIn, int x, int z, double noiseVal)
    {
        int l = x & 15;
        int i1 = z & 15;

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
                    chunkPrimerIn.setBlockState(i1, j1, l, ModBlocks.Hypha.getDefaultState());
                }
            }
        }
    }
    
    public Class <? extends Biome > getBiomeClass()
    {
        return BiomeManaMushroom.class;
    }
    
    @Override
    public int getWaterColorMultiplier() {
    	return 524543;
    }
    
    @SideOnly(Side.CLIENT)
    public int getGrassColorAtPos(BlockPos pos)
    {
            return 524543;
    }
    
    public static enum Type
    {
        NORMAL,
        CAVE,
        ISLAND;
    }
}
