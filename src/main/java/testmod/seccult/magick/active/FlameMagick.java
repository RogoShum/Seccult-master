package testmod.seccult.magick.active;

import net.minecraft.block.Block;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.init.Blocks;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.BlockPos;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;

public class FlameMagick extends Magick{

	public FlameMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}

	@Override
	void toEntity() {
			setFire(strengh);
			MagickFX();
		if(entity instanceof EntityLivingBase)
		{
			EntityLivingBase living = (EntityLivingBase) entity;
		
		if(strengh < 15)
		{
			living.setFire((int) strengh);
			living.attackEntityFrom(DamageSource.IN_FIRE, strengh);
			living.hurtResistantTime = -1;
		}
		else
		{
			living.setFire((int) strengh);
			living.hurtResistantTime = -1;
			living.world.newExplosion(null, living.posX, living.posY, living.posZ, strengh / 2, true, true);
			living.hurtResistantTime = -1;
		}
		}
	}
	
	private void setFire(float i)
	{
		entity.setFire((int) strengh);
	}
	
	@Override
	void toBlock() 
	{
		BlockPos Fire = new BlockPos(block.add(0, 1, 0));
		IBlockState state = player.world.getBlockState(Fire);
		Block block = state.getBlock();
		if(block == null || block.isAir(state, player.world, Fire) || block.isReplaceable(player.world, Fire)) {
		if(strengh < 15)
		{
			if(!player.world.isRemote)
			{
                player.world.setBlockState(Fire, Blocks.FIRE.getDefaultState(), 11);
                MagickFX();
			}
		}
		else
		{
			player.world.newExplosion(null, this.block.getX(), this.block.getY(), this.block.getZ(), strengh / 2, true, true);
			 MagickFX();
		}
		}
	}

	@Override
	void MagickFX() {
		double[] pos = new double[3], vec = new double[3];
		if(entity != null) {
		pos[0] = entity.posX;
		pos[1] = entity.posY + entity.height / 2;
		pos[2] = entity.posZ;
		}
		
		if(block != null)
		{
			pos[0] = block.getX();
			pos[1] = block.getY() + 1;
			pos[2] = block.getZ();
		}
		float[] color = {RGB[0], RGB[1], RGB[2]};
		NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, strengh, 102));
	}

	@Override
	public int getColor() {
		return ModMagicks.FlameMagickColor;
	}

	@Override
	public boolean doMagickNeedAtrribute() {
		return false;
	}
}
