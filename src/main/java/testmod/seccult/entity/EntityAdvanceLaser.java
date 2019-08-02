package testmod.seccult.entity;

import java.util.ArrayList;
import java.util.List;

import net.minecraft.entity.Entity;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.AxisAlignedBB;
import net.minecraft.util.math.Vec3d;
import net.minecraft.world.World;
import testmod.seccult.init.ModDamage;
import testmod.seccult.magick.active.FrozenMagick;
import testmod.seccult.magick.active.Magick;

public class EntityAdvanceLaser extends EntityLaserBeamBase{

	public float red;
	public float green;
	public float blue;
	
	public EntityAdvanceLaser(World worldIn) {
		super(worldIn);
	}

	@Override
	public void onUpdate() {
		super.onUpdate();
		this.setLength(this.getMyLength() + 1);
		if(this.ticksExisted > 60)
			this.setDead();
	}
	
	/*@Override
	public void createLaser(World worldIn, double posXIn, double posYIn, double posZIn, Entity player, float height) {
		SuperLaserBeamFX laser =  new SuperLaserBeamFX(worldIn, posXIn, posYIn, posZIn, player, height);
		laser.setRBGColorF(1, 0.5F, 0);
		laser.setAlphaF(1F);
		laser.setMaxAge(Lwidth);
		Minecraft.getMinecraft().effectRenderer.addEffect(laser);
		this.laserbeam = (SuperLaserBeamFX) laser;
	}*/
	
	@Override
    protected void collideWithNearbyEntities()
    {
    	Vec3d lok = this.getLookVec();
		Vec3d position = this.getPositionVector().addVector(lok.x * distance / 2, lok.y * distance / 2, lok.z * distance / 2);
    	//AxisAlignedBB boundingBox = new AxisAlignedBB(position.x, position.y, position.z, position.x, position.y, position.z).grow(distance / 2, distance / 2, distance / 2);
        //List<Entity> list = this.world.getEntitiesWithinAABBExcludingEntity(this, boundingBox);
        List<Vec3d> vecs = new ArrayList<>();
        for(float f = 0; f < distance; f+=1.5)
        {
        	Vec3d vec = this.getPositionVector().addVector(lok.x * f, lok.y * f, lok.z * f);
        	
        	if(this.snow)
        	{
        		for(int i = 0; i < 2; ++i)
        		this.world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, vec.x, vec.y, vec.z, 
        				this.getLookVec().x * 2 + this.rand.nextDouble(), this.getLookVec().y * 2 + + this.rand.nextDouble(), this.getLookVec().z * 2 + + this.rand.nextDouble());
        	}
        	vecs.add(vec);
        }
        
        /*if (!list.isEmpty() && !vecs.isEmpty())
        {
            for (int l = 0; l < list.size(); ++l)
            {
                Entity entity = list.get(l);
                for(Vec3d vec: vecs)
                {
                	AxisAlignedBB boundingBox1 = new AxisAlignedBB(vec.x, vec.y, vec.z, vec.x, vec.y, vec.z).grow(1, 1, 1);
                	List<Entity> list1 = this.world.getEntitiesWithinAABBExcludingEntity(this, boundingBox);
                	for(Entity entity2 : list1)
                		this.applyEntityCollision(entity2);
                }
                
            }
        }*/
        
        if (!vecs.isEmpty())
        {
                for(Vec3d vec: vecs)
                {
                	AxisAlignedBB boundingBox1 = new AxisAlignedBB(vec.x, vec.y, vec.z, vec.x, vec.y, vec.z).grow(1, 1, 1);
                	List<Entity> list1 = this.world.getEntitiesWithinAABBExcludingEntity(this, boundingBox1);
                	for(Entity entity2 : list1)
                		this.applyEntityCollision(entity2);
                }
        }
    }
	
	@Override
	public void applyEntityCollision(Entity entity) {
			if(owner != null && entity != owner && !(entity instanceof EntityFrozenFX) && !(entity instanceof EntityLaserBeamBase)) {
				attackEntityAsMob(entity);
		    	if(snow)
		    	{
		    		Magick m = new FrozenMagick("null", false);
		    		m.setMagickAttribute(owner, entity, null, 5, 5);
		    	}
			}
	}
	
	@Override
    public boolean attackEntityAsMob(Entity par1Entity)
    {
         boolean var4 = false;
         par1Entity.hurtResistantTime = -1;
         var4 = par1Entity.attackEntityFrom(ModDamage.causePureEntityDamage(this), this.Damage);
         par1Entity.hurtResistantTime = -1;
         return var4;
    }
}
