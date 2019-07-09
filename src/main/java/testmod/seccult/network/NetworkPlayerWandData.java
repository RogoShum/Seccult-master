package testmod.seccult.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;

public class NetworkPlayerWandData implements IMessage {
	private int color2;
	private int color3;
	private int color4;
	
	private int wandstyle;
	
	private long ulong;
	private long uleast;
	
	private UUID PUUID;
	
    public NetworkPlayerWandData() {}
    
    public NetworkPlayerWandData(int color, int color2, int color3, UUID p, int wand)
    {
    	this.color2 = color;
    	this.color3 = color2;
    	this.color4 = color3;
    	this.wandstyle = wand;
    	this.PUUID = p;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        color2 = buf.readInt();
        color3 = buf.readInt();
        color4 = buf.readInt();
        wandstyle = buf.readInt();
        uleast = buf.readLong();
        ulong = buf.readLong();
        PUUID = new UUID(ulong, uleast);
        System.out.println("=============");
        System.out.println("=============");
        System.out.println(ulong);
        System.out.println(uleast);
        System.out.println(PUUID);
        System.out.println("=============");
        System.out.println("=============");
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	buf.writeInt(color2);
    	buf.writeInt(color3);
    	buf.writeInt(color4);
    	buf.writeInt(wandstyle);
    	buf.writeLong(PUUID.getLeastSignificantBits());
    	buf.writeLong(PUUID.getMostSignificantBits());
    }
    
    public static class PacketMessageHandler implements IMessageHandler<NetworkPlayerWandData, IMessage> {

        @Override
        public IMessage onMessage(NetworkPlayerWandData message, MessageContext ctx) {
        	Minecraft mc = Minecraft.getMinecraft();
			World world = mc.world;
        	int color2 = message.color2;
        	int color3 = message.color3;
        	int color4 = message.color4;
        	
        	int wand = message.wandstyle;
        	if(message.PUUID != null)
        	{
        	EntityPlayer player = world.getPlayerEntityByUUID(message.PUUID);
        	if(player != null) {
        	PlayerData data = PlayerDataHandler.get(player);
        	data.setColor(color2, color3, color4, wand);
        	}
        	}
            return null;
        	
        }
}
}
