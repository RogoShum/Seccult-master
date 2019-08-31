package testmod.seccult.entity.livings.water;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.passive.EntityVillager;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.Blocks;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import testmod.seccult.entity.EntityAdvanceLaser;
import testmod.seccult.entity.EntityLaserBeamBase;
import testmod.seccult.entity.EntityLightingThing;
import testmod.seccult.entity.EntityMagickBubble;
import testmod.seccult.init.ModDamage;
import testmod.seccult.magick.active.CopyMagick;
import testmod.seccult.magick.active.TeleportMagick;
import testmod.seccult.network.NetworkEntityMoving;
import testmod.seccult.network.NetworkHandler;

public class EntityRockShellLeviathan extends EntityWaterCreature{
	private EntityLivingBase huntingTarget; 
	private EntityLivingBase attackTarget; 
	
	private Entity laser; 
	private Entity frozenLaser; 
	
	private List<DamageReduce> damageList = new ArrayList<DamageReduce>();
	private int swimingTime;
	private int warningTime;

	protected final int tackleCDLimit = 20;
	protected int tackleCD = 0;
	
	protected final int waterGunCDLimit = 150;
	protected int waterGunCD = 0;
	
	protected final int bubbleGunCDLimit = 100;
	protected int bubbleGunCD = 0;
	
	protected final int iceBeamCDLimit = 200;
	protected int iceBeamCD = 0;
	
	public EntityRockShellLeviathan(World worldIn) {
		super(worldIn);		
		this.setSize(10F, 7.5F);
		this.swimingTime += 35;
		this.swingLimit = 5;
	}
	
	@Override
	public void setDead() {
		super.setDead();
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
        this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(100.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(10.0D);
	}
	
	public void setDefenseMode(boolean bool)
	{
		this.setIsDayTime(bool);
	}
	
	public void setAttackMode(boolean bool)
	{
		this.setIsSleeping(bool);
	}
	
	public boolean getDefenseMode()
	{
		return this.getIsDayTime();
	}
	
	public boolean getAttackMode()
	{
		return this.getIsSleeping();
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		if(this.laser != null && this.laser.isEntityAlive())
			this.laser.setPositionAndRotation(this.posX + this.LookX(), this.posY + this.height * 0.6, this.posZ + this.LookY(), this.rotationYaw, this.rotationPitch);
		
		if(this.frozenLaser != null && this.frozenLaser.isEntityAlive())
			this.frozenLaser.setPositionAndRotation(this.posX + this.LookX(), this.posY + this.height * 0.6, this.posZ + this.LookY(), this.rotationYaw, this.rotationPitch);
		
		if(this.getDefenseMode())
			this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(22.0D);
		else
			this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(10.0D);
		
		if(this.huntingTarget != null && (!this.huntingTarget.isEntityAlive() || this.getDistance(huntingTarget) > 120))
			this.huntingTarget = null;
		
		if(this.attackTarget != null && (!this.attackTarget.isEntityAlive() || this.getDistance(attackTarget) > 120))
			this.attackTarget = null;
		
		//Accelerator();
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();

		//update skill cooldown
		updateCD();
		
		if(this.attackTarget != null)
			this.setAttackMode(true);
		else
			this.setAttackMode(false);
		
		
		//detect to defense or attack
		if(this.getDefenseMode())
			defenseMode();
		else if(this.getAttackMode())
			attackMode();
		else
			searchingTarget();
		
		if(!this.getAttackMode() && this.rand.nextInt(200) == 0)
		{
			this.rotationYaw += this.rand.nextInt(360) - 180;
			this.rotationPitch += this.rand.nextInt(90) - 45;
		}
		
		if(!this.world.isRemote)
			this.destroyBlocksInAABB(getEntityBoundingBox());
		
		List<Entity> entity = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(1));
		if(this.huntingTarget != null && entity.contains(this.huntingTarget))
			this.huntingMode(this.huntingTarget);
		
		if(this.getAttackMode())
		for(Entity entityIn : entity)
		{
			if(entityIn instanceof EntityLivingBase)
				this.huntingMode((EntityLivingBase) entityIn);
    		entityIn.addVelocity(this.motionX * 3, this.motionY * 3, this.motionZ * 3);
    		setEntityMove(entityIn, this.motionX * 3, this.motionY * 3, this.motionZ * 3);
		}
		
		List<Entity> entity1 = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(3));
		if(this.huntingTarget != null && entity1.contains(this.huntingTarget))
		{
			EntityWaterCreature.inWaterWalk(this, 0.1F, 5, 0.5F);
        	this.turn(2);
		}
	}
	
	public void defenseMode()
	{
		this.motionX = 0;
		this.motionY = 0;
		this.motionZ = 0;
		
		if(this.laser != null)
			this.laser.setDead();
		
		if(this.frozenLaser != null)
			this.frozenLaser.setDead();
		
		if(this.attackTarget != null)
		this.faceEntity(attackTarget, 2, 2);
		EntityMagickBubble bu = new EntityMagickBubble(this.world, this);
		bu.setPositionAndRotation(posX + this.rand.nextDouble(), posY + (this.height * 0.4) + this.rand.nextDouble(), posZ + this.rand.nextDouble(), rotationYaw, rotationPitch);
		if(!this.world.isRemote)
		this.world.spawnEntity(bu);

		if(this.rand.nextInt(300) == 0)
			this.setDefenseMode(false);
	}
	
	public void attackMode()
	{
		if(this.getDefenseMode())
			return;
		
		if(this.getDistance(attackTarget) <= 6)
		{
			if(this.rand.nextInt(20) == 0)
				this.waterGun(attackTarget);
			else if (this.rand.nextInt(5) == 0)
				this.bubbleGun(attackTarget);
			else
				this.tackle(attackTarget);
		}
		else if (this.getDistance(attackTarget) > 6 && this.getDistance(attackTarget) <= 15)
		{
			if(this.rand.nextInt(20) == 0)
				this.waterGun(attackTarget);
			else if (this.rand.nextInt(20) == 0)
				this.tackle(attackTarget);
			else
				this.bubbleGun(attackTarget);
		}
		else if (this.getDistance(attackTarget) > 15 && this.getDistance(attackTarget) <= 20)
		{
			if(this.rand.nextInt(10) == 0)
				this.iceBeam(attackTarget);
			else if(this.rand.nextInt(30) == 0)
				this.tackle(attackTarget);
			else if (this.rand.nextInt(5) == 0)
				this.bubbleGun(attackTarget);
			else
				this.waterGun(attackTarget);
		}
		else if(this.getDistance(attackTarget) > 20)
		{
			if(this.rand.nextInt(10) == 0)
				this.waterGun(attackTarget);
			else if(this.rand.nextInt(20) == 0)
				this.tackle(attackTarget);
			else if (this.rand.nextInt(10) == 0)
				this.bubbleGun(attackTarget);
			else
				this.iceBeam(attackTarget);
		}
		
		this.faceEntity(attackTarget, 20, 15);

		if(this.posY < this.attackTarget.posY)
			this.motionY += 0.05;
		else
			this.motionY -= 0.05;
		this.Moveto(attackTarget.posX, attackTarget.posY, attackTarget.posZ, 0.01F);
	}
	
	public void huntingMode(EntityLivingBase living)
	{
		this.faceEntity(living, 30F, 30F);
		boolean attack = living.attackEntityFrom(ModDamage.causeNormalEntityDamage(this), 7.5f);
		if(attack && (living instanceof EntityWaterCreature || living instanceof EntityWaterMob) && !(living instanceof EntityRockShellLeviathan))
		{
			if(this.getHealth() < this.getMaxHealth())
			{
				this.heal(2f);
			}
			else if(this.attackTarget != null)
			{
				this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.getMaxHealth() + 2);
			}
		}
		else
		{
			if(this.getHealth() < this.getMaxHealth())
			{
				this.heal(1f);
			}
			else if(this.attackTarget != null)
			{
				this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(this.getMaxHealth() + 1);
			}
		}
	}
	
	@Override
	public void waterMoving() 
	{
		BlockPos air1 = new BlockPos(this).up().up().up();
		BlockPos air2 = new BlockPos(air1).up();
		
		if(this.world.isAirBlock(air1) && this.world.isAirBlock(air2))
			this.motionY += -0.05;
		
		if(!this.world.isRemote && this.huntingTarget == null) {
            if(this.rand.nextInt(300) == 0)
            {
            	swimingTime += this.rand.nextInt(100) + 20;
            	spawnHuntingTool();
            }
            
            if(this.rand.nextInt(150) == 0)
            {
            	spawnHuntingTool();
            }
            
            if(swimingTime > 0)
            {
            	EntityWaterCreature.inWaterWalk(this, 0.1F, 5, 0.5F);
            	this.turn(2);
            	if(this.rand.nextInt(3) == 0)
            	{
            		EntityWaterCreature.inWaterWalk(this, 0.2F, 5, 0.5F);
            		this.turn(2);
            	}
            	swimingTime--;
            }
		}

		if(!this.world.isRemote && this.huntingTarget != null && this.ticksExisted % 200 == 0)
		{
			int size = (int)this.huntingTarget.width + (int)this.huntingTarget.height;
			size = size /2;
			if(size < 1)
				size = 1;
			for(int i = 0; i < size; ++i)
			{
				spawnHuntingTool();
			}
		}
		else if(this.huntingTarget != null && this.getAttackMode())
		{
			this.faceEntity(this.huntingTarget, 20, 30);
			this.Moveto(this.huntingTarget.posX, this.huntingTarget.posY, this.huntingTarget.posZ, 0.03F);
		}
		
		if(warningTime > 0)
			warningTime--;
	}
	
	public void spawnHuntingTool()
	{
		EntityLightingThing light = new EntityLightingThing(this.world, this, this.huntingTarget);
		light.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
		if(!this.world.isRemote)
			this.world.spawnEntity(light);
	}
	
	public static void setEntityTP(Entity e, double movex, double movey, double movez)
	{
		double[] move = {0, 0, 0};
		double[] pos = {movex, movey, movez};
		NetworkHandler.getNetwork().sendToAll(new NetworkEntityMoving(e.getUniqueID(), pos, move, 0));
	}
	
	public static void setEntityMove(Entity e, double movex, double movey, double movez)
	{
		double[] pos = {0, 0, 0};
		double[] move = {movex, movey, movez};
		NetworkHandler.getNetwork().sendToAll(new NetworkEntityMoving(e.getUniqueID(), pos, move, 1));
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) 
	{
		boolean hasDamage = false;
		for(DamageReduce re : damageList)
		{
			if(re.damage.equals(source.damageType))
			{
				hasDamage = true;
				amount *= re.getReduce();
			}
		}
		
		if(!hasDamage)
		{
			DamageReduce reduce = new DamageReduce(source.damageType);
			damageList.add(reduce);
		}
		
		if((amount > this.getHealth() && this.getHealth() > 2) || (this.getHealth() < (this.getMaxHealth() / 3) && this.rand.nextInt(3) == 0))
		{
			amount = amount / 2;
			//here use datTime instead of defense, 'cause it's a existing var and i'm lazy.
			this.setDefenseMode(true);
		}
		
		if(this.getDefenseMode())
			amount = amount / 10;
		
		boolean attack = super.attackEntityFrom(source, amount);
		
		if(attack)
		{
			this.warningTime += 1;
			warningOthers();
		}
		
		if(source.damageType.equals(DamageSource.IN_WALL.damageType))
		{
    		amount = 0;
    		return false;
		}
		
		if(source.getTrueSource() instanceof EntityLivingBase)
			this.attackTarget = (EntityLivingBase) source.getTrueSource();

		return attack;
	}
	
	@Override
	protected void collideWithEntity(Entity entityIn) {
        if (!this.isRidingSameEntity(entityIn))
        {
            if (!this.noClip)
            {
                double d0 = entityIn.posX - this.posX;
                double d1 = entityIn.posZ - this.posZ;
                double d2 = MathHelper.absMax(d0, d1);

                if (d2 >= 0.009999999776482582D)
                {
                    d2 = (double)MathHelper.sqrt(d2);
                    d0 = d0 / d2;
                    d1 = d1 / d2;
                    double d3 = 1.0D / d2;

                    if (d3 > 1.0D)
                    {
                        d3 = 1.0D;
                    }

                    d0 = d0 * d3;
                    d1 = d1 * d3;
                    d0 = d0 * 0.05000000074505806D;
                    d1 = d1 * 0.05000000074505806D;
                    d0 = d0 * (double)(1.0F - this.entityCollisionReduction);
                    d1 = d1 * (double)(1.0F - this.entityCollisionReduction);
                    
                    if (!entityIn.isBeingRidden())
                    {
                    		entityIn.addVelocity(this.motionX, this.motionY, this.motionZ);
                    		setEntityMove(entityIn, this.motionX, this.motionY, this.motionZ);
                    }
                }
            }
        }
	}
	
	@Override
	public void addVelocity(double x, double y, double z) {}
	
	private void warningOthers()
	{
		List<Entity> entities = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(20));
		
		for(Entity e : entities)
		{
			if(e instanceof EntityRockShellLeviathan)
			{
				EntityRockShellLeviathan fish = (EntityRockShellLeviathan) e;
				if(this.attackTarget != null && fish.attackTarget == null)
					fish.attackTarget = this.attackTarget;
			}
		}
	}
	
	@Override
	protected void doSwing() {
		float motion = (float) (Math.abs(this.motionX) + Math.abs(this.motionY) + Math.abs(this.motionZ));
		
		if(swingUp)
		{
			if(motion > 0)
				swing += motion;
		}
		else
		{
			if(motion > 0)
				swing -= motion;
		}
		
		if(swing > this.swingLimit)
			swingUp = false;
		else if(swing < -this.swingLimit)
			swingUp = true;
	}
	
	private void searchingTarget() {
		AxisAlignedBB box = new AxisAlignedBB(this.posX, this.posY - height, this.posZ, this.posX, this.posY, this.posZ).grow(10, 10, 10);
		List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, box);
			
		for(int i = 0; i < list.size(); i++)
		{
			Entity e = list.get(i);
			if(e instanceof EntityLivingBase && !(e instanceof EntityRockShellLeviathan)  && !(e instanceof EntityVillager) && this.canSeeTarget(e))
			{
				boolean creat = false;
				EntityLivingBase living = (EntityLivingBase) e;
				if(living instanceof EntityPlayer)
				{
					EntityPlayer p = (EntityPlayer) living;
					if(p.capabilities.isCreativeMode)
						creat = true;
				}
				
				if(!creat && living.isEntityAlive())
				{
				if(this.huntingTarget == null)
					this.huntingTarget = living;
				else if(this.getIfCloestTarget(this, living, this.huntingTarget))
				{
					this.huntingTarget = living;
				}
				}
			}
		}
	}
	
	@Override
	public float getEyeHeight() {
		return this.height * 0.6F;
	}
	
	@Override
	public void GroundThing() 
	{
		for(int i = 0; i < 2; ++i)
		{
		EntityMagickBubble bu = new EntityMagickBubble(this.world, this);
		bu.setPositionAndRotation(posX + this.rand.nextDouble(), posY + this.getEyeHeight() + this.rand.nextDouble(), posZ + this.rand.nextDouble(), this.rand.nextInt(360), this.rand.nextInt(360));
		if(!this.world.isRemote)
		this.world.spawnEntity(bu);
		}
	if(!(this.ticksExisted % 20 == 0))
		return;
	this.setNoGravity(false);
	
	if(this.getAir() < 20)
	{
	boolean water = false;
	Iterable<BlockPos> blocks= BlockPos.getAllInBox(getPosition().add(-30, -30, -30), getPosition().add(30, 30, 30));
	BlockPos waterPos = null;
	for(BlockPos pos : blocks)
	{
		if(this.world.getBlockState(pos).getBlock() == Blocks.WATER || this.world.getBlockState(pos).getBlock() == Blocks.FLOWING_WATER)
		{
			water = true;
			
			if(waterPos == null)
				waterPos = pos;
			if(waterPos != null && this.getIfCloestTarget(this, pos, waterPos))
				waterPos = pos;
		}
	}
	
	if(water)
		new TeleportMagick(this, waterPos.add(0, -2, 0));
	else
	{
		Iterable<BlockPos> blocks1= BlockPos.getAllInBox(getPosition().add(-2, -1, -2), getPosition().add(2, 1, 2));
	
		for(BlockPos pos : blocks1)
		{
			if(this.world.isAirBlock(pos))
				this.world.setBlockState(pos, Blocks.WATER.getDefaultState());
		}
	}
	}
	}
	
	public void Accelerator()
	{
		if(!this.getDefenseMode())
		{
			AxisAlignedBB box1 = new AxisAlignedBB(getPosition().add(0, -0.5, 0)).grow(this.width / 2 + 1, 2, this.width / 2 + 1);
			List<Entity> entity1 = this.world.getEntitiesWithinAABBExcludingEntity(this, box1);
    	
			AxisAlignedBB box2 = new AxisAlignedBB(getPosition().add(0, this.height + 0.5, 0)).grow(this.width / 2 + 1, 2, this.width / 2 + 1);
			List<Entity> entity2 = this.world.getEntitiesWithinAABBExcludingEntity(this, box2);
    	
    	for(Entity entity : entity1)
    	{
    		if(!(entity instanceof EntityLivingBase) && !(entity instanceof EntityLaserBeamBase) && !(entity instanceof EntityMagickBubble))
    		{
    			Entity newEntity = CopyMagick.copyEntity(entity);
    			newEntity.motionX = -entity.motionX;
    			newEntity.motionY = -entity.motionY;
    			newEntity.motionZ = -entity.motionZ;
    			
    			newEntity.rotationPitch = -entity.rotationPitch;
    			newEntity.rotationYaw = -entity.rotationYaw;
    			if(!this.world.isRemote)
    				this.world.removeEntity(entity);
    			if(!this.world.isRemote)
    				this.world.spawnEntity(newEntity);
    		}
    	}
    	
    	for(Entity entity : entity2)
    	{
    		if(!(entity instanceof EntityLivingBase) && !(entity instanceof EntityLaserBeamBase) && !(entity instanceof EntityMagickBubble))
    		{
    			Entity newEntity = CopyMagick.copyEntity(entity);
    			newEntity.motionX = -entity.motionX;
    			newEntity.motionY = -entity.motionY;
    			newEntity.motionZ = -entity.motionZ;
    			
    			newEntity.rotationPitch = -entity.rotationPitch;
    			newEntity.rotationYaw = -entity.rotationYaw;
    			this.world.removeEntity(entity);
    			this.world.spawnEntity(newEntity);
    		}
    	}
    	
		}
		else
		{
			List<Entity> preEntity = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(1));
    	
    	for(Entity entity : preEntity)
    	{
    		if(!(entity instanceof EntityLivingBase) && !(entity instanceof EntityLaserBeamBase) && !(entity instanceof EntityMagickBubble))
    		{
    			Entity newEntity = CopyMagick.copyEntity(entity);
    			newEntity.motionX = -entity.motionX;
    			newEntity.motionY = -entity.motionY;
    			newEntity.motionZ = -entity.motionZ;
    			
    			newEntity.rotationPitch = -entity.rotationPitch;
    			newEntity.rotationYaw = -entity.rotationYaw;
    			this.world.removeEntity(entity);
    			this.world.spawnEntity(newEntity);
    		}
    	}
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound compound) {
		super.readEntityFromNBT(compound);
		
		if(compound.hasKey("SC_DamageReduce"))
		{
			NBTTagList list = compound.getTagList("SC_DamageReduce", 10);
			for(int i = 0; i < list.tagCount(); ++i)
			{
				NBTTagCompound nbt = list.getCompoundTagAt(i);
				if(nbt.hasKey("DamageType"))
				{
					DamageReduce reduce = new DamageReduce(nbt.getString("DamageType"));
					reduce.setReduce(nbt.getFloat("Reduce"));
					this.damageList.add(reduce);
				}
			}
		}
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound compound) {
		super.writeEntityToNBT(compound);
		
		if(this.damageList != null)
		{
			NBTTagList list = new NBTTagList();
			for(DamageReduce reduce : this.damageList)
			{
				NBTTagCompound nbt = new NBTTagCompound();
				nbt.setFloat("Reduce", reduce.getTrueReduce());
				nbt.setString("DamageType", reduce.getDamage());
				list.appendTag(nbt);
			}
			compound.setTag("SC_DamageReduce", list);
		}
	}
	
	public class DamageReduce
	{
		private String damage;
		private float reduce;
		
		public DamageReduce(String damage) {
			this.damage = damage;
			this.reduce = 1.0F;
		}
		
		public float getReduce()
		{
			this.reduce *= 0.8;
			return this.reduce;
		}
		
		public float getTrueReduce()
		{
			return this.reduce;
		}
		
		public void setReduce(float re)
		{
			this.reduce = re;
		}
		
		public String getDamage()
		{
			return this.damage;
		}
	}
	
	public void updateCD()
	{
		if(tackleCD > 0)
			tackleCD--;
		
		if(waterGunCD > 0)
			waterGunCD--;
		
		if(bubbleGunCD > 0)
			bubbleGunCD--;
		
		if(iceBeamCD > 0)
			iceBeamCD--;
	}
	
	public  void tackle(Entity e)
	{
		if(tackleCD <= 0)
		{
			this.faceEntity(e, 360, 40);
			this.Moveto(e.posX, e.posY, e.posZ, 2);
			tackleCD = tackleCDLimit;
		}
	}
	
	public  void waterGun(Entity e)
	{
		if(waterGunCD <= 0)
		{
			this.faceEntity(e, 180, 40);
			EntityAdvanceLaser laser = new EntityAdvanceLaser(this.world);
			laser.setOwner(this);
			laser.setDamage(1);
			laser.setPositionAndRotation(this.posX + this.LookX(), this.posY + this.getEyeHeight(), this.posZ + this.LookZ(), this.rotationYaw, this.rotationPitch);
			laser.setWidth(0.5F);
			laser.red = 0;
			laser.blue = 1;
			laser.green = 1;
			this.laser = laser;
			if(!this.world.isRemote)
				this.world.spawnEntity(laser);
			waterGunCD = waterGunCDLimit;
		}
	}
	
	public  void iceBeam(Entity e)
	{
		if(iceBeamCD <= 0)
		{
			this.faceEntity(e, 90, 40);
			EntityAdvanceLaser laser = new EntityAdvanceLaser(this.world);
			laser.setOwner(this);
			laser.setDamage(1);
			laser.setPositionAndRotation(this.posX + this.LookX(), this.posY + this.getEyeHeight(), this.posZ + this.LookZ(), this.rotationYaw, this.rotationPitch);
			laser.setWidth(0.5F);
			laser.red = 0;
			laser.blue = 1;
			laser.green = 1;
			laser.setSnow();
			this.frozenLaser = laser;
			if(!this.world.isRemote)
				this.world.spawnEntity(laser);
			iceBeamCD = iceBeamCDLimit;
		}
	}
	
	public  void bubbleGun(Entity e)
	{
		if(bubbleGunCD <= 0)
		{
			this.faceEntity(e, 180, 40);
			for(int i = 0; i < 50; ++i)
			{
				EntityMagickBubble bu = new EntityMagickBubble(this.world, this);
				bu.setPositionAndRotation(posX + this.rand.nextDouble(), posY + this.rand.nextDouble() + this.getEyeHeight(), posZ + this.rand.nextDouble(),
						rotationYaw + 5 - this.rand.nextInt(10), rotationPitch + 5 - this.rand.nextInt(10));
				if(!this.world.isRemote)
					this.world.spawnEntity(bu);
			}
			bubbleGunCD = bubbleGunCDLimit;
		}
	}
}
