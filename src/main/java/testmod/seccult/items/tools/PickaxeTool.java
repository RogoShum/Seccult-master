package testmod.seccult.items.tools;

import net.minecraft.item.ItemPickaxe;
import testmod.seccult.Seccult;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.init.ModItems;
import testmod.seccult.util.WaNB;

public class PickaxeTool extends ItemPickaxe implements WaNB 
{
	
	public PickaxeTool(String name, ToolMaterial material)
	{
		super(material);
		setUnlocalizedName(name);
		setRegistryName(name);
		setCreativeTab(CreativeTabsLoader.tab);
		
		ModItems.ITEMS.add(this);
	}
	
	@Override
	public void registerModels() 
	{
		Seccult.proxy.registerItemRenderer(this, 0, "inventory");
	}
}
