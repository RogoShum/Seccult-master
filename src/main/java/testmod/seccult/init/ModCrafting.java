package testmod.seccult.init;

import net.minecraft.init.Items;
import net.minecraft.item.ItemStack;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import testmod.seccult.Seccult;

@Mod.EventBusSubscriber(modid = Seccult.MODID)
public class ModCrafting {
    public ModCrafting()
    {
        registerRecipe();
        registerSmelting();
    }

    private static void registerRecipe()
    {

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
