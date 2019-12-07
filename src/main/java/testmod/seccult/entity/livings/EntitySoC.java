package testmod.seccult.entity.livings;

import java.util.List;

import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.SharedMonsterAttributes;
import net.minecraft.entity.monster.IMob;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.DamageSource;
import net.minecraft.world.World;

public class EntitySoC extends EntityBase implements IMob{

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
			player = SearchEnermy();
		if(player != null && CoolDown <= 0) {
			Moveto(player.posX, player.posY, player.posZ, 0.05f);
			this.faceEntity(player, 360, 360);
		}
		else if(player != null) {
			Moveto(player.posX, player.posY, player.posZ, 0.015f);
			this.faceEntity(player, 180, 180);
		}
		
		if(player != null && this.getDistanceSq(player) < 1) {
			if(player.attackEntityFrom(DamageSource.causeMobDamage(this), 1))
				CoolDown = 80;
		}
	}
	
	protected EntityPlayer SearchEnermy() {
		EntityPlayer player = null;
		EntityPlayer attackTarget = null;
		float distance = 120;
	    List<EntityPlayer> list = this.world.playerEntities;

	    for (int j1 = 0; j1 < list.size(); ++j1)
	    {
	    	player = (EntityPlayer)list.get(j1);
		        
		    if(player.dimension == this.dimension && player.getDistance(this) < distance)
		    {
		    	distance = player.getDistance(this);
		    	attackTarget = player;
		    }
		}
	    
	    return attackTarget;
	}
	
	@Override
	protected void applyEntityAttributes() {
		super.applyEntityAttributes();
		this.getEntityAttribute(SharedMonsterAttributes.MAX_HEALTH).setBaseValue(8D);
	}
}
