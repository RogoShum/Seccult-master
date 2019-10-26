package testmod.seccult.blocks;

import java.util.List;
import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.block.BlockLeaves;
import net.minecraft.block.BlockPlanks.EnumType;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.BlockRenderLayer;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.blocks.item.ItemBlockVariants;
import testmod.seccult.client.FX.ParticleEnchantment;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.init.ModItems;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.TransPoint;
import testmod.seccult.util.registerModel;
import testmod.seccult.util.WaNP;
import testmod.seccult.util.handlers.TreeHandler;

public class Leaf extends BlockLeaves implements registerModel, WaNP
{
    int[] surroundings;
	
	public static final PropertyEnum<TreeHandler.EnumType> VARIANT = PropertyEnum.<TreeHandler.EnumType>create("variant", TreeHandler.EnumType.class, new Predicate<TreeHandler.EnumType>()
	{
		public boolean apply(@Nullable TreeHandler.EnumType apply)
		{
			return apply.getMeta() < 4;
		}
	});
	
	public Leaf(String name) 
	{
		setUnlocalizedName(name);
		setRegistryName(name);
		setSoundType(SoundType.PLANT);
		setCreativeTab(CreativeTabsLoader.tab);
		setHardness(15.0F);
		setLightLevel(0.08F);
		setResistance(1.0F);
		setHarvestLevel("axe", 2);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
	}
	
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, TreeHandler.EnumType.byMetadata(meta));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		int i = ((TreeHandler.EnumType)state.getValue(VARIANT)).getMeta();
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
	public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand)
    {
		
        	if(state.getValue(VARIANT).toString().equals(TreeHandler.EnumType.MANA_TREE_MAGIC.toString()) && rand.nextInt(5) == 0)
        	{
        		particle(worldIn, pos, state, rand);
        	}
    }
	
	@SideOnly(Side.CLIENT)
	public void particle(World worldIn, BlockPos pos, IBlockState state, Random rand)
	{
		Particle par = new ParticleEnchantment(worldIn, pos.getX(), pos.getY() - 1, pos.getZ(), 0, -rand.nextFloat() / 3, 0);
		Minecraft.getMinecraft().effectRenderer.addEffect(par);
		double[] Bpos = {pos.getX(), pos.getY() - 1, pos.getZ()};
		double[] vec = {(1 - rand.nextFloat() * 2)/10, -rand.nextFloat() / 3, (1 - rand.nextFloat() * 2)/10};
		float[] color = {0.2F, 0.7F, 0.7F};

		NetworkHandler.sendToAllAround(new NetworkEffectData(Bpos, vec, color, rand.nextFloat() * 2, 0), 
				new TransPoint(-12450, Bpos[0], Bpos[1], Bpos[2], 32), worldIn);
	}
	
	@Override
	protected void dropApple(World worldIn, BlockPos pos, IBlockState state, int chance) {return;}
	
	@Override
	protected int getSaplingDropChance(IBlockState state) {return 30;}

	@Override
	public List<ItemStack> onSheared(ItemStack item, IBlockAccess world, BlockPos pos, int fortune) 
	{
		//Particle par = new ParticleEnchantment(worldIn, pos.getX(), pos.getY(), pos.getZ(), 0, -0.1, 0);
		//Minecraft.getMinecraft().effectRenderer.addEffect(par);
		//System.out.println("qwq");
		return NonNullList.withSize(1, new ItemStack(this, 1, world.getBlockState(pos).getValue(VARIANT).getMeta()));
	}
	
	@Override
	protected BlockStateContainer createBlockState() 
	{
		return new BlockStateContainer(this, new IProperty[] {VARIANT,DECAYABLE,CHECK_DECAY});
	}
	
	@SideOnly(Side.CLIENT)
    public boolean isOpaqueCube(IBlockState state)
    {
        return !Minecraft.getMinecraft().gameSettings.fancyGraphics;
    }

    @SideOnly(Side.CLIENT)
    public BlockRenderLayer getBlockLayer()
    {
        return Minecraft.getMinecraft().gameSettings.fancyGraphics ? BlockRenderLayer.CUTOUT_MIPPED : BlockRenderLayer.SOLID;
    }
    
	@Override
	public void registerModels() 
	{
		for(int i = 0; i < TreeHandler.EnumType.values().length; i++)
		{
			Seccult.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "leaves_" + TreeHandler.EnumType.values()[i].getName(), "inventory");
		}
	}

	@Override
	public MapColor getMapColor(IBlockState state, IBlockAccess worldIn, BlockPos pos) {
		return MapColor.GRASS;
	}
	
	@Override
	public EnumType getWoodType(int meta) {
		return null;
	}
}