package testmod.seccult.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.block.BlockLog;
import net.minecraft.block.SoundType;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
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
			return apply.getMeta() < 4;
		}
	});
	
	private String name;
	
	public Log(String name) 
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.WOOD);
		setLightLevel(0.125F);
		setCreativeTab(CreativeTabsLoader.tab);
		setHardness(50.0F);
		setResistance(30.0F);
		setHarvestLevel("axe", 2);
		
		this.name = name;
		
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
		super.updateTick(worldIn, pos, state, rand);
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
		IBlockState state = this.getDefaultState().withProperty(VARIANT, TreeHandler.EnumType.byMetadata(meta));
		state = state.withProperty(LOG_AXIS, EnumAxis.NONE);
		return state;
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		int i = 0;
		i = ((TreeHandler.EnumType)state.getValue(VARIANT)).getMeta();
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