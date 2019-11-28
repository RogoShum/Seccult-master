package testmod.seccult.world.gen.plant;

import java.util.Random;

import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.state.IBlockState;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;
import testmod.seccult.Seccult;
import testmod.seccult.blocks.Leaf;
import testmod.seccult.blocks.Log;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.util.handlers.TreeHandler.EnumType;

public class WorldGenSeccultTreeCOPY_2 extends WorldGenHugeTrees{
	
	public WorldGenSeccultTreeCOPY_2(boolean notify, EnumType type) {
		super(notify, 7, 10, ModBlocks.LOGS.getDefaultState().withProperty(Log.VARIANT, type), ModBlocks.LEAVES.getDefaultState().withProperty(Leaf.VARIANT, type));
	}

    void generateLanternFruit(World world, BlockPos pos, Random rand)
    {
    	switch(rand.nextInt(5))
    	{
    	case 0:
    		if(world.isAirBlock(pos.north()))
    		this.setBlockAndNotifyAdequately(world, pos.north(), ModBlocks.LANTERN_FRUIT.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH).withProperty(BlockCocoa.AGE, Integer.valueOf(0)));
    		break;
    	case 1:
    		if(world.isAirBlock(pos.south()))
    		this.setBlockAndNotifyAdequately(world, pos.south(), ModBlocks.LANTERN_FRUIT.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.NORTH).withProperty(BlockCocoa.AGE, Integer.valueOf(0)));
    		break;
    	case 2:
    		if(world.isAirBlock(pos.east()))
    		this.setBlockAndNotifyAdequately(world, pos.east(), ModBlocks.LANTERN_FRUIT.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.WEST).withProperty(BlockCocoa.AGE, Integer.valueOf(0)));
    		break;
    	case 3:
    		if(world.isAirBlock(pos.west()))
    		this.setBlockAndNotifyAdequately(world, pos.west(), ModBlocks.LANTERN_FRUIT.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.EAST).withProperty(BlockCocoa.AGE, Integer.valueOf(0)));
    		break;
    	case 4:
    		int t = rand.nextInt(5) + 1;
    		for(int i = 1; i < t; i++)
    		if(world.isAirBlock(pos.down(i)))
    		this.setBlockAndNotifyAdequately(world, pos.down(i), ModBlocks.LANTERN_VINE.getDefaultState());
    		break;
    	}
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        boolean loaded = worldIn.isBlockLoaded(position.add(16, 0, 0));
        boolean loaded2 = worldIn.isBlockLoaded(position.add(0, 0, 16));
        boolean loaded4 = worldIn.isBlockLoaded(position.add(16, 0, 16));

        boolean loaded1 = worldIn.isBlockLoaded(position.add(-16, 0, 0));
        boolean loaded3 = worldIn.isBlockLoaded(position.add(0, 0, -16));
        boolean loaded5 = worldIn.isBlockLoaded(position.add(-16, 0, -16));
        
        if(!loaded || !loaded1 || !loaded2 || !loaded3 || !loaded4 || !loaded5)
        	return false;
        
        int i = this.getHeight(rand);

        if (!this.ensureGrowable(worldIn, rand, position, i))
        {
            return false;
        }
        else
        {
            this.createCrown(worldIn, position.up(i), 1);

            for (int j = position.getY() + i - 2 - rand.nextInt(4); j > position.getY() + i / 2; j -= 2 + rand.nextInt(4))
            {
                float f = rand.nextFloat() * ((float)Math.PI * 2F);
                int k = position.getX() + (int)(0.5F + MathHelper.cos(f) * 4.0F);
                int l = position.getZ() + (int)(0.5F + MathHelper.sin(f) * 4.0F);

                for (int i1 = 0; i1 < 5; ++i1)
                {
                    k = position.getX() + (int)(1.5F + MathHelper.cos(f) * (float)i1);
                    l = position.getZ() + (int)(1.5F + MathHelper.sin(f) * (float)i1);
                    this.setBlockAndNotifyAdequately(worldIn, new BlockPos(k, j - 3 + i1 / 2, l), this.woodMetadata);
                }

                int j2 = 1 + rand.nextInt(2);
                int j1 = j;

                for (int k1 = j - j2; k1 <= j1; ++k1)
                {
                    int l1 = k1 - j1;
                    this.growLeavesLayer(worldIn, new BlockPos(k, k1, l), 1 - l1);
                }
            }

            for (int i2 = 0; i2 < i; ++i2)
            {
                BlockPos blockpos = position.up(i2);

                if (this.isAirLeaves(worldIn,blockpos))
                {
                    this.setBlockAndNotifyAdequately(worldIn, blockpos, this.woodMetadata);

                    if (i2 > 0 && rand.nextInt(10) == 0)
                    {
                    	generateLanternFruit(worldIn, blockpos, rand);
                    }
                }

                /*if (i2 < i - 1)
                {
                    BlockPos blockpos1 = blockpos.east();

                    if (this.isAirLeaves(worldIn,blockpos1))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos1, this.woodMetadata);

                        if (i2 > 0)
                        {
                            this.placeVine(worldIn, rand, blockpos1.east(), BlockVine.WEST);
                            this.placeVine(worldIn, rand, blockpos1.north(), BlockVine.SOUTH);
                        }
                    }

                    BlockPos blockpos2 = blockpos.south().east();

                    if (this.isAirLeaves(worldIn,blockpos2))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos2, this.woodMetadata);

                        if (i2 > 0)
                        {
                            this.placeVine(worldIn, rand, blockpos2.east(), BlockVine.WEST);
                            this.placeVine(worldIn, rand, blockpos2.south(), BlockVine.NORTH);
                        }
                    }

                    BlockPos blockpos3 = blockpos.south();

                    if (this.isAirLeaves(worldIn,blockpos3))
                    {
                        this.setBlockAndNotifyAdequately(worldIn, blockpos3, this.woodMetadata);

                        if (i2 > 0)
                        {
                            this.placeVine(worldIn, rand, blockpos3.west(), BlockVine.EAST);
                            this.placeVine(worldIn, rand, blockpos3.south(), BlockVine.NORTH);
                        }
                    }
                    
                }*/
            }

            return true;
        }
    }
    
    @Override
    protected void growLeavesLayer(World worldIn, BlockPos layerCenter, int width) {
        int i = width * width;

        for (int j = -width; j <= width; ++j)
        {
            for (int k = -width; k <= width; ++k)
            {
                if (j * j + k * k <= i)
                {
                    BlockPos blockpos = layerCenter.add(j, 0, k);
                    IBlockState state = worldIn.getBlockState(blockpos);

                    if (state.getBlock().isAir(state, worldIn, blockpos) || state.getBlock().isLeaves(state, worldIn, blockpos) || state.getBlock() == ModBlocks.LANTERN_VINE)
                    {
                    	if(Seccult.rand.nextInt(20) == 0)
                    		putLantern(worldIn, blockpos);
	            		this.setBlockAndNotifyAdequately(worldIn, blockpos, this.leavesMetadata);
                    }
                }
            }
        }
    }
    
    private void putLantern(World worldIn, BlockPos blockpos)
    {
		int ran = Seccult.rand.nextInt(3)+1;
		for(int z = 0; z < ran; ++z)
		this.setBlockAndNotifyAdequately(worldIn, blockpos.add(0, -z, 0), ModBlocks.LANTERN_VINE.getDefaultState());
    }
    
    private void createCrown(World worldIn, BlockPos p_175930_2_, int wid)
    {
        int i = 2;

        for (int j = -2; j <= 0; ++j)
        {
            this.growLeavesLayerStrict(worldIn, p_175930_2_.up(j), wid + 1 - j);
        }
    }
    
    protected void growLeavesLayerStrict(World worldIn, BlockPos layerCenter, int width)
    {
        int i = width * width;

        for (int j = -width; j <= width + 1; ++j)
        {
            for (int k = -width; k <= width + 1; ++k)
            {
                int l = j - 1;
                int i1 = k - 1;

                if (j * j + k * k <= i || l * l + i1 * i1 <= i || j * j + i1 * i1 <= i || l * l + k * k <= i)
                {
                    BlockPos blockpos = layerCenter.add(j, 0, k);
                    IBlockState state = worldIn.getBlockState(blockpos);

                    if (state.getBlock().isAir(state, worldIn, blockpos) || state.getBlock().isLeaves(state, worldIn, blockpos) || state.getBlock() == ModBlocks.LANTERN_VINE)
                    {
                    	if(Seccult.rand.nextInt(20) == 0)
                    		putLantern(worldIn, blockpos);
	            		this.setBlockAndNotifyAdequately(worldIn, blockpos, this.leavesMetadata);
                    }
                }
            }
        }
    }
    
    //Helper macro
    private boolean isAirLeaves(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos);
    }
}