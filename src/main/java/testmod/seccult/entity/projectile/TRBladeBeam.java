package testmod.seccult.entity.projectile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.EnumSkyBlock;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.FX.PropraFX;
import testmod.seccult.client.FX.RainbowFX;
import testmod.seccult.items.TRprojectile.TRprojectileID;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;

public class TRBladeBeam extends TRprojectileBase{
	private int SEn;
	private boolean isAvatar;
	private boolean hasSplitAvatar;
	
	public float randlight = this.rand.nextFloat() * 0.2F;
	ResourceLocation trblade = new ResourceLocation(Seccult.MODID + ":trblade");
	
	
	public TRBladeBeam(World worldIn) {
		super(worldIn);
	}
	
	@Override
	public void onUpdate() {
		if(this.getID() == TRprojectileID.VampireKnives && !this.isAvatar && !this.hasSplitAvatar) {
			int a = rand.nextInt(2);
			int b = rand.nextInt(4);
			int c = rand.nextInt(8);
			int d = rand.nextInt(16);
			if(b == 3)
				b = 1;
			if(c == 7)
				c = 1;
			if(d == 15) 
				d = 1;
			createAvatar(a, b, c, d);
			this.hasSplitAvatar = true;
		}
		super.onUpdate();
		particles();
		//addLight();
		if (this.isDead) {
			BlockPos pos = new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ);
			this.world.checkLightFor(EnumSkyBlock.BLOCK, pos);
			}
	}
	
	private void createAvatar(int booleanA, int booleanB, int booleanC, int booleanD) {
		for(int times = 0; times < 7; times++) {
			
			Entity entity = EntityList.createEntityByIDFromName(trblade, this.world);
			TRBladeBeam newprojectile = (TRBladeBeam) entity;
			
			switch(times) {
				case 0:
					entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw + 5, this.rotationPitch + 5);
					break;
				case 1:
					entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw - 5, this.rotationPitch + 5);
					break;
				case 2:
					entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw + 5, this.rotationPitch - 5);
					break;
				case 3:
					entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw - 5, this.rotationPitch - 5);
					break;
				case 4:
					entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw + 7, this.rotationPitch + 7);
					break;
				case 5:
					entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw + 7, this.rotationPitch - 7);
					break;
				case 6:
					entity.setLocationAndAngles(this.posX, this.posY, this.posZ, this.rotationYaw - 7, this.rotationPitch - 7);
					break;
			}
		 
        newprojectile.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), this.getID());
        newprojectile.isAvatar = true;
        
        if(!this.world.isRemote && times < 3) {
        this.world.spawnEntity(newprojectile);
        }
        else if(!this.world.isRemote && times >= 3){
        	switch(times) {
			case 3:
				this.world.spawnEntity(newprojectile);
				
				if(booleanA == 0)
					newprojectile.setDead();
				break;
			case 4:
				this.world.spawnEntity(newprojectile);
				
				if(booleanA != 1 && booleanB != 1)
					newprojectile.setDead();
				break;
			case 5:
				this.world.spawnEntity(newprojectile);
				
				if(booleanB != 1 && booleanC != 1)
					newprojectile.setDead();
				break;
			case 6:
				this.world.spawnEntity(newprojectile);

				if(booleanC != 1 && booleanD != 1)
					newprojectile.setDead();
				break;
		}
        }
		}
	}
	
	private void addLight() {
		BlockPos pos = new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ);
		BlockPos pos1 = new BlockPos((int) this.posX - 1, (int) this.posY, (int) this.posZ);
		BlockPos pos2 = new BlockPos((int) this.posX + 1, (int) this.posY, (int) this.posZ);
		BlockPos pos3 = new BlockPos((int) this.posX, (int) this.posY - 1, (int) this.posZ);
		BlockPos pos4 = new BlockPos((int) this.posX, (int) this.posY + 1, (int) this.posZ);
		BlockPos pos5 = new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ - 1);
		BlockPos pos6 = new BlockPos((int) this.posX, (int) this.posY, (int) this.posZ + 1);
		switch(this.getRenderSkin()) {
			case 0:
				this.world.setLightFor(EnumSkyBlock.BLOCK, pos, 7);
				break;
			case 1:
				this.world.setLightFor(EnumSkyBlock.BLOCK, pos, 15);
				break;
			default:
				this.world.setLightFor(EnumSkyBlock.BLOCK, pos, 5);
				break;
		}
		this.world.markBlockRangeForRenderUpdate((int) this.posX,
				(int) this.posY, (int) this.posX, 12, 12, 12);
		this.world.notifyLightSet(pos);
		this.world.checkLightFor(EnumSkyBlock.BLOCK, pos1);
		this.world.checkLightFor(EnumSkyBlock.BLOCK, pos2);
		this.world.checkLightFor(EnumSkyBlock.BLOCK, pos3);
		this.world.checkLightFor(EnumSkyBlock.BLOCK, pos4);
		this.world.checkLightFor(EnumSkyBlock.BLOCK, pos5);
		this.world.checkLightFor(EnumSkyBlock.BLOCK, pos6);
	}
	
    @SideOnly(Side.CLIENT)
    public int getBrightnessForRender()
    {
        return 15728880;
    }

    /**
     * Gets how bright this entity is.
     */
    public float getBrightness()
    {
        return 5.0F;
    }
    
    public void onMoving(Vec3d getPosition, double d) {
    	double x = getPosition.x;
    	double y = getPosition.y;
    	double z = getPosition.z;
        this.motionX += (Math.signum(x - this.posX) - this.motionX) * d;
        this.motionY += (Math.signum(y - this.posY) - this.motionY) * d;
        this.motionZ += (Math.signum(z - this.posZ) - this.motionZ) * d;
        
  		 this.posX += this.motionX;
         this.posY += this.motionY;
         this.posZ += this.motionZ;
         this.setPosition(this.posX, this.posY, this.posZ);
    }
    
	public Vec3d onLook(Vec3d look, Vec3d AP, Entity e) {
		AP.addVector(0, e.height / 2, 0);
		double dis = (this.SEn * 0.7);
		return AP.addVector(look.x * dis, look.y * dis, look.z * dis);
	}
    
	private void particles() {
		double X = getVec().x;
		double Y = getVec().y;
		double Z = getVec().z;
		double[] vec = {0, 0, 0};
		double[] pos = {X, Y, Z};
		if(this.getRenderSkin() == 0)
		{
			switch(this.ticksExisted % 3) {
			case 0:			
				float[] color = {1, 0.1F + randlight, 0.5F + randlight};
				NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, this.rand.nextFloat() * 0.5F + 0.5F, 2));
			break;
			case 1:
				float[] color1 = {0.2F + randlight, 0.4F + randlight, 1.0F};
				NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color1, this.rand.nextFloat() * 0.5F + 0.5F, 2));
			break;
			case 2:
				float[] color2 = {1, 0.9F + randlight, 0.1F + randlight};
				NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color2, this.rand.nextFloat() * 0.5F + 0.5F, 2));
			}
			
			if(this.isStick) {
	        	
				double[] vec1 = {this.getLookVec().x / 3, this.getLookVec().y / 3, this.getLookVec().z / 3};
				for(int ae = 0; ae < 6; ae++) {
					float[] color = {1, 0.1F + randlight, 0.5F + randlight};
					NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec1, color, this.rand.nextFloat() * 0.5F + 0.5F, 2));
				}
				
				for(int ae = 0; ae < 6; ae++) {
					float[] color1 = {0.2F + randlight, 0.4F + randlight, 1.0F};
					NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec1, color1, this.rand.nextFloat() * 0.5F + 0.5F, 2));
				}
				
				for(int ae = 0; ae < 6; ae++) {
					float[] color2 = {1, 0.9F + randlight, 0.1F + randlight};
					NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec1, color2, this.rand.nextFloat() * 0.5F + 0.5F, 2));
				}
				
				this.setDead();
			}
		}
		else
		if(this.getRenderSkin() == 1)
		{
			float[] color = {0.3F + randlight, 1F, 0.17F + randlight};
			for(int ae = 0; ae < 3; ae++) {
				NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, this.rand.nextFloat() * 0.5F + 0.5F, 2));
			}
			
			if(this.isStick) {
				for(int ae = 0; ae < 6; ae++) {
					double[] vec1 = {this.getLookVec().x / 3, this.getLookVec().y / 3, this.getLookVec().z / 3};
					NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec1, color, this.rand.nextFloat() * 0.5F + 0.5F, 2));
				}
				this.setDead();
			}
		}
		else
		if(this.getRenderSkin() == 12) {
			float[] color = {1F, 0.85F + randlight, 0.17F  + randlight};
			if(this.ticksExisted % 7 == 0) {
				NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, this.rand.nextFloat() * 0.5F + 0.5F, 2));
			}
				
				if(this.isStick) {
					for(int ae = 0; ae < 10; ae++) {
						double[] vec1 = {this.getLookVec().x / 3, this.getLookVec().y / 3, this.getLookVec().z / 3};
						NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec1, color, this.rand.nextFloat() * 0.5F + 0.5F, 2));
					}
					this.setDead();
				}
		}
		else
		if(this.getRenderSkin() == 10) {
			double[] vec3 = {0, 0, 0};
			double[] pos3 = {this.prevPosX, this.prevPosY + (this.height / 2), this.prevPosZ};
			float[] color = {1F, 0.85F + randlight, 0.17F  + randlight};
			NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos3, vec3, color, this.rand.nextFloat() * 0.5F + 0.5F, 8));
			if(this.isStick && this.collisionTimes >= 5)  {
					this.setDead();
			}
		}
	}
	
	private Vec3d getVec() {
		float ran = this.rand.nextFloat() - 0.5F;
		if(ran > 0.3)
			ran = 0.3F;
		else if (ran < -0.3)
			ran = -0.3F;

		 double X = this.posX + ran;
		 double Y = this.posY + ran;
		 double Z = this.posZ + ran;
		 Vec3d vec = new Vec3d(X, Y, Z);
		 return vec;
	}
	
	@Override
	protected void readEntityFromNBT(NBTTagCompound nbt) {
		super.readEntityFromNBT(nbt);
		if(nbt.hasKey("isAvatar")) {
			isAvatar = nbt.getBoolean("isAvatar");
			hasSplitAvatar = nbt.getBoolean("hasSplitAvatar");
		}
	}

	@Override
	protected void writeEntityToNBT(NBTTagCompound nbt) {
		super.writeEntityToNBT(nbt);
		if(!nbt.hasKey("isAvatar")) {
			nbt.setBoolean("isAvatar", isAvatar);
			nbt.setBoolean("hasSplitAvatar", hasSplitAvatar);
		}
	}
}
