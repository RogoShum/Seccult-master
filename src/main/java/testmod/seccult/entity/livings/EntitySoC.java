package testmod.seccult.entity.livings;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySoC extends EntityBase{

	private EntityLivingBase player;
	private int CoolDown = 0;
	public EntitySoC(World worldIn) {
		super(worldIn);
		this.setSize(0.3F, 0.3F);
		this.setNoGravity(true);
		this.noClip = true;
		this.isTRboss = true;
	}
	
	@Override
	public void onUpdate() {
		super.onUpdate();
		CoolDown--;
			player = findPlayerToAttack(this);
		if(player != null && CoolDown <= 0) {
			Moveto(player.posX, player.posY, player.posZ, 0.1f);
			this.faceEntity(player, 360, 360);
		}
		else if(player != null) {
			Moveto(player.posX, player.posY, player.posZ, 0.03f);
			this.faceEntity(player, 180, 180);
		}
		
		if(player != null && this.getDistanceSq(player) < 1) {
			if(player.attackEntityFrom(DamageSource.causeMobDamage(this), 12))
				CoolDown = 80;
		}
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8D);
	}
}
