package testmod.seccult.magick.active;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import testmod.seccult.init.ModDamage;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;

public class DamageMagick extends Magick{
	protected DamageSource damage;
	
	public DamageMagick(String nbtName, boolean hasDetailedText) 
	{
		super(nbtName, hasDetailedText);
	}
	
	@Override
	public void doMagick() {
		super.doMagick();
	}
	
	public void damage(Entity pl)
	{
		damage = ModDamage.causeNormalEntityDamage(pl);
	}
	
	public void doMagickToEntity(Entity e) 
	{
		if(e != null)
		{
			Vec3d QAQ = e.getLookVec();
			QAQ.scale(0.2);
			e.motionX = QAQ.x * strengh;
			e.motionY = QAQ.y * strengh;
			e.motionZ = QAQ.z * strengh;
		}
	}

	@Override
	void toEntity() {
		if(entity instanceof EntityLivingBase && player != null)
		{
			MagickFX();
			damage(player);
			EntityLivingBase living = (EntityLivingBase) entity;
			living.attackEntityFrom(damage, strengh);
			living.hurtResistantTime = -1;
		}
		else if(player != null)
		{
			doMagickToEntity(entity);
		}
	}

	@Override
	void toBlock() {
		player.world.destroyBlock(block, true);
		MagickFX();
	}

	@Override
	void MagickFX() 
	{
		for(int i = 0; i < strengh; i++) {
		double[] pos = new double[3], vec = new double[3];
		if(entity != null)
		{
			pos[0] = entity.posX;
			pos[1] = entity.posY + (entity.height / 2);
			pos[2] = entity.posZ;
			Vec3d look = player.getLookVec();
			vec[0] = entity.world.rand.nextFloat() / 2 * look.x * strengh;
			vec[1] = entity.world.rand.nextFloat() / 2 * look.y * strengh;
			vec[2] = entity.world.rand.nextFloat() / 2 * look.z * strengh;
		}
		
		if(block != null)
		{
			pos[0] = block.getX();
			pos[1] = block.getY() + 1;
			pos[2] = block.getZ();
			Vec3d look = player.getLookVec();
			vec[0] = player.world.rand.nextFloat() / 10 * look.x * strengh;
			vec[1] = player.world.rand.nextFloat() / 10 * look.y * strengh;
			vec[2] = player.world.rand.nextFloat() / 10 * look.z * strengh;
		}
		
		float[] color = {RGB[0], RGB[1], RGB[2]};
		NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, strengh / 5, 0));
		}
	}

	@Override
	public int getColor() {
		return ModMagicks.DamageMagickColor;
	}
}
