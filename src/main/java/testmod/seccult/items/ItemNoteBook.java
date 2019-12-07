 package testmod.seccult.items;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Set;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.gui.GuiElementLoader;
import testmod.seccult.client.textlib.TextLib;
import testmod.seccult.init.ModItems;

public class ItemNoteBook extends ItemBase{
	private static final ResourceLocation Note_Prefix = new ResourceLocation(Seccult.MODID, "newKnowladge");
	
	public ItemNoteBook(String name) {
		super(name);
		this.maxStackSize = 1;
		this.addPropertyOverride(Note_Prefix, new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return hasNewKnowladge(stack) ? 1 : 0;
			}
		});
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		setNewNote(TextLib.Category.Introduce, stack, TextLib.Item.NoteBook_base);
		setNewNote(TextLib.Category.Introduce, stack, TextLib.Item.NoteBook_1);
		setNewNote(TextLib.Category.Introduce, stack, TextLib.Item.NoteBook_2);
		return super.getItemStackDisplayName(stack);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
			player.setActiveHand(hand);

			if(stack.getTagCompound().hasKey("Note_New_Knowladge"))
				stack.getTagCompound().removeTag("Note_New_Knowladge");
			
			if(world.isRemote)
			{
				player.openGui(Seccult.instance, GuiElementLoader.GUI_NoteBook, world, (int)player.posX, (int)player.posY, (int)player.posZ);
			}
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack); 
	}
	
	public static void setNewNote(TextLib.Category cate, EntityPlayer player, TextLib.Item item)
	{
		for(int i = 0; i < player.inventory.mainInventory.size(); i++)
		{
			ItemStack stack = player.inventory.mainInventory.get(i);
			if(stack.getItem() == ModItems.NOTEBOOK)
			{
				setNewNote(cate, stack, item);
			}
		}
	}
	
	public static void setNewNote(TextLib.Category cate, EntityPlayer player, String itemName, String string)
	{
		for(int i = 0; i < player.inventory.mainInventory.size(); i++)
		{
			ItemStack stack = player.inventory.mainInventory.get(i);
			if(stack.getItem() == ModItems.NOTEBOOK)
			{
				setNewNote(cate, stack, itemName, string);
			}
		}
	}
	
	public static void setNewNote(TextLib.Category cate, ItemStack book, TextLib.Item item)
	{
		setNewNote(cate, book, item.getItemName(), item.getString());
	}
	
	public static void setNewNote(TextLib.Category cate, ItemStack book, String itemName, String string)
	{
		NBTTagCompound bookTag = null;
		if(!book.hasTagCompound())
			book.setTagCompound(new NBTTagCompound());

		bookTag = book.getTagCompound();
		if(!bookTag.hasKey("Categories"))
			bookTag.setTag("Categories", new NBTTagCompound());
		
		NBTTagCompound allNote = bookTag.getCompoundTag("Categories");
		if(!allNote.hasKey(cate.getName()))
			allNote.setTag(cate.getName(), new NBTTagCompound());
		
		NBTTagCompound category = allNote.getCompoundTag(cate.getName());

		if(!category.hasKey(itemName))
			category.setTag(itemName, new NBTTagList());
		
		NBTTagList noteItem = category.getTagList(itemName, 8);

		boolean hasTag = false;
		
		for(int i = 0; i < noteItem.tagCount(); ++i)
		{
			NBTTagString tagString = (NBTTagString) noteItem.get(i);
			if(tagString.getString().equals(string))
			{
				hasTag = true;
			}
		}

		if(!hasTag)
		{
			noteItem.appendTag(new NBTTagString(string));
			book.getTagCompound().setBoolean("Note_New_Knowladge", true);
		}
	}
	
	public static NBTTagCompound getAllCategories(ItemStack stack)
	{
		NBTTagCompound bookTag = null;
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());

		bookTag = stack.getTagCompound();
		
		if(!bookTag.hasKey("Categories"))
			bookTag.setTag("Categories", new NBTTagCompound());
			
		return bookTag.getCompoundTag("Categories");
	}
	
	public static NBTTagCompound getCategory(NBTTagCompound list, String CateString)
	{
		if(list.hasKey(CateString))
			return list.getCompoundTag(CateString);

		return null;
	}
	
	public static List<NBTTagList> getAllItemNote(NBTTagCompound list)
	{
		List<NBTTagList> Items = new ArrayList<NBTTagList>();
		
		Set<String> keys = list.getKeySet();
		for(Iterator<String> k = keys.iterator(); k.hasNext();)
		{
			
			Items.add(list.getTagList(k.next(), 8));
		}
		
		return Items;
	}
	
	public static NBTTagList getNoteItem(NBTTagCompound list, String itemName)
	{
		if(list.hasKey(itemName))
			return list.getTagList(itemName, 8);
		
		return new NBTTagList();
	}
	
	public static String[] getItemNote(NBTTagList list)
	{
		String[] newString = new String[list.tagCount()];
		
		for(int i = 0; i < list.tagCount(); ++i)
		{
			NBTTagString string = (NBTTagString) list.get(i);
			newString[i] = string.getString();
		}
		
		return newString;
	}
	
	public static boolean hasNewKnowladge(ItemStack stack)
	{
		boolean b = false;
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("Note_New_Knowladge") && stack.getTagCompound().getBoolean("Note_New_Knowladge"))
			b = true;
		return b;
	}
}
