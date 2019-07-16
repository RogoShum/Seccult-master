package testmod.seccult.blocks;

import java.util.Random;

import javax.annotation.Nullable;

import com.google.common.base.Predicate;

import net.minecraft.block.Block;
import net.minecraft.block.BlockBush;
import net.minecraft.block.SoundType;
import net.minecraft.block.material.MapColor;
import net.minecraft.block.material.Material;
import net.minecraft.block.properties.IProperty;
import net.minecraft.block.properties.PropertyEnum;
import net.minecraft.block.state.BlockStateContainer;
import net.minecraft.block.state.IBlockState;
import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.util.NonNullList;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.world.IBlockAccess;
import net.minecraft.world.World;
import net.minecraftforge.common.IPlantable;
import testmod.seccult.Seccult;
import testmod.seccult.blocks.item.ItemBlockVariants;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.init.ModItems;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.util.WaNP;
import testmod.seccult.util.registerModel;
import testmod.seccult.util.handlers.PlantsHandler;

public class BlockPlant extends BlockBush implements IPlantable, registerModel, WaNP
{
	public static final float[] color_White = {1,1,1};
	public static final float[] color_Red = {0.8F,0,0};
	public static final float[] color_C = {0.0F,1,1};
	public static final float[] color_M = {1F,0,1};
	public static final float[] color_Y = {1,1,0};
	public static final float[] color_Grass = {0.5F,0.7F,0.1F};
	
	public static final PropertyEnum<PlantsHandler.EnumType> VARIANT = PropertyEnum.<PlantsHandler.EnumType>create("variant", PlantsHandler.EnumType.class, new Predicate<PlantsHandler.EnumType>()
	{
		public boolean apply(@Nullable PlantsHandler.EnumType apply)
		{
			return apply.getMeta() < 6;
		}
	});
	
	private float[] color = {0,0,0};
    public BlockPlant(String name)
    {
        this(Material.PLANTS);
        this.setSoundType(SoundType.PLANT);
        this.setRegistryName(name);
        this.setUnlocalizedName(name);
		setLightLevel(5);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlockVariants(this).setRegistryName(this.getRegistryName()));
    }
	
    protected BlockPlant(Material materialIn)
    {
        this(materialIn, materialIn.getMaterialMapColor());
    }
    
    protected BlockPlant(Material materialIn, MapColor mapColorIn)
    {
        super(materialIn, mapColorIn);
        this.setTickRandomly(true);
        this.setCreativeTab(CreativeTabsLoader.tab);
    }
    
	@Override
	public IBlockState getStateFromMeta(int meta)
	{
		return this.getDefaultState().withProperty(VARIANT, PlantsHandler.EnumType.byMetadata(meta));
	}
	
	@Override
	public ItemStack getPickBlock(IBlockState state, RayTraceResult target, World world, BlockPos pos, EntityPlayer player) 
	{
		return new ItemStack(Item.getItemFromBlock(this), 1, getMetaFromState(world.getBlockState(pos)));
	}
	
	@Override
	public int getMetaFromState(IBlockState state) 
	{
		return ((PlantsHandler.EnumType)state.getValue(VARIANT)).getMeta();
	}
	
	@Override
	public void getSubBlocks(CreativeTabs itemIn, NonNullList<ItemStack> items) 
	{
		for(PlantsHandler.EnumType customblockplanks$enumtype : PlantsHandler.EnumType.values())
		{
			items.add(new ItemStack(this, 1, customblockplanks$enumtype.getMeta()));
		}
	}
    
	@Override
	public int damageDropped(IBlockState state) 
	{
		return ((PlantsHandler.EnumType)state.getValue(VARIANT)).getMeta();
	}
	
    @Override
    public void updateTick(World worldIn, BlockPos pos, IBlockState state, Random rand) {
    	super.updateTick(worldIn, pos, state, rand);
    	if(rand.nextInt(10) == 0) {
    		int times = rand.nextInt(3);
    	for(int i = 0; i<times; i++) {
    		double[] Bpos = {pos.getX(), pos.add(0, 0.5, 0).getY(), pos.getZ()};
    		double[] vec = {(1 - rand.nextFloat() * 2)/10, rand.nextFloat() / 8, (1 - rand.nextFloat() * 2)/10};
			switch(getMetaFromState(state))
			{
			case 0:
				this.color = color_White;
				break;
			case 1:
				this.color = color_Red;
				break;
			case 2:
				this.color = color_C;
				break;
			case 3:
				this.color = color_M;
				break;
			case 4:
				this.color = color_Y;
				break;
			case 5:
				this.color = color_Grass;
				break;
			}
    		NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(Bpos, vec, color, rand.nextFloat(), 0));
    	}
    	}
    }
    
    public BlockPlant setColor(float[] color)
    {
    	this.color = color;
    	return this;
    }
    
    @Override
    public AxisAlignedBB getBoundingBox(IBlockState state, IBlockAccess source, BlockPos pos)
    {
        return super.getBoundingBox(state, source, pos).offset(state.getOffset(source, pos));
    }

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
		for(int i = 0; i < PlantsHandler.EnumType.values().length; i++)
		{
			Seccult.proxy.registerVariantRenderer(Item.getItemFromBlock(this), i, "flower_" + PlantsHandler.EnumType.values()[i].getName(), "inventory");
		}
	}

	@Override
	public String getSpecialName(ItemStack stack) 
	{
		return PlantsHandler.EnumType.values()[stack.getItemDamage()].getName();
	}
}