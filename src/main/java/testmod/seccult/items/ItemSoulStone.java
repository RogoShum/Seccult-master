package testmod.seccult.items;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.init.ModSounds;
import testmod.seccult.items.armor.MagickArmor;

public class ItemSoulStone extends ItemBase{
	private static final ResourceLocation soul_Prefix = new ResourceLocation(Seccult.MODID, "hassoul");
	
	public ItemSoulStone(String name) {
		super(name);
		this.maxStackSize = 1;

		this.addPropertyOverride(soul_Prefix, new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				if(entityIn != null)
					return getSoul(stack, entityIn.world) != null ? 1 : 0;
				else
					return getSoul(stack, worldIn) != null ? 1 : 0;
			}
		});
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
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
				 return true;
			 }
		}
		
		return false;
	}
	
	@Override
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if(getSoul(stack, worldIn) != null)
			MagickArmor.addStringToTooltip(I18n.format(getSoul(stack, worldIn).getName()), tooltip);
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
		worldIn.playSound((EntityPlayer)null, new BlockPos(playerIn.posX, playerIn.posY, playerIn.posZ), ModSounds.gatorix_spawn, SoundCategory.NEUTRAL, 2.0F, 2.0F);
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}
}
