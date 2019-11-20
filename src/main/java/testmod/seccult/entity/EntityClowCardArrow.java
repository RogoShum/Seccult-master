package testmod.seccult.entity;

import java.util.List;

import net.minecraft.block.Block;
import net.minecraft.block.BlockLiquid;
import net.minecraft.block.material.Material;
import net.minecraft.block.state.IBlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.MathHelper;
import net.minecraft.world.World;
import testmod.seccult.init.ModDamage;

public class EntityClowCardArrow extends Entity{
	private Entity owner;
	private int damage;
	private float speed;
	private int Split;
	
	public EntityClowCardArrow(World worldIn) {
		super(worldIn);
		this.noClip = false;
		this.setSize(0.3F, 0.3F);
	}

	public EntityClowCardArrow(World worldIn, Entity entity, int Damage, float speed, int split) {
		super(worldIn);
		this.owner = entity;
		this.setPositionAndRotation(owner.posX, owner.posY, owner.posZ, owner.rotationYaw, owner.rotationPitch);
		this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), SoundEvents.ENTITY_ARROW_SHOOT, SoundCategory.PLAYERS, 1F, 2F);
		this.damage = Damage;
		this.speed = speed;
		this.Split = split;
	}
	
	@Override
	protected void entityInit() {

	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		Move();
		collideWithNearbyEntities();
		Split();
		if(this.ticksExisted % 40 == 0)
			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), SoundEvents.BLOCK_PORTAL_AMBIENT, SoundCategory.PLAYERS, 1F, 2F);
		if(this.ticksExisted > 200)
			this.setDead();
	}
	
	private void Split() {
		if(this.Split > 0 && this.ticksExisted > 3)
		{
			int times = this.rand.nextInt(2) + 1;
			for(int i = 0; i < times; times--)
			{
				if(this.owner != null)
				{
					EntityClowCardArrow newArrow = new EntityClowCardArrow(this.world, owner, this.damage, this.speed, this.Split - 1);
					newArrow.setPositionAndRotation(this.posX + 1 - this.rand.nextFloat() * 2, this.posY + 1 - this.rand.nextFloat() * 2, this.posZ + 1 - this.rand.nextFloat() * 2, this.rotationYaw, this.rotationPitch);
					this.world.spawnEntity(newArrow);
				}
				else
				{
					EntityClowCardArrow newArrow = new EntityClowCardArrow(this.world, this, this.damage, this.speed, this.Split - 1);
					newArrow.setPositionAndRotation(this.posX + 1 - this.rand.nextFloat() * 2, this.posY + 1 - this.rand.nextFloat() * 2, this.posZ + 1 - this.rand.nextFloat() * 2, this.rotationYaw, this.rotationPitch);
					this.world.spawnEntity(newArrow);
				}
			}
			this.Split = 0;
		}
	}

	private void Move() {
		if(stick(getEntityBoundingBox()))
		{
			this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), SoundEvents.ENTITY_ENDEREYE_DEATH, SoundCategory.PLAYERS, 1F, 1F + this.rand.nextFloat());
			this.setDead();
			return;
		}
		//System.out.println(this.world.isRemote + " " +this.getPositionVector());
		this.motionX = this.getLookVec().x / 2 * speed;
		this.motionY = this.getLookVec().y / 2 * speed;
		this.motionZ = this.getLookVec().z / 2 * speed;
		
		this.posX = this.lastTickPosX + this.motionX;
		this.posY = this.lastTickPosY + this.motionY;
		this.posZ = this.lastTickPosZ + this.motionZ;
		
		this.lastTickPosX = this.posX;
		this.lastTickPosY = this.posY;
		this.lastTickPosZ = this.posZ;
		
		this.setPosition(posX, posY, posZ);
	}
	
	protected boolean stick(AxisAlignedBB p_70972_1_)
    {
        int i = MathHelper.floor(p_70972_1_.minX);
        int j = MathHelper.floor(p_70972_1_.minY);
        int k = MathHelper.floor(p_70972_1_.minZ);
        int l = MathHelper.floor(p_70972_1_.maxX);
        int i1 = MathHelper.floor(p_70972_1_.maxY);
        int j1 = MathHelper.floor(p_70972_1_.maxZ);
        boolean flag = false;
        
        for (int k1 = i; k1 <= l; ++k1)
        {
            for (int l1 = j; l1 <= i1; ++l1)
            {
                for (int i2 = k; i2 <= j1; ++i2)
                {
                    BlockPos blockpos = new BlockPos(k1, l1, i2);
                    IBlockState iblockstate = this.world.getBlockState(blockpos);
                    Block block = iblockstate.getBlock();

                    if (!block.isAir(iblockstate, this.world, blockpos) && iblockstate.getMaterial() != Material.FIRE && iblockstate.getMaterial() != Material.VINE
                    		 && iblockstate.getMaterial() != Material.SNOW  && iblockstate.getMaterial() != Material.PLANTS)
                    {
                        {
                            flag = true;
                        }
                    }
                }
            }
        }

        return flag;
    }
	
    protected void collideWithNearbyEntities()
    {
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(0.5));
        if (!list.isEmpty())
        {
        	//System.out.println(list);
            for (int l = 0; l < list.size(); ++l)
            {
                Entity entity = list.get(l);
                if(!(entity instanceof EntityClowCardArrow))
                	this.applyEntityCollision(entity);
            }
        }
    }
	
	@Override
	public void applyEntityCollision(Entity entityIn) {
		if(owner != null)
		{
			if(entityIn != owner)
			{
				entityIn.hurtResistantTime = -1;
				entityIn.attackEntityFrom(ModDamage.causePureEntityDamage(owner), this.damage);
				entityIn.hurtResistantTime = -1;
			}
		}
		else
		{
			entityIn.hurtResistantTime = -1;
			entityIn.attackEntityFrom(ModDamage.causePureEntityDamage(this), this.damage);
			entityIn.hurtResistantTime = -1;
		}
		this.world.playSound((EntityPlayer)null, new BlockPos(this.posX, this.posY, this.posZ), SoundEvents.ENTITY_ENDEREYE_DEATH, SoundCategory.PLAYERS, 1F, 1F + this.rand.nextFloat());
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {
		if(compound.hasKey("Split"))
			this.Split = compound.getInteger("Split");
		
		if(compound.hasKey("damage"))
			this.damage = compound.getInteger("damage");
		
		if(compound.hasKey("speed"))
			this.speed = compound.getFloat("speed");
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {
		compound.setFloat("Split", this.Split);
		compound.setFloat("speed", this.speed);
		compound.setFloat("damage", this.damage);
	}
}
