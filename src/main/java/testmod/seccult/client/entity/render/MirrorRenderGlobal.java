package testmod.seccult.client.entity.render;

import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.RenderGlobal;
import net.minecraft.client.renderer.culling.ICamera;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.SoundEvent;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

@SideOnly(Side.CLIENT)
public class MirrorRenderGlobal extends RenderGlobal
{
    public MirrorRenderGlobal(Minecraft mcIn)
    {
        super(mcIn);
    }

    @Override
    public void renderClouds(float partialTicks, int pass, double p_180447_3_, double p_180447_5_, double p_180447_7_)
    {
        if(true)
            super.renderClouds(partialTicks, pass, p_180447_3_, p_180447_5_, p_180447_7_);
    }

    @Override
    public void playRecord(SoundEvent soundIn, BlockPos pos)
    {
    }

    @Override
    public void renderSky(float partialTicks, int pass) {
    	super.renderSky(partialTicks, pass);
    }
    
    @Override
    public void playSoundToAllNearExcept(EntityPlayer player, SoundEvent soundIn, SoundCategory category, double x, double y, double z, float volume, float pitch)
    {
    }

    @Override
    public void broadcastSound(int soundID, BlockPos pos, int data)
    {
    }

    @Override
    public void playEvent(EntityPlayer player, int type, BlockPos blockPosIn, int data)
    {
    }
    
    @Override
    public void renderEntities(Entity renderViewEntity, ICamera camera, float partialTicks) {
    	super.renderEntities(renderViewEntity, camera, partialTicks);
    }
}
