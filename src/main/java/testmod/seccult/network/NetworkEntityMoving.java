package testmod.seccult.network;

import java.util.UUID;

import javax.annotation.Nullable;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;

public class NetworkEntityMoving implements IMessage {
	private double[] pos;
	private int type;
	private double[] move;
	
	private UUID PUUID;
	
	private long ulong;
	private long uleast;
	
    public NetworkEntityMoving() {}
    
    public NetworkEntityMoving(UUID p, double[] pos, double[] move, int type)
    {
    	this.PUUID = p;
    	this.pos = pos;
    	this.move = move;
    	this.type = type;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	type = buf.readInt();
    	double[] pos = new double[3];
    	double[] move = new double[3];
    	
    	for(int i = 0; i < 3; i ++)
    	{
        	pos[i] = buf.readDouble();
        	move[i] = buf.readDouble();
    	}
    	this.move = move;
    	this.pos = pos;
        uleast = buf.readLong();
        ulong = buf.readLong();
        PUUID = new UUID(ulong, uleast);
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	buf.writeInt(type);
    	for(int i = 0; i < 3; i++)
    	{
    		buf.writeDouble(pos[i]);
    		buf.writeDouble(move[i]);
    	}
    	
    	buf.writeLong(PUUID.getLeastSignificantBits());
    	buf.writeLong(PUUID.getMostSignificantBits());
    }
    
    public static class PacketMessageHandler implements IMessageHandler<NetworkEntityMoving, IMessage> {
    	
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(NetworkEntityMoving message, MessageContext ctx) {
			World world = Minecraft.getMinecraft().world;
			if(message.PUUID == null)
			return null;
        	Entity player = getEntityByUUID(message.PUUID, world);
        	if(player != null)
        	{
        		switch(message.type)
        		{
        			case 0:
        				player.setPositionAndUpdate(message.pos[0], message.pos[1], message.pos[2]);
        				break;
        			case 1:
        				player.motionX = message.move[0];
        				player.motionY = message.move[1];
        				player.motionZ = message.move[2];
        				break;
        			case 2:
        				player.noClip = true;
        				break;
        			case 3:
        				player.motionY = message.move[1];
        				break;
        		}
        	}
            return null;
        }
    }
    
    @Nullable
    public static Entity getEntityByUUID(UUID uuid, World world)
    {
        for (int j2 = 0; j2 < world.loadedEntityList.size(); ++j2)
        {
            Entity entityplayer = world.loadedEntityList.get(j2);
            //System.out.println(uuid);
            try {
            if (uuid != null && uuid.equals(entityplayer.getUniqueID()))
            {
                return entityplayer;
            }
            }
            catch (Exception e)
            {
            	
            }
        }

        return null;
    }
}
