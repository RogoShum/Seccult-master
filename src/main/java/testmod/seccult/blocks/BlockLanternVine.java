package testmod.seccult.blocks;

import java.util.Random;

import net.minecraft.block.Block;
import net.minecraft.block.BlockShulkerBox;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyInteger;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.init.Blocks;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.blocks.item.ItemBlockVariants;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.init.ModItems;
import testmod.seccult.util.WaNP;
import testmod.seccult.util.registerModel;
import testmod.seccult.util.handlers.PlantsHandler;

public class BlockLanternVine extends Block implements registerModel, WaNP{
	private final String name;
	protected static final AxisAlignedBB BUSH_AABB = new AxisAlignedBB(0.30000001192092896D, 0.0D, 0.30000001192092896D, 0.699999988079071D, 0.6000000238418579D, 0.699999988079071D);
    public static final PropertyInteger VARIANT = PropertyInteger.create("variant", 0, 2);
    
	public BlockLanternVine(String name) {
        super(Material.PLANTS);
        this.setSoundType(SoundType.PLANT);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
        setLightLevel(10);
        this.name = name;
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabsLoader.tab);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void updateTick(World world, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(world, pos, state, rand);
		if(world.isAirBlock(pos.up()) || isExceptBlockForAttaching(world.getBlockState(pos.up()).getBlock()))
			{
				world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
				this.dropBlockAsItem(world, pos, state, 0);
				return;
			}

		if(this == ModBlocks.LANTERN_VINE)
		{
			if(world.getBlockState(pos.up()).getBlock() == ModBlocks.LANTERN_VINE && world.getBlockState(pos.down()).getBlock() == ModBlocks.LANTERN_VINE)
				world.setBlockState(pos, ModBlocks.LANTERN_VINE.getDefaultState().withProperty(VARIANT, 1));
			else if(world.getBlockState(pos.up()).getBlock() != ModBlocks.LANTERN_VINE && world.getBlockState(pos.down()).getBlock() == ModBlocks.LANTERN_VINE)
				world.setBlockState(pos, ModBlocks.LANTERN_VINE.getDefaultState().withProperty(VARIANT, 0));
			else if(world.getBlockState(pos.up()).getBlock() == ModBlocks.LANTERN_VINE && world.getBlockState(pos.down()).getBlock() != ModBlocks.LANTERN_VINE)
				world.setBlockState(pos, ModBlocks.LANTERN_VINE.setLightLevel(12).getDefaultState().withProperty(VARIANT, 2));
			else
				world.setBlockState(pos, ModBlocks.LANTERN_VINE.setLightLevel(12).getDefaultState().withProperty(VARIANT, 2));
		}
		else if(this == ModBlocks.HYPHA_LIGHT)
		{
			if(world.getBlockState(pos.up()).getBlock() == ModBlocks.HYPHA_LIGHT && world.getBlockState(pos.down()).getBlock() == ModBlocks.HYPHA_LIGHT)
				world.setBlockState(pos, ModBlocks.HYPHA_LIGHT.getDefaultState().withProperty(VARIANT, 1));
			else if(world.getBlockState(pos.up()).getBlock() != ModBlocks.HYPHA_LIGHT && world.getBlockState(pos.down()).getBlock() == ModBlocks.HYPHA_LIGHT)
				world.setBlockState(pos, ModBlocks.HYPHA_LIGHT.getDefaultState().withProperty(VARIANT, 0));
			else if(world.getBlockState(pos.up()).getBlock() == ModBlocks.HYPHA_LIGHT && world.getBlockState(pos.down()).getBlock() != ModBlocks.HYPHA_LIGHT)
				world.setBlockState(pos, ModBlocks.HYPHA_LIGHT.setLightLevel(12).getDefaultState().withProperty(VARIANT, 2));
			else
				world.setBlockState(pos, ModBlocks.HYPHA_LIGHT.setLightLevel(12).getDefaultState().withProperty(VARIANT, 2));
		}
		else if(this == ModBlocks.HYPHA_LIGHT_BLUE)
		{
			if(world.getBlockState(pos.up()).getBlock() == ModBlocks.HYPHA_LIGHT_BLUE && world.getBlockState(pos.down()).getBlock() == ModBlocks.HYPHA_LIGHT_BLUE)
				world.setBlockState(pos, ModBlocks.HYPHA_LIGHT_BLUE.getDefaultState().withProperty(VARIANT, 1));
			else if(world.getBlockState(pos.up()).getBlock() != ModBlocks.HYPHA_LIGHT_BLUE && world.getBlockState(pos.down()).getBlock() == ModBlocks.HYPHA_LIGHT_BLUE)
				world.setBlockState(pos, ModBlocks.HYPHA_LIGHT_BLUE.getDefaultState().withProperty(VARIANT, 0));
			else if(world.getBlockState(pos.up()).getBlock() == ModBlocks.HYPHA_LIGHT_BLUE && world.getBlockState(pos.down()).getBlock() != ModBlocks.HYPHA_LIGHT_BLUE)
				world.setBlockState(pos, ModBlocks.HYPHA_LIGHT_BLUE.setLightLevel(12).getDefaultState().withProperty(VARIANT, 2));
			else
				world.setBlockState(pos, ModBlocks.HYPHA_LIGHT_BLUE.setLightLevel(12).getDefaultState().withProperty(VARIANT, 2));
		}
		
		
		if(state.getValue(VARIANT).intValue() == 2)
			this.setLightLevel(12);
		else
			this.setLightLevel(3);
	}
	
	@Override
	public void neighborChanged(IBlockState state, World world, BlockPos pos, Block blockIn, BlockPos fromPos) {
		if(world.isAirBlock(pos.up()) || isExceptBlockForAttaching(world.getBlockState(pos.up()).getBlock()))
		{
			world.setBlockState(pos, Blocks.AIR.getDefaultState(), 3);
			this.dropBlockAsItem(world, pos, state, 0);
			return;
		}
	}
	
	@Override
	public int tickRate(World worldIn) {
		return 1;
	}
	
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return BUSH_AABB;
    }

    @Override
    public AxisAlignedBB getCollisionBoundingBox(IBlockState blockState, IBlockAccess worldIn, BlockPos pos) {
    	return (AxisAlignedBB)null;
    }
    
    @Override
    public int getMetaFromState(IBlockState state){ return 0;}
    
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {VARIANT});
	}
    
    @Override
    public Block.EnumOffsetType getOffsetType()
    {
        return Block.EnumOffsetType.XZ;
    }

	@Override
	public void registerModels() 
	{
		Seccult.proxy.registerVariantRenderer(Item.getItemFromBlock(this), 0, this.name, "inventory");
	}

	@Override
	public boolean canPlaceBlockAt(World worldIn, BlockPos pos) {
		IBlockState soil = worldIn.getBlockState(pos.up());
		return super.canPlaceBlockAt(worldIn, pos) && !isExceptBlockForAttaching(soil.getBlock()) && !worldIn.isAirBlock(pos.up());
	}
	
    protected static boolean isExceptBlockForAttaching(Block p_193397_0_)
    {
        return p_193397_0_ instanceof BlockShulkerBox || p_193397_0_ == Blocks.BEACON || p_193397_0_ == Blocks.CAULDRON || p_193397_0_ == Blocks.GLASS || p_193397_0_ == Blocks.STAINED_GLASS || p_193397_0_ == Blocks.PISTON || p_193397_0_ == Blocks.STICKY_PISTON || p_193397_0_ == Blocks.PISTON_HEAD || p_193397_0_ == Blocks.TRAPDOOR;
    }
	
    public boolean isOpaqueCube(IBlockState state)
    {
        return false;
    }

    public boolean isFullCube(IBlockState state)
    {
        return false;
    }
    
    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return BlockRenderLayer.CUTOUT;
    }
    
	@Override
	public String getSpecialName(ItemStack stack) 
	{
		return null;
	}
}
