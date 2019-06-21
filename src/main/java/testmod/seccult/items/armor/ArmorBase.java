package testmod.seccult.items.armor;

import net.minecraft.inventory.EntityEquipmentSlot;
import net.minecraft.item.ItemArmor;
import testmod.seccult.Seccult;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.init.ModItems;
import testmod.seccult.util.WaNB;

public class ArmorBase extends ItemArmor implements WaNB
{

	public ArmorBase(String name, ArmorMaterial materialIn, int renderIndexIn, EntityEquipmentSlot equipmentSlotIn) {
		super(materialIn, renderIndexIn, equipmentSlotIn);
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
