package testmod.seccult.util.MathHelper;

import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityItemFrame;
import net.minecraft.entity.item.EntityPainting;

public class AuraEntities {

	public static MovingObjectPosition inAura(List list, MovingObjectPosition movingObjectPosition, Entity entity, Entity me) {
		boolean pass = false;
		if ((list != null) && (list.size() > 0))
	    {
	      for (int j1 = 0; j1 < list.size(); ++j1)
	      {
	        entity = (Entity)list.get(j1);
	        
	        if (entity != null)
	        {
	          movingObjectPosition = new MovingObjectPosition(entity);
	        }

	        if ((me.world.isRemote) || (movingObjectPosition == null) || (movingObjectPosition.entityHit instanceof EntityItemFrame) || (movingObjectPosition.entityHit instanceof EntityPainting)) {
	          continue;
	        }

	        pass = false;

	        if (pass)
	          continue;
	        return movingObjectPosition;       	
	            }      
          }
		return null;
		
	}
}
