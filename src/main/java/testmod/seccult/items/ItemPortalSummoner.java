 package testmod.seccult.items;

import java.util.List;

import javax.annotation.Nonnull;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.minecraftforge.common.DimensionManager;
import testmod.seccult.entity.EntityBorderCrosser;
import testmod.seccult.entity.SpiritManager;
import testmod.seccult.entity.livings.EntitySpaceGatorix;
import testmod.seccult.entity.livings.EntitySpirit;
import testmod.seccult.magick.implementation.ImplementationFocused;
import testmod.seccult.world.gen.DimensionMagic;

public class ItemPortalSummoner extends ItemBase{

	public ItemPortalSummoner(String name) {
		super(name);
		this.maxStackSize = 10;
	}

	@Override
	public String getItemStackDisplayName(ItemStack stack) {
		if(stack.hasTagCompound() && stack.getTagCompound().hasKey("Dimensions"))
		{
			int[] dims = stack.getTagCompound().getIntArray("Dimensions");
			int dim = dims[stack.getTagCompound().getInteger("DimSlot")];
		
			return "Dimension " + String.valueOf(dim);
		}
		
		return super.getItemStackDisplayName(stack);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
			player.setActiveHand(hand);
			addDimensions(DimensionMagic.MAGIC_ID, player.getHeldItem(hand));
			if(!player.world.isRemote)
			{
				if(player.isSneaking())
				{
					int[] dims = player.getHeldItem(hand).getTagCompound().getIntArray("Dimensions");
					int slot = player.getHeldItem(hand).getTagCompound().getInteger("DimSlot");
					if(slot+1 < dims.length)
						player.getHeldItem(hand).getTagCompound().setInteger("DimSlot", slot+1);
					else
						player.getHeldItem(hand).getTagCompound().setInteger("DimSlot", 0);
				}
				else
				{
					BlockPos pos = ImplementationFocused.getBlockLookedAt(player, 32);
					EntityBorderCrosser crosser = null;
					int[] dims = player.getHeldItem(hand).getTagCompound().getIntArray("Dimensions");
					int dim = dims[player.getHeldItem(hand).getTagCompound().getInteger("DimSlot")];
					crosser = new EntityBorderCrosser(player.world, dim);
					crosser.setPosition(pos.getX()+0.5, pos.getY()+1, pos.getZ()+0.5);
					if(dim != player.dimension)
					{
						List<Entity> list = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(20));
						
						for(int i = 0; i < list.size(); ++i)
						{
							if(list.get(i) instanceof EntitySpaceGatorix)
								((EntitySpaceGatorix)list.get(i)).setCharged(0.1F);
						}
						
						//player.world.spawnEntity(crosser);
					}
					else
					{
						EntitySpaceGatorix gatorix = new EntitySpaceGatorix(player.world, player);
						player.world.spawnEntity(gatorix);
					}
				}
				
			}
			//player.getHeldItem(hand).shrink(1);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
		addDimensions(entityIn.dimension, stack);
	}
	
	public void addDimensions(int i, ItemStack stack)
	{
		if(!stack.hasTagCompound())
			stack.setTagCompound(new NBTTagCompound());
		
		if(DimensionManager.isDimensionRegistered(i))
		{
			int[] dims = stack.getTagCompound().getIntArray("Dimensions");
			boolean hasDim = false;
			
			for(int c = 0; c < dims.length; ++c)
			{
				if(dims[c] == i)
					hasDim = true;
			}
			
			if(!hasDim)
			{
				int[] newDims = new int[dims.length+1];
				for(int c = 0; c < dims.length; ++c)
				{
						newDims[c] = dims[c];
				}
				newDims[dims.length] = i;
				stack.getTagCompound().setIntArray("Dimensions", newDims);
			}
		}
			
			
	}
	
	public void restoreSpirits(World world, EntityPlayer player)
	{
		List<Entity> entity = world.getEntitiesWithinAABBExcludingEntity(player, player.getEntityBoundingBox().grow(5));
		for(int i = 0; i < entity.size(); i++)
		{
			if(entity.get(i) instanceof EntitySpirit)
			{
				EntitySpirit s = (EntitySpirit)entity.get(i);
				SpiritManager.restore(s);
			}
		}
	}
}
