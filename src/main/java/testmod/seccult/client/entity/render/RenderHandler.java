package testmod.seccult.client.entity.render;

import net.minecraftforge.fml.client.registry.RenderingRegistry;
import testmod.seccult.entity.*;
import testmod.seccult.entity.livings.*;
import testmod.seccult.entity.livings.flying.EntityAirTentacle;
import testmod.seccult.entity.livings.flying.EntityBird;
import testmod.seccult.entity.livings.insect.*;
import testmod.seccult.entity.livings.water.*;
import testmod.seccult.entity.projectile.*;

public class RenderHandler 
{
	public static void registerEntityRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityLight.class, m -> new RenderLight(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityNotoriousBIG.class, m -> new RenderBIG(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityEoC.class, m -> new RenderEoC(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityBloodBeam.class, m -> new RenderBloodBeam(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityEoW.class, m -> new RenderEoW(m));
		RenderingRegistry.registerEntityRenderingHandler(EntitySolarEruption.class, m -> new RenderSolar(m));
		RenderingRegistry.registerEntityRenderingHandler(TRMagickProjectile.class, m -> new RenderTRMagick(m));
		RenderingRegistry.registerEntityRenderingHandler(TRBladeBeam.class, m -> new RenderTRBlade(m));
		RenderingRegistry.registerEntityRenderingHandler(EntitySoC.class, m -> new RenderSoC(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityLaserBeamBase.class, m -> new RenderLaser(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityDummy.class, m -> new RenderDummy(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityChangeling.class, m -> new RenderChangeling(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityLmr.class, m -> new RenderLmr(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityIMProjectile.class, m -> new RenderLmr(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityIMCircle.class, m -> new RenderIMCircle(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityClowCardArrow.class, m -> new RenderClowArrow(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityTimeManager.class, m -> new RenderTimeManager(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityBlackVelvetHell.class, m -> new RenderBlackVelvetHell(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityCarne.class, m -> new RenderCarne(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityFish.class, m -> new RenderFish(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityButterfly.class, m -> new RenderButterFly(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityJellyfish.class, m -> new RenderJellyFish(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityBird.class, m -> new RenderBird(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityAirTentacle.class, m -> new RenderAirTentacle(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityWaterTentacle.class, m -> new RenderWaterTentacle(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityBoneShark.class, m -> new RenderBoneShark(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityLightingThing.class, m -> new RenderLightingThing(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityRockShellLeviathan.class, m -> new RenderRockShell(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityMagickBubble.class, m -> new RenderMagickBubble(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityFrozenFX.class, m -> new RenderFrozenFX(m));
		RenderingRegistry.registerEntityRenderingHandler(EntityAdvanceLaser.class, m -> new RenderAdvanceLaser(m));
		RenderingRegistry.registerEntityRenderingHandler(EntitySpirit.class, m -> new RenderSpirit(m));
	}
}
