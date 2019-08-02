package testmod.seccult.entity;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;

public class EntitySCP173 extends EntitySCPBase{

	private EntityLivingBase cloest;
	private boolean stop;
	
	public EntitySCP173(World worldIn) {
		super(worldIn);
		this.setSize(1.2F, 2.2F);
		setSufferTimes(10);
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();

        Entity entity = null;
        List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, this.getEntityBoundingBox().grow(32D));
        
        if ((list != null) && (list.size() > 0))
	    {
	      for (int j1 = 0; j1 < list.size(); ++j1)
	      {
	        entity = (Entity)list.get(j1);
	        
	        if (entity != null)
	        {
	        	Ref(entity);
	        }
	        
	       }      
         }
        
	    Entity cloestEntity = null;
	    cloestEntity = this.world.findNearestEntityWithinAABB(Entity.class, this.getEntityBoundingBox().grow(40D, 3.0D, 40D), this);
	    if(cloestEntity instanceof EntityLivingBase)
	    	cloest = (EntityLivingBase) cloestEntity;
		if(cloest != null) {
			Moving();
			if(cloest.isEntityAlive() && this.getDistance(cloest) < 0.5F) {
				cloest.isDead = true;
				cloest.setDead();
				cloest.setHealth(cloest.getHealth() - 1);
			}
			}
			else 
			{
				cloest = null;
			}
	}

	private void Moving() {
		if(cloest != null && !stop)
		this.moveHelper.setMoveTo(cloest.posX, cloest.posY, cloest.posZ, 2);
		stop = false;
	}
	
	private void Ref(Entity entityHit) {
		Entity e = entityHit;
		if(!(e instanceof EntityLivingBase))
			return;
		EntityLivingBase living = (EntityLivingBase) e;
		/*if(this.CanIBeSeen(this, living, 90F))
			stop = true;*/
	}
	
    public boolean CanIBeSeen(Entity watched, EntityLivingBase watcher, float angleRange) {
        assert angleRange <= 180.0f;
        Vec3d vecOne = new Vec3d(watched.posX - watcher.posX, watched.posY + watched.getEyeHeight() - watcher.posY - watcher.getEyeHeight(), watched.posZ - watcher.posZ);
        vecOne = vecOne.normalize();
        Vec3d vecTwo = watcher.getLook(1.0f).normalize();
        double dotproduct = vecTwo.dotProduct(vecOne);
        float threshold = (180.0f - angleRange) / 180.0f;
        return dotproduct > threshold && this.canEntityBeSeen(watcher);
    }
    
    public boolean canBeSeen(Entity entityIn)
    {
        return this.world.rayTraceBlocks(new Vec3d(entityIn.posX, entityIn.posY + (double)entityIn.getEyeHeight(), entityIn.posZ), new Vec3d(this.posX, this.posY + (double)this.getEyeHeight(), this.posZ), false, true, false) == null;
    }
}
