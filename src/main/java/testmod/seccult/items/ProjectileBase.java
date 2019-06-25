package testmod.seccult.items;

import net.minecraft.item.Item;
import net.minecraft.util.ResourceLocation;
import testmod.seccult.Seccult;
import testmod.seccult.init.ModItems;
import testmod.seccult.util.registerModel;

public class ProjectileBase extends Item implements registerModel
	{
		ResourceLocation EOWres = new ResourceLocation("seccult:trueeaterofworlds");
		public ProjectileBase(String name)
		{
			setUnlocalizedName(name);
			setRegistryName(name);
			
			ModItems.ITEMS.add(this);
		}
		
		@Override
		public void registerModels() 
		{
			Seccult.proxy.registerItemRenderer(this, 0, "inventory");
		}
}
