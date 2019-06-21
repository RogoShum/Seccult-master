package testmod.seccult.blocks;

import java.util.List;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import testmod.seccult.Seccult;
import testmod.seccult.blocks.item.ItemBlockVariants;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.init.ModItems;
import testmod.seccult.util.WaNB;
import testmod.seccult.util.WaNP;
import testmod.seccult.util.handlers.TreeHandler;

public class Leaf extends BlockLeaves implements WaNB, WaNP
{
	public static final PropertyEnum<TreeHandler.EnumType> VARIANT = PropertyEnum.<TreeHandler.EnumType>create("variant", TreeHandler.EnumType.class, new Predicate<TreeHandler.EnumType>()
	{
		public boolean apply(@Nullable TreeHandler.EnumType apply)
		{
			return apply.getMeta() < 2;
		}
	});
	
	private String name;
	
	public Leaf(String name) 
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.PLANT);
		setDefaultState(this.blockState.getBaseState().withProperty(VARIANT, TreeHandler.EnumType.EVERYTHING).withProperty(CHECK_DECAY, Boolean.valueOf(true)).withProperty(DECAYABLE, Boolean.valueOf(true)));
		setCreativeTab(CreativeTabsLoader.tab);
		setHardness(15.0F);
		setResistance(450.0F);
		setHarvestLevel("axe", 4);
		
		this.name = name;
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, TreeHandler.EnumType.byMetadata(meta % 2));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		int i = ((TreeHandler.EnumType)state.getValue(VARIANT)).getMeta();
		
		if(!((Boolean)state.getValue(DECAYABLE)).booleanValue())
		{
			i |= 2;
		}
		
		if(!((Boolean)state.getValue(CHECK_DECAY)).booleanValue())
		{
			i |= 4;
		}
		
		return i;
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
	protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {return;}
	
	@Override
	protected int getSaplingDropChance(IBlockState state) {return 30;}
	
	@Override
	public EnumType getWoodType(int meta) {return null;}
	
	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) 
	{
		return NonNullList.withSize(1, new ItemStack(this, 1, world.getBlockState(pos).getValue(VARIANT).getMeta()));
	}
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {VARIANT,DECAYABLE,CHECK_DECAY});
	}
	
	@Override
	public boolean isOpaqueCube(IBlockState state) 
	{
		return false;
	}
	
	@Override
	public BlockRenderLayer getBlockLayer() 
	{
		return BlockRenderLayer.TRANSLUCENT;
	}	
	
	@Override
	public void registerModels() 
	{
		for(int i = 0; i < TreeHandler.EnumType.values().length; i++)
		{
			Seccult.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "leaves_" + TreeHandler.EnumType.values()[i].getName(), "inventory");
		}
	}
}