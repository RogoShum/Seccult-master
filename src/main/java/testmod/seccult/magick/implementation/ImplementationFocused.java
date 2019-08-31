package testmod.seccult.magick.implementation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.util.MathHelper.Vector3;

public class ImplementationFocused extends Implementation{
	
	public ImplementationFocused(String nbtName) {
		super(nbtName);
	}
	
	@Override
	public void getTarget() {
				if(getEntity() != null) 
				{
					List<Entity> eList = getEntity();
					List<Entity> newList = new ArrayList<>();
					List<BlockPos> newbList = new ArrayList<>();
					for(int i = 0; i < eList.size(); i++)
					{
						if(eList.get(i)!=null) {
							Entity e = getEntityLookedAt(eList.get(i), base + addtion);
							BlockPos b = getBlockLookedAt(eList.get(i), base + addtion);
						if(e!=null) {
							float d = e.getDistance(eList.get(i));
								applyMagickTrail(eList.get(i).world, eList.get(i).posX, eList.get(i).posY + (eList.get(i).getEyeHeight() * 0.8), eList.get(i).posZ, e.posX, e.posY + (e.height / 2), e.posZ, d * 4);
							newList.add(e);
						}
						
						if(b != null)
						{
							float d = (float)eList.get(i).getDistanceSqToCenter(b);
							if(e == null)
							applyMagickTrail(eList.get(i).world, eList.get(i).posX, eList.get(i).posY + (eList.get(i).getEyeHeight() * 0.8), eList.get(i).posZ, b.getX(), b.getY() + 1, b.getZ(), d);
							newbList.add(b);
						}
						}
					}
					setEntity(newList);
					setBlock(newbList);
				}
	}
	
	private void applyMagickTrail(World world, double srcX, double srcY, double srcZ, double destX, double destY, double destZ, float particles) 
	{
		double[] pos = new double[3], vec = new double[3];
		pos[0] = srcX;
		pos[1] = srcY;
		pos[2] = srcZ;
		vec[0] = destX;
		vec[1] = destY;
		vec[2] = destZ;
		float[] color = {this.color[0], this.color[1], this.color[2]};
        NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, particles, 101));
	}
	
	public static BlockPos getBlockLookedAt(Entity e, double finalDistance)
	{
		RayTraceResult pos = raycast(e, finalDistance);
		if(pos != null)
			return pos.getBlockPos();
		else
			return null;
	}
	
	public static Entity getEntityLookedAt(Entity e, double finalDistance){
		Entity foundEntity = null;

		double distance = finalDistance;
		RayTraceResult pos = raycast(e, finalDistance);
		Vec3d positionVector = e.getPositionVector();
		if(e instanceof EntityPlayer)
			positionVector = positionVector.addVector(0, e.getEyeHeight(), 0);

		if(pos != null)
			distance = pos.hitVec.distanceTo(positionVector);

		Vec3d lookVector = e.getLookVec();
		Vec3d reachVector = positionVector.addVector(lookVector.x * finalDistance, lookVector.y * finalDistance, lookVector.z * finalDistance);

		Entity lookedEntity = null;
		List<Entity> entitiesInBoundingBox = e.getEntityWorld().getEntitiesWithinAABBExcludingEntity(e, e.getEntityBoundingBox().grow(lookVector.x * finalDistance, lookVector.y * finalDistance, lookVector.z * finalDistance).expand(1F, 1F, 1F));
		double minDistance = distance;

		for(Entity entity : entitiesInBoundingBox) {
				float collisionBorderSize = entity.getCollisionBorderSize();
				AxisAlignedBB hitbox = entity.getEntityBoundingBox().expand(collisionBorderSize, collisionBorderSize, collisionBorderSize);
				RayTraceResult interceptPosition = hitbox.calculateIntercept(positionVector, reachVector);

				if(hitbox.contains(positionVector)) {
					if(0.0D < minDistance || minDistance == 0.0D) {
						lookedEntity = entity;
						minDistance = 0.0D;
					}
				} else if(interceptPosition != null) {
					double distanceToEntity = positionVector.distanceTo(interceptPosition.hitVec);

					if(distanceToEntity < minDistance || minDistance == 0.0D) {
						lookedEntity = entity;
						minDistance = distanceToEntity;
					}
				}
				
			if(lookedEntity != null && (minDistance < distance || pos == null))
				foundEntity = lookedEntity;
		}
		return foundEntity;
	}
	
	public static RayTraceResult raycast(Entity e, double len){
		Vector3 vec = Vector3.fromEntity(e);
		if(e instanceof EntityPlayer)
			vec.add(0, e.getEyeHeight(), 0);
		
		Vec3d look = e.getLookVec();

		return raycast(e.getEntityWorld(), vec, new Vector3(look), len);
	}
	
	public static RayTraceResult raycast(World world, Vector3 origin, Vector3 ray, double len) {
		Vector3 end = origin.copy().add(ray.copy().normalize().multiply(len));
		RayTraceResult pos = world.rayTraceBlocks(origin.toVec3D(), end.toVec3D());
		return pos;
	}
}
