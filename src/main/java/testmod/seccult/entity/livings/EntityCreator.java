package testmod.seccult.entity.livings;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.util.ResourceLocation;

public class EntityCreator {
	
	static ResourceLocation BIG = new ResourceLocation("seccult:notoriousBIG");
	
	public static void createEntity(Entity creater, int a) {
		switch(a) {
		case 0:
			Entity entity = null;
        	entity = EntityList.createEntityByIDFromName(BIG , creater.world);
            entity.setLocationAndAngles(creater.posX, creater.posY, creater.posZ, creater.rotationYaw, creater.rotationPitch);
            EntityNotoriousBIG big = (EntityNotoriousBIG) entity;
            big.setTail();
            EntityNotoriousBIG c = (EntityNotoriousBIG) creater;
            big.setBody(c);
            c.setMyTail(big);
	 		if(!creater.world.isRemote)
	 			creater.world.spawnEntity(big);
		}
	}
}
