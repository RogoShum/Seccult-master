package testmod.seccult.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.Entity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import testmod.seccult.Seccult;
import testmod.seccult.blocks.item.ItemBlockVariants;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.init.ModItems;
import testmod.seccult.util.registerModel;
import testmod.seccult.util.WaNP;
import testmod.seccult.util.handlers.TreeHandler;

public class Log extends BlockLog implements registerModel, WaNP
{
	public static final PropertyEnum<TreeHandler.EnumType> VARIANT = PropertyEnum.<TreeHandler.EnumType>create("variant", TreeHandler.EnumType.class, new Predicate<TreeHandler.EnumType>()
	{
		public boolean apply(@Nullable TreeHandler.EnumType apply)
		{
			return apply.getMeta() < 2;
		}
	});
	
	private String name;
	
	public Log(String name) 
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.WOOD);
		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, TreeHandler.EnumType.EVERYTHING).withProperty(LOG_AXIS, EnumAxis.Y));
		setCreativeTab(CreativeTabsLoader.tab);
		setHardness(50.0F);
		setResistance(10086.0F);
		setHarvestLevel("axe", 5);
		
		this.name = name;
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
	}
	
	private static final AxisAlignedBB BOTTOM_AABB = new AxisAlignedBB(0, 0, 0, 1, 1/16.0, 1);
	private static final AxisAlignedBB NORTH_AABB = new AxisAlignedBB(0, 0, 15/16.0, 1, 0.5, 1);
	private static final AxisAlignedBB SOUTH_AABB = new AxisAlignedBB(0, 0, 0, 1, 0.5, 1/16.0);
	private static final AxisAlignedBB WEST_AABB = new AxisAlignedBB(0, 0, 0, 1/16.0, 0.5, 1);
	private static final AxisAlignedBB EAST_AABB = new AxisAlignedBB(15/16.0, 0, 0, 1, 0.5, 1);


	@Override
	public void addCollisionBoxToList(IBlockState state, @Nonnull World world, @Nonnull BlockPos pos, @Nonnull AxisAlignedBB entityBox, @Nonnull List<AxisAlignedBB> boxes, Entity entity, boolean isActualState) {
		addCollisionBoxToList(pos, entityBox, boxes, BOTTOM_AABB);
		addCollisionBoxToList(pos, entityBox, boxes, NORTH_AABB);
		addCollisionBoxToList(pos, entityBox, boxes, SOUTH_AABB);
		addCollisionBoxToList(pos, entityBox, boxes, WEST_AABB);
		addCollisionBoxToList(pos, entityBox, boxes, EAST_AABB);
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) 
	{
		for(TreeHandler.EnumType customblockplanks$enumtype : TreeHandler.EnumType.values())
		{
			items.add(new ItemStack(this, 1, customblockplanks$enumtype.getMeta()));
		}
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta) 
	{
		IBlockState state = this.getDefaultState().withProperty(VARIANT, TreeHandler.EnumType.byMetadata((meta & 1) % 2));
		
		switch(meta & 6)
		{
		case 0:
			state = state.withProperty(LOG_AXIS, EnumAxis.Y);
			break;
			
		case 2:
			state = state.withProperty(LOG_AXIS, EnumAxis.X);
			break;
			
		case 4:
			state = state.withProperty(LOG_AXIS, EnumAxis.Z);
			break;
			
		default:
			state = state.withProperty(LOG_AXIS, EnumAxis.NONE);
		}
		
		return state;
	}
	
	@SuppressWarnings("incomplete-switch")
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		int i = 0;
		i = i | ((TreeHandler.EnumType)state.getValue(VARIANT)).getMeta();
		
		switch((BlockLog.EnumAxis)state.getValue(LOG_AXIS))
		{
		case X:
			i |= 2;
			break;
			
		case Y:
			i |= 4;
			break;
			
		case Z:
			i |= 6;
		}
		
		return i;
	}
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {VARIANT,LOG_AXIS});
	}
	
	@Override
	protected ItemStack getSilkTouchDrop(IBlockState state) 
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, ((TreeHandler.EnumType)state.getValue(VARIANT)).getMeta());
	}
	
	@Override
	public int damageDropped(IBlockState state) 
	{
		return ((TreeHandler.EnumType)state.getValue(VARIANT)).getMeta();
	}
	
	@Override
	public String getSpecialName(ItemStack stack) 
	{
		return TreeHandler.EnumType.values()[stack.getItemDamage()].getName();
	}
	
	@Override
	public void registerModels() 
	{
		for(int i = 0; i < TreeHandler.EnumType.values().length; i++)
		{
			Seccult.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "log_" + TreeHandler.EnumType.values()[i].getName(), "inventory");
		}
	}	
}