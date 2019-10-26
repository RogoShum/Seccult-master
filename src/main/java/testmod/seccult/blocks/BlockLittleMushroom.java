package testmod.seccult.blocks;

import java.util.Random;

import net.minecraft.block.BlockDirt;
import net.minecraft.block.BlockMushroom;
import net.minecraft.block.SoundType;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraft.world.gen.feature.WorldGenerator;
import testmod.seccult.Seccult;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.init.ModItems;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.util.registerModel;
import testmod.seccult.world.gen.WorldGenSeccultMushroom;

public class BlockLittleMushroom extends BlockMushroom implements registerModel{
	private final String name;
	public BlockLittleMushroom(String name) {
		setRegistryName(name);
		setUnlocalizedName(name);
		setLightLevel(0.25F);
		setSoundType(SoundType.PLANT);
		this.name = name;
		this.setTickRandomly(true);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
		setCreativeTab(CreativeTabsLoader.tab);
	}

	@Override
	public void registerModels() 
	{
		Seccult.proxy.registerVariantRenderer(Item.getItemFromBlock(this), 0, this.name, "inventory");
	}

	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
		
    	if(rand.nextInt(10) == 0)
    	{
    		double[] Bpos = {pos.getX(), pos.getY() + 0.5, pos.getZ()};
    		double[] vec = {0, 0, 0};
			float[] color = {0.2F, 0.7F, 0.7F};
    		NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(Bpos, vec, color, 5, 0));
    		
    	}
	}
	
	@Override
	public boolean generateBigMushroom(World world, BlockPos pos, IBlockState state, Random rand)
	{
		
		WorldGenerator gen = null;
		
		if(this == ModBlocks.LittleMush)
			gen = new WorldGenSeccultMushroom(ModBlocks.Mush);
		else if(this == ModBlocks.LittleRedMush)
			gen = new WorldGenSeccultMushroom(ModBlocks.RedMush);
		
		IBlockState iblockstate = Blocks.AIR.getDefaultState();
		
        if (gen != null && gen.generate(world, rand, pos))
        {
            return true;
        }
        else
        {
            world.setBlockState(pos, iblockstate, 3);
            return false;
        }
		
	}
	
	@Override
    public boolean canBlockStay(World worldIn, BlockPos pos, IBlockState state)
    {
        if (pos.getY() >= 0 && pos.getY() < 256)
        {
            IBlockState iblockstate = worldIn.getBlockState(pos.down());

            if (iblockstate.getBlock() == ModBlocks.Hypha)
            {
                return true;
            }
            else if (iblockstate.getBlock() == Blocks.DIRT && iblockstate.getValue(BlockDirt.VARIANT) == BlockDirt.DirtType.PODZOL)
            {
                return true;
            }
            else
            {
                return worldIn.getLight(pos) < 13 && iblockstate.getBlock().canSustainPlant(iblockstate, worldIn, pos.down(), net.minecraft.util.EnumFacing.UP, this);
            }
        }
        else
        {
            return false;
        }
    }
	
	@Override
	public boolean canUseBonemeal(World worldIn, Random rand, BlockPos pos, IBlockState state) 
	{
		return (double)worldIn.rand.nextFloat() < 0.45D;
	}
}
