package testmod.seccult.client.entity.render;

import net.minecraft.client.renderer.entity.Render;
import net.minecraft.client.renderer.entity.RenderManager;
import net.minecraftforge.fml.client.registry.IRenderFactory;
import net.minecraftforge.fml.client.registry.RenderingRegistry;
import testmod.seccult.entity.*;
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
		
		RenderingRegistry.registerEntityRenderingHandler(EntityLmr.class, new IRenderFactory<EntityLmr>()
		{
			@Override
			public Render<? super EntityLmr> createRenderFor(RenderManager manager)
			{
				return new RenderLmr(manager);
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
		
		RenderingRegistry.registerEntityRenderingHandler(EntityTimeManager.class, new IRenderFactory<EntityTimeManager>()
		{
			@Override
			public Render<? super EntityTimeManager> createRenderFor(RenderManager manager)
			{
				return new RenderTimeManager(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityBlackVelvetHell.class, new IRenderFactory<EntityBlackVelvetHell>()
		{
			@Override
			public Render<? super EntityBlackVelvetHell> createRenderFor(RenderManager manager)
			{
				return new RenderBlackVelvetHell(manager);
			}
		});
		
		RenderingRegistry.registerEntityRenderingHandler(EntityCarne.class, new IRenderFactory<EntityCarne>()
		{
			@Override
			public Render<? super EntityCarne> createRenderFor(RenderManager manager)
			{
				return new RenderCarne(manager);
			}
		});
	}
}
