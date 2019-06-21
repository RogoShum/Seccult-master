package testmod.seccult.items;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.api.PlayerMagickDataHandler;
import testmod.seccult.client.gui.GuiElementLoader;
import testmod.seccult.magick.ActiveHandler;
import testmod.seccult.magick.ImplemrntationHandler;
import testmod.seccult.magick.MagickHandler;

public class ItemWand extends ItemBase{
	public static final ResourceLocation wand_prefix = new ResourceLocation(Seccult.MODID, "wandstyle");
	public ItemWand(String name) {
		super(name);
		this.maxStackSize = 1;
		this.addPropertyOverride(wand_prefix, new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return getWandS(stack) ? stack.getTagCompound().getInteger("WandStyle") : -1F;
			}
		});
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
			player.setActiveHand(hand);
			
			if(!stack.getTagCompound().hasKey("Slot"))
				stack.getTagCompound().setInteger("Slot", 1);
	        if (!world.isRemote)
	        {
	            if (player.isSneaking())
	            {
	            	PlayerMagickDataHandler.setMagickData(player, ActiveHandler.DamageMagick);
	            	PlayerMagickDataHandler.setMagickData(player, ActiveHandler.FlameMagick);
	            	PlayerMagickDataHandler.setMagickData(player, ImplemrntationHandler.FocuseI);
	            	PlayerMagickDataHandler.setMagickData(player, ImplemrntationHandler.CircleI);
	            	PlayerMagickDataHandler.setMagickData(player, ImplemrntationHandler.GroupI);
	            	PlayerMagickDataHandler.setMagickData(player, ImplemrntationHandler.ProjectileI);
	            }
	            else
	            {
	            	MagickHandler.decompileMagick(getMagickCode(stack), player);
	            }
	        }
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		boolean hasUUID = stack.getTagCompound().hasKey("UUIDLeast") && stack.getTagCompound().hasKey("UUIDMost");
		UUID id = null;
		if(hasUUID)
		{
			id = new UUID(stack.getTagCompound().getLong("UUIDMost"), stack.getTagCompound().getLong("UUIDLeast"));
		}
		if(entityIn instanceof EntityPlayer && (!hasUUID || (hasUUID && id != entityIn.getUniqueID())))
		{
			EntityPlayer player = (EntityPlayer) entityIn;
			PlayerData data = PlayerDataHandler.get(player);
			setWandStyle(stack, data.getColor2(), data.getColor3(), data.getColor4());
			setWandS(stack, data.getWandStyle());
			setLastPlayerUUID(stack, player.getUniqueID());
		}
	}
	
	private static NBTTagList getMagickCode(ItemStack stack)
	{
		NBTTagCompound nbttag = stack.getTagCompound();
		if(nbttag == null || !nbttag.hasKey("Slot"))
			return null;
		
		NBTTagList magicklist = MagickHandler.getMagickCode(nbttag);
		if(magicklist == null)
			return null;
		for(int i = 0; i < magicklist.tagCount(); i++)
		{
			NBTTagCompound nbt = magicklist.getCompoundTagAt(i);
			if(nbt.getInteger("Slot") == nbttag.getInteger("Slot")) 
			{
				return nbt.getTagList("MagickList", 10);
			}
		}
		return null;
	}
	
	
	private void setWandStyle(ItemStack stack, int color2, int color3, int color4)
	{
		NBTTagList list = new NBTTagList();
		
		for(int i = 2; i < 5; i++) {
			NBTTagCompound nbt = new NBTTagCompound();
			nbt.setInteger("number", i);
			switch(i)
			{
				case 2:
					nbt.setInteger("color", color2);
					break;
				
				case 3:
					nbt.setInteger("color", color3);
					break;
					
				case 4:
					nbt.setInteger("color", color4);
					break;
			}
			list.appendTag(nbt);
		}
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		stack.getTagCompound().setTag("WandColor", list);
	}
	
	public static int getMagickColor(ItemStack stack)
	{
		NBTTagList magicklist = getMagickCode(stack);
		if(magicklist == null)
			return 1;
		
		NBTTagCompound nbt = magicklist.getCompoundTagAt(0);
		return new ActiveHandler().getAttributeFromName(nbt.getString("Magick")).getColor();
	}
	
	public static int getWandStyle(ItemStack stack, int tintIndex)
	{
		if(stack.getTagCompound() == null)
			return 0;
		NBTTagList list = stack.getTagCompound().getTagList("WandColor", 10);
		if(list != null)
		{
			for(int i = 0; i < list.tagCount(); i++)
			{
				if(list.getCompoundTagAt(i).getInteger("number") == tintIndex) {
					return list.getCompoundTagAt(i).getInteger("color");
				}
			}
		}
		return 0;
	}
	
	public static UUID getLastPlayerUUID(ItemStack stack)
	{
		if(stack.getTagCompound() == null)
			return null;
		if(stack.getTagCompound().hasKey("UUIDLeast"))
		{
			long least = stack.getTagCompound().getLong("UUIDLeast");
			long most = stack.getTagCompound().getLong("UUIDMost");
			UUID id = new UUID(most, least);
			return id;
		}
		
		return null;
	}
	
	public void setLastPlayerUUID(ItemStack stack, UUID ID) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setLong("UUIDLeast", ID.getLeastSignificantBits());
		stack.getTagCompound().setLong("UUIDMost", ID.getMostSignificantBits());
	}
	
	@Nullable
	private static boolean getWandS(ItemStack stack) {
		return stack.hasTagCompound() && stack.getTagCompound().hasKey("WandStyle");
	}
	
	public void setWandS(ItemStack stack, int ID) {
		if (!stack.hasTagCompound()) {
			stack.setTagCompound(new NBTTagCompound());
		}
		stack.getTagCompound().setInteger("WandStyle", ID);
	}
}
