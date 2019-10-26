package testmod.seccult.magick.implementation;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.RayTraceResult;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import testmod.seccult.Seccult;
import testmod.seccult.entity.EntityProtectionShieldFX;
import testmod.seccult.entity.EntityShieldFX;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.TransPoint;
import testmod.seccult.util.MathHelper.Vector3;

public class ImplementationFocused extends Implementation{
	private int FXType;
	private float LightingScale;
	public ImplementationFocused(String nbtName) {
		super(nbtName);
	}
	
	public void setFXType(int i)
	{
		this.FXType = i;
	}
	
	public void setLightingScale(float i)
	{
		this.LightingScale = i;
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
						if(this.doEntity && e!=null) {
							float d = e.getDistance(eList.get(i));
								applyMagickTrail(eList.get(i).world, eList.get(i).posX, eList.get(i).posY + (eList.get(i).getEyeHeight() * 0.8), eList.get(i).posZ, e.posX, e.posY + (e.height / 2), e.posZ, d * 4);
							newList.add(e);
						}
						
						if(this.doBlock && b != null)
						{
							float d = (float)eList.get(i).getDistanceSqToCenter(b);
							if(e == null)
							applyMagickTrail(eList.get(i).world, eList.get(i).posX, eList.get(i).posY + (eList.get(i).getEyeHeight() * 0.8), eList.get(i).posZ, b.getX() + 0.5, b.getY() + 0.5, b.getZ() + 0.5, d);
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
		if(this.FXType == 1)
		{
			int times = (int)this.LightingScale / 3;
			for(int i = 0; i < times + 1; ++i)
			{
				double[] pos = new double[3], vec = new double[3];
				pos[0] = srcX;
				pos[1] = srcY;
				pos[2] = srcZ;
				vec[0] = destX;
				vec[1] = destY;
				vec[2] = destZ;
				float[] color = {this.player.rotationYaw, this.player.rotationPitch, 0};
				if(LightingScale < 0.8F)
					LightingScale = 0.8F;

	    		NetworkHandler.sendToAllAround(new NetworkEffectData(pos, vec, color, LightingScale, 4), 
	    				new TransPoint(-12450, pos[0], pos[1], pos[2], 32), world);
			}
			
			world.playSound(null, new BlockPos(this.player.getPositionVector()), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.PLAYERS, 1.0F, 2);
			world.playSound(null, new BlockPos(this.player.getPositionVector()), SoundEvents.ENTITY_LIGHTNING_IMPACT, SoundCategory.PLAYERS, 2.0F, 1.8F + Seccult.rand.nextFloat() * 0.2F);
		}
		else if(this.FXType == 2)
		{
			double[] pos = new double[3], vec = new double[3];
			pos[0] = srcX;
			pos[1] = srcY;
			pos[2] = srcZ;
			vec[0] = destX;
			vec[1] = destY;
			vec[2] = destZ;
			float[] color = {this.color[0], this.color[1], this.color[2]};
    		NetworkHandler.sendToAllAround(new NetworkEffectData(pos, vec, color, LightingScale, 5), 
    				new TransPoint(-12450, pos[0], pos[1], pos[2], 32), world);
	        world.playSound(null, new BlockPos(this.player.getPositionVector()), SoundEvents.ENTITY_PARROT_FLY, SoundCategory.PLAYERS, 4.0F, 2);
		}
		else if(this.FXType == 3)
		{
			double[] pos = new double[3], vec = new double[3];
			pos[0] = srcX;
			pos[1] = srcY;
			pos[2] = srcZ;
			vec[0] = destX;
			vec[1] = destY;
			vec[2] = destZ;
			float[] color = {this.color[0], this.color[1], this.color[2]};
    		NetworkHandler.sendToAllAround(new NetworkEffectData(pos, vec, color, LightingScale, 6), 
    				new TransPoint(-12450, pos[0], pos[1], pos[2], 32), world);
	        world.playSound(null, new BlockPos(this.player.getPositionVector()), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.PLAYERS, 2.0F, 2);
	        world.playSound(null, new BlockPos(this.player.getPositionVector()), SoundEvents.BLOCK_FIRE_AMBIENT, SoundCategory.PLAYERS, 2.0F, 1);
		}
		else
		{
		double[] pos = new double[3], vec = new double[3];
		pos[0] = srcX;
		pos[1] = srcY;
		pos[2] = srcZ;
		vec[0] = destX;
		vec[1] = destY;
		vec[2] = destZ;
		float[] color = {this.color[0], this.color[1], this.color[2]};
		NetworkHandler.sendToAllAround(new NetworkEffectData(pos, vec, color, LightingScale, 101), 
				new TransPoint(-12450, pos[0], pos[1], pos[2], 32), world);
        
        world.playSound(null, new BlockPos(this.player.getPositionVector()), SoundEvents.ENTITY_FIREWORK_SHOOT, SoundCategory.PLAYERS, 1.0F, 1.6F + player.world.rand.nextFloat() * 0.4F);
        world.playSound(null, new BlockPos(this.player.getPositionVector()), SoundEvents.ITEM_ARMOR_EQUIP_DIAMOND, SoundCategory.PLAYERS, 1.0F, player.world.rand.nextFloat());

		}
	}
	
	public static BlockPos getBlockLookedAt(Entity e, double finalDistance)
	{
		RayTraceResult pos = raycast(e, finalDistance);
		if(pos != null)
			return pos.getBlockPos();
		else
		{
			Vec3d vec = e.getLookVec();
			Vec3d posAir = e.getPositionVector().addVector(0, e.getEyeHeight(), 0).addVector(vec.x * finalDistance, vec.y * finalDistance, vec.z * finalDistance);
			BlockPos bPos = new BlockPos(posAir);
			
			if(bPos != null && e.world.isAirBlock(bPos))
				return bPos;
		}
			return null;
	}
	
	public static Entity getEntityLookedAt(Entity e, double finalDistance){
		Entity foundEntity = null;

		double distance = finalDistance;
		RayTraceResult pos = raycast(e, finalDistance);
		Vec3d positionVector = e.getPositionVector();

			positionVector = positionVector.addVector(0, e.getEyeHeight(), 0);

			if(pos != null)
			distance = pos.hitVec.distanceTo(positionVector);

		Vec3d lookVector = e.getLookVec();
		Vec3d reachVector = positionVector.addVector(lookVector.x * finalDistance, lookVector.y * finalDistance, lookVector.z * finalDistance);

		Entity lookedEntity = null;
		List<Entity> entitiesInBoundingBox = e.getEntityWorld().getEntitiesWithinAABBExcludingEntity(e, e.getEntityBoundingBox().grow(lookVector.x * finalDistance, lookVector.y * finalDistance, lookVector.z * finalDistance).expand(1F, 1F, 1F));
		double minDistance = distance;

		if(entitiesInBoundingBox.contains(e))
			entitiesInBoundingBox.remove(e);
		
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
			{
				boolean owner = false;
				if(lookedEntity instanceof EntityShieldFX)
				{
					EntityShieldFX shield = (EntityShieldFX) lookedEntity;
					if(shield.getOwner() == e)
						owner = true;
				}
				
				if(lookedEntity instanceof EntityProtectionShieldFX)
				{
					
					EntityProtectionShieldFX shield = (EntityProtectionShieldFX) lookedEntity;
					if(shield.getOwner() == e)
						owner = true;
				}
				if(!owner)
					foundEntity = lookedEntity;
			}
		}
		
		return foundEntity;
	}
	
	public static Entity getEntityMotionAt(Entity e, double finalDistance){
		Entity foundEntity = null;

		double distance = finalDistance;
		RayTraceResult pos = raycast(e, finalDistance);
		Vec3d positionVector = e.getPositionVector();

			positionVector = positionVector.addVector(0, e.getEyeHeight(), 0);

			if(pos != null)
			distance = pos.hitVec.distanceTo(positionVector);

		Vec3d lookVector = new Vec3d(e.motionX, e.motionY, e.motionZ);
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
				{
					boolean owner = false;
					if(lookedEntity instanceof EntityShieldFX)
					{
						EntityShieldFX shield = (EntityShieldFX) lookedEntity;
						if(shield.getOwner() == e)
							owner = true;
							
					}
					
					if(lookedEntity instanceof EntityProtectionShieldFX)
					{
						EntityProtectionShieldFX shield = (EntityProtectionShieldFX) lookedEntity;
						if(shield.getOwner() == e)
							owner = true;
					}
					
					if(!owner)
						foundEntity = lookedEntity;
				}
		}
		return foundEntity;
	}
	
	public static Entity getEntityLookedAt(Entity e, double finalDistance, Entity neglecteEntity){
		Entity foundEntity = null;

		double distance = finalDistance;
		RayTraceResult pos = raycast(e, finalDistance);
		Vec3d positionVector = e.getPositionVector();

			positionVector = positionVector.addVector(0, e.getEyeHeight(), 0);

		if(pos != null)
			distance = pos.hitVec.distanceTo(positionVector);
		
		Vec3d lookVector = e.getLookVec();
		Vec3d reachVector = positionVector.addVector(lookVector.x * finalDistance, lookVector.y * finalDistance, lookVector.z * finalDistance);

		Entity lookedEntity = null;
		List<Entity> entitiesInBoundingBox = e.getEntityWorld().getEntitiesWithinAABBExcludingEntity(e, e.getEntityBoundingBox().grow(lookVector.x * finalDistance, lookVector.y * finalDistance, lookVector.z * finalDistance).expand(1F, 1F, 1F));
		double minDistance = distance;

		if(entitiesInBoundingBox.contains(e))
			entitiesInBoundingBox.remove(e);
		
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

				if(lookedEntity != null && (minDistance < distance || pos == null) && lookedEntity != neglecteEntity)
				{
					boolean owner = false;
					if(lookedEntity instanceof EntityShieldFX)
					{
						EntityShieldFX shield = (EntityShieldFX) lookedEntity;
						if(shield.getOwner() == e)
							owner = true;
							
					}
					
					if(lookedEntity instanceof EntityProtectionShieldFX)
					{
						EntityProtectionShieldFX shield = (EntityProtectionShieldFX) lookedEntity;
						if(shield.getOwner() == e)
							owner = true;
					}
					
					if(!owner)
						foundEntity = lookedEntity;
				}
		}
		return foundEntity;
	}
	
	public static RayTraceResult raycast(Entity e, double len){
		Vector3 vec = Vector3.fromEntity(e);
			vec.add(0, e.getEyeHeight(), 0);
		Vec3d look = e.getLookVec();
		return raycast(e.getEntityWorld(), vec, new Vector3(look), len);
	}
	
	public static RayTraceResult raycast(World world, Vector3 origin, Vector3 ray, double len) {
		Vector3 end = origin.copy().add(ray.copy().normalize().multiply(len));
		RayTraceResult pos = world.rayTraceBlocks(origin.toVec3D(), end.toVec3D());

		return pos;
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
