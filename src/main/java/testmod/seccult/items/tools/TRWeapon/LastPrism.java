package testmod.seccult.items.tools.TRWeapon;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class LastPrism extends ItemWeaponBase{

	public LastPrism(String name) {
		super(name);
		ItemAttribute(false, 10, 12, 10, 30, "seccult:laserbeam", -1);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, EnumHand hand) {
		
		return super.onItemRightClick(world, player, hand);
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		if(count % 100 == 0)
		player.world.playSound((EntityPlayer)null, new BlockPos(player.posX, player.posY, player.posZ), SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.PLAYERS, 0.5F, 0.5F);
		super.onUsingTick(stack, player, count);
	}
}
