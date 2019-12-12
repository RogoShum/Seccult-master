package testmod.seccult.init;

import net.minecraft.init.Blocks;
import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import testmod.seccult.Seccult;

@Mod.EventBusSubscriber(modid = Seccult.MODID)
public class ModCrafting {
    public ModCrafting()
    {
    	registerOre();
        registerSmelting();
    }

    private static void registerOre()
    {
    	OreDictionary.registerOre("oreCopper", ModItems.ALL_METAL);
    	OreDictionary.registerOre("oreGold", ModItems.ALL_METAL);
    	OreDictionary.registerOre("oreIron", ModItems.ALL_METAL);
    	
    	OreDictionary.registerOre("record", ModItems.RecordA);
    	OreDictionary.registerOre("record", ModItems.RecordAF);
    	OreDictionary.registerOre("record", ModItems.RecordD);
    	OreDictionary.registerOre("record", ModItems.RecordG);
    	OreDictionary.registerOre("record", ModItems.RecordGO);
    	OreDictionary.registerOre("record", ModItems.RecordI);
    	OreDictionary.registerOre("record", ModItems.RecordL);
    	OreDictionary.registerOre("record", ModItems.RecordQ);
    	OreDictionary.registerOre("record", ModItems.RecordS);
    	OreDictionary.registerOre("record", ModItems.RecordSA);
    	OreDictionary.registerOre("record", ModItems.RecordST);
    	OreDictionary.registerOre("record", ModItems.RecordSU);
    	OreDictionary.registerOre("record", ModItems.RecordT);
    	OreDictionary.registerOre("record", ModItems.RecordZ);
    	
    	OreDictionary.registerOre("record", ModItems.RecordST);
    	OreDictionary.registerOre("record", ModItems.RecordSU);
    	OreDictionary.registerOre("record", ModItems.RecordT);
    	OreDictionary.registerOre("record", ModItems.RecordZ);
    	
    	OreDictionary.registerOre("logWood", ModBlocks.LOGS);
    	OreDictionary.registerOre("plankWood", ModBlocks.PLANKS);
    	OreDictionary.registerOre("treeSapling", ModBlocks.SAPLINGS);
    	OreDictionary.registerOre("treeLeaves", ModBlocks.LEAVES);
    }

    private static void registerSmelting()
    {
    	GameRegistry.addSmelting(ModBlocks.Hypha, new ItemStack(Items.COAL), 0.5F);
    }

    @SubscribeEvent
    public static void getVanillaFurnaceFuelValue(FurnaceFuelBurnTimeEvent event) {
    	int time = check(event.getItemStack());
        if(time > 0)
        	event.setBurnTime(time);
    }
    
    public static int check(ItemStack stack)
    {
    	int burnTime = 0;
    	return burnTime;
    }
}
