package testmod.seccult.blocks;

import net.minecraft.block.BlockMycelium;
import net.minecraft.block.SoundType;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
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
	public void registerModels() 
	{
		Seccult.proxy.registerVariantRenderer(Item.getItemFromBlock(this), 0, this.name, "inventory");
	}
}
