package testmod.seccult.items.Accessories;

import java.util.List;

import javax.annotation.Nullable;

import net.minecraft.client.resources.I18n;
import net.minecraft.client.util.ITooltipFlag;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.IItemPropertyGetter;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;
import net.minecraftforge.event.entity.living.LivingAttackEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.api.accessorie.PlayerAccessorieHandler;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.armor.MagickArmor;

@Mod.EventBusSubscriber(modid = Seccult.MODID)
public class AFriendshipRing extends ItemAccessories{
	private static final ResourceLocation soul_Prefix = new ResourceLocation(Seccult.MODID, "hasentity");
	
	public AFriendshipRing(String name) {
		super(name);
		this.maxStackSize = 1;

		this.addPropertyOverride(soul_Prefix, new IItemPropertyGetter() {
			@SideOnly(Side.CLIENT)
			public float apply(ItemStack stack, @Nullable World worldIn, @Nullable EntityLivingBase entityIn) {
				if(entityIn != null)
					return getEntity(stack, entityIn.world) != null ? 1 : 0;
				else
					return getEntity(stack, worldIn) != null ? 1 : 0;
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
		
		if(stack.getTagCompound().hasKey("StoredEntity"))
			return false;
		
		if(stack.getItem() != null && stack.getItem() instanceof AFriendshipRing && living != null)
		{
			 ResourceLocation className = EntityList.getKey(living.getClass());
			 if(className != null) 
			 {
				 NBTTagCompound tag = new NBTTagCompound();
				 living.writeToNBT(tag);
				 tag.setString("id", className.toString());
				 stack.getTagCompound().setTag("StoredEntity", tag);
				 return true;
			 }
		}
		
		return false;
	}
	
	@Override
	@SideOnly(Side.CLIENT)
	public void addInformation(ItemStack stack, World worldIn, List<String> tooltip, ITooltipFlag flagIn) {
		super.addInformation(stack, worldIn, tooltip, flagIn);
		if(getEntity(stack, worldIn) != null)
			MagickArmor.addStringToTooltip(I18n.format(getEntity(stack, worldIn).getName()), tooltip);
	}
	
	public static EntityLivingBase getEntity(ItemStack stack, World world)
	{
		if(stack.getItem() != null && stack.getItem() instanceof AFriendshipRing)
		{
		
			if(!stack.hasTagCompound())
			{
				stack.setTagCompound(new NBTTagCompound());
				return null;
			}
			
			NBTTagCompound nbt = stack.getTagCompound();
			if (nbt != null && nbt.hasKey("StoredEntity"))
			{
				NBTTagCompound soul = nbt.getCompoundTag("StoredEntity");
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
	public boolean itemInteractionForEntity(ItemStack stack, EntityPlayer playerIn, EntityLivingBase target,
			EnumHand hand) {
		if(getEntity(playerIn.getHeldItemMainhand(), playerIn.world) == null)
		{
			return putSoul(playerIn.getHeldItemMainhand(), target);
		}
		return super.itemInteractionForEntity(stack, playerIn, target, hand);
	}
	
	@SubscribeEvent
	public static void attackEvent(LivingAttackEvent event)
	{
		if(event.getSource().getImmediateSource() == null)
			return;
		if(event.getEntityLiving() instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) event.getEntityLiving();
			ItemStack[] stack = PlayerAccessorieHandler.get(player).getAccessories();
			for(int i = 0; i < stack.length; i++)
			{
				if(stack[i] != null && stack[i].getItem() == ModItems.FRIENDSHIP_RING)
				{
					if(getEntity(stack[i], player.world) != null && event.getSource().getImmediateSource().getClass() == getEntity(stack[i], player.world).getClass())
						event.setCanceled(true);
				}
			}
		}
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}
}
