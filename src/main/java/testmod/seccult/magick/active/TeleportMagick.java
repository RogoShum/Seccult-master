package testmod.seccult.magick.active;

import net.minecraft.entity.Entity;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.implementation.ImplementationFocused;
import testmod.seccult.network.NetworkEntityMoving;
import testmod.seccult.network.NetworkHandler;

public class TeleportMagick extends Magick{

	public TeleportMagick(String nbtName, boolean hasDetailedText) {
		super(nbtName, hasDetailedText);
	}

	@Override
	void toEntity() {
		Entity e = ImplementationFocused.getEntityLookedAt(entity, strengh);
		BlockPos b = ImplementationFocused.getBlockLookedAt(entity, strengh);
		if(e != null)
		{
			setPlayerTP(entity, e.posX, e.posY + 1, e.posZ,0);
			entity.setPositionAndRotation(e.posX, e.posY + 1, e.posZ, entity.rotationYaw, entity.rotationPitch);
		}
		else if(b != null)
		{
			setPlayerTP(entity, b.getX(), b.getY() + 1, b.getZ(),0);
			entity.setPositionAndRotation(b.getX(), b.getY() + 1, b.getZ(), entity.rotationYaw, entity.rotationPitch);
		}
		else {
		Vec3d look = entity.getLookVec();
		Vec3d pos = entity.getPositionVector();
		pos = pos.addVector(strengh * look.x, strengh * look.y, strengh * look.z);
		
		setPlayerTP(entity, pos.x, pos.y, pos.z,0);
		entity.setPositionAndRotation(pos.x, pos.y, pos.z, entity.rotationYaw, entity.rotationPitch);
		}
	}

	public static void setPlayerTP(Entity e, double movex, double movey, double movez, int type)
	{
		double[] move = {0, 0, 0};
		double[] pos = {movex, movey, movez};
		NetworkHandler.getNetwork().sendToAll(new NetworkEntityMoving(e.getUniqueID(), pos, move, type));
	}
	
	@Override
	void toBlock() {
	}

	@Override
	void MagickFX() {
		// TODO Auto-generated method stub
		
	}

	@Override
	public int getColor() {
		return ModMagicks.TeleportMagickColor;
	}

}
