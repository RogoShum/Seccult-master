package testmod.seccult.world.gen.plant;

import java.util.Random;


import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenAbstractTree;
import testmod.seccult.blocks.Leaf;
import testmod.seccult.blocks.Log;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.util.handlers.TreeHandler;

public class WorldGenEverythingTree extends WorldGenAbstractTree 
{
	private final int minHeight;
	
	public WorldGenEverythingTree() 
	{
		super(false);
		this.minHeight = 5;
	}

	@Override
    public boolean generate(World worldIn, Random rand, BlockPos position)
    {
		 if(worldIn.isRemote)
	        	return false;
        int i = rand.nextInt(3) + this.minHeight;
        boolean flag = true;

        if (position.getY() >= 1 && position.getY() + i + 1 <= worldIn.getHeight())
        {
            for (int j = position.getY(); j <= position.getY() + 1 + i; ++j)
            {
                int k = 1;

                if (j == position.getY())
                {
                    k = 0;
                }

                if (j >= position.getY() + 1 + i - 2)
                {
                    k = 2;
                }

                BlockPos.MutableBlockPos blockpos$mutableblockpos = new BlockPos.MutableBlockPos();

                for (int l = position.getX() - k; l <= position.getX() + k && flag; ++l)
                {
                    for (int i1 = position.getZ() - k; i1 <= position.getZ() + k && flag; ++i1)
                    {
                        if (j >= 0 && j < worldIn.getHeight())
                        {
                            if (!this.isReplaceable(worldIn,blockpos$mutableblockpos.setPos(l, j, i1)))
                            {
                                flag = false;
                            }
                        }
                        else
                        {
                            flag = false;
                        }
                    }
                }
            }

            if (!flag)
            {
                return false;
            }
            else
            {
                IBlockState state = worldIn.getBlockState(position.down());

                if (state.getBlock().canSustainPlant(state, worldIn, position.down(), net.minecraft.util.EnumFacing.UP, ModBlocks.SAPLINGS) && position.getY() < worldIn.getHeight() - i - 1)
                {
                    state.getBlock().onPlantGrow(state, worldIn, position.down(), position);
                    int k2 = 3;
                    int l2 = 0;

                    for (int i3 = position.getY() - 3 + i; i3 <= position.getY() + i; ++i3)
                    {
                        int i4 = i3 - (position.getY() + i);
                        int j1 = 1 - i4 / 2;

                        for (int k1 = position.getX() - j1; k1 <= position.getX() + j1; ++k1)
                        {
                            int l1 = k1 - position.getX();

                            for (int i2 = position.getZ() - j1; i2 <= position.getZ() + j1; ++i2)
                            {
                                int j2 = i2 - position.getZ();

                                if (Math.abs(l1) != j1 || Math.abs(j2) != j1 || rand.nextInt(2) != 0 && i4 != 0)
                                {
                                    BlockPos blockpos = new BlockPos(k1, i3, i2);
                                    state = worldIn.getBlockState(blockpos);

                                    if (state.getBlock().isAir(state, worldIn, blockpos) || state.getBlock().isLeaves(state, worldIn, blockpos) || state.getMaterial() == Material.VINE)
                                    {
                                        this.setBlockAndNotifyAdequately(worldIn, blockpos, ModBlocks.LEAVES.getDefaultState());
                                    }
                                }
                            }
                        }
                    }

                    for (int j3 = 0; j3 < i; ++j3)
                    {
                        BlockPos upN = position.up(j3);
                        state = worldIn.getBlockState(upN);

                        if (state.getBlock().isAir(state, worldIn, upN) || state.getBlock().isLeaves(state, worldIn, upN) || state.getMaterial() == Material.VINE)
                        {
                            this.setBlockAndNotifyAdequately(worldIn, position.up(j3), ModBlocks.LOGS.getDefaultState());
                        }
                    }

                    return true;
                }
                else
                {
                    return false;
                }
            }
        }
        else
        {
            return false;
        }
    }
	
	@Override
	protected boolean canGrowInto(Block blockType)
	{
		Material material = blockType.getDefaultState().getMaterial();
        return material == Material.AIR || material == Material.LEAVES || material == Material.GROUND || blockType == Blocks.DIRT || blockType == Blocks.GRASS || blockType == Blocks.DIRT || blockType == Blocks.LOG || blockType == Blocks.LOG2 || blockType == Blocks.SAPLING || blockType == Blocks.VINE;
  
	}
}