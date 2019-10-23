package testmod.seccult.items;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.world.World;
import testmod.seccult.Seccult;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.init.ModMagicks;

public class ItemAlterScroll extends ItemBase{

	public static List<String> alterMagick = new ArrayList<>();
	
	public ItemAlterScroll(String name) {
		super(name);
		alterMagick.add(ModMagicks.FrozenMagick);
		alterMagick.add(ModMagicks.noClipMagick);
		alterMagick.add(ModMagicks.LoseMindMagick);
		alterMagick.add(ModMagicks.MoveMagick);
		alterMagick.add(ModMagicks.WhiteAlbumMagick);
		alterMagick.add(ModMagicks.GratefulDeadMagick);
		alterMagick.add(ModMagicks.TeleportMagick);
		alterMagick.add(ModMagicks.CopyMagick);
		alterMagick.add(ModMagicks.TheWorldMagick);
		alterMagick.add(ModMagicks.KraftWorkMagick);
		alterMagick.add(ModMagicks.FloatingMagick);
		alterMagick.add(ModMagicks.CatchTheSoulMagick);
		alterMagick.add(ModMagicks.SoulControlMagick);
	}
	
	@Override
	public ActionResult<ItemStack> onItemRightClick(World worldIn, EntityPlayer playerIn, EnumHand handIn) {
		if(playerIn.getHeldItem(handIn).getItem() == this)
		{
			PlayerData data = PlayerDataHandler.get(playerIn);
			data.addMagickData(ModMagicks.GetMagickIDByString(alterMagick.get(Seccult.rand.nextInt(alterMagick.size()))));
			playerIn.getHeldItem(handIn).shrink(1);
		}
		return super.onItemRightClick(worldIn, playerIn, handIn);
	}
}
