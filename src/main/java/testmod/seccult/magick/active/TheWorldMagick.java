package testmod.seccult.magick.active;

import net.minecraft.entity.EntityLivingBase;
import testmod.seccult.entity.EntityTimeManager;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;

public class TheWorldMagick extends Magick{

	public TheWorldMagick(String nbtName, boolean hasDetailedText) {
		super(nbtName, hasDetailedText);
	}

	@Override
	void toEntity() {
		if(entity instanceof EntityLivingBase)
		{
			EntityLivingBase pl = (EntityLivingBase) entity;
			pl.getEntityData().setInteger("TimeStop", 1);
			EntityTimeManager time = new EntityTimeManager(pl.world, pl, 5 * (int)attribute, -20 * (int)strengh);
			time.setPosition(pl.posX, pl.posY, pl.posZ);
			if(!pl.world.isRemote)
				pl.world.spawnEntity(time);
		}
		else
		{
			MagickFX();
			StateManager.setState(entity, StateManager.FROZEN, 20 * (int)strengh);
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
		NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, strengh / 5, 0));
	}

	@Override
	public int getColor() {
		return ModMagicks.TheWorldMagickColor;
	}

}