package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import testmod.seccult.entity.*;
import testmod.seccult.entity.livings.EntityCarne;
import testmod.seccult.entity.livings.EntityChangeling;
import testmod.seccult.entity.livings.EntityEoC;
import testmod.seccult.entity.livings.EntityEoW;
import testmod.seccult.entity.livings.EntityLight;
import testmod.seccult.entity.livings.EntityNotoriousBIG;
import testmod.seccult.entity.livings.EntitySoC;
import testmod.seccult.entity.projectile.*;

public class RenderHandler 
{
	public static void registerEntityRenders()
	{
		RenderingRegistry.registerEntityRenderingHandler(EntityLight.class, new IRenderFactory<EntityLight>()
		{
			@Override
			public Render<? super EntityLight> createRenderFor(RenderManager manager)
			{
				return new RenderLight(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityNotoriousBIG.class, new IRenderFactory<EntityNotoriousBIG>()
		{
			@Override
			public Render<? super EntityNotoriousBIG> createRenderFor(RenderManager manager)
			{
				return new RenderBIG(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityBloodBeam.class, new IRenderFactory<EntityBloodBeam>()
		{
			@Override
			public Render<? super EntityBloodBeam> createRenderFor(RenderManager manager)
			{
				return new RenderBloodBeam(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityEoW.class, new IRenderFactory<EntityEoW>()
		{
			@Override
			public Render<? super EntityEoW> createRenderFor(RenderManager manager)
			{
				return new RenderEoW(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityEoC.class, new IRenderFactory<EntityEoC>()
		{
			@Override
			public Render<? super EntityEoC> createRenderFor(RenderManager manager)
			{
				return new RenderEoC(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntitySoC.class, new IRenderFactory<EntitySoC>()
		{
			@Override
			public Render<? super EntitySoC> createRenderFor(RenderManager manager)
			{
				return new RenderSoC(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(TRBladeBeam.class, new IRenderFactory<TRBladeBeam>()
		{
			@Override
			public Render<? super TRBladeBeam> createRenderFor(RenderManager manager)
			{
				return new RenderTRBlade(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(TRMagickProjectile.class, new IRenderFactory<TRMagickProjectile>()
		{
			@Override
			public Render<? super TRMagickProjectile> createRenderFor(RenderManager manager)
			{
				return new RenderTRMagick(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntitySolarEruption.class, new IRenderFactory<EntitySolarEruption>()
		{
			@Override
			public Render<? super EntitySolarEruption> createRenderFor(RenderManager manager)
			{
				return new RenderSolar(manager);
			}
		});
		
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
	}
}
