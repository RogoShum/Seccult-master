package testmod.seccult.blocks;

import net.minecraft.block.BlockContainer;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.world.World;
import testmod.seccult.Seccult;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.init.ModItems;
import testmod.seccult.util.registerModel;

public abstract class BlockTileEntity extends BlockContainer implements registerModel{
	private final String name;
	
	protected BlockTileEntity(String name, Material materialIn) {
		super(materialIn);
		this.name = name;
		setRegistryName(name);
		setUnlocalizedName(name);
		this.setCreativeTab(CreativeTabsLoader.tab);
		ModBlocks.BLOCKS.add(this);
		ModItems.ITEMS.add(new ItemBlock(this).setRegistryName(this.getRegistryName()));
	}

	@Override
	public TileEntity createNewTileEntity(World worldIn, int meta) {
		return null;
	}

	@Override
	public void registerModels() 
	{
		Seccult.proxy.registerVariantRenderer(Item.getItemFromBlock(this), 0, this.name, "inventory");
	}
}
