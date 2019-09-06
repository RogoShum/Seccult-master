package testmod.seccult.magick.active;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.magick.implementation.ImplementationFocused;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkEntityMoving;
import testmod.seccult.network.NetworkHandler;

public class TeleportMagick extends Magick{
	
	private boolean directTeleport;
	public TeleportMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}
	
	public TeleportMagick(Entity e, BlockPos pos) {
		super("null", true, 0, 0);
		this.directTeleport = true;
		this.setMagickAttribute(e, e, pos, 0, 0, 0);
	}
	
	@Override
	void toEntity() {
		if(entity instanceof EntityPlayer)
		{
		preMagickFX();
		Entity e = ImplementationFocused.getEntityLookedAt(entity, strengh);
		BlockPos b = ImplementationFocused.getBlockLookedAt(entity, strengh);
		
		if(directTeleport)
		{
			e = null;
			b = block;
		}
		
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
		MagickFX();
		}
		else
		{
			Entity e = ImplementationFocused.getEntityLookedAt(entity, strengh);
			BlockPos b = ImplementationFocused.getBlockLookedAt(entity, strengh);
			
			if(directTeleport)
			{
				e = null;
				b = block;
			}
			
			if(e != null)
			{
				MagickTeleporter tele = new MagickTeleporter(entity, entity.world, new BlockPos(e.posX, e.posY + 1, e.posZ));
				tele.preTeleport();
			}
			else if(b != null)
			{
				MagickTeleporter tele = new MagickTeleporter(entity, entity.world, new BlockPos(b.getX(), b.getY() + 1, b.getZ()));
				tele.preTeleport();
			}
			else {
			Vec3d look = entity.getLookVec();
			Vec3d pos = entity.getPositionVector();
			pos = pos.addVector(strengh * look.x, strengh * look.y, strengh * look.z);
			
			
			MagickTeleporter tele = new MagickTeleporter(entity, entity.world, new BlockPos(pos.x, pos.y, pos.z));
			tele.preTeleport();
			}
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

	void preMagickFX() {
		if(this.entity == null)
			 return;
		entity.playSound(SoundEvents.ENTITY_SHULKER_TELEPORT, 1.0F, 1.0F);
		for(int i = 0; i < 40; i++) {
		double[] pos = new double[3], vec = new double[3];
		pos[0] = entity.posX + (this.rand.nextDouble() - 0.5D) * (double)entity.width;
		pos[1] = entity.posY + this.rand.nextDouble() * (double)entity.height - 0.25D;
		pos[2] = entity.posZ + (this.rand.nextDouble() - 0.5D) * (double)entity.width;
		
		vec[0] = (this.rand.nextDouble()*0.5D - 0.25D);
		vec[1] = -this.rand.nextDouble();
		vec[2] = (this.rand.nextDouble()*0.5D - 0.25D);
		
		float[] color = {0, 1, 0.55F};
        NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, 0.2F, 0));
		}
	}
	
	@Override
	void MagickFX() {
		double[] pos = new double[3], vec = new double[3];
		pos[0] = entity.posX;
		pos[1] = entity.posY;
		pos[2] = entity.posZ;
		float[] color = {0, 1, 0.55F};
        NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, 0, 100));
	}
	
	@Override
	public int getColor() {
		return ModMagicks.TeleportMagickColor;
	}

	@Override
	public boolean doMagickNeedAtrribute() {
		return false;
	}

}
