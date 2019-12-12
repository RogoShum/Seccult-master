package testmod.seccult.entity.projectile;

import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.EnumHand;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.NetworkRegistry.TargetPoint;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.init.ModItems;
import testmod.seccult.items.TRprojectile.TRprojectileID;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.TransPoint;

public class EntitySolarEruption extends TRprojectileBase{
	
	private static final double MAX_CHAIN = 16;
	public float randlight = this.rand.nextFloat() * 0.2F;
	
	private EnumHand hand = EnumHand.MAIN_HAND;
	private boolean isReturning = false;
	private int thistick;

	public EntitySolarEruption part1 = null;
	public EntitySolarEruption part2 =  null;
	public EntitySolarEruption part3 =  null;
	public EntitySolarEruption part4 =  null;
	public EntitySolarEruption part5 =  null;
	public EntitySolarEruption part6 =  null;
	public EntitySolarEruption part7 =  null;
	public EntitySolarEruption part8 =  null;
	public EntitySolarEruption part9 =  null;
	public EntitySolarEruption part10 =  null;
	public EntitySolarEruption part11 =  null;
	public EntitySolarEruption part12 =  null;
	public EntitySolarEruption part13 =  null;
	public EntitySolarEruption part14 =  null;
	public EntitySolarEruption part15 = null;
	public EntitySolarEruption part16 =  null;
	public EntitySolarEruption part17 =  null;
	public EntitySolarEruption part18 =  null;
	public EntitySolarEruption part19 =  null;
	public EntitySolarEruption part20 =  null;
	
	public boolean parent;
	private boolean hasPart;

	public EntitySolarEruption(World world) {
		super(world);
	}

	public void setParent() {
		this.parent = true;
		this.setSize(0.5F, 0.5F);
		this.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), 3000);
		this.makeDamage = false;
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

    protected void partecle()
    {
    	double X = getVec().x;
		double Y = getVec().y;
		double Z = getVec().z;

    	double[] vec = {0, 0, 0};
		double[] pos = {X, Y, Z};
		float[] color = {0.8F + randlight, 0.4F + randlight, randlight};

        NetworkHandler.sendToAllAround(new NetworkEffectData(pos, vec, color, this.rand.nextFloat() * 0.5F + 0.5F, 2),
        		new TransPoint(dimension, pos[0], pos[1], pos[2], 32), this.world);
    }
    
	@Override
	public void onUpdate() {
		super.onUpdate();
		
		if(this.ticksExisted % 2 == 0) {
			partecle();
		}
		
		if(this.world.isRemote)
			return;
			if(this.parent && !hasPart) {
				EntitySolarEruption part1 = new EntitySolarEruption(this.world);
				EntitySolarEruption part2 = new EntitySolarEruption(this.world);
				EntitySolarEruption part3 = new EntitySolarEruption(this.world);
				EntitySolarEruption part4 = new EntitySolarEruption(this.world);
				EntitySolarEruption part5 = new EntitySolarEruption(this.world);
				EntitySolarEruption part6 = new EntitySolarEruption(this.world);
				EntitySolarEruption part7 = new EntitySolarEruption(this.world);
				EntitySolarEruption part8 = new EntitySolarEruption(this.world);
				EntitySolarEruption part9 = new EntitySolarEruption(this.world);
				EntitySolarEruption part10 = new EntitySolarEruption(this.world);
				EntitySolarEruption part11 = new EntitySolarEruption(this.world);
				EntitySolarEruption part12 = new EntitySolarEruption(this.world);
				EntitySolarEruption part13 = new EntitySolarEruption(this.world);
				EntitySolarEruption part14 = new EntitySolarEruption(this.world);
				EntitySolarEruption part15 = new EntitySolarEruption(this.world);
				EntitySolarEruption part16 = new EntitySolarEruption(this.world);
				EntitySolarEruption part17 = new EntitySolarEruption(this.world);
				EntitySolarEruption part18 = new EntitySolarEruption(this.world);
				EntitySolarEruption part19 = new EntitySolarEruption(this.world);
				EntitySolarEruption part20 = new EntitySolarEruption(this.world);
				
				part1.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				part2.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				part3.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				part4.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				part5.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				
				part6.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				part7.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				part8.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				part9.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				part10.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				
				part11.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				part12.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				part13.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				part14.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				part15.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				
				part16.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				part17.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				part18.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				part19.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				part20.setPositionAndRotation(this.posX, this.posY, this.posZ, this.rotationYaw, this.rotationPitch);
				
				part1.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption_Handle);
				part2.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				part3.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				part4.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				part5.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				
				part6.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				part7.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				part8.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				part9.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				part10.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				
				part11.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				part12.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				part13.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				part14.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				part15.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				
				part16.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				part17.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				part18.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				part19.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption);
				part20.setAttribute(this.getDamage(), this.getSpeed(), this.getPlayer(), TRprojectileID.SolarEruption_Tip);
				
				if(!this.world.isRemote) {
				this.world.spawnEntity(part1);		
				this.world.spawnEntity(part2);
				this.world.spawnEntity(part3);
				this.world.spawnEntity(part4);
				this.world.spawnEntity(part5);
				
				this.world.spawnEntity(part6);
				this.world.spawnEntity(part7);
				this.world.spawnEntity(part8);
				this.world.spawnEntity(part9);
				this.world.spawnEntity(part10);
				
				this.world.spawnEntity(part11);
				this.world.spawnEntity(part12);
				this.world.spawnEntity(part13);
				this.world.spawnEntity(part14);
				this.world.spawnEntity(part15);
				
				this.world.spawnEntity(part16);
				this.world.spawnEntity(part17);
				this.world.spawnEntity(part18);
				this.world.spawnEntity(part19);
				this.world.spawnEntity(part20);
				}
				this.part1 = part1;
				this.part2 = part2;
				this.part3 = part3;
				this.part4 = part4;
				this.part5 = part5;
				
				this.part6 = part6;
				this.part7 = part7;
				this.part8 = part8;
				this.part9 = part9;
				this.part10 = part10;
				
				this.part11 = part11;
				this.part12 = part12;
				this.part13 = part13;
				this.part14 = part14;
				this.part15 = part15;
				
				this.part16 = part16;
				this.part17 = part17;
				this.part18 = part18;
				this.part19 = part19;
				this.part20 = part20;
				
				this.hasPart = true;
			}
			if(!this.parent)
				return;
			if (getPlayer() == null) {
				setDead();
				if(this.part1 != null) {
				this.part1.setDead();
				this.part2.setDead();
				this.part3.setDead();
				this.part4.setDead();
				this.part5.setDead();
				
				this.part6.setDead();
				this.part7.setDead();
				this.part8.setDead();
				this.part9.setDead();
				this.part10.setDead();
				
				this.part11.setDead();
				this.part12.setDead();
				this.part13.setDead();
				this.part14.setDead();
				this.part15.setDead();
				
				this.part16.setDead();
				this.part17.setDead();
				this.part18.setDead();
				this.part19.setDead();
				this.part20.setDead();
				}
				
			} else {
				double distToPlayer = this.getDistance(this.getPlayer().posX, this.getPlayer().posY + this.getPlayer().getEyeHeight(), this.getPlayer().posZ);
				// return if far enough away
				if (!this.isReturning && distToPlayer > MAX_CHAIN) {
					this.isReturning = true;
					this.thistick = this.ticksExisted;
				}

				if (this.isReturning) {
					// despawn if close enough
					if (distToPlayer < 2.0F) {
						this.setDead();
					}
				}
			}
		
	}

	
	
	@Override
	public void setDead() {
		super.setDead();
		if(this.part1 != null) {
			this.part1.setDead();
			this.part2.setDead();
			this.part3.setDead();
			this.part4.setDead();
			this.part5.setDead();
			this.part6.setDead();
			this.part7.setDead();
			this.part8.setDead();
			this.part9.setDead();
			this.part10.setDead();
			this.part11.setDead();
			this.part12.setDead();
			this.part13.setDead();
			this.part14.setDead();
			this.part15.setDead();
			this.part16.setDead();
			this.part17.setDead();
			this.part18.setDead();
			this.part19.setDead();
			this.part20.setDead();
		}
		EntityLivingBase thrower = this.getPlayer();
		if (thrower != null && thrower.getActiveItemStack().getItem() == ModItems.Solar_Eruption) {
			thrower.resetActiveHand();
		}
	}

	@Override
	protected void updateMoveingAttribute() {
		if(!parent)
			return;
		Vec3d handVec = this.getPlayer().getLookVec().rotateYaw(hand == EnumHand.MAIN_HAND ? -0.4F : 0.4F);
		 Vec3d lok = this.getPlayer().getLookVec();
		 Vec3d position = this.getPlayer().getPositionVector().addVector(handVec.x, handVec.y - 0.8F + this.getPlayer().getEyeHeight(), handVec.z);
		if(!isReturning) {
			double multi = 0.0D;
			Vec3d QAQ1 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ2 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ3 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ4 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ5 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ6 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ7 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ8 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ9 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ10 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ11 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ12 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ13 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ14 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ15 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ16 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ17 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ18 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ19 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			multi += 0.05D;
			Vec3d QAQ20 = position.addVector(lok.x * this.ticksExisted * 2 * multi, lok.y * this.ticksExisted * 2 * multi, lok.z * this.ticksExisted * 2 * multi);
			if(part1 != null) {
				this.setPositionAndRotation(part20.posX, part20.posY, part20.posZ, this.getPlayer().rotationYaw, this.getPlayer().rotationPitch);
				this.part1.setPositionAndRotation(QAQ1.x, QAQ1.y, QAQ1.z, this.rotationYaw, this.rotationPitch);
				this.part2.setPositionAndRotation(QAQ2.x, QAQ2.y, QAQ2.z, this.rotationYaw, this.rotationPitch);
				this.part3.setPositionAndRotation(QAQ3.x, QAQ3.y, QAQ3.z, this.rotationYaw, this.rotationPitch);
				this.part4.setPositionAndRotation(QAQ4.x, QAQ4.y, QAQ4.z, this.rotationYaw, this.rotationPitch);
				this.part5.setPositionAndRotation(QAQ5.x, QAQ5.y, QAQ5.z, this.rotationYaw, this.rotationPitch);
				
				this.part6.setPositionAndRotation(QAQ6.x, QAQ6.y, QAQ6.z, this.rotationYaw, this.rotationPitch);
				this.part7.setPositionAndRotation(QAQ7.x, QAQ7.y, QAQ7.z, this.rotationYaw, this.rotationPitch);
				this.part8.setPositionAndRotation(QAQ8.x, QAQ8.y, QAQ8.z, this.rotationYaw, this.rotationPitch);
				this.part9.setPositionAndRotation(QAQ9.x, QAQ9.y, QAQ9.z, this.rotationYaw, this.rotationPitch);
				this.part10.setPositionAndRotation(QAQ10.x, QAQ10.y, QAQ10.z, this.rotationYaw, this.rotationPitch);
				
				this.part11.setPositionAndRotation(QAQ11.x, QAQ11.y, QAQ11.z, this.rotationYaw, this.rotationPitch);
				this.part12.setPositionAndRotation(QAQ12.x, QAQ12.y, QAQ12.z, this.rotationYaw, this.rotationPitch);
				this.part13.setPositionAndRotation(QAQ13.x, QAQ13.y, QAQ13.z, this.rotationYaw, this.rotationPitch);  
				this.part14.setPositionAndRotation(QAQ14.x, QAQ14.y, QAQ14.z, this.rotationYaw, this.rotationPitch);
				this.part15.setPositionAndRotation(QAQ15.x, QAQ15.y, QAQ15.z, this.rotationYaw, this.rotationPitch);
				
				this.part16.setPositionAndRotation(QAQ16.x, QAQ16.y, QAQ16.z, this.rotationYaw, this.rotationPitch);
				this.part17.setPositionAndRotation(QAQ17.x, QAQ17.y, QAQ17.z, this.rotationYaw, this.rotationPitch);
				this.part18.setPositionAndRotation(QAQ18.x, QAQ18.y, QAQ18.z, this.rotationYaw, this.rotationPitch);
				this.part19.setPositionAndRotation(QAQ19.x, QAQ19.y, QAQ19.z, this.rotationYaw, this.rotationPitch);
				this.part20.setPositionAndRotation(QAQ20.x, QAQ20.y, QAQ20.z, this.rotationYaw, this.rotationPitch);
			}
		}
		else {
			int back = this.thistick - this.ticksExisted;
			double multi = 0.0D;
			Vec3d QAQ1 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ2 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ3 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ4 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ5 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ6 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ7 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ8 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ9 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ10 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ11 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ12 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ13 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ14 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ15 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ16 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ17 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ18 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ19 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			multi += 0.05D;
			Vec3d QAQ20 = position.addVector(lok.x * (back * 2 + MAX_CHAIN) * multi, lok.y * (back * 2 + MAX_CHAIN) * multi, lok.z * (back * 2 + MAX_CHAIN) * multi);
			if(part1 != null) {
				this.setPositionAndRotation(part20.posX, part20.posY, part20.posZ, this.getPlayer().rotationYaw, this.getPlayer().rotationPitch);
				this.part1.setPositionAndRotation(QAQ1.x, QAQ1.y, QAQ1.z, this.rotationYaw, this.rotationPitch);
				this.part2.setPositionAndRotation(QAQ2.x, QAQ2.y, QAQ2.z, this.rotationYaw, this.rotationPitch);
				this.part3.setPositionAndRotation(QAQ3.x, QAQ3.y, QAQ3.z, this.rotationYaw, this.rotationPitch);
				this.part4.setPositionAndRotation(QAQ4.x, QAQ4.y, QAQ4.z, this.rotationYaw, this.rotationPitch);
				this.part5.setPositionAndRotation(QAQ5.x, QAQ5.y, QAQ5.z, this.rotationYaw, this.rotationPitch);
				
				this.part6.setPositionAndRotation(QAQ6.x, QAQ6.y, QAQ6.z, this.rotationYaw, this.rotationPitch);
				this.part7.setPositionAndRotation(QAQ7.x, QAQ7.y, QAQ7.z, this.rotationYaw, this.rotationPitch);
				this.part8.setPositionAndRotation(QAQ8.x, QAQ8.y, QAQ8.z, this.rotationYaw, this.rotationPitch);
				this.part9.setPositionAndRotation(QAQ9.x, QAQ9.y, QAQ9.z, this.rotationYaw, this.rotationPitch);
				this.part10.setPositionAndRotation(QAQ10.x, QAQ10.y, QAQ10.z, this.rotationYaw, this.rotationPitch);
				
				this.part11.setPositionAndRotation(QAQ11.x, QAQ11.y, QAQ11.z, this.rotationYaw, this.rotationPitch);
				this.part12.setPositionAndRotation(QAQ12.x, QAQ12.y, QAQ12.z, this.rotationYaw, this.rotationPitch);
				this.part13.setPositionAndRotation(QAQ13.x, QAQ13.y, QAQ13.z, this.rotationYaw, this.rotationPitch);  
				this.part14.setPositionAndRotation(QAQ14.x, QAQ14.y, QAQ14.z, this.rotationYaw, this.rotationPitch);
				this.part15.setPositionAndRotation(QAQ15.x, QAQ15.y, QAQ15.z, this.rotationYaw, this.rotationPitch);
				
				this.part16.setPositionAndRotation(QAQ16.x, QAQ16.y, QAQ16.z, this.rotationYaw, this.rotationPitch);
				this.part17.setPositionAndRotation(QAQ17.x, QAQ17.y, QAQ17.z, this.rotationYaw, this.rotationPitch);
				this.part18.setPositionAndRotation(QAQ18.x, QAQ18.y, QAQ18.z, this.rotationYaw, this.rotationPitch);
				this.part19.setPositionAndRotation(QAQ19.x, QAQ19.y, QAQ19.z, this.rotationYaw, this.rotationPitch);
				this.part20.setPositionAndRotation(QAQ20.x, QAQ20.y, QAQ20.z, this.rotationYaw, this.rotationPitch);
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
}
