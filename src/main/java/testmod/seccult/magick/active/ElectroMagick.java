package testmod.seccult.magick.active;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.init.Blocks;
import net.minecraft.util.math.Vec3d;
import testmod.seccult.init.ModDamage;
import testmod.seccult.init.ModMagicks;
import testmod.seccult.network.NetworkEffectData;
import testmod.seccult.network.NetworkHandler;

public class ElectroMagick extends Magick implements AttackingMagic{
	
	public ElectroMagick(String nbtName, boolean hasDetailedText, float cost1, float cost2) 
	{
		super(nbtName, hasDetailedText, cost1, cost2);
	}
	
	@Override
	public void doMagick() {
		super.doMagick();
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
			EntityLivingBase living = (EntityLivingBase) entity;
			living.hurtResistantTime = -1;
			
			strengh = strengh - 5;
			if(strengh < 0)
			{
				strengh = 0;
			}
			else
			{
			if(strengh < 15)
			{
				living.attackEntityFrom(ModDamage.causeMagickElectroDamage(player), strengh);
			}
			else
				living.attackEntityFrom(ModDamage.causeMagickThunderDamage(player), strengh);
			}
			living.hurtResistantTime = -1;

			entity.onStruckByLightning(new EntityLightningBolt(entity.world, entity.posX, entity.posY, entity.posZ, true));
		}
		else if(player != null)
		{
			doMagickToEntity(entity);
		}
	}

	@Override
	void toBlock() {
		player.world.setBlockState(block.up(), Blocks.FIRE.getDefaultState());
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
		return ModMagicks.ElectroMagickColor;
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
