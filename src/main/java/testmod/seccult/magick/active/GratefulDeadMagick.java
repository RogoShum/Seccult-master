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
		for(int i = 0; i < attribute; ++i)
		{
			ItemStack stack = new ItemStack(Items.DYE);
			stack.setItemDamage(EnumDyeColor.WHITE.getDyeDamage());
			ItemDye.applyBonemeal(stack, player.world, block);
		
		/*Block b = this.player.world.getBlockState(block).getBlock();
        if (b instanceof IGrowable)
        {
            IGrowable igrowable = (IGrowable)b;

            if (igrowable.canGrow(player.world, block, player.world.getBlockState(block), player.world.isRemote))
            {
                if (!player.world.isRemote)
                {
                    if (igrowable.canUseBonemeal(player.world, player.world.rand, block, player.world.getBlockState(block)))
                    {
                        igrowable.grow(player.world, player.world.rand, block, player.world.getBlockState(block));
                    }
                }
            }
        }*/
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
