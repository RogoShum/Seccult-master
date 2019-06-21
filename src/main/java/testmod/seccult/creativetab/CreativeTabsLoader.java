package testmod.seccult.creativetab;

import net.minecraft.creativetab.CreativeTabs;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;

public class CreativeTabsLoader 
{

	public static CreativeTabs tab;

    public CreativeTabsLoader(FMLPreInitializationEvent event)
    {
        tab = new SeccultCreativeTab();
    }
}
