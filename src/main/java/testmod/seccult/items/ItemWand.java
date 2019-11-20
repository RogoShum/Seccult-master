package testmod.seccult.items;

import java.util.UUID;

import javax.annotation.Nonnull;
import javax.annotation.Nullable;

import org.lwjgl.util.glu.Disk;

import net.minecraft.block.state.IBlockState;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.renderer.GlStateManager.DestFactor;
import net.minecraft.client.renderer.GlStateManager.SourceFactor;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.client.renderer.texture.TextureManager;
import net.minecraft.client.settings.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.Blocks;
import net.minecraft.item.EnumAction;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagFloat;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.ClientProxy;
import testmod.seccult.Seccult;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerSpellReleaseTool;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.api.PlayerSpellReleaseTool.PlayerSpellTool;
import testmod.seccult.client.gui.GuiElementLoader;
import testmod.seccult.init.ModBlocks;
import testmod.seccult.init.ModItems;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.MagickCompiler;
import testmod.seccult.magick.active.Magick;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.NetworkPlayerWandData;
import testmod.seccult.network.TransPoint;

public class ItemWand extends ItemBase{
	public static final ResourceLocation wand_prefix = new ResourceLocation(Seccult.MODID, "wandstyle");
	
	public int cycleTime;
	
	public ItemWand(String name) {
		super(name);
		this.maxStackSize = 1;
		this.setMaxDamage(0);
		this.addPropertyOverride(wand_prefix, new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				return getWandS(stack) ? stack.getTagCompound().getInteger("WandStyle") : -1F;
			}
		});
	}
	
	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(stack.hasTagCompound())
		{
			return stack.getTagCompound().getString("SpellName");
		}
		return super.getItemStackDisplayName(stack);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
			player.setActiveHand(hand);

	        if (!world.isRemote && player.isSneaking())
	        {
	        	PlayerSpellReleaseTool.get(player).switchSpell();
	            return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(hand));
	        }
	        else
	        	PlayerSpellReleaseTool.get((EntityPlayer)player).releaseSpell();
	        return ActionResult.newResult(EnumActionResult.FAIL, player.getHeldItem(hand));
	}
	
	@Override
	public int getMaxItemUseDuration(ItemStack stack) {
		return 7200;
	}
	
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		super.onUsingTick(stack, player, count);
		cycleTime++;
		if(cycleTime > 20 && player instanceof EntityPlayer && player.getHeldItemMainhand() == stack)
			PlayerSpellReleaseTool.get((EntityPlayer)player).releaseSpell();
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		cycleTime=0;
		super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
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
		
		if(!worldIn.isRemote && entityIn instanceof EntityPlayer) {
			EntityPlayer player = (EntityPlayer) entityIn;
			
			PlayerSpellTool tool = PlayerSpellReleaseTool.get(player);
			if(tool!= null)
			{
				stack.getTagCompound().setInteger("MagickColor", tool.getSpellColorInt());
				stack.getTagCompound().setString("SpellName", tool.getMagickName());
			}
		}
		
		if(entityIn instanceof EntityPlayer && (!hasUUID || (hasUUID && id != entityIn.getUniqueID())))
		{
			
			EntityPlayer player = (EntityPlayer) entityIn;
			
			PlayerData data = PlayerDataHandler.get(player);
			if(!worldIn.isRemote)
			NetworkHandler.getNetwork().sendTo(new NetworkPlayerWandData(data.getColor2(), data.getColor3(), data.getColor4(), data.getWandStyle()), (EntityPlayerMP) player); 
			setWandStyle(stack, data.getColor2(), data.getColor3(), data.getColor4());
			setWandS(stack, data.getWandStyle());
			setLastPlayerUUID(stack, player.getUniqueID());
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
	
	@SideOnly(Side.CLIENT)
	public static int getMagickColor(ItemStack stack)
	{
		PlayerSpellTool tool = PlayerSpellReleaseTool.get(Minecraft.getMinecraft().player);
		return tool.getSpellColorInt();
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
