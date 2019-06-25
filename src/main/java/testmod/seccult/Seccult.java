package testmod.seccult;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.network.NetworkRegistry;
import net.minecraftforge.fml.common.network.simpleimpl.SimpleNetworkWrapper;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.api.ModMagicks;
import testmod.seccult.client.FX.ModFX;
import testmod.seccult.client.gui.GuiElementLoader;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.events.ModEventHandler;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.ItemWand;
import testmod.seccult.magick.ActiveHandler;
import testmod.seccult.magick.ImplemrntationHandler;
import testmod.seccult.magick.MagickHandler;
import testmod.seccult.magick.implementation.Implementation;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.util.handlers.RegistryHandler;

@Mod(modid = "seccult", name = "Seccult", version = "0.1")

public class Seccult 
{
	public static final String MODID = "seccult";
	public static final String NAME = "Seccult";
	public static final String VERSION = "0.1";
	
	public static final String Data = MODID + ":data";
	public static final String AccessorieData = MODID + ":accessorie_data";
	public static final String MagickData = "MagickData";
	public static final String MagickList = "MagickList";
	
	@Instance(Seccult.MODID)
    public static Seccult instance;
	
	@SidedProxy(clientSide = "testmod.seccult.ClientProxy", serverSide = "testmod.seccult.CommonProxy")
	public static CommonProxy proxy;

	@EventHandler
	public static void PreInit(FMLPreInitializationEvent event)
	{
		new CreativeTabsLoader(event);
		ModMagicks.init();
		NetworkHandler.init();
		RegistryHandler.otherRegistries();
		RegistryHandler.preInitRegisteries();
		MinecraftForge.EVENT_BUS.register(new ModFX());
		
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		new GuiElementLoader();
		proxy.sphereRender();
		proxy.cylinderRender();
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event)
	{
		ModEventHandler.RegisterEvents();
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
		{
			@SideOnly(Side.CLIENT)
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {
				if(tintIndex == 1)
					return ItemWand.getMagickColor(stack);
				if(tintIndex == 2)
					return ItemWand.getWandStyle(stack, 2);
				if(tintIndex == 3)
					return ItemWand.getWandStyle(stack, 3);
				if(tintIndex == 4)
					return ItemWand.getWandStyle(stack, 4);
				
				return 1;
			}
		}, ModItems.Wand);
	}
}

    
