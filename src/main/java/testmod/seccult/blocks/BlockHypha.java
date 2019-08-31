package testmod.seccult.blocks;

import java.util.Random;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockMycelium;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import testmod.seccult.Seccult;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.init.ModItems;
import testmod.seccult.util.registerModel;

public class BlockHypha extends BlockMycelium implements registerModel{
	private final String name;
	public BlockHypha(String name) {
		setRegistryName(name);
		setUnlocalizedName(name);
		setLightLevel(0.7F);
		this.name = name;
		this.setSoundType(SoundType.PLANT);
		this.setTickRandomly(true);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		setCreativeTab(CreativeTabsLoader.tab);
	}

	@Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
        if (!worldIn.isRemote)
        {
            if (!worldIn.isAreaLoaded(pos, 2)) return; // Forge: prevent loading unloaded chunks when checking neighbor's light and spreading
            for (int i = 0; i < 4; ++i)
            {
            	BlockPos blockpos = pos.add(rand.nextInt(3) - 1, rand.nextInt(5) - 3, rand.nextInt(3) - 1);
            	IBlockState iblockstate = worldIn.getBlockState(blockpos);
            	IBlockState iblockstate1 = worldIn.getBlockState(blockpos.up());

            	if (iblockstate.getBlock() == Blocks.DIRT && iblockstate.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.DIRT && worldIn.getLightFromNeighbors(blockpos.up()) >= 4 && iblockstate1.getLightOpacity(worldIn, blockpos.up()) <= 2)
            	{
            		worldIn.setBlockState(blockpos, this.getDefaultState());
            	}
            }
        }
    }
	
	@Override
	public void registerModels() 
	{
		Seccult.proxy.registerVariantRenderer(Item.getItemFromBlock(this), 0, this.name, "inventory");
	}
}
