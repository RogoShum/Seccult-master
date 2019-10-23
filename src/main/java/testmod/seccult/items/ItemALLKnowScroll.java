package testmod.seccult.items;

import java.util.ArrayList;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.init.ModMagicks;

public class ItemALLKnowScroll extends ItemBase{

	public ItemALLKnowScroll(String name) {
		super(name);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(playerIn.getHeldItem(handIn).getItem() == this)
		{
			clearMagick(playerIn);
			addMagick(playerIn);
			playerIn.getHeldItem(handIn).shrink(1);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
	
	public void clearMagick(EntityPlayer player)
	{
		PlayerData data = PlayerDataHandler.get(player);
		int[] i = {0};
		data.setMagickData(i);
	}
	
	public void addMagick(EntityPlayer player)
	{
		ArrayList<String> list = ModMagicks.GetAllMagickID();
		for(int  i = 0; i < list.size(); i++)
		PlayerDataHandler.get(player).addMagickData(i);
	}
}
