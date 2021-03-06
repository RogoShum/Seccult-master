package testmod.seccult.world.gen.plant;

import java.util.List;
import java.util.Random;

import com.google.common.collect.Lists;

import net.minecraft.block.Block;
import net.minecraft.block.BlockCocoa;
import net.minecraft.block.BlockHorizontal;
import net.minecraft.block.BlockLog;
import net.minecraft.block.BlockVine;
import net.minecraft.block.properties.PropertyBool;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraft.world.biome.BiomeJungle;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import net.minecraft.world.gen.feature.WorldGenBigTree;
import net.minecraft.world.gen.feature.WorldGenHugeTrees;
import testmod.seccult.blocks.Leaf;
import testmod.seccult.blocks.Log;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.util.handlers.TreeHandler;
import testmod.seccult.util.handlers.TreeHandler.EnumType;

public class WorldGenSeccultTreeCopy extends WorldGenHugeTrees{
    private Random rand;
    private World world;
    private BlockPos basePos = BlockPos.ORIGIN;
    int heightLimit;
    int height;
    double heightAttenuation = 0.618D;
    double branchSlope = 0.381D;
    double scaleWidth = 1.0D;
    double leafDensity = 1.0D;
    int trunkSize = 1;
    int heightLimitLimit = 12;
    /** Sets the distance limit for how far away the generator will populate leaves from the base leaf node. */
    int leafDistanceLimit = 3;
    List<WorldGenSeccultTreeCopy.FoliageCoordinates> foliageCoords;
    private EnumType TYPE = null;

	public WorldGenSeccultTreeCopy(boolean notify, EnumType type) {
		super(notify, 7, 12, ModBlocks.LOGS.getDefaultState().withProperty(Log.VARIANT, type), ModBlocks.LEAVES.getDefaultState().withProperty(Leaf.VARIANT, type));
		this.TYPE = type;
	}

    /**
     * Generates a list of leaf nodes for the tree, to be populated by generateLeaves.
     */
    void generateLeafNodeList()
    {
        this.height = (int)((double)this.heightLimit * this.heightAttenuation);

        if (this.height >= this.heightLimit)
        {
            this.height = this.heightLimit - 1;
        }

        int i = (int)(1.382D + Math.pow(this.leafDensity * (double)this.heightLimit / 13.0D, 2.0D));

        if (i < 1)
        {
            i = 1;
        }

        int j = this.basePos.getY() + this.height;
        int k = this.heightLimit - this.leafDistanceLimit;
        this.foliageCoords = Lists.<WorldGenSeccultTreeCopy.FoliageCoordinates>newArrayList();
        this.foliageCoords.add(new WorldGenSeccultTreeCopy.FoliageCoordinates(this.basePos.up(k), j));

        for (; k >= 0; --k)
        {
            float f = this.layerSize(k);

            if (f >= 0.0F)
            {
                for (int l = 0; l < i; ++l)
                {
                    double d0 = this.scaleWidth * (double)f * ((double)this.rand.nextFloat() + 0.328D);
                    double d1 = (double)(this.rand.nextFloat() * 2.0F) * Math.PI;
                    double d2 = d0 * Math.sin(d1) + 0.5D;
                    double d3 = d0 * Math.cos(d1) + 0.5D;
                    BlockPos blockpos = this.basePos.add(d2, (double)(k - 1), d3);
                    BlockPos blockpos1 = blockpos.up(this.leafDistanceLimit);
                    if (this.checkBlockLine(blockpos, blockpos1) == -1)
                    {
                        int i1 = this.basePos.getX() - blockpos.getX();
                        int j1 = this.basePos.getZ() - blockpos.getZ();
                        double d4 = (double)blockpos.getY() - Math.sqrt((double)(i1 * i1 + j1 * j1)) * this.branchSlope;
                        int k1 = d4 > (double)j ? j : (int)d4;
                        BlockPos blockpos2 = new BlockPos(this.basePos.getX(), k1, this.basePos.getZ());

                        if (this.checkBlockLine(blockpos2, blockpos) == -1)
                        {
                            this.foliageCoords.add(new WorldGenSeccultTreeCopy.FoliageCoordinates(blockpos, blockpos2.getY()));
                        }
                    }
                }
            }
        }
    }

    void crosSection(BlockPos pos, float p_181631_2_, IBlockState p_181631_3_)
    {
        int i = (int)((double)p_181631_2_ + 0.618D);

        for (int j = -i; j <= i; ++j)
        {
            for (int k = -i; k <= i; ++k)
            {
                if (Math.pow((double)Math.abs(j) + 0.5D, 2.0D) + Math.pow((double)Math.abs(k) + 0.5D, 2.0D) <= (double)(p_181631_2_ * p_181631_2_))
                {

                    BlockPos blockpos = pos.add(j, 0, k);
                	 if(world == null)
                		 return;
                    	IBlockState blockstate = this.world.getBlockState(blockpos);
	            	 try {
	                	 if(world == null)
	                		 return;
	            		 if (blockstate.getBlock().isAir(blockstate, world, blockpos) || blockstate.getBlock().isLeaves(blockstate, world, blockpos) || blockstate.getBlock() == ModBlocks.LANTERN_VINE)
	            		 {
	            			 this.setBlockAndNotifyAdequately(this.world, blockpos, p_181631_3_);
	            			 if(world.isAirBlock(pos.down()) && rand.nextInt(10) == 0)
	            		        this.setBlockAndNotifyAdequately(this.world, pos.west(), ModBlocks.LANTERN_VINE.getDefaultState());
	            		 }
	            	 }
	            	 catch(Exception e) {
	            		 System.out.println("World is " + world);
	            		 System.out.println("Block is " + blockstate);
	            		 System.out.println("Type is " + TYPE);
	            	 }
                }
            }
        }
    }

    /**
     * Gets the rough size of a layer of the tree.
     */
    float layerSize(int y)
    {
        if ((float)y < (float)this.heightLimit * 0.7F)
        {
            return -1.0F;
        }
        else
        {
            float f = (float)this.heightLimit / 2.0F;
            float f1 = f - (float)y;
            float f2 = MathHelper.sqrt(f * f - f1 * f1);

            if (f1 == 0.0F)
            {
                f2 = f;
            }
            else if (Math.abs(f1) >= f)
            {
                return 0.0F;
            }

            return f2 * 0.7F;
        }
    }

    float leafSize(int y)
    {
        if (y >= 0 && y < this.leafDistanceLimit)
        {
            return y != 0 && y != this.leafDistanceLimit - 1 ? 4.0F : 2.0F;
        }
        else
        {
            return -1.0F;
        }
    }

    /**
     * Generates the leaves surrounding an individual entry in the leafNodes list.
     */
    void generateLeafNode(BlockPos pos)
    {
        for (int i = 0; i < this.leafDistanceLimit; ++i)
        {
        	if(TYPE == null)
        		this.crosSection(pos.up(i), this.leafSize(i), Blocks.AIR.getDefaultState());
        	else
        		this.crosSection(pos.up(i), this.leafSize(i), ModBlocks.LEAVES.getDefaultState().withProperty(Leaf.VARIANT, TYPE));
        }
    }

    void limb(BlockPos p_175937_1_, BlockPos p_175937_2_, Block p_175937_3_)
    {
        BlockPos blockpos = p_175937_2_.add(-p_175937_1_.getX(), -p_175937_1_.getY(), -p_175937_1_.getZ());
        int i = this.getGreatestDistance(blockpos);
        float f = (float)blockpos.getX() / (float)i;
        float f1 = (float)blockpos.getY() / (float)i;
        float f2 = (float)blockpos.getZ() / (float)i;

        for (int j = 0; j <= i; ++j)
        {
            BlockPos blockpos1 = p_175937_1_.add((double)(0.5F + (float)j * f), (double)(0.5F + (float)j * f1), (double)(0.5F + (float)j * f2));
            BlockLog.EnumAxis blocklog$enumaxis = this.getLogAxis(p_175937_1_, blockpos1);
           
            if(world != null)
            { 
            if(TYPE == null)
            	this.setBlockAndNotifyAdequately(this.world, blockpos1, Blocks.AIR.getDefaultState());
        	else
        	{
        		this.setBlockAndNotifyAdequately(this.world, blockpos1, p_175937_3_.getDefaultState().withProperty(BlockLog.LOG_AXIS, blocklog$enumaxis).withProperty(Log.VARIANT, TYPE));
        		if(j > i / 3 && rand.nextInt(4) == 0)
        		{
        			generateLanternFruit(blockpos1);
        		}
        	}
            }
        }
    }

    void generateLanternFruit(BlockPos pos)
    {
    	switch(rand.nextInt(5))
    	{
    	case 0:
    		if(world.isAirBlock(pos.north()))
    		this.setBlockAndNotifyAdequately(this.world, pos.north(), ModBlocks.LANTERN_FRUIT.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.SOUTH).withProperty(BlockCocoa.AGE, Integer.valueOf(0)));
    		break;
    	case 1:
    		if(world.isAirBlock(pos.south()))
    		this.setBlockAndNotifyAdequately(this.world, pos.south(), ModBlocks.LANTERN_FRUIT.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.NORTH).withProperty(BlockCocoa.AGE, Integer.valueOf(0)));
    		break;
    	case 2:
    		if(world.isAirBlock(pos.east()))
    		this.setBlockAndNotifyAdequately(this.world, pos.east(), ModBlocks.LANTERN_FRUIT.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.WEST).withProperty(BlockCocoa.AGE, Integer.valueOf(0)));
    		break;
    	case 3:
    		if(world.isAirBlock(pos.west()))
    		this.setBlockAndNotifyAdequately(this.world, pos.west(), ModBlocks.LANTERN_FRUIT.getDefaultState().withProperty(BlockHorizontal.FACING, EnumFacing.EAST).withProperty(BlockCocoa.AGE, Integer.valueOf(0)));
    		break;
    	case 4:
    		int t = rand.nextInt(5) + 1;
    		for(int i = 1; i < t; i++)
    		if(world.isAirBlock(pos.down(i)))
    		this.setBlockAndNotifyAdequately(this.world, pos.down(i), ModBlocks.LANTERN_VINE.getDefaultState());
    		break;
    	}
    }
    
    /**
     * Returns the absolute greatest distance in the BlockPos object.
     */
    private int getGreatestDistance(BlockPos posIn)
    {
        int i = MathHelper.abs(posIn.getX());
        int j = MathHelper.abs(posIn.getY());
        int k = MathHelper.abs(posIn.getZ());

        if (k > i && k > j)
        {
            return k;
        }
        else
        {
            return j > i ? j : i;
        }
    }

    private BlockLog.EnumAxis getLogAxis(BlockPos p_175938_1_, BlockPos p_175938_2_)
    {
        BlockLog.EnumAxis blocklog$enumaxis = BlockLog.EnumAxis.Y;
        int i = Math.abs(p_175938_2_.getX() - p_175938_1_.getX());
        int j = Math.abs(p_175938_2_.getZ() - p_175938_1_.getZ());
        int k = Math.max(i, j);

        if (k > 0)
        {
            if (i == k)
            {
                blocklog$enumaxis = BlockLog.EnumAxis.X;
            }
            else if (j == k)
            {
                blocklog$enumaxis = BlockLog.EnumAxis.Z;
            }
        }

        return blocklog$enumaxis;
    }

    /**
     * Generates the leaf portion of the tree as specified by the leafNodes list.
     */
    void generateLeaves()
    {
        for (WorldGenSeccultTreeCopy.FoliageCoordinates BlueTree$foliagecoordinates : this.foliageCoords)
        {
            this.generateLeafNode(BlueTree$foliagecoordinates);
        }
    }

    /**
     * Indicates whether or not a leaf node requires additional wood to be added to preserve integrity.
     */
    boolean leafNodeNeedsBase(int p_76493_1_)
    {
        return (double)p_76493_1_ >= (double)this.heightLimit * 0.2D;
    }

    /**
     * Places the trunk for the big tree that is being generated. Able to generate double-sized trunks by changing a
     * field that is always 1 to 2.
     */
    void generateTrunk()
    {
        BlockPos blockpos = this.basePos;
        BlockPos blockpos1 = this.basePos.up(this.height);
        Block block = ModBlocks.LOGS;
        this.limb(blockpos, blockpos1, block);
        if (this.trunkSize == 2)
        {
            this.limb(blockpos.east(), blockpos1.east(), block);
            this.limb(blockpos.east().south(), blockpos1.east().south(), block);
            this.limb(blockpos.south(), blockpos1.south(), block);
        }
    }
    
    /**
     * Generates additional wood blocks to fill out the bases of different leaf nodes that would otherwise degrade.
     */
    void generateLeafNodeBases()
    {
        for (WorldGenSeccultTreeCopy.FoliageCoordinates BlueTree$foliagecoordinates : this.foliageCoords)
        {
            int i = BlueTree$foliagecoordinates.getBranchBase();
            BlockPos blockpos = new BlockPos(this.basePos.getX(), i, this.basePos.getZ());

            if (!blockpos.equals(BlueTree$foliagecoordinates) && this.leafNodeNeedsBase(i - this.basePos.getY()))
            {
                this.limb(blockpos, BlueTree$foliagecoordinates, ModBlocks.LOGS);
            }
        }
    }

    /**
     * Checks a line of blocks in the world from the first coordinate to triplet to the second, returning the distance
     * (in blocks) before a non-air, non-leaf block is encountered and/or the end is encountered.
     */
    int checkBlockLine(BlockPos posOne, BlockPos posTwo)
    {
        BlockPos blockpos = posTwo.add(-posOne.getX(), -posOne.getY(), -posOne.getZ());
        int i = this.getGreatestDistance(blockpos);
        float f = (float)blockpos.getX() / (float)i;
        float f1 = (float)blockpos.getY() / (float)i;
        float f2 = (float)blockpos.getZ() / (float)i;

        if (i == 0)
        {
            return -1;
        }
        else
        {
            for (int j = 0; j <= i; ++j)
            {
                BlockPos blockpos1 = posOne.add((double)(0.5F + (float)j * f), (double)(0.5F + (float)j * f1), (double)(0.5F + (float)j * f2));
                if(world != null)
                if (!this.isReplaceable(world, blockpos1))
                {
                    return j;
                }
            }

            return -1;
        }
    }

    public void setDecorationDefaults()
    {
        this.leafDistanceLimit = 3;
    }

    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
        int i = this.getHeight(rand);

        if (!this.ensureGrowable(worldIn, rand, position, i))
        {
            return false;
        }
        else
        {
            this.createCrown(worldIn, position.up(i), 2);

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

                    if (i2 > 0)
                    {
                        this.placeVine(worldIn, rand, blockpos.west(), BlockVine.EAST);
                        this.placeVine(worldIn, rand, blockpos.north(), BlockVine.SOUTH);
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
    
    private void createCrown(World worldIn, BlockPos p_175930_2_, int p_175930_3_)
    {
        int i = 2;

        for (int j = -2; j <= 0; ++j)
        {
            this.growLeavesLayerStrict(worldIn, p_175930_2_.up(j), p_175930_3_ + 1 - j);
        }
    }
    
    private void placeVine(World p_181632_1_, Random p_181632_2_, BlockPos p_181632_3_, PropertyBool p_181632_4_)
    {
        if (p_181632_2_.nextInt(3) > 0 && p_181632_1_.isAirBlock(p_181632_3_))
        {
            //this.setBlockAndNotifyAdequately(p_181632_1_, p_181632_3_, Blocks.VINE.getDefaultState().withProperty(p_181632_4_, Boolean.valueOf(true)));
        }
    }
    
    //Helper macro
    private boolean isAirLeaves(World world, BlockPos pos)
    {
        IBlockState state = world.getBlockState(pos);
        return state.getBlock().isAir(state, world, pos) || state.getBlock().isLeaves(state, world, pos);
    }
    
    /*public boolean generate(World worldIn, Random rand, BlockPos position)
    {
    	
        this.world = worldIn;
        if(world.isRemote)
        	return false;
        this.basePos = position;
        this.rand = new Random(rand.nextLong());
        
        boolean loaded = this.world.isBlockLoaded(position.add(16, 0, 0));
        boolean loaded2 = this.world.isBlockLoaded(position.add(0, 0, 16));
        boolean loaded4 = this.world.isBlockLoaded(position.add(16, 0, 16));

        boolean loaded1 = this.world.isBlockLoaded(position.add(-16, 0, 0));
        boolean loaded3 = this.world.isBlockLoaded(position.add(0, 0, -16));
        boolean loaded5 = this.world.isBlockLoaded(position.add(-16, 0, -16));
        
        if(!loaded || !loaded1 || !loaded2 || !loaded3 || !loaded4 || !loaded5)
        	return false;

        if (this.heightLimit == 0)
        {
            this.heightLimit = 7 + this.rand.nextInt(this.heightLimitLimit);
        }
        
        if (!this.validTreeLocation())
        {
            this.world = null; //Fix vanilla Mem leak, holds latest world
            return false;
        }
        else
        {
        	
            this.generateLeafNodeList();
            this.generateLeaves();
            this.generateTrunk();
            this.generateLeafNodeBases();
            
            this.world = null; //Fix vanilla Mem leak, holds latest world
        	return true;
        }
    }*/

    /**
     * Returns a boolean indicating whether or not the current location for the tree, spanning basePos to to the height
     * limit, is valid.
     */
    private boolean validTreeLocation()
    {
        BlockPos down = this.basePos.down();
        net.minecraft.block.state.IBlockState state = this.world.getBlockState(down);
        boolean isSoil = state.getBlock().canSustainPlant(state, this.world, down, net.minecraft.util.EnumFacing.UP, ((net.minecraft.block.BlockSapling)Blocks.SAPLING));

        if (!isSoil)
        {
            return false;
        }
        else
        {
            int i = this.checkBlockLine(this.basePos, this.basePos.up(this.heightLimit - 1));

            if (i == -1)
            {
                return true;
            }
            else if (i < 6)
            {
                return false;
            }
            else
            {
                this.heightLimit = i;
                return true;
            }
        }
    }

    static class FoliageCoordinates extends BlockPos
        {
            private final int branchBase;

            public FoliageCoordinates(BlockPos pos, int p_i45635_2_)
            {
                super(pos.getX(), pos.getY(), pos.getZ());
                this.branchBase = p_i45635_2_;
            }

            public int getBranchBase()
            {
                return this.branchBase;
            }
        }
}