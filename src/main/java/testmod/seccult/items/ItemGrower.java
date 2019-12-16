package testmod.seccult.items;

import net.minecraft.block.IGrowable;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemEnderPearl;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class ItemGrower extends ItemMagickTool {

	public ItemGrower(String name) {
		super(name);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		ItemStack stack = playerIn.getHeldItem(handIn);
		initAttribue(stack);

		if(this.stackCoolDwon <= 0)
		{
		Iterable<BlockPos> blocks = BlockPos.getAllInBox(playerIn.getPosition().add(-this.attribute, -this.attribute, -this.attribute), playerIn.getPosition().add(this.attribute, this.attribute, this.attribute));
		for(BlockPos pos: blocks)
		{
			 IBlockState iblockstate = worldIn.getBlockState(pos);

			 if (iblockstate.getBlock() instanceof IGrowable)
			 {
				 IGrowable igrowable = (IGrowable)iblockstate.getBlock();

				 if (igrowable.canGrow(worldIn, pos, iblockstate, worldIn.isRemote))
				 {
					 for(int i = 0; i < this.attribute; ++i)
						 igrowable.grow(worldIn, worldIn.rand, pos, iblockstate);
		         }
		     }
		}
		this.stackCoolDwon = this.cooldown;
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	@Override
	public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
		
		super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
	}
}
