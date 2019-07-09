package testmod.seccult.items.Accessories;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class AVirtulBody extends ItemAccessories{
	public AVirtulBody(String name) {
		super(name);
	}
	
	@Override
	public void onArmorTick(World world, EntityPlayer player, ItemStack itemStack) {
		super.onArmorTick(world, player, itemStack);
		if(!hasAccessories(player, itemStack)) return;
		player.noClip = true;
		if(!player.isSneaking() && world.isAirBlock(player.getPosition().add(0, 0.1, 0)) && player.motionY < 0)
		{
			player.onGround = true;
			player.setAIMoveSpeed(1);
			player.motionY = 0;
		}
		/*if(player.motionY > 0)
		{
			System.out.println(player.motionY);
			player.motionY += player.motionY * 10;
		}*/
	}
}
