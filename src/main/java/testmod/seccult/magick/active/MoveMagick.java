package testmod.seccult.magick.active;

import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.Vec3d;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import testmod.seccult.Seccult;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.magickState.StateManager;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.TransPoint;

@Mod.EventBusSubscriber(modid = Seccult.MODID)
public class MoveMagick extends Magick{

	public MoveMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}

	@Override
	void toEntity() {
		Vec3d QAQ = player.getLookVec();
		MagickFX();
		entity.motionX = QAQ.scale(0.5).x *(strengh);
		entity.motionY = QAQ.scale(0.5).y *(strengh);
		entity.motionZ = QAQ.scale(0.5).z *(strengh);
		if(entity instanceof EntityPlayer)
		{
			EntityPlayer player = (EntityPlayer) entity;
			StateManager.setPlayerMove(player, QAQ.scale(0.5).x *(strengh), QAQ.scale(0.5).y *(strengh), QAQ.scale(0.5).z *(strengh), 1);
		}
	}

	@Override
	void toBlock() 
	{
		
	}

	@Override
	public int getColor() {
		return ModMagicks.MoveMagickColor;
	}

	@Override
	void MagickFX() 
	{
		for(int i = 0; i < strengh * 2; i++) {
		double[] pos = new double[3], vec = new double[3];
		if(entity != null)
		{
			pos[0] = entity.posX;
			pos[1] = entity.posY + (entity.height / 2);
			pos[2] = entity.posZ;
			Vec3d look = player.getLookVec();
			vec[0] = entity.world.rand.nextFloat() / 2 * -look.x * strengh / 8;
			vec[1] = entity.world.rand.nextFloat() / 2 * -look.y * strengh / 8;
			vec[2] = entity.world.rand.nextFloat() / 2 * -look.z * strengh / 8;
		}
		
		if(block != null)
		{
			pos[0] = block.getX();
			pos[1] = block.getY() + 1;
			pos[2] = block.getZ();
			Vec3d look = player.getLookVec();
			vec[0] = player.world.rand.nextFloat() / 10 * -look.x * strengh / 8;
			vec[1] = player.world.rand.nextFloat() / 10 * -look.y * strengh / 8;
			vec[2] = player.world.rand.nextFloat() / 10 * -look.z * strengh / 8;
		}
		
		float[] color = {RGB[0], RGB[1], RGB[2]};
		NetworkHandler.sendToAllAround(new NetworkEffectData(pos, vec, color, strengh / 10, 0), 
				new TransPoint(player.dimension, pos[0], pos[1], pos[2], 32), player.world);
		}
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
