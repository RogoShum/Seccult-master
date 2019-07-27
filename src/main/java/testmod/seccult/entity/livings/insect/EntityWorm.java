package testmod.seccult.entity.livings.insect;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import testmod.seccult.entity.livings.EntityBase;

public class EntityWorm extends EntityBase implements IEntityInsect{
	private EntityWorm former;
	private EntityWorm latter;
	private EntityWorm parent;
	
	private float speed;
	
	public EntityWorm(World worldIn) {
		super(worldIn);
		this.setSize(0.2F, 0.2F);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(former != null && !former.isEntityAlive())
			former = null;
		
		if(latter != null && !latter.isEntityAlive())
			latter = null;
		
		if(parent != null && !parent.isEntityAlive())
			parent = null;
		
		if(this.former == null)
			this.parent = null;
			
		if(this.former == null)
		{
			List<Entity> entity = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(3));
			
			for(Entity e : entity)
			{
				if(e instanceof EntityWorm)
				{
					EntityWorm worm = (EntityWorm) e;
					if(worm.latter == null)
					{
						this.former = worm;
						worm.latter = this;
						
						if(worm.parent != null && worm.parent == this)
						{
							this.former = null;
							worm.latter = null;
						}
					}
					
					if(worm.latter == this)
					{
						if(worm.parent == null)
							this.parent = worm;
						else
							this.parent = worm.parent;
					}
				}
				
			}
		}
		else
		{

		}
	}
	
	@Override
	public void onLivingUpdate() {
		super.onLivingUpdate();
		
    	//double d0 = this.posX + 1-(rand.nextFloat()*2) - this.posX;
    	//double d2 = this.posZ + 1-(rand.nextFloat()*2) - this.posZ;
		
		//this.motionX += (Math.signum(d0) * 0.5D - this.motionX) * 0.10000000149011612D;
        //this.motionY += (rand.nextFloat()*2.2 - 1)/3;
        //this.motionZ += (Math.signum(d2) * 0.5D - this.motionZ) * 0.10000000149011612D;
        float f = (float)(MathHelper.atan2(this.motionZ, this.motionX) * (180D / Math.PI)) - 90.0F;
        float f1 = MathHelper.wrapDegrees(f - this.rotationYaw);
        this.moveForward = 0.5F;
        this.rotationYaw += f1;
        
        if(this.former != null)
        {
        	//System.out.println(this.parent);
        	speed = 0.01F;
        	Vec3d look = former.getLookVec();    	
        	Vec3d pos = former.getPositionVector();
        	
        	Vec3d newPos = pos.addVector(-look.x, -look.y, -look.z);
        	this.Moveto(newPos.x, newPos.y, newPos.z, speed);
        	
            this.faceEntity(former, 20, 20);
        }
	}
}
