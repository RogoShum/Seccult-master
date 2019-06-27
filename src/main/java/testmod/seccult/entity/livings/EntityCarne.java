package testmod.seccult.entity.livings;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.monster.EntityMob;
import net.minecraft.util.ResourceLocation;
import net.minecraft.world.World;

public class EntityCarne extends EntityMob{
	ResourceLocation BIG = new ResourceLocation("seccult:notoriousBIG");
	private boolean hasStand;
	public EntityCarne(World worldIn) {
		super(worldIn);
		this.setSize(1.2F, 2.0F);
	}
	
	@Override
	protected void onDeathUpdate() {
		Entity entity = null;
        entity = (Entity) EntityList.createEntityByIDFromName(BIG, this.world);
        EntityNotoriousBIG big = (EntityNotoriousBIG) entity;
        big.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
		   	if(!this.world.isRemote && !hasStand) {
		   		big.setOwner(this);
                this.world.spawnEntity(big);
                hasStand = true;
		   	}
		super.onDeathUpdate();
	}
}
