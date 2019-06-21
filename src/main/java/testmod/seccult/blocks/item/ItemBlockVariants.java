package testmod.seccult.blocks.item;


import net.minecraft.block.Block;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import testmod.seccult.util.WaNP;

public class ItemBlockVariants extends ItemBlock
{

	public ItemBlockVariants(Block block) 
	{
		super(block);
		setHasSubtypes(true);
		setMaxDamage(0);
	}
	
	@Override
	public int getMetadata(int damage) 
	{
		return damage;
	}
	
	@Override
	public String getUnlocalizedName(ItemStack stack)
	{
		return super.getUnlocalizedName() + "_" + ((WaNP)this.block).getSpecialName(stack);
	}

}
