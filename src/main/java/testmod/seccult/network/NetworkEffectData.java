package testmod.seccult.network;

import java.util.Iterator;

import com.google.common.collect.AbstractIterator;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.entity.effect.EntityLightningBolt;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.SoundEvents;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.SoundCategory;
import net.minecraft.util.math.BlockPos;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.SidedProxy;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.Seccult;
import testmod.seccult.client.FX.*;
import testmod.seccult.magick.magickState.StateManager;

public class NetworkEffectData implements IMessage {
	private double[] pos, vec = new double[3];
	private float[] color = new float[3];
	private float scale;
	private int type;
	
    public NetworkEffectData() {}
    
    public NetworkEffectData(double[] pos, double[] vec, float[] color, float scale, int type)
    {
    	this.pos = pos;
    	this.vec = vec;
    	this.color = color;
    	this.scale = scale;
    	this.type = type;
    }
     
    @Override
    public void fromBytes(ByteBuf buf) {
    	pos = new double[3]; vec = new double[3];
    	color = new float[3];
    	for(int i = 0; i < 3; i ++)
    	{
    		pos[i] = buf.readDouble();
    		vec[i] = buf.readDouble();
    		color[i] = buf.readFloat();
    	}
    	scale = buf.readFloat();
    	type = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	for(int i = 0; i < 3; i ++)
    	{
    		buf.writeDouble(pos[i]);
    		buf.writeDouble(vec[i]);
    		buf.writeFloat(color[i]);
    	}
    	buf.writeFloat(scale);
    	buf.writeInt(type);
    }
    
    public static class PacketMessageHandler implements IMessageHandler<NetworkEffectData, IMessage> {

        @Override
        public IMessage onMessage(NetworkEffectData message, MessageContext ctx) {
				Seccult.proxy.SeccultFX(message.pos, message.vec, message.color, message.scale, message.type);
            return null;
        }

	}
}
