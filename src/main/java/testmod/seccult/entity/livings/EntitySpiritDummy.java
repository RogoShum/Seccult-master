package testmod.seccult.entity.livings;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityFlying;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.entity.projectile.EntityThrowable;
import net.minecraft.init.Blocks;
import net.minecraft.item.ItemStack;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.nbt.NBTTagString;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import testmod.seccult.entity.livings.flying.EntityFlyable;
import testmod.seccult.items.ItemSoulStone;
import testmod.seccult.magick.implementation.ImplementationFocused;

public class EntitySpiritDummy extends EntityBase{
	public ItemStack SoulStone = ItemStack.EMPTY;
	
	public EntityLivingBase Owner; 
	public UUID OwnerUUID; 
	
	private int ManaValue;
	
	private int ColdDown_Left = 35;
	private int ColdDown_Right = 35;
	
	private float Awaydistance = 2;
	private float defenceColdDown = 100;
	private boolean DodgeDirection = false;
	private List<String> hurtedMe = new ArrayList<String>();
	
	private static final DataParameter<ItemStack> ItemStackSoul = EntityDataManager.<ItemStack>createKey(EntitySpiritDummy.class, DataSerializers.ITEM_STACK);
	
	private static final DataParameter<Boolean> canFly = EntityDataManager.<Boolean>createKey(EntitySpiritDummy.class, DataSerializers.BOOLEAN);
	
	private static final DataParameter<Boolean> canSwim = EntityDataManager.<Boolean>createKey(EntitySpiritDummy.class, DataSerializers.BOOLEAN);
	
	private int walkingTime;

	public EntitySpiritDummy(World worldIn) {
		super(worldIn);
		this.setSize(1, 1.5F);
		
		this.addMagickData(8);
		this.addMagickData(9);
		this.addMagickData(10);
		this.addMagickData(22);
		this.addMagickData(25);
		this.addMagickData(26);
	}
	
	public EntityLivingBase getEntity()
	{
		if(this.SoulStone != ItemStack.EMPTY && this.SoulStone != null)
			return ItemSoulStone.getSoul(this.SoulStone, this.world);
		else
			return null;
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
					this.setCanFly(true);
				else
					this.setCanFly(false);
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
		
		if(source.getTrueSource() == this.getLastAttackedEntity() || source.getTrueSource() == this.getRevengeTarget())
			this.Awaydistance += 0.3;
		if(this.Awaydistance > 12)
			this.Awaydistance = 12;
		
		this.defenceColdDown += 100;
		
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
		
		if(eS != null && !(eS instanceof EntityPlayer) && !(eS instanceof EntitySpiritDummy))
		{
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

		if(player.getHeldItemMainhand().getItem() == new ItemStack(Blocks.AIR).getItem() && player.isSneaking())
		{
			if(!this.world.isRemote)
			this.entityDropItem(SoulStone.copy(), this.height / 2);
			this.SoulStone = ItemStack.EMPTY;
			this.setSoulStone(ItemStack.EMPTY);
			this.Owner = null;
			this.OwnerUUID = null;
			NBTTagCompound tag = new NBTTagCompound();
			this.writeToNBT(tag);
			tag.removeTag("OwnerUUID");
			tag.removeTag("SoulStone");
			this.writeToNBT(tag);
			return EnumActionResult.SUCCESS;
		}
		
		return EnumActionResult.PASS;
	}
	
	@Override
	public float getEyeHeight() {
		return this.height + 0.1F;
	}
	
	public void onUpdate()
	{
		super.onUpdate();
		this.turn(1);
		this.turn(2);

		/*if(this.ticksExisted % 20 == 0)
		{
		System.out.println(this.OwnerUUID);
		System.out.println(this.Owner);
		System.out.println(this.hurtedMe);
		System.out.println(this.SoulStone);
		}
		*/
		if(this.isBurning())
		{
			Iterable<BlockPos> pos = BlockPos.getAllInBox(this.getPosition().add(-7, -5, -7), this.getPosition().add(7, 5, 7));
			
			for(BlockPos bPos : pos)
			{
				if(this.world.getBlockState(bPos).getBlock() == Blocks.WATER || this.world.getBlockState(bPos).getBlock() == Blocks.FLOWING_WATER)
				{
					this.moveHelper.setMoveTo(bPos.getX(), bPos.getY(), bPos.getZ(), this.getSpeed());
				}
			}
			
		}
		
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
		
		if(this.ColdDown_Left > 0)
			ColdDown_Left--;
		
		if(this.ColdDown_Right > 0)
			ColdDown_Right--;
		
		if(this.ManaValue < 500)
			ManaValue += 2;
		
		if(this.defenceColdDown > 0)
			this.defenceColdDown--;
		
		if(this.Owner != null)
		{
			protectOwner();
		}
		
		awayFromProjectile();
	}
	
	protected void tryDodge()
	{
		if(this.rand.nextInt(5) == 0)
		this.motionX *= this.motionZ * this.rand.nextFloat();
		else
		this.motionZ *= this.motionX * this.rand.nextFloat();
	}
	
	protected void tryJump()
	{
		BlockPos pos = new BlockPos(this.getPositionVector().addVector(this.motionX * 3, 0, this.motionZ * 3));
		
		BlockPos posAir = new BlockPos(this.getPositionVector().addVector(this.motionX * 3, -1, this.motionZ * 3));
		
		BlockPos posBlock = new BlockPos(this.getPositionVector().addVector(this.motionX * 6, -1, this.motionZ * 6));
		
		BlockPos Lpos = new BlockPos(this.getPositionVector().addVector(this.LookX(), 0, this.LookZ()));
		
		BlockPos LposAir = new BlockPos(this.getPositionVector().addVector(this.LookX(), -1, this.LookZ()));
		
		BlockPos LposBlock = new BlockPos(this.getPositionVector().addVector(this.LookX() * 2, -1, this.LookZ() * 2));

		if(this.world.getBlockState(pos).isFullBlock() || (!this.world.getBlockState(posAir).isFullBlock() && this.world.getBlockState(posBlock).isFullBlock()))
			this.motionY += 0.1F;
		
		if(this.world.getBlockState(Lpos).isFullBlock() || (!this.world.getBlockState(LposAir).isFullBlock() && this.world.getBlockState(LposBlock).isFullBlock()))
			this.motionY += 0.1F;
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
				this.AwayFrom(entity.get(i).posX, entity.get(i).posY, entity.get(i).posZ, (float)(this.getSpeed()));
				this.tryDodge();
			}
			}
		}
		
		List<Entity> entity_mob = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(5));
		
		for(int i = 0; i < entity_mob.size(); i++)
		{
			if(entity_mob.get(i) instanceof EntityMob)
			{
				this.AwayFrom(entity_mob.get(i).posX, entity_mob.get(i).posY, entity_mob.get(i).posZ, (float)(this.getSpeed() / 100));
				this.tryDodge();
			}
			
			
			String id = "oh";
			ResourceLocation className = EntityList.getKey(entity_mob.get(i).getClass());
		    if(className != null) 
		    {
		    	id = className.toString();
		    }
			
		    if(hurtedMe.contains(id))
		    {
		    	this.faceEntity(entity.get(i), 20, 20);
		    	this.tryMagickAttack(entity.get(i));
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
				this.AwayFrom(entity_mob.get(i).posX, entity_mob.get(i).posY, entity_mob.get(i).posZ, (float)(this.getSpeed()));
				this.tryDodge();
			}
		}
	}
	
	protected void protectOwner()
	{
		if(this.getRevengeTarget() == null && this.Owner.getRevengeTarget() != this)
			this.setRevengeTarget(this.Owner.getRevengeTarget());
		if(this.getLastAttackedEntity() == null && this.Owner.getLastAttackedEntity() != this)
			this.setLastAttackedEntity(this.Owner.getLastAttackedEntity());
		
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
		else if (this.getRevengeTarget() != null && Owner.getDistance(this) < 16)
		{
			faceEntity(this.getRevengeTarget(), 30, 90);
			tryMagickAttack(this.getRevengeTarget());
		}
		else if (this.getLastAttackedEntity() != null && Owner.getDistance(this) < 16)
		{
			faceEntity(this.getLastAttackedEntity(), 30, 90);
			tryMagickAttack(this.getLastAttackedEntity());
		}
		else
		if(Owner.getDistance(this) > 16)
		{
			faceEntity(Owner, 30, 90);
			//Path path = this.navigator.getPathToEntityLiving(Owner);
			//this.navigator.setPath(path, this.getSpeed());
			this.moveHelper.setMoveTo(Owner.posX, Owner.posY, Owner.posZ, this.getSpeed());
			tryJump();
		}
		
		if(Owner.getDistance(this) > 32)
		{
			this.attemptTeleport(Owner.posX, Owner.posY, Owner.posZ);
		}
	}
	
	protected void tryMagickAttack(Entity entity)
	{
		if (this.getDistanceSq(entity) < Awaydistance) 
		{
			this.AwayFrom(entity.posX, entity.posY, entity.posZ, (float)(this.getSpeed() / 100));
			tryJump();
		}
		
		if (this.getDistanceSq(entity) > 16) 
		{
			this.moveHelper.setMoveTo(entity.posX, entity.posY, entity.posZ, this.getSpeed());
			tryJump();
		}
		
		if(this.rand.nextInt(10) == 0)
		{
			this.getControlMagic(DoYouGotMagickHand.LeftHand);
			
			if(this.leftHandMagick != null && this.ColdDown_Left <= 1 && this.canSeeTarget(entity) && ManaValue > 20)
			{
				if(doControlMagick(DoYouGotMagickHand.LeftHand, entity, getAttackDamage(), getAttackDamage()))
				{
					this.ColdDown_Left = 35 - this.getAttackSpeed();
					this.ManaValue -= 20;
				}
			}
		}
		else
		{
			this.getAttackingMagic(DoYouGotMagickHand.LeftHand);
			
			if(this.leftHandMagick != null && this.ColdDown_Left <= 1 && this.canSeeTarget(entity) && ManaValue > 20)
			{
				if(doFocusMagick(DoYouGotMagickHand.LeftHand, entity, getAttackDamage(), getAttackDamage()))
				{
					this.ColdDown_Left = 35 - this.getAttackSpeed();
					this.ManaValue -= 20;
				}
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
					this.ManaValue -= 20;
				}
			}
		}
		else
		{
			this.getDefenceMagic(DoYouGotMagickHand.RightHand);
			
			if(this.rightHandMagick != null)
			{
				if(doProtectMagick(getAttackDamage(), getAttackSpeed()) && ManaValue > 1)
				{
					this.ManaValue -= 1;
				}
			}
		}
	}
	
	protected void standBy()
	{
		if(walkingTime <= 10 && Owner.getDistance(this) > 7 || this.rand.nextInt(300) == 0)
		{
			walkingTime += this.rand.nextInt(100);
		}
		this.Awaydistance = 2F;
		faceEntity(Owner, 30, 20);
		
		if(this.walkingTime > 0)
		{
			this.moveHelper.setMoveTo(Owner.posX, Owner.posY, Owner.posZ, this.getSpeed());
			tryJump();
			//Path path = this.navigator.getPathToEntityLiving(Owner);
			//this.navigator.setPath(path, this.getSpeed());
			walkingTime--;
		}
	}
	
    /*@SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    public float getBrightness()
    {
        return 5.0F;
    }
	*/
	
	@Override
	protected void entityInit() 
	{
		super.entityInit();
		this.dataManager.register(ItemStackSoul, ItemStack.EMPTY);
		this.dataManager.register(canFly, false);
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
	
	@Override
	public void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		if(this.SoulStone != ItemStack.EMPTY && this.SoulStone != null)
		{
			NBTTagCompound tag = new NBTTagCompound();
			this.SoulStone.writeToNBT(tag);
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
	}
}
