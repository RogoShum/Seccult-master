package testmod.seccult.entity.livings.landCreature;

import java.lang.reflect.InvocationTargetException;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityAIAttackRanged;
import net.minecraft.entity.ai.EntityAIBase;
import net.minecraft.entity.ai.EntityAILookIdle;
import net.minecraft.entity.ai.EntityAIMoveTowardsRestriction;
import net.minecraft.entity.ai.EntityAINearestAttackableTarget;
import net.minecraft.entity.ai.EntityAIWatchClosest;
import net.minecraft.entity.ai.EntityLookHelper;
import net.minecraft.entity.monster.EntityCreeper;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.pathfinding.PathNavigate;
import net.minecraft.pathfinding.PathNavigateFlying;
import net.minecraft.pathfinding.PathNavigateGround;
import net.minecraft.pathfinding.PathNodeType;
import net.minecraft.util.DamageSource;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.util.text.translation.I18n;
import net.minecraft.world.World;
import testmod.seccult.entity.ISpaceEntity;
import testmod.seccult.entity.ai.EntityAIAlertForHelp;
import testmod.seccult.entity.ai.EntityAIHurtByTarget;
import testmod.seccult.entity.projectile.EntitySpaceGatorix;
import testmod.seccult.init.ModSounds;
import testmod.seccult.magick.implementation.ImplementationFocused;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.TransPoint;

public class EntityNightmarePop extends EntityDreamPop implements ISpaceEntity{

	private EntityNightmarePop Partner_A;
	private EntityNightmarePop Partner_B;
	
	private EntitySpaceGatorix spaceGatorix;
	private int chargedTime;
	
	public EntityNightmarePop(World worldIn) {
		super(worldIn);
	}

	@Override
	protected void initEntityAI() {
		this.tasks.addTask(1, new EntityAIAttackRanged(this, 1, 60, 4));
		this.tasks.addTask(2, new EntityAIFollowPatner(this, 1.0D, 3F, 15F));
		this.tasks.addTask(3, new EntityAIMoveTowardsRestriction(this, 1.0D));
		this.tasks.addTask(7, new EntityAIWatchClosest(this, EntityPlayer.class, 6F));
		this.tasks.addTask(8, new EntityAILookIdle(this));
		this.targetTasks.addTask(1, new EntityAIHurtByTarget(this, true, new Class[] {EntityNightmarePop.class}));
		this.targetTasks.addTask(2, new EntityAIAlertForHelp(this, new Class[] {EntityNightmarePop.class}));
		this.targetTasks.addTask(3, new EntityAINightmareFindPartner(this));
        this.targetTasks.addTask(4, new EntityAINearestAttackableTarget<>(this, EntityCreeper.class, true));
        this.targetTasks.addTask(5, new EntityAINearestAttackableTarget<>(this, EntityPlayer.class, false));
	}

	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(60.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(7.0D);
		this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(8.0D);
		this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(1D);
		this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(1D);
		this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(0.5D);
		this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).setBaseValue(24.0D);
	}
	
	@Override
	public String getName() {
        if (this.hasCustomName())
        {
            return this.getCustomNameTag();
        }
        else
        {
            String s = EntityList.getEntityString(this);

            if (s == null)
            {
                s = "generic";
            }
            else
            {
            	if(this.hasPartner())
            		s = s + "_ton";
            }

            return I18n.translateToLocal("entity." + s + ".name");
        }
	}
	
	@Override
	public void onEntityUpdate() {
		super.onEntityUpdate();
		int chargedLimit = 80;
		if(!this.world.isRemote && this.getState() == PopState.ATTACK.getIndex() && (this.chargedTime >= chargedLimit|| !this.hasPartner() || this.spaceGatorix == null || (this.spaceGatorix != null && spaceGatorix.isDead)))
		{
			this.setState(PopState.NORMAL.getIndex());
		}
		
		if(this.getState() == PopState.NORMAL.getIndex() && this.chargedTime > 0)
			this.chargedTime--;
			
		if(this.spaceGatorix != null && this.chargedTime < chargedLimit)
		{
			this.motionX *= 0.1;
			this.motionY *= 0.1;
			this.motionZ *= 0.1;
			
			this.spaceGatorix.setCharged(0.01F);
			this.spaceGatorix.motionX = 0;
			this.spaceGatorix.motionY = 0;
			this.spaceGatorix.motionZ = 0;
			
			Vec3d pos = getGatorixPos(this.getPositionVector(), Partner_A != null ? Partner_A.getPositionVector() : null, Partner_B != null ? Partner_B.getPositionVector() : null);
			spaceGatorix.setPosition(pos.x, pos.y, pos.z);
			
			double[] pPos = new double[3], vec = new double[3];
			pPos[0] = this.posX;
			pPos[1] = this.posY;
			pPos[2] = this.posZ;
			vec[0] = pos.x;
			vec[1] = pos.y + spaceGatorix.height / 2;
			vec[2] = pos.z;

			NetworkHandler.sendToAllAround(new NetworkEffectData(pPos, vec, new float[] {1, 0, 0}, 32F, 101), 
					new TransPoint(-12450, pPos[0], pPos[1], pPos[2], 32), world);
			
			chargedTime++;
			if(!this.hasPartner())
				chargedTime++;
			
			if(chargedTime >= chargedLimit || spaceGatorix.isDead)
				spaceGatorix = null;
		}
	}
	
	@Override
	public boolean isImmuneToExplosions() {
		return true;
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
	
	private boolean hasPartner(EntityNightmarePop pop)
	{
		boolean flag = false;
		
		if(this.Partner_A != null && this.Partner_A == pop)
			flag = true;
		
		if(this.Partner_B != null && this.Partner_B == pop)
			flag = true;
		
		return flag;
	}
	
	private boolean hasPartner()
	{
		return this.Partner_A != null && this.Partner_A.hasPartner(this) && this.Partner_B != null && this.Partner_B.hasPartner(this);
	}
	
	@Override
	public void attackEntityWithRangedAttack(EntityLivingBase target, float distanceFactor) 
	{
		if(this.hasPartner() && this.getState() == PopState.NORMAL.getIndex())
		{
			this.setState(PopState.ATTACK.getIndex());
			this.Partner_A.setState(PopState.ATTACK.getIndex());
			this.Partner_B.setState(PopState.ATTACK.getIndex());
			
			EntitySpaceGatorix gatorix = new EntitySpaceGatorix(getEntityWorld(), this, target);
			
			this.spaceGatorix = gatorix;
			this.Partner_A.spaceGatorix = gatorix;
			this.Partner_B.spaceGatorix = gatorix;

			Vec3d pos = getGatorixPos(this.getPositionVector(), Partner_A.getPositionVector(), Partner_B.getPositionVector());
			gatorix.setPosition(pos.x, pos.y, pos.z);
			gatorix.setExplosion(true);
			if(!this.world.isRemote)
				this.world.spawnEntity(gatorix);
			
		}
		else if(this.getState() == PopState.NORMAL.getIndex())
		{
			super.attackEntityWithRangedAttack(target, distanceFactor);
		}
	}
	
	public Vec3d getGatorixPos(Vec3d posA, Vec3d posB, Vec3d posC)
	{
		Vec3d pos = posA;
		
		if(posB != null && posC != null)
			pos = new Vec3d((pos.x + posB.x + posC.x) / 3, (pos.y + posB.y + posC.y) / 3, (pos.z + posB.z + posC.z) / 3);
		
		else if(posB == null && posC != null)
		pos = new Vec3d((pos.x + posC.x) / 2, (pos.y + posC.y) / 2, (pos.z + posC.z) / 2);

		else if(posC == null && posB != null)
				pos = new Vec3d((pos.x + posB.x) / 2, (pos.y + posB.y) / 2, (pos.z + posB.z) / 2);
		
		return pos;
	}

	private enum PopState
	{
		NORMAL(0), ATTACK(1);
		
		private int index;
		
		private PopState(int index) {
			this.index = index;
		}
		
		public int getIndex()
		{
			return index;
		}
	}
	
	private class EntityAINightmareFindPartner extends EntityAIBase
	{
		private EntityNightmarePop pop;
		public EntityAINightmareFindPartner(EntityNightmarePop owner) {
			this.pop = owner;
		}
		
		@Override
		public boolean shouldExecute() {
			if(pop.Partner_A != null && pop.Partner_A.isDead)
				pop.Partner_A = null;
			if(pop.Partner_B != null && pop.Partner_B.isDead)
				pop.Partner_B = null;
			
			if(pop.Partner_A == null || pop.Partner_B == null)
				return true;
			else
				return false;
		}
		
		@Override
		public void startExecuting() {
			findPartner();
			super.startExecuting();
		}
		
		private EntityNightmarePop findPartner()
		{
			EntityNightmarePop partner = null;
			if(pop != null)
			{
				List<Entity> list = pop.world.getEntitiesWithinAABBExcludingEntity(pop, pop.getEntityBoundingBox().grow(20));
				
				for(int i = 0; i < list.size(); ++i)
				{
					if(list.get(i) instanceof EntityNightmarePop)
					{
						EntityNightmarePop prePartner = (EntityNightmarePop) list.get(i);
						if(prePartner.Partner_A == null || prePartner.Partner_B == null)
						{
							setPartner(pop, prePartner);
							setPartner(prePartner, pop);
						}
					}
				}
			}
				return partner;
		}
		
		private void setPartner(EntityNightmarePop A, EntityNightmarePop partner)
		{
			if(((A.Partner_B != null && A.Partner_B != partner) || A.Partner_B == null) &&  A.Partner_A == null)
				A.Partner_A = partner;
			
			if(((A.Partner_A != null && A.Partner_A != partner) || A.Partner_A == null) &&  A.Partner_B == null)
				A.Partner_B = partner;
		}
	}
	
	private class EntityAIFollowPatner extends EntityAIBase
	{
		private final EntityNightmarePop entity;
	    private EntityLiving followingEntity;
	    private final double speedModifier;
	    private final PathNavigate navigation;
	    private int timeToRecalcPath;
	    private final float stopDistance;
	    private float oldWaterCost;

	    public EntityAIFollowPatner(final EntityNightmarePop entity, double speedModifier, float stopDistance, float areaSize)
	    {
	        this.entity = entity;
	        this.speedModifier = speedModifier;
	        this.navigation = entity.getNavigator();
	        this.stopDistance = stopDistance;
	        this.setMutexBits(3);

	        if (!(entity.getNavigator() instanceof PathNavigateGround) && !(entity.getNavigator() instanceof PathNavigateFlying))
	        {
	            throw new IllegalArgumentException("Unsupported mob type for FollowMobGoal");
	        }
	    }

	    /**
	     * Returns whether the EntityAIBase should begin execution.
	     */
	    public boolean shouldExecute()
	    {
	       if(entity.Partner_A != null && entity.getDistance(entity.Partner_A) > stopDistance)
	       {
	    	   this.followingEntity = entity.Partner_A;
	    	   return true;
	       }
	       else if(entity.Partner_B != null && entity.getDistance(entity.Partner_B) > stopDistance)
	       {
	    	   this.followingEntity = entity.Partner_B;
	    	   return true;
	       }
	        return false;
	    }

	    /**
	     * Returns whether an in-progress EntityAIBase should continue executing
	     */
	    public boolean shouldContinueExecuting()
	    {
	        return this.followingEntity != null && !this.navigation.noPath() && this.entity.getDistanceSq(this.followingEntity) > (double)(this.stopDistance * this.stopDistance);
	    }

	    /**
	     * Execute a one shot task or start executing a continuous task
	     */
	    public void startExecuting()
	    {
	        this.timeToRecalcPath = 0;
	        this.oldWaterCost = this.entity.getPathPriority(PathNodeType.WATER);
	        this.entity.setPathPriority(PathNodeType.WATER, 0.0F);
	    }

	    /**
	     * Reset the task's internal state. Called when this task is interrupted by another one
	     */
	    public void resetTask()
	    {
	        this.followingEntity = null;
	        this.navigation.clearPath();
	        this.entity.setPathPriority(PathNodeType.WATER, this.oldWaterCost);
	    }

	    /**
	     * Keep ticking a continuous task that has already been started
	     */
	    public void updateTask()
	    {
	        if (this.followingEntity != null && !this.entity.getLeashed())
	        {
	            //this.entity.getLookHelper().setLookPositionWithEntity(this.followingEntity, 10.0F, (float)this.entity.getVerticalFaceSpeed());

	            if (--this.timeToRecalcPath <= 0)
	            {
	                this.timeToRecalcPath = 10;
	                double d0 = this.entity.posX - this.followingEntity.posX;
	                double d1 = this.entity.posY - this.followingEntity.posY;
	                double d2 = this.entity.posZ - this.followingEntity.posZ;
	                double d3 = d0 * d0 + d1 * d1 + d2 * d2;

	                if (d3 > (double)(this.stopDistance * this.stopDistance))
	                {
	                    this.navigation.tryMoveToEntityLiving(this.followingEntity, this.speedModifier);
	                }
	                else
	                {
	                    this.navigation.clearPath();
	                    EntityLookHelper entitylookhelper = this.followingEntity.getLookHelper();

	                    if (d3 <= (double)this.stopDistance || entitylookhelper.getLookPosX() == this.entity.posX && entitylookhelper.getLookPosY() == this.entity.posY && entitylookhelper.getLookPosZ() == this.entity.posZ)
	                    {
	                        double d4 = this.followingEntity.posX - this.entity.posX;
	                        double d5 = this.followingEntity.posZ - this.entity.posZ;
	                        this.navigation.tryMoveToXYZ(this.entity.posX - d4, this.entity.posY, this.entity.posZ - d5, this.speedModifier);
	                    }
	                }
	            }
	        }
	    }
	}
}
