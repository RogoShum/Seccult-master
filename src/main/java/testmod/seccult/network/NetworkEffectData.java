package testmod.seccult.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
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
        	Minecraft mc = Minecraft.getMinecraft();
        	Particle par = null;
        	double x = message.pos[0];
        	double y = message.pos[1];
        	double z = message.pos[2];
        	
        	double xx = message.vec[0];
        	double yy = message.vec[1];
        	double zz = message.vec[2];
        	
        	float r = message.color[0];
        	float g = message.color[1];
        	float b = message.color[2];
        	switch(message.type)
        	{
        		case 0:
        			par = new LightFX(mc.world,x,y,z, xx, yy, zz, message.scale);
        			par.setRBGColorF(r, g, b);
        			break;
        		case 1:
        			par = new StarFX(mc.world,x,y,z, xx, yy, zz, message.scale);
        			par.setRBGColorF(r, g, b);
        			break;
        		case 2:
        			par = new PentagonFX(mc.world,x,y,z, xx, yy, zz, message.scale);
        			par.setRBGColorF(r, g, b);
        			break;
        		case 100:
        			for(int i = 0; i < 10 ; i++) {
        	            double d0 = (double)((float)message.pos[0] + mc.world.rand.nextFloat());
        	            double d1 = (double)((float)message.pos[1] + mc.world.rand.nextFloat());
        	            double d2 = (double)((float)message.pos[2] + mc.world.rand.nextFloat());
        	            double d3 = (1 - 2*StateManager.rand.nextFloat()) / 2;
        	            double d4 = (1 - 2*StateManager.rand.nextFloat()) / 2;
        	            double d5 = (1 - 2*StateManager.rand.nextFloat()) / 2;
        	        	Particle me = new LightFX(mc.world, (d0 +x) / 2.0D, (d1 +y) / 2.0D, (d2 +z) / 2.0D, d3/6, d4/6, d5/6, 1);
        	        	me.setRBGColorF(r, g, b);
        	        	Particle smoke = new LightFX(mc.world, d0, d1, d2, d3 / 5, d4 / 5, d5 / 5, 1);
        	        	smoke.setRBGColorF(r, g, b);
        	        	Particle pop = new LightFX(mc.world, d0, d1, d2, -d3/6, -d4/6, -d5/6, 1);
        	        	pop.setRBGColorF(r, g, b);
        				}
        			break;
        		case 101:
        			int distance = (int) message.scale * 10;
        			for (int i = 0; i < distance; i++) {
        				double trailFactor = i / (distance - 1.0D);
        				double tx = x + (xx - x) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
        				double ty = y + (yy - y) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
        				double tz = z + (zz - z) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
        				double motionX = 1 - 2*mc.world.rand.nextFloat();
        				double motionY = 1 - 2*mc.world.rand.nextFloat();
        				double motionZ = 1 - 2*mc.world.rand.nextFloat();
        				
        				Particle big = new PentagonFX(mc.world, tx, ty, tz, motionX / 25, motionY / 25, motionZ / 25, 1F);
        		    	big.setRBGColorF(r, g, b);
        		    	Minecraft.getMinecraft().effectRenderer.addEffect(big);
        			}
        			break;
        		case 102:
        			for(int i = 0; i < (message.scale * 2); i++) {
        			       double d0 = (double)((float)x + mc.world.rand.nextFloat());
        			       double d1 = (double)((float)2 + mc.world.rand.nextFloat());
        			       double d2 = (double)((float)z + mc.world.rand.nextFloat());
        			       double d3 = (1 - 2*StateManager.rand.nextFloat()) / 2;
        			       double d4 = (1 - 2*StateManager.rand.nextFloat()) / 2;
        			       double d5 = (1 - 2*StateManager.rand.nextFloat()) / 2;
        			    	Particle me = new LightFX(mc.world, (d0 + x) / 2.0D, (d1 + y) / 2.0D, (d2 + z) / 2.0D, d3, d4, d5, 3);
        			    	me.setRBGColorF(r, g, b);
        			    	Particle smoke = new StarFX(mc.world, d0, d1, d2, d3 / 2, d4 / 2, d5 / 2, message.scale / 3);
        			    	smoke.setRBGColorF(r, g, b);
        			    	Particle pop = new LightFX(mc.world, d0, d1, d2, -d3, -d4, -d5, message.scale / 4);
        			    	pop.setRBGColorF(r, g, b);
        			    	Particle big = new LightFX(mc.world, d0, d1, d2, -d3 / 5, -d4 / 5, -d5 / 5, message.scale * 2);
        			    	big.setRBGColorF(r, g, b);
        			    	big.setMaxAge(60);
        			    	Minecraft.getMinecraft().effectRenderer.addEffect(me);
        			    	Minecraft.getMinecraft().effectRenderer.addEffect(smoke);
        			    	Minecraft.getMinecraft().effectRenderer.addEffect(pop);
        			    	Minecraft.getMinecraft().effectRenderer.addEffect(big);
        				}
        			break;
        		case 103:
        			for (int i = 0; i < message.scale * 30; i++) {
        				double motionX = 1 - 2*mc.world.rand.nextFloat();
        				double motionY = 1 - 2*mc.world.rand.nextFloat();
        				double motionZ = 1 - 2*mc.world.rand.nextFloat();
        				motionX = motionX * message.scale;
        				motionY = motionY * message.scale;
        				motionZ = motionZ * message.scale;
        				Particle big = new LightFX(mc.world, x, y, z, motionX, motionY, motionZ, message.scale * 10);
        		    	big.setRBGColorF(r, g, b);
        				big.setMaxAge(10);
        		    	Minecraft.getMinecraft().effectRenderer.addEffect(big);
        			}
        			break;
        	}
        	if(par != null)
			mc.effectRenderer.addEffect(par);
            return null;
        }
}
}
