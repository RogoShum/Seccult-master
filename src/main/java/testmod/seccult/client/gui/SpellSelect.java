package testmod.seccult.client.gui;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.inventory.Container;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.NetworkPlayerTransMagickToClient;

public class SpellSelect extends Container
{
    public SpellSelect(EntityPlayer player)
    {
        super();
        PlayerData data = PlayerDataHandler.get(player);
        NBTTagList magick = data.getAllMagick();
        for(int i = 0; i < magick.tagCount(); i++)
        {
        	decompiler(magick.getCompoundTagAt(i), player.getUniqueID());
        }
    }
    
    @Override
    public ItemStack transferStackInSlot(EntityPlayer playerIn, int index)
    {
        return null;
    }


    
    public static void decompiler(NBTTagCompound magickNbt, UUID uuid)
    {
    	NBTTagList LoadMagick = magickNbt.getTagList("Magick", 10);
    	NBTTagList LoadSelect = magickNbt.getTagList("Selector", 10);
    	
    	int amount = LoadMagick.tagCount();
    	
    	int[][] MagickThing = new int[4][amount];
    	
		int[][] SelectorList = new int[amount][];
		int[][] SelectorPowerList = new int[amount][];
		int[][] SelectorAttributeList = new int[amount][];
		
		List<int[]> ListMagick = new ArrayList<>();
		
		List<int[]> ListSelector = new ArrayList<>();
		List<int[]> ListSelectorPower = new ArrayList<>();
		List<int[]> ListSelectorAttribute = new ArrayList<>();
		
		int MaxLength = 0;
		
    	for(int i = 0; i < magickNbt.getTagList("Magick", 10).tagCount(); i++)
    	{
    		NBTTagCompound MagickNBT = LoadMagick.getCompoundTagAt(i);
    		if(MagickNBT.hasNoTags())
				return;
    		
    		List<Integer> Selectors = new ArrayList<>();
    		List<Integer> SelectorPowers = new ArrayList<>();
    		List<Integer> SelectorAttributes = new ArrayList<>();
    		
    		int slot = MagickNBT.getInteger("Slot");
    		
    		for(int x = 0; x < LoadSelect.tagCount(); x++)
    		{
    			if(LoadSelect.getCompoundTagAt(x).getInteger("Slot") == slot) {
    				NBTTagCompound SelectNBT = LoadSelect.getCompoundTagAt(x);
    				Selectors.add(SelectNBT.getInteger("Selector"));
    				SelectorPowers.add(SelectNBT.getInteger("SelectorPower"));
    				SelectorAttributes.add(SelectNBT.getInteger("SelectorAttribute"));
    			}
    		}
    		
			if(Selectors.size() > MaxLength)
				MaxLength = Selectors.size();
    		
			int[] Magick = {MagickNBT.getInteger("Magick"),
					MagickNBT.getInteger("MagickPower"),
					MagickNBT.getInteger("MagickAttribute"),
					Selectors.size()};
			
			ListMagick.add(Magick);

			int[] QAQ = new int[Selectors.size()];
			int[] QwQ = new int[Selectors.size()];
			int[] OAO= new int[Selectors.size()];
			
			for(int len = 0; len < Selectors.size(); len++)
			{
				QAQ[len] = Selectors.get(len);
				QwQ[len] = SelectorPowers.get(len);
				OAO[len] = SelectorAttributes.get(len);
			}
			
			ListSelector.add(QAQ);
			ListSelectorPower.add(QwQ);
			ListSelectorAttribute.add(OAO);
    	}
		
		for(int i = 0; i < amount; i++)
		{
			int[] m = ListMagick.get(i);
			for(int z = 0; z < 4; z ++)
        	{
				MagickThing[z][i] = m[z];
        	}
			int L = MagickThing[3][i];
			int[] im = ListSelector.get(i);
			SelectorList[i] = new int[L];
			SelectorPowerList[i] = new int[L];
			SelectorAttributeList[i] = new int[L];
			for(int z = 0; z < im.length; z++)
			{
				SelectorList[i][z] = ListSelector.get(i)[z];
				SelectorPowerList[i][z] = ListSelectorPower.get(i)[z];
				SelectorAttributeList[i][z] = ListSelectorAttribute.get(i)[z];
			}
		}
		NetworkHandler.getNetwork().sendToAll(new NetworkPlayerTransMagickToClient(uuid, MagickThing, SelectorList, SelectorPowerList, SelectorAttributeList, amount));
    }
    
	@Override
	public boolean canInteractWith(EntityPlayer playerIn) {
		
		return canPlayerInteract(playerIn);
	}
	
	public boolean canPlayerInteract(EntityPlayer player) {
		return !player.isDead;
	}
}