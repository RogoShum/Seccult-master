package testmod.seccult.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.block.BlockLiquid;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.world.World;
import testmod.seccult.entity.livings.water.EntityRockShellLeviathan;
import testmod.seccult.init.ModDamage;

public class EntityMagickBubble extends Entity{
	protected EntityLivingBase owner;
	
	public EntityMagickBubble(World worldIn) {
		super(worldIn);
        this.setSize(0.4F, 0.4F);
        this.noClip = false;
	}
	
	public EntityMagickBubble(World worldIn, EntityLivingBase owner) {
		super(worldIn);
        this.setSize(0.4F, 0.4F);
		this.owner = owner;
	}
	
	public void setOwner(EntityLivingBase owner)
	{
		this.owner = owner;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		collideWithNearbyEntities();
		
		if(!(this.world.getBlockState(getPosition()).getBlock() instanceof BlockLiquid) || this.ticksExisted > 300)
			this.setDead();
			
		double speed = this.rand.nextDouble();
		this.motionX = this.getLookVec().x + (0.25 - this.rand.nextDouble() * 0.5) / 3 * speed;
		this.motionY = this.getLookVec().y + (0.25 - this.rand.nextDouble() * 0.5) / 3 * speed;
		this.motionZ = this.getLookVec().z + (0.25 - this.rand.nextDouble() * 0.5) / 3 * speed;
		
		this.prevPosX = this.posX;
		this.prevPosY = this.posY;
		this.prevPosZ = this.posZ;
		
		this.posX += this.motionX;
		this.posY += this.motionY;
		this.posZ += this.motionZ;
		
		this.setPositionAndRotation(posX, posY, posZ, this.rotationYaw, this.rotationPitch);
		
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(0.5));
        List<Entity> newList = new ArrayList<Entity>();
        for (int i = 0; i < list.size(); ++i)
        {
        	Entity entity1 = list.get(i);
            if(!(entity1 instanceof EntityRockShellLeviathan) && !(entity1 instanceof EntityMagickBubble))
            {
            	newList.add(entity1);
        	}
        }

        for (int z = 0; z < newList.size(); ++z)
        {
        	Entity entity2 = newList.get(z);
        	entity2.hurtResistantTime = -1;
            entity2.attackEntityFrom(ModDamage.causePureEntityDamage(this), 3F);
            entity2.hurtResistantTime = -1;
        	this.setDead();
        }
		
		 onParticle();
	}
	
    protected void collideWithNearbyEntities()
    {
		

    }
    
	private void onParticle() {
		this.world.spawnParticle(EnumParticleTypes.WATER_BUBBLE, this.posX, this.posY + 0.2, this.posZ, 0, 0, 0);
	}

	@Override
	protected void entityInit() {

	}

	@Override
	protected void readEntityFromNBT(NBTTagCompound compound) {

	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound compound) {

	}
}
