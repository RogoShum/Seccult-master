package testmod.seccult.magick.implementation;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import testmod.seccult.util.MathHelper.Vector3;

public class ImplementationFocused extends Implementation{
	
	public ImplementationFocused(String nbtName, String shortName) {
		super(nbtName, shortName);
	}
	
	@Override
	public void getTarget(Entity player) {
			if(doEntity) {
				setEntity(getEntityLookedAt(player));
				if(getEntity() != null) {
					makeRedMagicTrail(player.world, getEntity().posX, getEntity().posY + getEntity().getEyeHeight(), getEntity().posZ, player.posX, player.posY + player.getEyeHeight(), player.posZ);
				}
			}
			
			if(doBlock) {
				setBlock(getBlockLookedAt(player));
				if(getBlock() != null)
					applyMagickTrail(player.world, getBlock().getX(), getBlock().getY(), getBlock().getZ(), player.posX, player.posY + player.getEyeHeight(), player.posZ);
			}
	}
	
	private void applyMagickTrail(World world, double srcX, double srcY, double srcZ, double destX, double destY, double destZ) 
	{
		int particles = 32;
		for (int i = 0; i < particles; i++) {
			double trailFactor = i / (particles - 1.0D);
			float f = 1.0F;
			float f1 = 0.5F;
			float f2 = 0.5F;
			double tx = srcX + (destX - srcX) * trailFactor + world.rand.nextGaussian() * 0.005;
			double ty = srcY + (destY - srcY) * trailFactor + world.rand.nextGaussian() * 0.005;
			double tz = srcZ + (destZ - srcZ) * trailFactor + world.rand.nextGaussian() * 0.005;
			world.spawnParticle(EnumParticleTypes.CRIT_MAGIC, tx, ty, tz, f, f1, f2);
		}
	}
	
	private void makeRedMagicTrail(World world, double srcX, double srcY, double srcZ, double destX, double destY, double destZ) {
		// make particle trail
		int particles = 32;
		for (int i = 0; i < particles; i++) {
			double trailFactor = i / (particles - 1.0D);
			float f = 1.0F;
			float f1 = 0.5F;
			float f2 = 0.5F;
			double tx = srcX + (destX - srcX) * trailFactor + world.rand.nextGaussian() * 0.005;
			double ty = srcY + (destY - srcY) * trailFactor + world.rand.nextGaussian() * 0.005;
			double tz = srcZ + (destZ - srcZ) * trailFactor + world.rand.nextGaussian() * 0.005;
			world.spawnParticle(EnumParticleTypes.SPELL_MOB, tx, ty, tz, f, f1, f2);
		}
	}
	
	public BlockPos getBlockLookedAt(Entity e)
	{
		final double finalDistance = 128;
		double distance = finalDistance;
		RayTraceResult pos = raycast(e, finalDistance);
		Vec3d positionVector = e.getPositionVector();
		if(e instanceof EntityPlayer)
			positionVector = positionVector.addVector(0, e.getEyeHeight(), 0);

		if(pos != null)
			distance = pos.hitVec.distanceTo(positionVector);

		Vec3d lookVector = e.getLookVec();
		Vec3d reachVector = positionVector.addVector(lookVector.x * finalDistance, lookVector.y * finalDistance, lookVector.z * finalDistance);
		return new BlockPos(reachVector);
	}
	
	public Entity getEntityLookedAt(Entity e){
		Entity foundEntity = null;

		final double finalDistance = 128;
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
			if(entity.canBeCollidedWith()) {
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
