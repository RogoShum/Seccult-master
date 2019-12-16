package testmod.seccult.magick.active;

import net.minecraft.block.Block;
import net.minecraft.block.IGrowable;
import net.minecraft.block.state.BlockStateBase;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.passive.EntityCow;
import net.minecraft.init.Items;
import net.minecraft.item.EnumDyeColor;
import net.minecraft.item.ItemDye;
import net.minecraft.item.ItemStack;
import net.minecraft.tileentity.TileEntity;
import net.minecraft.util.ITickable;
import net.minecraft.util.math.BlockPos;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;

public class GratefulDeadMagick extends Magick implements ControllerMagic{

	public GratefulDeadMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}

	@Override
	void toEntity() {
		StateManager.setState(entity, StateManager.GratefulDead, (int)(strengh), (int)attribute);
	}

	@Override
	void toBlock() {
		
		IBlockState iblockstate = player.world.getBlockState(block);

		 if (iblockstate.getBlock() instanceof IGrowable)
		 {
			 IGrowable igrowable = (IGrowable)iblockstate.getBlock();

			 if (igrowable.canGrow(player.world, block, iblockstate, player.world.isRemote))
			 {
				 for(int i = 0; i < attribute * strengh; ++i)
				 {
					 igrowable.grow(player.world, player.world.rand, block, iblockstate);
				 }
	         }
		 }

		if(player.world.getTileEntity(block) != null)
		{
			TileEntity tile = player.world.getTileEntity(block);
			if(tile instanceof ITickable)
			for(int i = 0; i < attribute * strengh; ++i)
			{
				((ITickable) tile).update();
			}
		}
	}

	@Override
	void MagickFX() {
	}

	@Override
	public int getColor() {
		return ModMagicks.GratefulDeadMagickColor;
	}

	@Override
	public boolean doMagickNeedAtrribute() {
		return true;
	}

	@Override
	public boolean doMagickNeedStrength() {
		return true;
	}
}
