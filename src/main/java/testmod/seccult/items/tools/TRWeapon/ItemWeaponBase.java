package testmod.seccult.items.tools.TRWeapon;

import javax.annotation.Nonnull;

import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.item.EnumAction;
import net.minecraft.item.ItemBow;
import net.minecraft.item.ItemStack;
import net.minecraft.util.ActionResult;
import net.minecraft.util.DamageSource;
import net.minecraft.util.EnumActionResult;
import net.minecraft.util.EnumHand;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import testmod.seccult.entity.EntityLaserBeamBase;
import testmod.seccult.entity.projectile.EntitySolarEruption;
import testmod.seccult.entity.projectile.TRprojectileBase;
import testmod.seccult.items.ItemBase;

public class ItemWeaponBase extends ItemBase{
	private boolean isMelee;
	private int Damage;
	private int Mana;
	private int useTime;
	private int useSpeed;
	private int myColdTime;
	private boolean lock;
	private String project = null;
	private int id;
	
	EntityLaserBeamBase laser = null;
	
	private EnumHand hand = EnumHand.MAIN_HAND;
	
	public ItemWeaponBase(String name) {
		super(name);
		this.maxStackSize = 1;
	}
	
	public void ItemAttribute(boolean isMelee, int damage, int mana, int useTime, int useSpeed, String projectile, int id) {
		this.maxStackSize = 1;
		this.isMelee = isMelee;
		this.Damage = damage;
		this.Mana = mana;
		this.useTime = useTime;
		this.useSpeed = useSpeed;
		this.project = projectile;
		this.myColdTime = 0;
		this.id = id;
	}
	
	@Override
	public boolean onEntitySwing(EntityLivingBase entityLiving, ItemStack stack) {
		if(this.isMelee) {
		if(entityLiving instanceof EntityPlayer && stack.getItem() == this) {
			EntityPlayer player = (EntityPlayer) entityLiving;
		
			this.lock = false;
		
		if(project != null && !player.world.isRemote && this.isOK()) {
			SpawnProjectile(player);
		}
		}
		}
		return super.onEntitySwing(entityLiving, stack);
	}
	
	@Override
	public void onPlayerStoppedUsing(ItemStack stack, World worldIn, EntityLivingBase entityLiving, int timeLeft) {
		// TODO Auto-generated method stub
		super.onPlayerStoppedUsing(stack, worldIn, entityLiving, timeLeft);
		if(laser != null)
			laser.setDead();
	}
	
	@Override
	public void onUsingTick(ItemStack stack, EntityLivingBase player, int count) 
	{
		super.onUsingTick(stack, player, count);
		EntityPlayer pl = (EntityPlayer) player;
	}
	
	@Nonnull
	@Override
	public EnumAction getItemUseAction(ItemStack stack) 
	{
		return EnumAction.BOW;
	}
	
	public void coldTime() {
		if(this.myColdTime > this.useTime / 3)
			this.myColdTime = 0;
	}
	
	protected boolean isOK() {
    	if(this.myColdTime >= this.useTime && this.lock == false) {
    		this.myColdTime = 0;
    		return true;
    	}
    	else
    		this.lock = true;
    	return false;
	}
	
    public float getDestroySpeed(ItemStack stack, IBlockState state)
    {
        return 0;
    }
	
    public boolean canHarvestBlock(IBlockState blockIn)
    {
        return false;
    }
    
    @Override 
    public void onUpdate(ItemStack stack, World worldIn, Entity entityIn, int itemSlot, boolean isSelected) {
    	super.onUpdate(stack, worldIn, entityIn, itemSlot, isSelected);
    	this.myColdTime++;
    } 
    
	@Override
	public boolean onLeftClickEntity(ItemStack stack, EntityPlayer player, Entity entity) {
		if(this.isMelee)
		{
			entity.attackEntityFrom(DamageSource.causePlayerDamage(player), this.Damage);
			if(project != null && !player.world.isRemote && this.isOK()) {
				SpawnProjectile(player);
			}
			return true;
		}
		return false;
	}
	
	@Nonnull
	@Override
	public ActionResult<ItemStack> onItemRightClick(World world, EntityPlayer player, @Nonnull EnumHand hand) {
		ItemStack stack = player.getHeldItem(hand);
			player.setActiveHand(hand);
				this.lock = false;
				
			if(project != null && !this.isMelee && !player.world.isRemote && this.isOK() && project.equals("seccult:laserbeam")) {			
				SpawnLaser(player);
				return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(hand));
			}
				
			if(project != null && !this.isMelee && !player.world.isRemote && this.isOK() && !project.equals("seccult:laserbeam")) {
				SpawnProjectile(player);
				return ActionResult.newResult(EnumActionResult.SUCCESS, stack);
			}
			return ActionResult.newResult(EnumActionResult.PASS, player.getHeldItem(hand));
	}
	
    public int getMaxItemUseDuration(ItemStack stack)
    {
        return 72000;
    }
	
	public void SpawnLaser(EntityPlayer player) {
		ResourceLocation res = new ResourceLocation(project);
		 Entity entity = EntityList.createEntityByIDFromName(res, player.world);
		 EntityLaserBeamBase newprojectile = (EntityLaserBeamBase) entity;
         newprojectile.setOwner(player);
         newprojectile.setDamage(this.Damage);
         Vec3d handVec = player.getLookVec().rotateYaw(hand == EnumHand.MAIN_HAND ? -0.4F : 0.4F);
		 Vec3d lok = player.getLookVec();
		 Vec3d position = player.getPositionVector().addVector(handVec.x, handVec.y - 0.8 + player.getEyeHeight(), handVec.z);
		 Vec3d QAQ = position.addVector(lok.x, lok.y, lok.z);
		 newprojectile.setWidth(1);
         entity.setLocationAndAngles(QAQ.x, QAQ.y , QAQ.z, player.rotationYaw, player.rotationPitch);
        if(!player.world.isRemote) {
    	  player.world.spawnEntity(newprojectile);
    	  this.laser = newprojectile;
        }
	}
	
	public void SpawnProjectile(EntityPlayer player) {
		ResourceLocation res = new ResourceLocation(project);
		 Entity entity = EntityList.createEntityByIDFromName(res, player.world);
		 TRprojectileBase newprojectile = (TRprojectileBase) entity;
			Vec3d handVec = player.getLookVec().rotateYaw(hand == EnumHand.MAIN_HAND ? -0.4F : 0.4F);
			 Vec3d lok = player.getLookVec();
			 Vec3d position = player.getPositionVector().addVector(handVec.x, handVec.y - 0.8F + player.getEyeHeight(), handVec.z);
			 Vec3d QAQ = position.addVector(lok.x, lok.y, lok.z);
             entity.setLocationAndAngles(QAQ.x, QAQ.y, QAQ.z, player.rotationYaw, player.rotationPitch);
             newprojectile.setAttribute(this.Damage, this.useSpeed, player, this.id);
             if(newprojectile instanceof EntitySolarEruption) {
            	 EntitySolarEruption solar = (EntitySolarEruption) newprojectile;
            	 solar.setParent();
                 if(!player.world.isRemote)
               	  player.world.spawnEntity(solar);
                 return;
             }
      if(!player.world.isRemote)
    	  player.world.spawnEntity(newprojectile);
	}
}
