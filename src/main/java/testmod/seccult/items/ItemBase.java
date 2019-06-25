package testmod.seccult.items;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import testmod.seccult.Seccult;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.init.ModItems;
import testmod.seccult.util.registerModel;

public class ItemBase extends Item implements registerModel
{
	ResourceLocation EOWres = new ResourceLocation("seccult:trueeaterofworlds");
	ResourceLocation BVH = new ResourceLocation("seccult:blackvelvethell");
	ResourceLocation SCP173 = new ResourceLocation("seccult:scp173");
	
	public ItemBase(String name)
	{
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
