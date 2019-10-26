package testmod.seccult.magick.active;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import testmod.seccult.entity.livings.EntitySpirit;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.TransPoint;

public class SoulControlMagick extends Magick{
	
	public SoulControlMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}
	
	@Override
	public void doMagick() {
		super.doMagick();
	}

	@Override
	void toEntity() {
		if(entity instanceof EntitySpirit && this.player instanceof EntityLivingBase)
		{
			((EntitySpirit) entity).Owner = (EntityLivingBase) player;
			((EntitySpirit) entity).ServeingTime = (int)this.strengh * 20;
		}
	}

	@Override
	void toBlock() {}

	@Override
	void MagickFX() 
	{
		for(int i = 0; i < strengh; i++) {
		double[] pos = new double[3], vec = new double[3];
		if(entity != null)
		{
			pos[0] = entity.posX;
			pos[1] = entity.posY + (entity.height / 2);
			pos[2] = entity.posZ;
			Vec3d look = player.getLookVec();
			look = look.scale(0.05);
			vec[0] = entity.world.rand.nextFloat() / 2 * look.x * strengh;
			vec[1] = entity.world.rand.nextFloat() / 2 * look.y * strengh;
			vec[2] = entity.world.rand.nextFloat() / 2 * look.z * strengh;
		}

		float[] color = {RGB[0], RGB[1], RGB[2]};
		NetworkHandler.sendToAllAround(new NetworkEffectData(pos, vec, color, strengh / 5, 0), 
				new TransPoint(player.dimension, pos[0], pos[1], pos[2], 32), player.world);
		}
	}

	@Override
	public int getColor() {
		return ModMagicks.SoulControlMagickColor;
	}

	@Override
	public boolean doMagickNeedAtrribute() {
		return false;
	}
	
	@Override
	public boolean doMagickNeedStrength() {
		return true;
	}
}
