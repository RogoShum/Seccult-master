package testmod.seccult.api.accessorie;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map.Entry;
import java.util.Random;

import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiScreen;
import net.minecraft.client.gui.inventory.GuiInventory;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ResourceLocation;
import testmod.seccult.ClientProxy;
import testmod.seccult.Seccult;
import testmod.seccult.client.gui.GuiElementLoader;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.Accessories.ItemAccessories;

public class PlayerAccessorieHandler {
	
	private static HashMap<Integer, PlayerAccessorie> playerData = new HashMap();
	
	public static PlayerAccessorie get(EntityPlayer player) {
		int key = getKey(player);
		if(!playerData.containsKey(key))
			playerData.put(key, new PlayerAccessorie(player));

		PlayerAccessorie data = playerData.get(key);
		if(data != null && data.player != null && data.player != player) {
			NBTTagCompound cmp = new NBTTagCompound();
			data.writeToNBT(cmp);
			playerData.remove(key);
			data = get(player);
			data.readFromNBT(cmp);
		}

		return data;
	}
	
	public static void cleanup() {
		List<Integer> removals = new ArrayList();
		Iterator<Entry<Integer, PlayerAccessorie>> it = playerData.entrySet().iterator();
		while(it.hasNext()) {
			Entry<Integer, PlayerAccessorie> item = it.next();
			PlayerAccessorie d = item.getValue();
			if(d != null && d.player == null)
				removals.add(item.getKey());
		}

		for(int i : removals)
			playerData.remove(i);
	}
	
	private static int getKey(EntityPlayer player) {
		return player.hashCode() << 1 + (player.getEntityWorld().isRemote ? 1 : 0);
	}
	
	public static NBTTagCompound getDataCompoundForPlayer(EntityPlayer player) {
		NBTTagCompound forgeData = player.getEntityData();
		
		if(!forgeData.hasKey(EntityPlayer.PERSISTED_NBT_TAG))
			forgeData.setTag(EntityPlayer.PERSISTED_NBT_TAG, new NBTTagCompound());

		NBTTagCompound persistentData = forgeData.getCompoundTag(EntityPlayer.PERSISTED_NBT_TAG);
		if(!persistentData.hasKey(Seccult.AccessorieData))
			persistentData.setTag(Seccult.AccessorieData, new NBTTagCompound());

		return persistentData.getCompoundTag(Seccult.AccessorieData);
	}
	
	public static class PlayerAccessorie{
		static Random rand = new Random();
		private static final String TAG_ACCESSORIES = "Accessories";
		private static final String TAG_ITEM_SLOT = "Slot";
		private static final String TAG_ITEM_DAMAGE = "Damege";
		private static final String TAG_ITEM_COUNT = "Count";
		private static final String TAG_ITEM_TAG = "tag";
		private static final String TAG_ITEM_ID = "id";
		
		ItemStack[] itemList = new ItemStack[8];
		
		private EntityPlayer player;
		private final boolean client;
		
		public PlayerAccessorie(EntityPlayer player) {
			this.player = player;
			client = player.getEntityWorld().isRemote;

			load();
		}
		
		public void load() {
			if(!client) {
				if(this.player != null) {
					NBTTagCompound cmp = getDataCompoundForPlayer(this.player);
					readFromNBT(cmp);
				}
			}
		}
		
		private void readFromNBT(NBTTagCompound cmp) {
			if(cmp.hasKey(TAG_ACCESSORIES))
			{
				NBTTagList tagList = cmp.getTagList(TAG_ACCESSORIES, 10);
				for(int i = 0; i < tagList.tagCount(); i++)
				{
					NBTTagCompound tag = tagList.getCompoundTagAt(i);
					Item item = Item.getByNameOrId(tag.getString(TAG_ITEM_ID));
					ItemStack stack = new ItemStack(item, tag.getInteger(TAG_ITEM_COUNT), tag.getInteger(TAG_ITEM_DAMAGE));
					if(tag.hasKey(TAG_ITEM_TAG))
						stack.setTagCompound(tag.getCompoundTag(TAG_ITEM_TAG));
					itemList[tag.getInteger(TAG_ITEM_SLOT)] = stack;
				}
			}
		}
		
		public void save() {
			if(!client) {
				if(this.player != null) {
					NBTTagCompound cmp = getDataCompoundForPlayer(player);
					writeToNBT(cmp);
				}
			}
		}

		public void tick()
		{	
			for(int i = 0; i < itemList.length; i++)
			{
				if(itemList[i]!=null)
				{
					itemList[i].getItem().onArmorTick(player.world, player, itemList[i]);
				}
			}
			save();
		}
		
		private void writeToNBT(NBTTagCompound cmp) {
			NBTTagList tag = new NBTTagList();
			for(int i = 0; i < itemList.length; i++)
			{
				if(itemList[i] != null) {
					NBTTagCompound nbt = new NBTTagCompound();
					ItemStack item = itemList[i];
					ResourceLocation id = item.getItem().getRegistryName();
					if(id.equals(Items.AIR.getRegistryName()))
						continue;
					nbt.setString(TAG_ITEM_ID, id.toString());
					nbt.setInteger(TAG_ITEM_COUNT, item.getCount());
					if(item.getCount() == 0)
						continue;
					nbt.setInteger(TAG_ITEM_SLOT, i);
					nbt.setInteger(TAG_ITEM_DAMAGE, item.getItemDamage());
					if(item.hasTagCompound())
						nbt.setTag(TAG_ITEM_TAG, item.getTagCompound());
					tag.appendTag(nbt);
				}
			}
			cmp.setTag(TAG_ACCESSORIES, tag);
		}
		
		public ItemStack[] getAccessories()
		{
			return itemList;
		}
		
		public boolean addAccessorie(ItemStack access)
		{
			boolean flag = false;
			for(int i = 0; i < itemList.length; i++)
			{
				if(itemList[i] == null)
				{
					itemList[i] = access.copy();
					return true;
				}
			}
			return flag;
		}
		
		public void setAccessorie(ItemStack[] access)
		{
			this.itemList = access;
		}
		
		public boolean addAccessorie(ItemStack access, int slot)
		{
			return addAccessorie(access, slot, false);
		}
		
		public boolean addAccessorie(ItemStack access, int slot, boolean force)
		{
			if(force)
			{
				itemList[slot] = access;
				return true;
			}
			
			if(itemList[slot] == null)
			{
				itemList[slot] = access;
				return true;
			}
			return false;
		}
		
		public void cleanAccessories()
		{
			itemList = new ItemStack[8];
		}
	}
}
