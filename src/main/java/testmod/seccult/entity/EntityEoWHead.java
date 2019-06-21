package testmod.seccult.entity;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class EntityEoWHead extends EntityEoW{
		private boolean notFirst;
		
		EntityEoW A = this;
		EntityEoW B = null;
		public EntityEoWHead(World worldIn) {
			super(worldIn);
			this.FirstSpawn = 49;
			this.setSize(1.6F, 1.6F);
		}
		
		@Override
		public void onUpdate() {

			super.onUpdate();
			this.isHead = true;
			if(!notFirst && this.isHead) {
				onBody();
			}
		}
		
		@Override
		public void readEntityFromNBT(NBTTagCompound nbt) {
			super.readEntityFromNBT(nbt);
			if(nbt.hasKey("notFirstSpawn")) {
				notFirst = true;
			}
		}
		
		@Override
		public void writeEntityToNBT(NBTTagCompound nbt) {
			if(!nbt.hasKey("Part")) {
				nbt.setString("Part", "isHead");
				if(!nbt.hasKey("notFirstSpawn") && FirstSpawn == 0)
				{
					nbt.setBoolean("notFirstSpawn", false);
				}
				this.writeEntityToNBT(nbt);
			}
		}
		
	    private void onBody() {
	    	while(FirstSpawn > 0) {
	    	if(this.FirstSpawn > 1) 
	    	{
	    		spawnCreature();
	    	}
	 	   	else if(this.FirstSpawn == 1)
	 	   	{
	 	   		spawnCreature();
	 	   		}
	    	}
	    }
	    
	    private EntityEoW spawnCreature() {
	    	Entity entity = null;
	            entity = EntityList.createEntityByIDFromName(EOWres, this.world);
	            EntityEoW EOW = (EntityEoW) entity;
	                entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
	                EOW.rotationYawHead = EOW.rotationYaw;
	                EOW.renderYawOffset = EOW.rotationYaw;
	                EOW.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(EOW)), (IEntityLivingData)null);
	                EOW.setupperUUIDM(A.getUniqueID().getMostSignificantBits());
	                EOW.setupperUUIDL(A.getUniqueID().getLeastSignificantBits());
	                EOW.setupper(A);
	 	   	    	A.setfollowerUUIDL(EOW.getUniqueID().getLeastSignificantBits());
	 	   	    	A.setfollowerUUIDM(EOW.getUniqueID().getMostSignificantBits());
	 	   	    	A.setfollower(EOW);
	 	 		   	if(!this.world.isRemote)
	                this.world.spawnEntity(EOW);
	                //EOW.playLivingSound();
	 	 		   	FirstSpawn--;
	 	 		   	A = EOW;
	                return EOW;
	    }
	}