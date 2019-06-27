package testmod.seccult.items;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.client.FX.LightFX;
import testmod.seccult.client.entity.model.ModelLight;
import testmod.seccult.events.PlayerDataUpdateEvent;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.MagickCompiler;
import testmod.seccult.magick.active.Magick;
import testmod.seccult.magick.magickState.StateManager;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.NetworkPlayerMagickData;

public class ItemWand extends ItemBase{
	public static final ResourceLocation wand_prefix = new ResourceLocation(Seccult.MODID, "wandstyle");
	
	private NBTTagList MagickList;
	private int MagickColor;
	
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
			
			/*PlayerDataHandler.get(player).addMagickData(0);
			PlayerDataHandler.get(player).addMagickData(1);
			PlayerDataHandler.get(player).addMagickData(2);
			PlayerDataHandler.get(player).addMagickData(3);
			PlayerDataHandler.get(player).addMagickData(4);
			PlayerDataHandler.get(player).addMagickData(5);
			PlayerDataHandler.get(player).addMagickData(6);
			PlayerDataHandler.get(player).addMagickData(7);
			PlayerDataHandler.get(player).addMagickData(8);
			PlayerDataHandler.get(player).addMagickData(9);
			PlayerDataHandler.get(player).addMagickData(10);*/
			
			double[] pos = new double[3], vec = new double[3];
			pos[0] = player.posX;
			pos[1] = player.posY;
			pos[2] = player.posZ;
			float[] color = {getWandStyle(stack, 2), getWandStyle(stack, 3), getWandStyle(stack, 4)};
			//System.out.println(getWandStyle(stack, 2) +"    " +  getWandStyle(stack, 3) +"    " + getWandStyle(stack, 4));
            NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, 0, 100));
			
	        if (!world.isRemote && MagickList != null)
	        {
	        	int slot = stack.getTagCompound().getInteger("Slot");
	            if (player.isSneaking())
	            {
	            		stack.getTagCompound().setInteger("Slot", slot + 1);
	            	if(slot > MagickList.tagCount() - 2)
	            		stack.getTagCompound().setInteger("Slot", 0);
	            }
	            else
	            {
	            	MagickCompiler ma = new MagickCompiler();
	            	ma.pushMagickData(MagickList.getCompoundTagAt(slot), player);
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
		//entityIn.motionY = -0.5;
		if(hasUUID)
		{
			id = new UUID(stack.getTagCompound().getLong("UUIDMost"), stack.getTagCompound().getLong("UUIDLeast"));
		}
		if(!worldIn.isRemote && entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			PlayerData data = PlayerDataHandler.get(player);
			MagickList = data.getAllMagick();
			
			if(MagickList != null) {
			NBTTagCompound MagickNBT = MagickList.getCompoundTagAt(0);
			Magick getMagick = ModMagicks.getAttributeFromName(
					ModMagicks.GetMagickStringByID(
					MagickNBT.getInteger("Magick")));
			//int color = getMagick.getColor();
			//stack.getTagCompound().setInteger("MagickColor", getMagick.getColor());
			}
		}
		if(entityIn instanceof EntityPlayer && (!hasUUID || (hasUUID && id != entityIn.getUniqueID())))
		{
			EntityPlayer player = (EntityPlayer) entityIn;
			PlayerData data = PlayerDataHandler.get(player);
			setWandStyle(stack, data.getColor2(), data.getColor3(), data.getColor4());
			setWandS(stack, data.getWandStyle());
			setLastPlayerUUID(stack, player.getUniqueID());
			if(!stack.getTagCompound().hasKey("Slot"))
				stack.getTagCompound().setInteger("Slot", 0);
		}
	}

    protected NBTTagList newFloatNBTList(float... numbers)
    {
        NBTTagList nbttaglist = new NBTTagList();

        for (float f : numbers)
        {
            nbttaglist.appendTag(new NBTTagFloat(f));
        }

        return nbttaglist;
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
		if(!stack.hasTagCompound())
			return 1;
		NBTTagCompound nbt = stack.getTagCompound();
		if(!nbt.hasKey("MagickColor"))
			return 1;
		return nbt.getInteger("MagickColor");
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
