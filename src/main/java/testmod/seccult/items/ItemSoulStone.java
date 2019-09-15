package testmod.seccult.items;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import testmod.seccult.init.ModItems;

public class ItemSoulStone extends ItemBase{

	public ItemSoulStone(String name) {
		super(name);
	}

	public static boolean putSoul(ItemStack stack, EntityLivingBase living)
	{
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		if(stack.getTagCompound().hasKey("soul"))
			return false;
		
		if(stack.getItem() != null && stack.getItem() instanceof ItemSoulStone && living != null)
		{
			 ResourceLocation className = EntityList.getKey(living.getClass());
			 if(className != null) 
			 {
				 NBTTagCompound tag = new NBTTagCompound();
				 living.writeToNBT(tag);
				 tag.setString("id", className.toString());
				 stack.getTagCompound().setTag("soul", tag);
				 stack.setItemDamage(1);
				 return true;
			 }
		}
		
		return false;
	}
	
	public static EntityLivingBase getSoul(ItemStack stack, World world)
	{
		if(stack.getItem() != null && stack.getItem() instanceof ItemSoulStone)
		{
		
			if(!stack.hasTagCompound())
			{
				stack.setTagCompound(new NBTTagCompound());
				return null;
			}
			
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt != null && nbt.hasKey("soul"))
			{
				NBTTagCompound soul = nbt.getCompoundTag("soul");
				if(soul.getString("id").equals(" "))
					return null;

				EntityLivingBase living = null;
				living = (EntityLivingBase) EntityList.createEntityFromNBT(soul, world);
				if(living != null) {
					living.readFromNBT(soul);
					return living;
				}
			}
		}
		return null;
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		
		if(playerIn.getHeldItemMainhand().getItem() == ModItems.SoulStone)
		{
			Entity e = ItemSoulStone.getSoul(playerIn.getHeldItemMainhand(), worldIn);
			System.out.println(e);
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}
}
