package testmod.seccult.magick.active;

import net.minecraft.entity.EntityLivingBase;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import testmod.seccult.entity.EntityTimeManager;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.TransPoint;

public class TheWorldMagick extends Magick implements ControllerMagic{

	public TheWorldMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}

	@Override
	void toEntity() {
		if(entity instanceof EntityLivingBase)
		{
			EntityLivingBase pl = (EntityLivingBase) entity;
			pl.getEntityData().setInteger("TimeStop", 1);
			EntityTimeManager time = new EntityTimeManager(pl.world, pl, 2 * (int)attribute, 20 * (int)strengh);
			time.setPosition(pl.posX, pl.posY, pl.posZ);
			if(!pl.world.isRemote)
				pl.world.spawnEntity(time);
		}
		else
		{
			MagickFX();
			StateManager.setState(entity, StateManager.FROZEN, (int)(strengh), (int)attribute);
		}
	}

	@Override
	void toBlock() 
	{
	}

	@Override
	void MagickFX() 
	{
		double[] pos = {entity.posX, entity.posY, entity.posZ};
		double[] vec = new double[3];
		vec[0] = player.world.rand.nextFloat() / 10 *  strengh;
		vec[1] = player.world.rand.nextFloat() / 10 *  strengh;
		vec[2] = player.world.rand.nextFloat() / 10 *  strengh;
		float[] color = {RGB[0], RGB[1], RGB[2]};

		NetworkHandler.sendToAllAround(new NetworkEffectData(pos, vec, color, strengh / 5, 0), 
				new TransPoint(player.dimension, pos[0], pos[1], pos[2], 32), player.world);
	}

	@Override
	public int getColor() {
		return ModMagicks.TheWorldMagickColor;
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
