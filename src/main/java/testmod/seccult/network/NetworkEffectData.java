package testmod.seccult.network;

import java.util.Iterator;

import com.google.common.collect.AbstractIterator;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.client.particle.Particle;
import net.minecraft.util.EnumParticleTypes;
import net.minecraft.util.math.BlockPos;
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
        		case 3:
        		    for (int sn = 0; sn < 4; ++sn)
        		    {
        			double d0 = x + 3 - mc.world.rand.nextInt(6);
        			double d1 = y + 3 - mc.world.rand.nextInt(6);
        			double d2 = z + 3 - mc.world.rand.nextInt(6);
        			mc.world.spawnParticle(EnumParticleTypes.SNOW_SHOVEL, d0, d1, d2, 0, 0, 0);
        		    }
        			break;
        		case 100:
        			for(int i = 0; i < 20 ; i++) {
        	            double d0 = (double)((float)message.pos[0] + mc.world.rand.nextFloat());
        	            double d1 = (double)((float)message.pos[1] + mc.world.rand.nextFloat());
        	            double d2 = (double)((float)message.pos[2] + mc.world.rand.nextFloat());
        	            double d3 = (1 - 2*StateManager.rand.nextFloat()) / 2;
        	            double d4 = (1 - 2*StateManager.rand.nextFloat()) / 2;
        	            double d5 = (1 - 2*StateManager.rand.nextFloat()) / 2;
        	        	Particle me = new LightFX(mc.world, (d0 +x) / 2.0D, (d1 +y) / 2.0D, (d2 +z) / 2.0D, d3/6, d4/6, d5/6, 0.3F);
        	        	me.setRBGColorF(r, g, b);
        	        	Particle smoke = new StarFX(mc.world, d0, d1, d2, d3 / 5, d4 / 5, d5 / 5, 0.3f);
        	        	Minecraft.getMinecraft().effectRenderer.addEffect(me);
        	        	Minecraft.getMinecraft().effectRenderer.addEffect(smoke);
        				}
        			break;
        		case 101:
        			int distance = (int) message.scale;
        			for (int i = 0; i < distance; i++) {
        				double trailFactor = i / (distance - 1.0D);
        				double tx = x + (xx - x) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
        				double ty = y + (yy - y) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
        				double tz = z + (zz - z) * trailFactor + mc.world.rand.nextGaussian() * 0.005;
        				double motionX = 1 - 2*mc.world.rand.nextFloat();
        				double motionY = 1 - 2*mc.world.rand.nextFloat();
        				double motionZ = 1 - 2*mc.world.rand.nextFloat();
        				
        				Particle big = new PentagonFX(mc.world, tx, ty, tz, motionX / 25, motionY / 25, motionZ / 25, 0.4F);
        		    	big.setRBGColorF(r, g, b);
        		    	Minecraft.getMinecraft().effectRenderer.addEffect(big);
        			}
        			break;
        		case 102:
        			for(int i = 0; i < (message.scale * 2); i++) {
        			       double d0 = (double)((float)x + mc.world.rand.nextFloat());
        			       double d1 = (double)((float)y + mc.world.rand.nextFloat());
        			       double d2 = (double)((float)z + mc.world.rand.nextFloat());
        			       double d3 = (1 - 2*StateManager.rand.nextFloat()) / 2;
        			       double d4 = (1 - 2*StateManager.rand.nextFloat()) / 2;
        			       double d5 = (1 - 2*StateManager.rand.nextFloat()) / 2;
        			    	Particle big = new LightFX(mc.world, d0, d1, d2, -d3 / 20, -d4 / 20, -d5 / 20, message.scale / 2);
        			    	big.setRBGColorF(r, g, b);
        			    	big.setMaxAge(60);
        			    	Minecraft.getMinecraft().effectRenderer.addEffect(big);
        				}
        			break;
        		case 103:
        			for (int i = 0; i < message.scale * 30; i++) {
        				double motionX = 1 - 2*mc.world.rand.nextFloat();
        				double motionY = 1 - 2*mc.world.rand.nextFloat();
        				double motionZ = 1 - 2*mc.world.rand.nextFloat();
        				motionX = motionX * message.scale / 4;
        				motionY = motionY * message.scale / 4;
        				motionZ = motionZ * message.scale / 4;
        				Particle big = new LightFX(mc.world, x, y, z, motionX / 4, motionY / 4, motionZ / 4, message.scale * 5);
        		    	big.setRBGColorF(r, g, b);
        				big.setMaxAge(10);
        		    	Minecraft.getMinecraft().effectRenderer.addEffect(big);
        			}
        			break;
        		case 104:
        			int scale = (int)(message.scale / 2);
        				Iterable<BlockPos> Blocks = getAllInBox((int)x - scale, (int)y, (int)z - scale, (int)x + scale, (int)y, (int)z + scale);
        				for(BlockPos pos: Blocks)
        				{
        						float randF = mc.world.rand.nextFloat()*2;
        						if(randF > 1.75 || randF < 0.25)
        						{
        						Particle cc = new LightFX(mc.world, pos.getX(), pos.getY(), pos.getZ(), 0, (float)scale / 50, 0, (message.scale) / 6  * randF);
        						cc.setRBGColorF(r, g, b);
            					Minecraft.getMinecraft().effectRenderer.addEffect(cc);
        						}
        				}
        			break;
        		case 105:
        				Particle cc = new FrozenBlockFX(mc.world, x, y, z, r, g, 3);
            			Minecraft.getMinecraft().effectRenderer.addEffect(cc);
        				
        			break;	
        }
    
        	if(par != null)
			mc.effectRenderer.addEffect(par);
            return null;
        }
        
        public static Iterable<BlockPos> getAllInBox(final double x1, final double y1, final double z1, final double x2, final double y2, final double z2)
        {
            return new Iterable<BlockPos>()
            {
                public Iterator<BlockPos> iterator()
                {
                    return new AbstractIterator<BlockPos>()
                    {
                        private boolean first = true;
                        private double lastPosX;
                        private double lastPosY;
                        private double lastPosZ;
                        protected BlockPos computeNext()
                        {
                            if (this.first)
                            {
                                this.first = false;
                                this.lastPosX = x1;
                                this.lastPosY = y1;
                                this.lastPosZ = z1;
                                return new BlockPos(x1, y1, z1);
                            }
                            else if (this.lastPosX == x2 && this.lastPosY == y2 && this.lastPosZ == z2)
                            {
                                return (BlockPos)this.endOfData();
                            }
                            else
                            {
                                if (this.lastPosX < x2)
                                {
                                    ++this.lastPosX;
                                }
                                else if (this.lastPosY < y2)
                                {
                                    this.lastPosX = x1;
                                    ++this.lastPosY;
                                }
                                else if (this.lastPosZ < z2)
                                {
                                    this.lastPosX = x1;
                                    this.lastPosY = y1;
                                    ++this.lastPosZ;
                                }

                                return new BlockPos(this.lastPosX, this.lastPosY, this.lastPosZ);
                            }
                        }
                    };
                }
            };
        }
	}
}
