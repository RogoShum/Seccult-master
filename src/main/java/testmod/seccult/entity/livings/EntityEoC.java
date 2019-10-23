package testmod.seccult.entity.livings;

import java.util.ArrayList;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityList;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.IEntityLivingData;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.network.datasync.DataSerializers;
import net.minecraft.network.datasync.EntityDataManager;
import net.minecraft.util.DamageSource;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.BossInfo.Color;
import net.minecraft.world.BossInfo.Overlay;
import net.minecraft.world.World;
import testmod.seccult.events.BossEventHandler;
import testmod.seccult.events.ModEventHandler;
import testmod.seccult.init.ModSounds;

public class EntityEoC extends EntityBase implements IBossBase{
	protected EntityLivingBase ene;
	protected double eneX;
	protected double eneY;
	protected double eneZ;
	protected int state;
	private int Times;
	private int cooldown;

	private int CoolDown;
	private ResourceLocation EOWres = new ResourceLocation("seccult:servantofcthulhu");
	private static final DataParameter<Integer> EoC_VARIANT = EntityDataManager.<Integer>createKey(EntityEoC.class, DataSerializers.VARINT);
	
	public EntityEoC(World worldIn) {
		super(worldIn);
		this.setSize(5F, 5F);
		this.setNoGravity(true);
		this.CoolDown = 60;
		this.state = 0;
		this.Times = 0;
		this.noClip = true;
		this.isTRboss = true;
		ArrayList<EntityBase> boss = new ArrayList<>();
		boss.add(this);
		new BossEventHandler(boss);
	}
	
	@Override
	public boolean isNonBoss() {
		return false;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(2000.0D);
        this.getEntityAttribute(SharedMonsterAttributes.KNOCKBACK_RESISTANCE).setBaseValue(1.0D);
        if(this.getHealth() > this.getMaxHealth() / 2)
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(12.0D);
        else
        this.getEntityAttribute(SharedMonsterAttributes.ARMOR).setBaseValue(0);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		SearchEnermy();
		PreAttack();
		if(CoolDown > 0)
			CoolDown--;
		if(ene != null && this.getDistanceSq(ene) < 3) {
			if(this.getHealth() > this.getMaxHealth() / 2)
			ene.attackEntityFrom(DamageSource.causeMobDamage(this), 15);
			else
			ene.attackEntityFrom(DamageSource.causeMobDamage(this), 23);
		}
	}
	
    protected void entityInit()
    {
        super.entityInit();
        this.dataManager.register(EoC_VARIANT, Integer.valueOf(1));
    }
	
    public int getRenderSkin()
    {
        return ((Integer)this.dataManager.get(EoC_VARIANT)).intValue();
    }

    public void setRender(int skinId)
    {
        this.dataManager.set(EoC_VARIANT, Integer.valueOf(skinId));
    }
	
	protected void SearchEnermy() {
		ene = findPlayerToAttack(this);
	}

	protected void Wait() {
		this.faceEntity(ene, 180, 180);
		Moveto(ene.posX, ene.posY + ene.height + 9, ene.posZ, 0.2F);
	}

	protected void PreAttack() {
		if (ene == null)
			return;
		if(CoolDown > 0)
			Wait();
		else
		{
			switch (state) {
			 case 0:
				 AttackMode1();
				 break;
			 case 1:
				 AttackMode2();
				 break;
			 case 2:
				 rotate();
				 break;
			 case 3:
				 Sprint();
			}
		}
	}

	protected void Sprint() {
		if(this.getHealth() > this.getMaxHealth() / 2) {
		Moveto(eneX, eneY, eneZ, 0.1F);
		cooldown++;
		if(cooldown > 70) {
			state = 0;
			cooldown = 0;
		}
		}
		else 
		{
			Moveto(eneX, eneY, eneZ, 0.4F);
			cooldown++;
			if(cooldown > 50) {
				state = 1;
				cooldown = 0;
			}
		}
	}
	
	protected void AttackMode1() {
		
		if(this.getHealth() < this.getMaxHealth() / 2) {
			CoolDown = 60;
			this.state = 1;
		}

		if (Times < 3 && (tick % 30 == 0 || tick % 40 == 0 || tick % 50 == 0) && this.getHealth() > this.getMaxHealth() / 2)
		{
			Vec3d AP = this.getPositionVector();
			AP.addVector(0, this.height / 2, 0);
			Vec3d QAQ = AP.addVector(this.getLookVec().x * 2.7, this.getLookVec().y * 2.7, this.getLookVec().z * 2.7);
			Entity entity = null;
        	entity = EntityList.createEntityByIDFromName(EOWres , this.world);
        	EntitySoC SoC = (EntitySoC) entity;
            entity.setLocationAndAngles(QAQ.x, QAQ.y, QAQ.z, this.rotationYaw, this.rotationPitch);
            SoC.rotationYawHead = SoC.rotationYaw;
            SoC.renderYawOffset = SoC.rotationYaw;
            SoC.onInitialSpawn(this.world.getDifficultyForLocation(new BlockPos(SoC)), (IEntityLivingData)null);
	 		if(!this.world.isRemote)
            this.world.spawnEntity(SoC);
			Times++; 		
		}
		else if(Times < 6)
		{
			prepare();
		}
		else {
			CoolDown = 70;
			Times = 0;
		}
	}
	
	protected void AttackMode2() {	
		setRender(2);
		if(Times < 3)
		{	
			prepare();
		}
		else {
			CoolDown = 70;
			Times = 0;
		}
	}
	
	protected void prepare() {
		this.faceEntity(ene, 360, 360);
		Vec3d QAQ = onLook(this.getLookVec(), this.getPositionVector(), ene.getPositionVector(), 19);
		this.eneX = (int)QAQ.x;
		this.eneY = (int)QAQ.y;
		this.eneZ = (int)QAQ.z;
		Times++;
		this.state = 3;
	}
	
	protected Vec3d onLook(Vec3d look, Vec3d AP, Vec3d LP, double dist) {
		AP.addVector(0, this.height / 2, 0);
		double distance = AP.distanceTo(LP);
		double dis = distance + dist;
		return AP.addVector(look.x * dis, look.y * dis, look.z * dis);
	}
	
	protected void rotate() {
		this.CoolDown = 80;
		this.setRender(3);
	}

	@Override
	public Color getBarColor() {
		return Color.RED;
	}

	@Override
	public boolean DarkenSky() {
		return true;
	}

	@Override
	public Overlay getOverlay() {
		return Overlay.PROGRESS;
	}
	
	@Override
	public SoundEvent getBGM() {
		return ModSounds.qualia;
	}
}
