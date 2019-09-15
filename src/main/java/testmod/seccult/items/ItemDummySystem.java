package testmod.seccult.items;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import testmod.seccult.entity.livings.EntitySpiritDummy;
import testmod.seccult.init.ModItems;
import testmod.seccult.magick.implementation.ImplementationFocused;

public class ItemDummySystem extends ItemBase{

	public ItemDummySystem(String name) {
		super(name);
	}

	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		
		if(playerIn.getHeldItemMainhand().getItem() == ModItems.DummySystem)
		{
			BlockPos pos = ImplementationFocused.getBlockLookedAt(playerIn, 5);
			if(pos != null)
			{
				EntitySpiritDummy dummy = new EntitySpiritDummy(worldIn);
				dummy.setPositionAndRotation(pos.getX(), pos.getY() + 1, pos.getZ(), -playerIn.rotationYaw, -playerIn.rotationPitch);
				if(!worldIn.isRemote)
					worldIn.spawnEntity(dummy);
			}
		}
		
		return new ActionResult<ItemStack>(EnumActionResult.PASS, playerIn.getHeldItem(handIn));
	}
}
