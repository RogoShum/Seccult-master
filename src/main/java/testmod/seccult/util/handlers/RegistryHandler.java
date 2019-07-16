package testmod.seccult.util.handlers;

import net.minecraft.block.Block;
import net.minecraft.item.Item;
import net.minecraftforge.client.event.ModelRegistryEvent;
import net.minecraftforge.event.RegistryEvent;
import net.minecraftforge.fml.common.Mod.EventBusSubscriber;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import testmod.seccult.ClientProxy;
import testmod.seccult.client.entity.render.RenderHandler;
import testmod.seccult.entity.ModEntity;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.init.ModItems;
import testmod.seccult.util.registerModel;
import testmod.seccult.world.gen.SeccultBiomeRegistries;
import testmod.seccult.world.gen.WorldGenCustomOres;
import testmod.seccult.world.gen.WorldGenCustomTrees;

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
		//GameRegistry.registerWorldGenerator(new WorldGenCustomOres(), 0);
		GameRegistry.registerWorldGenerator(new WorldGenCustomTrees(), 0);
	}
	
	public static void preInitRegisteries()
	{
		ModEntity.registerEntities();
		RenderHandler.registerEntityRenders();
	}
}
