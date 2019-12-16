package testmod.seccult.init;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemBlock;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraftforge.event.furnace.FurnaceFuelBurnTimeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.common.registry.GameRegistry;
import net.minecraftforge.oredict.OreDictionary;
import scala.util.Random;
import testmod.seccult.Seccult;
import testmod.seccult.items.ItemMagickTool;

@Mod.EventBusSubscriber(modid = Seccult.MODID)
public class ModCrafting {
	private static HashMap<Integer, HashSet<MagickRecipe>> MagickCrafting = new HashMap<Integer, HashSet<MagickRecipe>>();
	private static HashMap<Integer, ItemStack[]> CraftingLoot = new HashMap<Integer, ItemStack[]>();
	private static int slot;
	private static final Random rand = new Random();
	
	private static final MagickRecipe m_wood = newMagickRecipe(new ItemBlock(ModBlocks.LOGS), 3, null);
	private static final MagickRecipe flower_powder = newMagickRecipe(ModItems.MAGICK_POWER);
	private static final MagickRecipe wind = newMagickRecipe(ModItems.WINDY_DUST);
	private static final MagickRecipe petal_y = newMagickRecipe(ModItems.Y_PETAL);
	private static final MagickRecipe petal_b = newMagickRecipe(ModItems.B_PETAL);
	private static final MagickRecipe petal_m = newMagickRecipe(ModItems.M_PETAL);
	private static final MagickRecipe feather_b = newMagickRecipe(ModItems.BLUE_FEATHER);
	private static final MagickRecipe feather_y = newMagickRecipe(ModItems.YELLOW_FEATHER);
	private static final MagickRecipe feather_m = newMagickRecipe(ModItems.MAGENTA_FEATHER);
	private static final MagickRecipe wing = newMagickRecipe(ModItems.WING);
	
    public ModCrafting()
    {
    	slot = 0;
    	registerOre();
        registerSmelting();
        CraftingHelper.init();
        registerMagickCrafting();
    }

    public static MagickRecipe newMagickRecipe(Item item, int damage, NBTTagCompound tag)
    {
    	return new MagickRecipe(item, damage, tag);
    }
    
    public static MagickRecipe newMagickRecipe(ItemStack stack)
    {
    	return newMagickRecipe(stack.getItem(), stack.getMetadata(), stack.hasTagCompound() ? stack.getTagCompound() : null);
    }
    
    public static MagickRecipe newMagickRecipe(Item item)
    {
    	return newMagickRecipe(item, 0, null);
    }
    
    private static void registerMagickCrafting()
    {
    	MagickRecipe[] recipe = new MagickRecipe[] {m_wood.copy(), m_wood.copy(), flower_powder.copy(), wind.copy()};
    	ModCrafting.addMagickCrafting(recipe, CraftingHelper.ToolGrower);
    	
    	recipe = new MagickRecipe[] {petal_b.copy(), feather_b.copy(), petal_b.copy(), feather_b.copy(), wing.copy()};
    	ModCrafting.addMagickCrafting(recipe, new Item[] {ModItems.MAGICK_POWER, ModItems.MAGICK_POWER});
    	
    	recipe = new MagickRecipe[] {petal_y.copy(), feather_y.copy(), petal_y.copy(), feather_y.copy(), wing.copy()};
    	ModCrafting.addMagickCrafting(recipe, new Item[] {ModItems.MAGICK_POWER, ModItems.MAGICK_POWER});
    	
    	recipe = new MagickRecipe[] {petal_m.copy(), feather_m.copy(), petal_m.copy(), feather_m.copy(), wing.copy()};
    	ModCrafting.addMagickCrafting(recipe, new Item[] {ModItems.MAGICK_POWER, ModItems.MAGICK_POWER});
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
    
    public static void addMagickCrafting(MagickRecipe[] recipes, ItemStack[] loots)
    {
    	HashSet<MagickRecipe> set = new HashSet<MagickRecipe>();
    	
    	for(int i = 0; i < recipes.length; ++i)
    		set.add(recipes[i]);
    		
    	if(!set.isEmpty())
    	{
    		MagickCrafting.put(Integer.valueOf(slot), set);
    		CraftingLoot.put(Integer.valueOf(slot), loots);
    	}
    	slot++;
    }
    
    public static void addMagickCrafting(MagickRecipe[] recipes, Item[] loots)
    {
    	ItemStack[] stacks = new ItemStack[loots.length];
    	
    	for(int i = 0; i < loots.length; ++i)
    		stacks[i] = new ItemStack(loots[i]);
    	
    	addMagickCrafting(recipes, stacks);
    }
    
    public static void addMagickCrafting(MagickRecipe[] recipes, Item item)
    {
    	ItemStack[] stacks = new ItemStack[] {new ItemStack(item)};
    	addMagickCrafting(recipes, stacks);
    }
    
    public static void addMagickCrafting(MagickRecipe[] recipes, ItemStack item)
    {
    	ItemStack[] stacks = new ItemStack[] {item};
    	addMagickCrafting(recipes, stacks);
    }

	public static ItemStack[] getCraftingLoot(List<ItemStack> stacks)
    {
    	for(int i = 0; i < MagickCrafting.size(); ++i)
    	{
    		HashSet<MagickRecipe> set = (HashSet<MagickRecipe>) MagickCrafting.get(i).clone();
    		
    		List<ItemStack> newStacks = new ArrayList<ItemStack>();
        	newStacks.addAll(stacks);
    		
    		boolean flag = true;
    		while(flag)
    		{
    			flag = false;

    			for(Iterator<MagickRecipe> it = set.iterator(); it.hasNext(); )
        		{
    				MagickRecipe recipe = it.next();
    				boolean flag2 = false;
    				for(int c = 0; c < newStacks.size(); ++c)
    				{
    					if(recipe.equals(newStacks.get(c)))
            			{
            				newStacks.remove(c);
            				if(set.contains(recipe))
            					it.remove();
            				flag = true;
            			}
    				}
        		}
    		}
    		
    		ItemStack[] loots = calculateLoots((HashSet<MagickRecipe>) set.clone(), newStacks, i, stacks);
    		if(loots != null)
    			return loots;
    	}

		List<ItemStack> Materials = new ArrayList<ItemStack>();
		Materials.addAll(stacks);
    	
		for(int i = 0; i < Materials.size(); ++i)
		{
			if(Seccult.rand.nextInt(5) == 0)
				Materials.remove(i);
			else if(Seccult.rand.nextInt(3) == 0)
				Materials.set(i, new ItemStack(ModItems.ANTI_MAGICK));
		}
		
		ItemStack[] stackLoots = new ItemStack[Materials.size()];
		
		for(int i = 0; i < Materials.size(); ++i)
		{
			stackLoots[i] = Materials.get(i);
		}
    	
    	return stackLoots;
    }
    
	public static ItemStack[] calculateLoots(HashSet<MagickRecipe> set, List<ItemStack> newStacks, int slot, List<ItemStack> oldMaterials)
	{
		List<ItemStack> Materials = new ArrayList<ItemStack>();
		Materials.addAll(oldMaterials);
		if(set.isEmpty())
		{
			ItemStack[] stackLoots = CraftingLoot.get(slot).clone();
			if(newStacks.isEmpty())
				return stackLoots;
			else
			{
				if(hasMagickTool(stackLoots))
					modifiyTool(newStacks, stackLoots, slot, true);
				
				return stackLoots;
			}
		}
		else if(set.size() <= 2)
		{
			ItemStack[] stackLoots = CraftingLoot.get(slot).clone();

			if(hasMagickTool(stackLoots))
				modifiyTool(newStacks, stackLoots, slot, false);
			else
			{
				for(int i = 0; i < Materials.size(); ++i)
				{
					if(Seccult.rand.nextInt(5) == 0)
						Materials.remove(i);
					else if(Seccult.rand.nextInt(3) == 0)
						Materials.set(i, new ItemStack(ModItems.ANTI_MAGICK));
				}
				
				stackLoots = new ItemStack[Materials.size()];
				
				for(int i = 0; i < Materials.size(); ++i)
				{
					stackLoots[i] = Materials.get(i);
				}
			}
			
			return stackLoots;
		}
		
		return null;
	}
	
	private static void modifiyTool(List<ItemStack> newStacks, ItemStack[] stackLoots, int slot, boolean upGrade)
	{
		boolean flag = true;
		HashSet<MagickRecipe> newSet = (HashSet<MagickRecipe>) MagickCrafting.get(slot).clone();
		HashMap<ItemStack, Integer> map = new HashMap<>();
		
		while(flag)
		{
			flag = false;
			for(Iterator<MagickRecipe> it = newSet.iterator(); it.hasNext(); )
    		{
				MagickRecipe recipe = it.next();
				for(int c = 0; c < newStacks.size(); ++c)
				{
					if(recipe.equals(newStacks.get(c)))
        			{
        				newStacks.remove(c);
        				if(newSet.contains(recipe))
        					it.remove();
        				for(int in = 0; in < stackLoots.length; ++in)
	    				{
        					if(stackLoots[in].getItem() instanceof ItemMagickTool && (!map.containsKey(stackLoots[in]) || map.get(stackLoots[in]) < 4))
        					{
        						if(upGrade && Seccult.rand.nextInt(2) == 0)
        						{
        							stackLoots[in] = ItemMagickTool.randomUpgrade(stackLoots[in]);
        							map.put(stackLoots[in], map.getOrDefault(stackLoots[in], 0) + 1);
        						}
        						else if(!upGrade)
        							stackLoots[in] = ItemMagickTool.randomDowngrade(stackLoots[in]);
        					}
	    				}
        				flag = true;
        			}
				}
    		}
		}
	}
	
    public static boolean hasMagickTool(ItemStack[] stacks)
    {
    	boolean flag = false;
    	for(int i = 0; i < stacks.length; ++i)
		{
    		ItemStack stack = stacks[i];
    		if(isMagickTool(stack))
    			flag = true;
		}
    	return flag;
    }
    
    public static boolean isMagickTool(Item item)
    {
    	return item instanceof ItemMagickTool;
    }
    
    public static boolean isMagickTool(ItemStack stack)
    {
    	return isMagickTool(stack.getItem());
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
    
    public static class MagickRecipe
    {
    	private final NBTTagCompound tag;
    	private final Item item;
    	private final int damage;
    	
    	public MagickRecipe(Item item)
    	{
    		this(item, 0, null);
    	}
    	
    	public MagickRecipe(Item item, int damage, NBTTagCompound tag) {
    		this.item = item;
    		this.tag = tag;
    		this.damage = damage;
		}
    	
    	public MagickRecipe copy()
    	{
    		return new MagickRecipe(this.item, this.damage, this.tag);
    	}
    	
    	@Override
    	public boolean equals(Object obj) {
    		if(obj instanceof ItemStack)
    		{
    			ItemStack stack = (ItemStack) obj;
    			boolean flag = false;
    			
    			if(stack.getItem() == this.item)
    				flag = true;
    			
    			if(stack.getItem() instanceof ItemBlock && this.item instanceof ItemBlock && ((ItemBlock)stack.getItem()).getBlock() == ((ItemBlock)this.item).getBlock())
    				flag = true;
    			
    			if(flag && stack.getItemDamage() != this.damage)
    				return false;
    			
    			if(this.tag != null && flag)
    			{
    				if(!stack.hasTagCompound())
    					return false;
    				
    				NBTTagCompound nbt = stack.getTagCompound();
    				
    				Set<String> keys = this.tag.getKeySet();
    				for(String s : keys)
    				{
    					if(!nbt.hasKey(s) || !nbt.getTag(s).equals(this.tag.getTag(s)))
    						return false;
    				}
    				
    				return true;
    			}
    			
    			return flag;
    		}
    		return super.equals(obj);
    	}
    	
    	@Override
    	public String toString() {
    		String tag = this.tag == null ? " " : this.tag.toString();
    		
    		String item = this.item == null ? "no Item" : this.item.getRegistryName().toString();
    		
    		if(this.item instanceof ItemBlock)
    			item = ((ItemBlock)this.item).getBlock().getRegistryName().toString();
    			
    		return item + " " + String.valueOf(this.damage) + " " + tag;
    		
    		//return super.toString();
    	}
    }
    
    public static class MagickLoot
    {
    	public MagickLoot(Item item)
    	{
    	}
    }
}
