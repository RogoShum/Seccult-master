package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraft.util.ResourceLocation;
import testmod.seccult.Seccult;
import testmod.seccult.client.FX.ModFX;
import testmod.seccult.client.FX.ParticleFX;
import testmod.seccult.entity.projectile.EntityIMProjectile;
import testmod.seccult.magick.magickState.StateManager;

public class RenderIMProjectile extends Render<EntityIMProjectile>{
	private float red, green, blue;
	public RenderIMProjectile(RenderManager manager) {
        super(manager);
        this.shadowSize = 0;
	}

	@Override
	public void doRender(EntityIMProjectile entity, double x, double y, double z, float entityYaw, float partialTicks) {
		super.doRender(entity, x, y, z, entityYaw, partialTicks);
		red = entity.getColorRed();
    	green = entity.getColorGreen();
    	blue = entity.getColorBlue();
    	
    	ParticleRender.renderFX(ModFX.Pentagon, entity.posX, entity.posY, entity.posZ, entity.getMySize() / 2, 0.9F, new float[] {red, green, blue});
    	
    	if(entity.ticksExisted % 1 == 0)
    	{
    		double d0 = -entity.motionX + (0.2 - Seccult.rand.nextDouble() * 0.1);
            double d1 = -entity.motionY + (0.2 - Seccult.rand.nextDouble() * 0.1);
            double d2 = -entity.motionZ + (0.2 - Seccult.rand.nextDouble() * 0.1);

            ParticleFX me = new ParticleFX(ParticleFX.ParticleType.Light, entity.posX, entity.posY, entity.posZ, d0, d1, d2, entity.getMySize());
        	me.setRBGColorF(red, green, blue);
        	me.setMaxAge(10);
        	ModFX.addPar(me);
    	}
	}
	
	@Override
	protected ResourceLocation getEntityTexture(EntityIMProjectile entity) {
		return null;
	}
}
