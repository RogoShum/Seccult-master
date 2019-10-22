package testmod.seccult.entity.livings;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.block.BlockFire;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.ai.EntityFlyHelper;
import net.minecraft.entity.ai.EntityMoveHelper;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.passive.EntityWaterMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagIntArray;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.pathfinding.Path;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import testmod.seccult.entity.livings.flying.EntityFlyable;
import testmod.seccult.entity.livings.water.EntityWaterCreature;
import testmod.seccult.init.ModItems;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.items.ItemMagickCore;
import testmod.seccult.items.ItemMagickable;
import testmod.seccult.items.ItemSoulStone;
import testmod.seccult.magick.active.DefenceMagic;
import testmod.seccult.magick.active.Magick;
import testmod.seccult.magick.implementation.ImplementationFocused;
import testmod.seccult.potions.ModPotions;

public class EntitySpiritDummy extends EntityBase{
	public ItemStack SoulStone = ItemStack.EMPTY;
	
	public EntityLivingBase Owner; 
	public UUID OwnerUUID; 
	
	private int ManaValue;
	
	private int ColdDown_Left = 35;
	private int ColdDown_Right = 35;
	
	private float Awaydistance = 2;
	private float defenceColdDown = 100;
	
	private Path path = null;
	private int lostTime;

	private List<String> hurtedMe = new ArrayList<String>();
	
	private static final DataParameter<ItemStack> ItemStackSoul = EntityDataManager.<ItemStack>createKey(EntitySpiritDummy.class, DataSerializers.ITEM_STACK);
	
	private static final DataParameter<Boolean> canFly = EntityDataManager.<Boolean>createKey(EntitySpiritDummy.class, DataSerializers.BOOLEAN);
	private static final DataParameter<Boolean> canSwim = EntityDataManager.<Boolean>createKey(EntitySpiritDummy.class, DataSerializers.BOOLEAN);
	
	private static final DataParameter<Integer> L_M = EntityDataManager.<Integer>createKey(EntitySpiritDummy.class, DataSerializers.VARINT);
	private static final DataParameter<Integer> R_M = EntityDataManager.<Integer>createKey(EntitySpiritDummy.class, DataSerializers.VARINT);
	
	private int walkingTime;

	public EntitySpiritDummy(World worldIn) {
		super(worldIn);
		this.setSize(0.6F, 1.5F);
	}
	
	public Magick getLeftHandMagick()
	{
		if(this.getMagickID_L() != 0)
		{
			Magick magick = ModMagicks.getMagickFromName(
					ModMagicks.GetMagickStringByID(this.getMagickID_L()));
			return magick;
		}
		else
		{
			return null;
		}
	}
	
	public Magick getRightHandMagick()
	{
		if(this.getMagickID_R() != 0)
		{
			Magick magick = ModMagicks.getMagickFromName(
					ModMagicks.GetMagickStringByID(this.getMagickID_R()));
			return magick;
		}
		else
		{
			return null;
		}
	}
	
	public EntityLivingBase getEntity()
	{
		if(this.SoulStone != ItemStack.EMPTY && this.SoulStone != null)
			return ItemSoulStone.getSoul(this.SoulStone, this.world);
		else
			return null;
	}
	
	public boolean hasSoulStone()
	{
		if(this.getSoulStone().getItem() == ModItems.SoulStone)
			return true;
		else
			return false;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(5D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.ATTACK_SPEED).setBaseValue(0D);
		this.getAttributeMap().registerAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(5D);
	}
	
	@Override
	public boolean canBreatheUnderwater() {
		return this.getCanSwim();
	}
	
	@Override
	public void setDead() {
		if(this.SoulStone != ItemStack.EMPTY && this.SoulStone != null)
		{
			if(!this.world.isRemote)
			this.entityDropItem(SoulStone, this.height / 2);
		}
		
		super.setDead();
	}
	
	@Override
	public boolean isEntityUndead() {
		if(this.getEntity() != null)
			return true;
		return false;
	}
	
	@Override
	public boolean isEntityAlive() {
		if(this.getEntity() != null)
			return true;
		return false;
	}
	
	public boolean putStoneIn(ItemStack item)
	{
		if(item != ItemStack.EMPTY && item.getItem() instanceof ItemSoulStone)
		{
			this.SoulStone = item.copy();
			this.setSoulStone(item.copy());
			
			EntityLivingBase living = this.getEntity();
			
			if(living != null)
			{
				if(living.getAttributeMap().getAllAttributes().contains(living.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MAX_HEALTH)))
				this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(living.getMaxHealth());
				
				if(living.getAttributeMap().getAllAttributes().contains(living.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR)))
				this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(living.getEntityAttribute(SharedMonsterAttributes.ARMOR).getBaseValue());
				
				if(living.getAttributeMap().getAllAttributes().contains(living.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ARMOR_TOUGHNESS)))
				this.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).setBaseValue(living.getEntityAttribute(SharedMonsterAttributes.ARMOR_TOUGHNESS).getBaseValue());
				
				if(living.getAttributeMap().getAllAttributes().contains(living.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_DAMAGE)))
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).setBaseValue(living.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue());
				
				if(living.getAttributeMap().getAllAttributes().contains(living.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.ATTACK_SPEED)))
				this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).setBaseValue(living.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getBaseValue());
				
				if(living.getAttributeMap().getAllAttributes().contains(living.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.FLYING_SPEED)))
				this.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).setBaseValue(living.getEntityAttribute(SharedMonsterAttributes.FLYING_SPEED).getBaseValue());
				
				if(living.getAttributeMap().getAllAttributes().contains(living.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.MOVEMENT_SPEED)))
				this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).setBaseValue(living.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue());
				
				if(living.getAttributeMap().getAllAttributes().contains(living.getAttributeMap().getAttributeInstance(SharedMonsterAttributes.KNOCKBACK_RESISTANCE)))
					this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(living.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).getBaseValue());

				this.heal(getMaxHealth());
				
				if(living instanceof EntityFlying || living instanceof EntityFlyable)
				{
					this.setCanFly(true);
					this.moveHelper = new EntityFlyHelper(this);
				}
				else
				{
					this.setCanFly(false);
					this.moveHelper = new EntityMoveHelper(this);
				}
				
				if(living instanceof EntityWaterMob || living instanceof EntityWaterCreature)
					this.setCanSwim(true);
				else
					this.setCanSwim(false);
				
				
			}
			return true;
		}
		
		return false;
	}
	
	public double getSpeed()
	{
		return this.getEntityAttribute(SharedMonsterAttributes.MOVEMENT_SPEED).getBaseValue() + 0.5F;
	}
	
	public double getTrackRange()
	{
		return this.getEntityAttribute(SharedMonsterAttributes.FOLLOW_RANGE).getBaseValue() + 1;
	}
	
	public int getAttackDamage()
	{
		return (int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_DAMAGE).getBaseValue() + 1;
	}
	
	public int getAttackSpeed()
	{
		return (int)this.getEntityAttribute(SharedMonsterAttributes.ATTACK_SPEED).getBaseValue() + 1;
	}
	
	@Override
	public boolean attackEntityFrom(DamageSource source, float amount) {
		
		if(source.getDamageType().equals(DamageSource.IN_WALL.getDamageType()) || source.getDamageType().equals(DamageSource.CRAMMING.getDamageType())
				|| source.getDamageType().equals(DamageSource.STARVE.getDamageType()))
			return false;
		
		if(this.getCanFly() && source.damageType.equals(DamageSource.FALL.damageType))
			return false;
		if(source.getTrueSource() == this.getLastAttackedEntity() || source.getTrueSource() == this.getRevengeTarget())
			this.Awaydistance += 0.3;
		if(this.Awaydistance > 12)
			this.Awaydistance = 12;
		
		Entity eI = source.getImmediateSource();
		
		Entity eS = source.getTrueSource();
		
		if(eI != null)
		{
			ResourceLocation className = EntityList.getKey(eI.getClass());
		    if(className != null) 
		    {
		    	if(!this.hurtedMe.contains(className.toString()))
		    	this.hurtedMe.add(className.toString());
		    }
		}
		
		if(eS instanceof EntitySpiritDummy && ((EntitySpiritDummy) eS).Owner == this.Owner)
			return false;
		
		if(eS != null && !(eS instanceof EntityPlayer) && !(eS instanceof EntitySpiritDummy))
		{
			this.defenceColdDown += 100;
			
			ResourceLocation className = EntityList.getKey(eS.getClass());
		    if(className != null) 
		    {
		    	if(!this.hurtedMe.contains(className.toString()))
		    	this.hurtedMe.add(className.toString());
		    }
		}
		
		return super.attackEntityFrom(source, amount);
	}
	
	@Override
	public EnumActionResult applyPlayerInteraction(EntityPlayer player, Vec3d vec, EnumHand hand) {
		
		if(putStoneIn(player.getHeldItemMainhand()))
		{
			player.getHeldItemMainhand().setCount(player.getHeldItemMainhand().getCount() - 1);
			this.Owner = player;
			return EnumActionResult.SUCCESS;
		}

		if(putMagickCore(player.getHeldItemMainhand()))
			return EnumActionResult.SUCCESS;
		
		if(player.getHeldItemMainhand().getItem() == new ItemStack(Blocks.AIR).getItem() && player.isSneaking())
		{
			if(!this.world.isRemote)
			this.entityDropItem(SoulStone.copy(), this.height / 2);
			this.SoulStone = ItemStack.EMPTY;
			this.setSoulStone(ItemStack.EMPTY);
			this.Owner = null;
			this.OwnerUUID = null;
			NBTTagCompound tag = new NBTTagCompound();
			this.setCanFly(false);
			this.setCanSwim(false);
			this.setMagickID_L(0);
			this.setMagickID_R(0);
			this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(5D);
			this.writeToNBT(tag);
			tag.removeTag("OwnerUUID");
			tag.removeTag("SoulStone");
			this.writeToNBT(tag);
			return EnumActionResult.SUCCESS;
		}
		
		return EnumActionResult.PASS;
	}
	
	public boolean putMagickCore(ItemStack core)
	{
		if(core.getItem() instanceof ItemMagickCore && ItemMagickCore.getMagickString(core) != null)
		{
			this.addMagickData(ModMagicks.GetMagickIDByString(ItemMagickCore.getMagickString(core)));
			core.shrink(1);
			return true;
		}
		return false;
	}
	
	@Override
	public float getEyeHeight() {
		return this.height + 0.1F;
	}
	
	//
	public void onUpdate()
	{
		super.onUpdate();
		
		if(this.ColdDown_Left > 0)
			ColdDown_Left--;
		
		if(this.ColdDown_Right > 0)
			ColdDown_Right--;
		
		if(this.ManaValue < 500)
			ManaValue += 2;
		
		if(this.defenceColdDown > 0)
			this.defenceColdDown--;
		
		if(this.getHealth() > 0)
			this.deathTime = 0;
		
		if(this.Owner == null && this.OwnerUUID != null)
		{
			List<Entity> entity = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(64));
			
			for(int i = 0; i < entity.size(); i++)
			{
				Entity e = entity.get(i);
				if(e.getUniqueID().equals(this.OwnerUUID))
					this.Owner = (EntityLivingBase) e;
			}
		}
		
		if(this.Owner != null)
		{
			protectOwner();
		}
		else
			return;
		
			Iterable<BlockPos> Bpos = BlockPos.getAllInBox(this.getPosition().add(-2, -2, -2), this.getPosition().add(2, 2, 2));
			
			for(BlockPos bPos : Bpos)
			{
				if(this.world.getBlockState(bPos).getBlock() == Blocks.LAVA || this.world.getBlockState(bPos).getBlock() == Blocks.LAVA || this.world.getBlockState(bPos).getBlock() instanceof BlockFire)
				{
					this.AwayFrom(bPos.getX(), bPos.getY(), bPos.getZ(), this.getSpeed() / 10);
					this.tryJump();
				}
			}

		if(this.isBurning())
		{
			Iterable<BlockPos> pos = BlockPos.getAllInBox(this.getPosition().add(-7, -5, -7), this.getPosition().add(7, 5, 7));
			
			for(BlockPos bPos : pos)
			{
				if(this.world.getBlockState(bPos).getBlock() == Blocks.WATER || this.world.getBlockState(bPos).getBlock() == Blocks.FLOWING_WATER)
				{
					this.moveHelper.setMoveTo(bPos.getX(), bPos.getY(), bPos.getZ(), this.getSpeed());
					this.tryJump();
				}
			}
			
		}
		
		awayFromProjectile();
	}
	
	protected void tryDodge(float vaule, double x, double z)
	{
		this.motionX += z * vaule;
		
		this.motionZ += x * vaule;
		
		this.tryJump();
	}
	
	protected void tryJump()
	{
		BlockPos pos = new BlockPos(this.getPositionVector().addVector(this.motionX * 3, 0, this.motionZ * 3));
		
		BlockPos posAir = new BlockPos(this.getPositionVector().addVector(this.motionX * 3, -1, this.motionZ * 3));
		
		BlockPos posBlock = new BlockPos(this.getPositionVector().addVector(this.motionX * 6, -1, this.motionZ * 6));
		
		BlockPos Lpos = new BlockPos(this.getPositionVector().addVector(this.LookX(), 0, this.LookZ()));
		
		BlockPos LposAir = new BlockPos(this.getPositionVector().addVector(this.LookX(), -1, this.LookZ()));
		
		BlockPos LposBlock = new BlockPos(this.getPositionVector().addVector(this.LookX() * 2, -1, this.LookZ() * 2));

		if(this.world.getBlockState(pos.up()).getBlock() instanceof BlockFire)
			this.world.setBlockToAir(pos.up());
		if(this.world.getBlockState(Lpos.up()).getBlock() instanceof BlockFire)
			this.world.setBlockToAir(Lpos.up());
		
		if(!(this.world.getBlockState(pos).getBlock() instanceof BlockLiquid) && !(this.world.getBlockState(Lpos).getBlock() instanceof BlockLiquid)
				&& !(this.world.getBlockState(posAir).getBlock() instanceof BlockLiquid) && !(this.world.getBlockState(posBlock).getBlock() instanceof BlockLiquid)
				&& !(this.world.getBlockState(LposAir).getBlock() instanceof BlockLiquid) && !(this.world.getBlockState(LposBlock).getBlock() instanceof BlockLiquid))
		{
		if(this.world.getBlockState(pos).isFullBlock() || (!this.world.getBlockState(posAir).isFullBlock() && this.world.getBlockState(posBlock).isFullBlock()))
			this.motionY += 0.1F;
		
		if(this.world.getBlockState(Lpos).isFullBlock() || (!this.world.getBlockState(LposAir).isFullBlock() && this.world.getBlockState(LposBlock).isFullBlock()))
			this.motionY += 0.1F;
		}
	}
	
	protected void awayFromProjectile()
	{
		List<Entity> entity = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(16));
		
		for(int i = 0; i < entity.size(); i++)
		{
			if(!(entity.get(i) instanceof EntityLivingBase))
			{
			Entity trackedEntity = ImplementationFocused.getEntityMotionAt(entity.get(i), 100);
			if(trackedEntity == this)
			{
				this.AwayFrom(entity.get(i).posX, entity.get(i).posY, entity.get(i).posZ, (float)(this.getSpeed() / 40));
				this.tryDodge((float)this.getSpeed() / 3, entity.get(i).motionX, entity.get(i).motionZ);
			}
			}
		}
		
		List<Entity> entity_mob = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(5));
		
		for(int i = 0; i < entity_mob.size(); i++)
		{
			if(entity_mob.get(i) instanceof EntityMob)
			{
				this.AwayFrom(entity_mob.get(i).posX, entity_mob.get(i).posY, entity_mob.get(i).posZ, (float)(this.getSpeed() / 5));
				//this.tryDodge((float)this.getSpeed() / 5, entity_mob.get(i).motionX, entity_mob.get(i).motionZ);
			}
			
			String id = "oh";
			ResourceLocation className = EntityList.getKey(entity_mob.get(i).getClass());
		    if(className != null) 
		    {
		    	id = className.toString();
		    }
			
		    if(hurtedMe.contains(id))
		    {
		    	
		    	if(entity.get(i) instanceof EntityLivingBase)
		    	{
		    		this.setRevengeTarget((EntityLivingBase)entity.get(i));
		    	}
		    	else
		    		this.defenceColdDown += 10;
		    }
		}
		
		List<Entity> entity_too_close = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(2.5));
		
		for(int i = 0; i < entity_too_close.size(); i++)
		{
			String id = "oh";
			ResourceLocation className = EntityList.getKey(entity_too_close.get(i).getClass());
		    if(className != null) 
		    {
		    	id = className.toString();
		    }
			
			if(entity_too_close.get(i) instanceof EntityMob || entity_too_close.get(i) instanceof EntityThrowable || hurtedMe.contains(id))
			{
				this.defenceColdDown += 35;
				//this.AwayFrom(entity_mob.get(i).posX, entity_mob.get(i).posY, entity_mob.get(i).posZ, (float)(this.getSpeed() / 5));
				this.tryDodge((float)this.getSpeed(), entity_too_close.get(i).motionX, entity_too_close.get(i).motionZ);
			}
		}
	}
	
	protected void protectOwner()
	{
		if(this.getRevengeTarget() == null && this.Owner.getAttackingEntity() != this)
		{
			this.setRevengeTarget(this.Owner.getAttackingEntity());
		}
		
		if(this.getRevengeTarget() == null && this.Owner.getRevengeTarget() != this)
		{
			this.setRevengeTarget(this.Owner.getRevengeTarget());
		}
		
		if(this.getLastAttackedEntity() == null && this.Owner.getLastAttackedEntity() != this)
		{
			this.setLastAttackedEntity(this.Owner.getLastAttackedEntity());
		}

		if(this.getRevengeTarget() == this.Owner)
			this.setRevengeTarget(null);
		
		if(this.getLastAttackedEntity() == this.Owner)
			this.setLastAttackedEntity(null);
		
		if(this.getRevengeTarget() instanceof EntitySpiritDummy && ((EntitySpiritDummy)this.getRevengeTarget()).Owner == this.Owner)
			this.setRevengeTarget(null);
		
		if(this.getLastAttackedEntity() instanceof EntitySpiritDummy && ((EntitySpiritDummy)this.getLastAttackedEntity()).Owner == this.Owner)
			this.setLastAttackedEntity(null);

		if(this.getRevengeTarget() == null && this.getLastAttackedEntity() == null)
			standBy();
		else if (this.getRevengeTarget() != null && this.getDistance(Owner) < 32)
		{
			faceEntity(this.getRevengeTarget(), 30, 90);
			tryMagickAttack(this.getRevengeTarget());
		}
		else if (this.getLastAttackedEntity() != null && this.getDistance(Owner) < 32)
		{
			faceEntity(this.getLastAttackedEntity(), 30, 90);
			tryMagickAttack(this.getLastAttackedEntity());
		}
		else
		if(Owner.getDistance(this) > 32)
		{
			faceEntity(Owner, 30, 90);
			this.moveHelper.setMoveTo(Owner.posX, Owner.posY, Owner.posZ, this.getSpeed());
			tryJump();
		}
		
		if(Owner.getDistance(this) > 64)
		{
			this.attemptTeleport(Owner.posX, Owner.posY, Owner.posZ);
		}
	}
	
	@Override
	protected void updateFallState(double y, boolean onGroundIn, IBlockState state, BlockPos pos) {
		if(this.getCanFly())
		fallDistance = 0;
		super.updateFallState(y, onGroundIn, state, pos);
	}
	
	protected void tryMagickAttack(Entity entity)
	{
		if(this.canEntityBeSeen(entity))
		{
			lostTime=0;
		if (this.getDistanceSq(entity) < Awaydistance) 
		{
			this.AwayFrom(entity.posX, entity.posY, entity.posZ, (float)(this.getSpeed() / 50));
			this.motionX += 0.2 - 0.4 * this.rand.nextFloat();
			this.motionZ += 0.2 - 0.4 * this.rand.nextFloat();
			tryJump();
		}
		
		if (this.getDistanceSq(entity) > 16) 
		{
			this.moveHelper.setMoveTo(entity.posX, entity.posY, entity.posZ, this.getSpeed());
			tryJump();
		}
		}
		else
		{
			this.lostTime++;
			if(lostTime > 100)
			{
				this.path = this.navigator.getPathToEntityLiving(entity);
				this.navigator.setPath(path, this.getSpeed());
				this.faceEntity(entity, 40, 90);
				lostTime = 0;
			}
		}
		
		if(this.leftHandMagick == null && this.rand.nextInt(10) == 0)
		{
			this.getControlMagic(DoYouGotMagickHand.LeftHand);
		}
		else if(this.rand.nextInt(4) == 0)
		{
			this.getAttackingMagic(DoYouGotMagickHand.LeftHand);
		}
		
			if(this.leftHandMagick != null && this.ColdDown_Left <= 1 && this.canSeeTarget(entity) && ManaValue > 20)
			{
				if(doControlMagick(DoYouGotMagickHand.LeftHand, entity, getAttackDamage(), getAttackDamage()))
				{
					this.ColdDown_Left = 35 - this.getAttackSpeed();
					if(ColdDown_Left < 0)
						this.ColdDown_Left = 0;
					this.ManaValue -= 20;
				}
				
				if(doFocusMagick(DoYouGotMagickHand.LeftHand, entity, getAttackDamage(), getAttackDamage()))
				{
					this.ColdDown_Left = 35 - this.getAttackSpeed();
					if(ColdDown_Left < 0)
						this.ColdDown_Left = 0;
					this.ManaValue -= 20;
				}
			}

		if(this.defenceColdDown <= 5)
		{
			
			this.getAttackingMagic(DoYouGotMagickHand.RightHand);

			if(this.rightHandMagick != null && this.ColdDown_Right <= 1 && this.canSeeTarget(entity) && ManaValue > 20)
			{
				if(doFocusMagick(DoYouGotMagickHand.RightHand, entity, getAttackDamage(), getAttackDamage()))
				{
					this.ColdDown_Right = 35 - this.getAttackSpeed();
					if(ColdDown_Left < 0)
						this.ColdDown_Right = 0;
					this.ManaValue -= 20;
				}
			}
		}
		else
		{
			this.getDefenceMagic(DoYouGotMagickHand.RightHand);
			if(this.rightHandMagick != null && this.rightHandMagick instanceof DefenceMagic)
			{
			if(this.ticksExisted % 5 == 0 && !this.isPotionActive(ModPotions.shield))
			{
				if(doProtectMagick(getAttackDamage(), getAttackSpeed()) && ManaValue > 1)
				{
					this.ManaValue -= 2;
				}
			}
			}
			else
			{
				this.getAttackingMagic(DoYouGotMagickHand.RightHand);

				if(this.rightHandMagick != null && this.ColdDown_Right <= 1 && this.canSeeTarget(entity) && ManaValue > 20)
				{
					if(doFocusMagick(DoYouGotMagickHand.RightHand, entity, getAttackDamage(), getAttackDamage()))
					{
						this.ColdDown_Right = 35 - this.getAttackSpeed();
						if(ColdDown_Left < 0)
							this.ColdDown_Right = 0;
						this.ManaValue -= 20;
					}
				}
			}
		}
		
		if(this.rightHandMagick != null)
		{
			if(!this.world.isRemote)
				this.setMagickID_R(ModMagicks.GetMagickIDByString(this.rightHandMagick.getNbtName()));
		}
		
		if(this.leftHandMagick != null)
		{
			if(!this.world.isRemote)
				this.setMagickID_L(ModMagicks.GetMagickIDByString(this.leftHandMagick.getNbtName()));
		}
	}
	
	protected void standBy()
	{
		if(walkingTime <= 0 || Owner.getDistance(this) > 16 || this.rand.nextInt(300) == 0)
		{
			if(this.rand.nextInt(40) == 0)
			walkingTime += this.rand.nextInt(100);
		}
		this.Awaydistance = 2F;
		faceEntity(Owner, 30, 20);
		
		if(this.walkingTime > 0)
		{
			this.moveHelper.setMoveTo(Owner.posX, Owner.posY, Owner.posZ, this.getSpeed());
			tryJump();
			walkingTime--;
		}
	}
	
	@Override
	protected void entityInit() 
	{
		super.entityInit();
		this.dataManager.register(ItemStackSoul, ItemStack.EMPTY);
		this.dataManager.register(canFly, false);
		this.dataManager.register(canSwim, false);
		this.dataManager.register(L_M, 0);
		this.dataManager.register(R_M, 0);
	}
	
	public void setSoulStone(ItemStack nbt) 
	{
		this.dataManager.set(ItemStackSoul, nbt);
	}
	
	public ItemStack getSoulStone() 
	{
		return this.dataManager.get(ItemStackSoul);
	}
	  
	public void setCanFly(boolean nbt) 
	{
		this.dataManager.set(canFly, nbt);
	}
	  
	public boolean getCanFly() 
	{
		return this.dataManager.get(canFly).booleanValue();
	}
	
	public void setCanSwim(boolean nbt) 
	{
		this.dataManager.set(canSwim, nbt);
	}
	  
	public boolean getCanSwim() 
	{
		return this.dataManager.get(canSwim).booleanValue();
	}
	
	public void setMagickID_L(int id) 
	{
		this.dataManager.set(L_M, id);
	}
	  
	public int getMagickID_L() 
	{
		return this.dataManager.get(L_M).intValue();
	}
	
	public void setMagickID_R(int id) 
	{
		this.dataManager.set(R_M, id);
	}
	  
	public int getMagickID_R() 
	{
		return this.dataManager.get(R_M).intValue();
	}
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		if(this.SoulStone != ItemStack.EMPTY && this.SoulStone != null)
		{
			NBTTagCompound tag = new NBTTagCompound();
			this.SoulStone.writeToNBT(tag);
			nbt.setTag("SoulStone", tag);
		}
		else if(this.getSoulStone() != ItemStack.EMPTY && this.getSoulStone() != null)
		{
			NBTTagCompound tag = new NBTTagCompound();
			this.getSoulStone().writeToNBT(tag);
			nbt.setTag("SoulStone", tag);
		}
		
		if(this.Owner != null)
		{
			nbt.setUniqueId("OwnerUUID", Owner.getUniqueID());
		}
		
		if(!this.hurtedMe.isEmpty())
		{
			NBTTagList list = new NBTTagList();
			for(String s : this.hurtedMe)
			{
				list.appendTag(new NBTTagString(s));
			}
			nbt.setTag("hurtedMe", list);
		}
		
		if(this.getCanFly())
		{
			nbt.setBoolean("canFly", true);
		}
		else
			nbt.setBoolean("canFly", false);
		
		if(this.getCanSwim())
		{
			nbt.setBoolean("canSwim", true);
		}
		else
			nbt.setBoolean("canSwim", false);
		
		if(this.magickData != null)
		{
			nbt.setIntArray("magickData", this.magickData);
		}
	}
	
	@Override
	public void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if(nbt.hasKey("SoulStone"))
		{
			NBTTagCompound soulStone = nbt.getCompoundTag("SoulStone");
			ItemStack stack = new ItemStack(soulStone);
			if(stack.getItem() instanceof ItemSoulStone)
				this.SoulStone = stack;
			this.setSoulStone(stack);
		}
		
		if(nbt.hasUniqueId("OwnerUUID"))
		{
			this.OwnerUUID = nbt.getUniqueId("OwnerUUID");
		}
		
		if(nbt.hasKey("hurtedMe"))
		{
			NBTTagList list = nbt.getTagList("hurtedMe", 8);
			
			for(int i = 0; i < list.tagCount(); ++i)
			{
				this.hurtedMe.add(list.getStringTagAt(i));
			}
		}
		
		if(nbt.hasKey("canFly"))
		{
			this.setCanFly(nbt.getBoolean("canFly"));
		}
		
		if(nbt.hasKey("canSwim"))
		{
			this.setCanFly(nbt.getBoolean("canSwim"));
		}
		
		if(nbt.hasKey("magickData"))
		{
			this.magickData = nbt.getIntArray("magickData");
		}
	}
}
