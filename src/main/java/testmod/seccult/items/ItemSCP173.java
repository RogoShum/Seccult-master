package testmod.seccult.items;

import javax.annotation.Nonnull;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import testmod.seccult.entity.EntitySCP173;

public class ItemSCP173 extends ItemBase{

	public ItemSCP173(String name) {
		super(name);
	}

	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
			player.setActiveHand(hand);
			spawnscp(player);
			return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
	} 
	
	private void spawnscp(EntityPlayer player) {
		double x = player.posX;
		double y = player.posY;
		double z = player.posZ;
		
		Entity entity = null;
        entity = EntityList.createEntityByIDFromName(SCP173, player.getEntityWorld());
        EntitySCP173 SCP173 = (EntitySCP173) entity;
        SCP173.setLocationAndAngles(x, y, z, -player.rotationYaw, -player.rotationPitch);
		if(!player.getEntityWorld().isRemote) {
			player.getEntityWorld().spawnEntity(SCP173);
		}
	}
}
