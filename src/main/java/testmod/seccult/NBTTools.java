package testmod.seccult;

import net.minecraft.init.Items;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;

public class NBTTools {
	private static final String TAG_ITEM_SLOT = "Slot";
	private static final String TAG_ITEM_DAMAGE = "Damege";
	private static final String TAG_ITEM_COUNT = "Count";
	private static final String TAG_ITEM_TAG = "tag";
	private static final String TAG_ITEM_ID = "id";
	
	public static void getNBT() {}
	
	public static NBTTagCompound setItemStackToTag(ItemStack stack) 
	{
		NBTTagCompound nbt = new NBTTagCompound();
		ItemStack item = stack;
		ResourceLocation id = item.getItem().getRegistryName();
		if(id.equals(Items.AIR.getRegistryName()))
			return null;
		nbt.setString(TAG_ITEM_ID, id.toString());
		nbt.setInteger(TAG_ITEM_COUNT, item.getCount());
		if(item.getCount() == 0)
			return null;
		nbt.setInteger(TAG_ITEM_DAMAGE, item.getItemDamage());
		if(item.hasTagCompound())
			nbt.setTag(TAG_ITEM_TAG, item.getTagCompound());
		return nbt;
	}
	
	public static ItemStack getItemStackFromTag(NBTTagCompound tag) 
	{
		Item item = Item.getByNameOrId(tag.getString(TAG_ITEM_ID));
		ItemStack stack = new ItemStack(item, tag.getInteger(TAG_ITEM_COUNT), tag.getInteger(TAG_ITEM_DAMAGE));
		if(tag.hasKey(TAG_ITEM_TAG))
			stack.setTagCompound(tag.getCompoundTag(TAG_ITEM_TAG));
		return stack;
	}
	
	public static NBTTagCompound getStackTag(ItemStack stack)
	{
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		return stack.getTagCompound();
	}
}
