package testmod.seccult;

import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.Mod.EventHandler;
import net.minecraftforge.fml.common.Mod.Instance;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.blocks.tileEntity.tileGenerator;
import testmod.seccult.client.FX.ModFX;
import testmod.seccult.client.gui.GuiElementLoader;
import testmod.seccult.creativetab.CreativeTabsLoader;
import testmod.seccult.events.ModEventHandler;
import testmod.seccult.init.ModItems;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.items.ItemMagickCore;
import testmod.seccult.items.ItemWand;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.util.handlers.RegistryHandler;
import testmod.seccult.world.gen.DimensionMagic;

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
	
	public static Random rand = new Random();
	
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
		
		TileEntity.register("tileGenerator", tileGenerator.class);
	}
	
	@EventHandler
	public static void init(FMLInitializationEvent event)
	{
		new GuiElementLoader();
		proxy.sphereRender();
		proxy.init();
		//DimensionMagic.register();
	}
	
	@EventHandler
	public static void PostInit(FMLPostInitializationEvent event)
	{
		ModEventHandler.RegisterEvents();
		ItemColoerRegister();
	}
	
	@SideOnly(Side.CLIENT)
	public static void ItemColoerRegister()
	{
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
		{
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {
				if(tintIndex == 1)
					return ItemWand.getMagickColor(stack);

					return ItemWand.getWandStyle(stack, tintIndex);
			}
		}, ModItems.Wand);
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
		{
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {

					return ItemMagickCore.getMagickColorInt(stack);
			}
		}, ModItems.MagickCore);
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
		{
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {

					return ItemMagickCore.getMagickColorInt(stack);
			}
		}, ModItems.CHLOROPHYTE_SWORD);
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
		{
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {

					return ItemMagickCore.getMagickColorInt(stack);
			}
		}, ModItems.OCEAN_SWORD);
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
		{
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {

					return ItemMagickCore.getMagickColorInt(stack);
			}
		}, ModItems.SHADOW_SWORD);
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
		{
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {

					return ItemMagickCore.getMagickColorInt(stack);
			}
		}, ModItems.SILK_SWORD);
		
		Minecraft.getMinecraft().getItemColors().registerItemColorHandler(new IItemColor()
		{
			@Override
			public int colorMultiplier(ItemStack stack, int tintIndex) {

					return ItemMagickCore.getMagickColorInt(stack);
			}
		}, ModItems.SORCERER_SWORD);
	}
}

    
