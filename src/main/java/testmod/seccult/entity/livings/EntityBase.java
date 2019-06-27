package testmod.seccult.entity.livings;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLiving;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import testmod.seccult.util.MathHelper.MovingObjectPosition;

public class EntityBase extends EntityLiving{
	protected int tick = this.ticksExisted;
	public boolean isTRboss;
	public EntityBase(World worldIn) {
		super(worldIn);
	}

	protected EntityPlayer findPlayerToAttack(Entity myentity) {
		 MovingObjectPosition movingObjectPosition = new MovingObjectPosition(myentity);
	       Entity entity = null;	    
	       boolean pass = false;
	       double effectiveBoundary = 128.0D;
	       List<Entity> list = myentity.world.getEntitiesWithinAABBExcludingEntity(myentity, myentity.getEntityBoundingBox().grow(effectiveBoundary, effectiveBoundary, effectiveBoundary));
	     
	       if ((list != null) && (list.size() > 0))
		    {
		      for (int j1 = 0; j1 < list.size(); ++j1)
		      {
		        entity = (Entity)list.get(j1);
		        
		        if (entity != null)
		        {
		          movingObjectPosition = new MovingObjectPosition(entity);
		        }

		        if ((myentity.world.isRemote) || (movingObjectPosition == null) || (movingObjectPosition.entityHit instanceof EntityItemFrame) || (movingObjectPosition.entityHit instanceof EntityPainting)) {
		          continue;
		        }

		        pass = false;
		        if (pass)
		          continue;
		        EntityPlayer player = Aura(movingObjectPosition);
		        if(player != null)
		        	return player;
		          }      
	         }
		return null;
	}
	
	protected EntityPlayer Aura(MovingObjectPosition movingObjectPosition) {
       Entity hitEntity = movingObjectPosition.entityHit;
       if(hitEntity instanceof EntityPlayer) {
    	   EntityPlayer player = (EntityPlayer) hitEntity;
    	   return player;
       }
	return null;
	}
	
	protected void Moveto(double x, double y, double z, float speed) {
        this.motionX += (Math.signum(x - this.posX) - this.motionX) * speed;
        this.motionY += (Math.signum(y - this.posY) - this.motionY) * speed;
        this.motionZ += (Math.signum(z - this.posZ) - this.motionZ) * speed;
	}
}
