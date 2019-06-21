package testmod.seccult.items;

import javax.annotation.Nonnull;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import testmod.seccult.entity.EntityTimeManager;

public class ItemYesYourHighness extends ItemBase{

	public ItemYesYourHighness(String name) {
		super(name);
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
			player.setActiveHand(hand);
			if(player.getEntityData().hasKey("YesYourHighness") && player.getEntityData().getInteger("YesYourHighness") == 1) {
					player.getEntityData().setInteger("YesYourHighness", 0);
			}
			else if(player.getEntityData().hasKey("YesYourHighness") && player.getEntityData().getInteger("YesYourHighness") == 0) {
				player.getEntityData().setInteger("YesYourHighness", 1);
			}
			else
			player.getEntityData().setInteger("YesYourHighness", 1);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	}
}
