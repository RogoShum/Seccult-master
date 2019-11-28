package testmod.seccult.entity.livings.landCreature;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAISwimming;
import net.minecraft.entity.ai.EntityAIWanderAvoidWater;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.monster.EntityShulker;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.world.World;
import testmod.seccult.entity.ai.EntityAIAlertForHelp;
import testmod.seccult.entity.ai.EntityAIHurtByTarget;
import testmod.seccult.init.ModSounds;

public class EntityNightmarePop extends EntityDreamPop {

	public EntityNightmarePop(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAIAttackRanged(this, 1, 60, 4));
		this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityNightmarePop.class}));
		this.targetTasks.addTask(2, new EntityAIAlertForHelp(this, new Class[] {EntityNightmarePop.class}));
        this.targetTasks.addTask(3, new EntityAINearestAttackableTarget<>(this, EntityCreeper.class, true));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(7.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1D);
		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(1D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(64.0D);
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		
		if(amount > 7)
			amount = 7;
		
		if(source.getTrueSource() instanceof EntityLivingBase)
			this.setAttackTarget((EntityLivingBase) source.getTrueSource());
		return super.attackEntityFrom(source, amount);
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.nightmare_living;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.nightmare_death;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSounds.nightmare_hurt;
	}
	
	@Override
	protected float getSoundPitch() {
		return 0.5F;
	}
}
