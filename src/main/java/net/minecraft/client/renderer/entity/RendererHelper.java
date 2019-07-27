package net.minecraft.client.renderer.entity;

import net.minecraft.client.model.ModelBase;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.util.ResourceLocation;

public class RendererHelper
{
	private static RenderManager Render;
	
	public static RenderLivingBase<EntityLivingBase> getLivingRender(EntityLivingBase e) 
	{
		return (RenderLivingBase)Render.getEntityRenderObject(e);
	}
	
	public static Render<Entity> getRender(Entity e) 
	{
		return Render.getEntityRenderObject(e);
	}
	
	public static ModelBase getMainModel(EntityLivingBase e)
	{
		return getLivingRender(e).mainModel;
		
	}

	public static String getTexture(EntityLivingBase e) 
	{
		ResourceLocation location = getLivingRender(e).getEntityTexture(e);
		return location.toString();
	}
	
	public static String getTexture(@SuppressWarnings("rawtypes") Render render2, Entity entity) 
	{
		ResourceLocation location = render2.getEntityTexture(entity);
		return location.toString();
	}
}