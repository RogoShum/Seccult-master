package testmod.seccult.entity.livings.landCreature;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.minecraft.advancements.PlayerAdvancements;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityCreature;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IRangedAttackMob;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAvoidEntity;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAIPanic;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.monster.EntityZombie;
import net.minecraft.entity.passive.EntityFlying;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.player.EntityPlayerMP;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import testmod.seccult.entity.EntityBorderCrosser;
import testmod.seccult.entity.ISpaceEntity;
import testmod.seccult.entity.ai.EntityAIMoveToMagick;
import testmod.seccult.entity.ai.EntityFloatHelper;
import testmod.seccult.entity.projectile.EntitySpaceGatorix;
import testmod.seccult.entity.ai.EntityAIAlertForHelp;
import testmod.seccult.entity.ai.EntityAIFindBorderCrosser;
import testmod.seccult.entity.ai.EntityAIHurtByTarget;
import testmod.seccult.init.ModSounds;
import testmod.seccult.magick.implementation.ImplementationFocused;
import testmod.seccult.world.gen.DimensionMagic;

public class EntityDreamPop extends EntityCreature implements EntityFlying, IRangedAttackMob, ISpaceEntity{
	private static final DataParameter<Integer> ENTITY_STATE = EntityDataManager.<Integer>createKey(EntityDreamPop.class, DataSerializers.VARINT);
	
	private EntityBorderCrosser crosser;
	
	public EntityDreamPop(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 1F);
		this.moveHelper = new EntityFloatHelper(this);
		this.setNoGravity(true);
	}
	
	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAIPanic(this, 2.0F)
				{
					@Override
					protected boolean findRandomPosition() {
						if(crosser != null)
						{
							Vec3d vec3d = crosser.getPositionVector();
							this.randPosX = vec3d.x;
							this.randPosY = vec3d.y;
							this.randPosZ = vec3d.z;
							return true;
						}
						return super.findRandomPosition();
					}
					
					@Override
					public void startExecuting() {
						if(crosser != null)
						{
							 this.creature.getMoveHelper().setMoveTo(this.randPosX, this.randPosY, this.randPosZ, this.speed);
						}
						super.startExecuting();
					}
				});
		this.tasks.addTask(3, new EntityAIMoveToMagick(this));
		this.tasks.addTask(4, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(5, new EntityAIAvoidEntity<>(this, EntityPlayer.class, 2.0F, 0.8F, 1.33F));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(0, new EntityAIFindBorderCrosser(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityDreamPop.class, EntityNightmarePop.class}));
		this.targetTasks.addTask(2, new EntityAIAlertForHelp(this, new Class[] {EntityNightmarePop.class}));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(20.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(3.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(10.0D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(0.5D);
	}
	
    protected PathNavigate createNavigator(World worldIn)
    {
        PathNavigateFlying pathnavigateflying = new PathNavigateFlying(this, worldIn);
        pathnavigateflying.setCanOpenDoors(true);
        pathnavigateflying.setCanFloat(true);
        pathnavigateflying.setCanEnterDoors(true);
        return pathnavigateflying;
    }
	
	@Override
	public void onUpdate() {
		super.onUpdate();

		this.setNoGravity(true);
		
		if(this.getAttackTarget() != null && this.getAttackTarget().isDead)
			this.setAttackTarget(null);
		
		if(this.getRevengeTarget() != null && this.getRevengeTarget().isDead)
			this.setRevengeTarget(null);
		
		if(this.onGround)
		{
			this.motionY += 0.15F;
		}
		
		if(this.crosser != null)
		{
	        {
	            this.moveHelper.setMoveTo(this.crosser.posX, this.crosser.posY, this.crosser.posZ, 2);
	        }

	        this.getLookHelper().setLookPositionWithEntity(this.crosser, 30.0F, 30.0F);
		}
		
		if(this.dimension == DimensionMagic.MAGIC_ID && this.ticksExisted % 40 == 1)
			this.heal(2);
		
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(2));
		for(int i = 0; i < list.size(); ++i)
		{
			Entity entity = list.get(i);
			if(!(entity instanceof EntityLivingBase) && !(entity instanceof ISpaceEntity) && !entity.onGround)
			{
				BlockPos pos = ImplementationFocused.getBlockMotionAt(entity, 4);
				for(int c = 2; c < 6; ++c)
				{
					if(this.getDistanceSq(pos) < 2)
						pos = ImplementationFocused.getBlockMotionAt(entity, 2*c);
				}
				
				NBTTagCompound tag = new NBTTagCompound();
				entity.writeToNBT(tag);
				tag.setTag("Pos", this.newDoubleNBTList(pos.getX(), pos.getY(), pos.getZ()));
				entity.setDead();
				entity.isDead = true;
				this.world.removeEntityDangerously(entity);
				try {
					Entity e = (Entity)entity.getClass().getConstructor(new Class[] { World.class }).newInstance(new Object[] { world });
					e.readFromNBT(tag);
					if(!this.world.isRemote)
					this.world.spawnEntity(e);
				} catch (InstantiationException | IllegalAccessException | IllegalArgumentException
						| InvocationTargetException | NoSuchMethodException | SecurityException e) {
					e.printStackTrace();
				}
			}
		}
	}
	
	public void setCrosser(EntityBorderCrosser crosser)
	{
		this.crosser = crosser;
	}
	
	public EntityBorderCrosser getCrosser()
	{
		return this.crosser;
	}

	@Override
	public boolean getCanSpawnHere()
	{
		EntityPlayer player = this.world.getClosestPlayerToEntity(this, 32);
		EntityPlayerMP playerMP = player instanceof EntityPlayerMP ? (EntityPlayerMP)player : null;
		boolean hasAchievement = false;

		if (playerMP != null && !playerMP.world.isRemote) {
			PlayerAdvancements Achievement = playerMP.getAdvancements();
			//if(Achievement.getProgress(advancementIn))
				hasAchievement = true;
		}
		
		return super.getCanSpawnHere();
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		
		if(source.damageType.equals(DamageSource.FALL.damageType))
			return false;
		
		if(source.damageType.equals(DamageSource.FALLING_BLOCK.damageType))
			return false;
		
		if(source.getTrueSource() instanceof EntityLivingBase && amount >= 20)
		{
			this.setDead();
			EntityNightmarePop night = new EntityNightmarePop(getEntityWorld());
			night.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			night.setAttackTarget((EntityLivingBase)source.getTrueSource());
			night.ticksExisted = 55;
			this.world.spawnEntity(night);
		}
		else if(amount >= 20)
		{
			this.setDead();
			EntityNightmarePop night = new EntityNightmarePop(getEntityWorld());
			night.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
			night.ticksExisted = 55;
			this.world.spawnEntity(night);
		}
		
		return super.attackEntityFrom(source, amount);
	}
	
	@Override
	public void fall(float distance, float damageMultiplier) {}
	
	@Override
	protected SoundEvent getFallSound(int heightIn) {
		return SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP;
	}
	
	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {}
	
	@Override
	protected void entityInit() 
	{
		super.entityInit();
		this.dataManager.register(ENTITY_STATE, 0);
	}
	
	protected void setState(int id) 
	{
		this.dataManager.set(ENTITY_STATE, id);
	}
	  
	public int getState() 
	{
		return this.dataManager.get(ENTITY_STATE).intValue();
	}

	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) 
	{
		int chance = (int)(this.rand.nextFloat() + distanceFactor*2);
		if(chance < 1)
			chance = 1;
		for(int i = 0; i < chance; ++i)
		{
			EntitySpaceGatorix gatorix = new EntitySpaceGatorix(getEntityWorld(), this, target);
			gatorix.shoot(this.getLookVec().x, this.getLookVec().y, this.getLookVec().z, 0, 0);
			if(!this.world.isRemote)
				this.world.spawnEntity(gatorix);
		}
	}

	@Override
	public float getEyeHeight() {
		return this.height / 3 * 2;
	}
	
	@Override
	public void setSwingingArms(boolean swingingArms) {
	}
	
	@Override
	protected float getSoundVolume() {
		return 0.2F;
	}
	
	@Override
	protected SoundEvent getAmbientSound() {
		return ModSounds.dream_living;
	}
	
	@Override
	protected SoundEvent getDeathSound() {
		return ModSounds.dream_death;
	}
	
	@Override
	protected SoundEvent getHurtSound(DamageSource damageSourceIn) {
		return ModSounds.dream_hurt;
	}
}
