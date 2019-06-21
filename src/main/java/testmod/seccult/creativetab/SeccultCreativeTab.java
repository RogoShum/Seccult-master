package testmod.seccult.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraft.item.ItemStack;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.init.ModItems;

public final class SeccultCreativeTab extends CreativeTabs
{
	public SeccultCreativeTab()
{
	super("Seccult");
}

@Override
@SideOnly(Side.CLIENT)
public ItemStack getTabIconItem()
{
	return new ItemStack(ModItems.SPA);
}
}