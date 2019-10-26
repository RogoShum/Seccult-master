package testmod.seccult.util.handlers;

import net.minecraft.block.Block;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.color.IItemColor;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.client.entity.render.RenderHandler;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.init.ModEntity;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.ItemMagickCore;
import testmod.seccult.items.ItemWand;
import testmod.seccult.util.registerModel;
import testmod.seccult.world.gen.WorldGenCustomOres;
import testmod.seccult.world.gen.WorldGenCustomTrees;
import testmod.seccult.world.gen.WorldGenStructures;

@EventBusSubscriber
public class RegistryHandler 
{

	@SubscribeEvent
	public static void onBlockRegister(RegistryEvent.Register<Block> event)
	{
		event.getRegistry().registerAll(ModBlocks.BLOCKS.toArray(new Block[0]));
	}
	
	@SubscribeEvent
	public static void onItemRegister(RegistryEvent.Register<Item> event)
	{
		event.getRegistry().registerAll(ModItems.ITEMS.toArray(new Item[0]));
	}
	
	@SubscribeEvent
	public static void onModelRegister(ModelRegistryEvent event)
	{
		for(Block block : ModBlocks.BLOCKS)
		{
			if(block instanceof registerModel) 
			{
				((registerModel)block).registerModels();
			}
		}
		
		for(Item item : ModItems.ITEMS)
		{
			if(item instanceof registerModel) 
			{
				((registerModel)item).registerModels();
			}
		}
	}
	
	public static void otherRegistries()
	{
		GameRegistry.registerWorldGenerator(new WorldGenStructures(), 0);
		GameRegistry.registerWorldGenerator(new WorldGenCustomOres(), 0);
		GameRegistry.registerWorldGenerator(new WorldGenCustomTrees(), 0);
	}
	
	public static void preInitRegisteries()
	{
		ModEntity.registerEntities();
	}
}
