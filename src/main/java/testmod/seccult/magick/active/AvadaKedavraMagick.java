package testmod.seccult.magick.active;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.nbt.NBTTagCompound;
import net.minecraft.util.DamageSource;
import net.minecraft.util.math.Vec3d;
import testmod.seccult.blocks.tileEntity.tileEnchantingStaff;
import testmod.seccult.entity.livings.EntitySpirit;
import testmod.seccult.init.ModDamage;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;
import testmod.seccult.network.NetworkMutekiGamer;

public class AvadaKedavraMagick extends Magick implements SlayMagic{
	protected DamageSource damage;
	
	public AvadaKedavraMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}
	
	@Override
	public void doMagick() {
		super.doMagick();
	}
	
	public void damage(Entity pl)
	{
		damage = ModDamage.causeForbiddenEntityDamage(pl);
	}

	@Override
	void toEntity() {
		
		if(entity instanceof EntitySpirit)
		{
			NetworkHandler.getNetwork().sendToAll(new NetworkMutekiGamer(true, entity, 2));
			((EntitySpirit) entity).setRelease();
			return;
		}
		
		if(entity instanceof EntityLivingBase && player != null)
		{
			MagickFX();
			damage(player);
			EntityLivingBase living = (EntityLivingBase) entity;
			living.hurtResistantTime = -1;
			if(!living.attackEntityFrom(damage, strengh * 300))
			{
				if(living.getHealth() > strengh * 300)
					living.setHealth(living.getHealth() - strengh * 300);
				else
				{
					NBTTagCompound tag = new NBTTagCompound();
					living.writeToNBT(tag);
					tag.setBoolean("AvadaKedavra", true);
					tag.setFloat("Health", living.getHealth() - strengh * 300);
					living.readFromNBT(tag);
					living.setHealth(living.getHealth() - strengh * 300);
					living.onDeath(damage);
					living.setDead();
					living.isDead = true;
				}
			}
			
			living.hurtResistantTime = -1;
		}
		else if(entity instanceof Entity)
		{
			entity.setDead();
			entity.isDead = true;
		}
	}

	@Override
	void toBlock() 
	{

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
			look = look.scale(0.05);
			vec[0] = entity.world.rand.nextFloat() / 2 * look.x * strengh;
			vec[1] = entity.world.rand.nextFloat() / 2 * look.y * strengh;
			vec[2] = entity.world.rand.nextFloat() / 2 * look.z * strengh;
		}

		float[] color = {RGB[0], RGB[1], RGB[2]};
		NetworkHandler.getNetwork().sendToAll(new NetworkEffectData(pos, vec, color, strengh / 5, 0));
		}
	}

	@Override
	public int getColor() {
		return ModMagicks.AvadaKedavraMagickColor;
	}

	@Override
	public boolean doMagickNeedAtrribute() {
		return false;
	}
	
	@Override
	public boolean doMagickNeedStrength() {
		return true;
	}
}
