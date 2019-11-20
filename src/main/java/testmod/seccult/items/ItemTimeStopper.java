package testmod.seccult.items;

import javax.annotation.Nonnull;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import testmod.seccult.Seccult;
import testmod.seccult.entity.EntityTimeManager;

public class ItemTimeStopper extends ItemBase{

	public int test ;
	
	public ItemTimeStopper(String name) {
		super(name);
		this.maxStackSize = 1;
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) {
		super.onUsingTick(stack, player, count);
		test++;
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
			player.setActiveHand(hand);
			if(player.getEntityData().hasKey("TimeStop")) {
				if(player.getEntityData().getInteger("TimeStop") == 1 || player.getEntityData().getInteger("TimeStop") == 2) 
				{
					player.world.playSound((EntityPlayer)null, new BlockPos(player.posX, player.posY, player.posZ), SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.PLAYERS, 2F, 1.5F);
					player.getEntityData().setInteger("TimeStop", 0);
				}
				else if(player.getEntityData().getInteger("TimeStop") == 0) {
					player.getEntityData().setInteger("TimeStop", 1);
					if(!player.world.isRemote) {
						player.world.playSound((EntityPlayer)null, new BlockPos(player.posX, player.posY, player.posZ), SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.PLAYERS, 2F, 1.5F + Seccult.rand.nextFloat());
					EntityTimeManager time = new EntityTimeManager(player.world, player, 80);
					time.setPosition(player.posX, player.posY, player.posZ);
					player.world.spawnEntity(time);
					}
				}
			} else {
				player.getEntityData().setInteger("TimeStop", 1);
				player.world.playSound((EntityPlayer)null, new BlockPos(player.posX, player.posY, player.posZ), SoundEvents.BLOCK_LEVER_CLICK, SoundCategory.PLAYERS, 2F, 1.5F + Seccult.rand.nextFloat());
				EntityTimeManager time = new EntityTimeManager(player.world, player, 80);
				time.setPosition(player.posX, player.posY, player.posZ);
				if(!player.world.isRemote)
					player.world.spawnEntity(time);
			}
			return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(hand));
	}
	
	@Nonnull
	@Override
	public EnumAction getItemUseAction(ItemStack stack) {
		return EnumAction.BOW;
	}
}
