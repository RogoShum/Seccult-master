package testmod.seccult.entity.livings;

import net.minecraft.init.SoundEvents;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntityKingCrimson extends EntityStand {

	private boolean killerOwner;
	
	public EntityKingCrimson(World worldIn) {
		super(worldIn);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(killerOwner && this.Owner != null)
		{
			this.faceEntity(Owner, 360, 360);
			Vec3d look = Owner.getLookVec().scale(-1);
			Vec3d pos = Owner.getPositionVector().add(look);
			this.Moveto(pos.x, pos.y, pos.z, 0.1F);
			
			if(this.ticksExisted > 30)
			{
				this.Owner.attackEntityFrom(DamageSource.OUT_OF_WORLD, 20000);
				this.Owner.attackEntityFrom(DamageSource.DRAGON_BREATH, 20000);
				this.Owner.attackEntityFrom(DamageSource.WITHER, 20000);
				this.Owner.attackEntityFrom(DamageSource.FALL, 20000);
				this.Owner.attackEntityFrom(DamageSource.LIGHTNING_BOLT, 20000);
				this.Owner.attackEntityFrom(DamageSource.LAVA, 20000);
				this.Owner.attackEntityFrom(DamageSource.HOT_FLOOR, 20000);
				this.Owner.attackEntityFrom(DamageSource.STARVE, 20000);
				this.Owner.setHealth(Owner.getHealth() - 20000);
				this.Owner.onDeath(DamageSource.causeMobDamage(Owner));
				
			}
		}
		if(this.Owner != null && (Owner.isDead || !Owner.isEntityAlive() || !this.world.loadedEntityList.contains(Owner)))
			this.setDead();
		
		if(!this.world.isRemote && this.Owner == null)
			this.setDead();
		
		//if(this.ticksExisted)
	}
	
	protected void Moveto(double x, double y, double z, float speed) {
        this.motionX += (Math.signum(x - this.posX) - this.motionX) * speed;
        this.motionY += (Math.signum(y - this.posY) - this.motionY) * speed;
        this.motionZ += (Math.signum(z - this.posZ) - this.motionZ) * speed;
	}
	
	public void setKillerOwner()
	{
		this.killerOwner = true;
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return SoundEvents.ENTITY_VEX_AMBIENT;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return SoundEvents.ENTITY_VEX_DEATH;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return SoundEvents.ENTITY_VEX_HURT;
	}
	
    @Override
    protected float getSoundVolume() {
    	return 2F;
    }
	
	@Override
	protected float getSoundPitch() {
		return 1.5F + this.rand.nextFloat() * 0.5F;
	}
}
