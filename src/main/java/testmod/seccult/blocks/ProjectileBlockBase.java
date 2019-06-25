package testmod.seccult.blocks;

import net.minecraft.block.Block;
import net.minecraft.block.material.Material;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import testmod.seccult.Seccult;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.init.ModItems;
import testmod.seccult.util.registerModel;
import testmod.seccult.util.WaNP;

public class ProjectileBlockBase extends Block implements registerModel, WaNP{

		public ProjectileBlockBase(String name, Material material)
		{
			super(material);
			setUnlocalizedName(name);
			setRegistryName(name);
			
			ModBlocks.BLOCKS.add(this);
		}

		@Override
		public void registerModels() 
		{

			Seccult.proxy.registerItemRenderer(Item.getItemFromBlock(this), 0, "inventory");
		}

		@Override
		public String getSpecialName(ItemStack stack) {
			// TODO Auto-generated method stub
			return null;
		}
}
