package testmod.seccult.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;
import testmod.seccult.magick.MagickCompiler;

public class NetworkPlayerTransMagickToClient implements IMessage {
	private int[][] MagickData;
	private int length;
	private int[][] Selector;
	private int[][] SelectorPower;
	private int[][] SelectorAttribute;
	
	private UUID PUUID;
	
	private long ulong;
	private long uleast;
	
    public NetworkPlayerTransMagickToClient() {}
    
    public NetworkPlayerTransMagickToClient(UUID p, int[][] list, int[][] list1, int[][] list2, int[][] list3, int amount)
    {
    	this.PUUID = p;
    	this.MagickData = list;
    	this.length = amount;
    	
    	this.Selector = list1;
    	this.SelectorPower = list2;
    	this.SelectorAttribute = list3;
    }

    @Override
    public void fromBytes(ByteBuf buf) {
    	length = buf.readInt();
    	int[][] MagickThing = new int[4][length];
    	
    	int[][] SelectorList = new int[length][];
		int[][] SelectorPowerList = new int[length][];
		int[][] SelectorAttributeList = new int[length][];
    	for(int i = 0; i < length; i ++)
    	{
        	for(int z = 0; z < 4; z ++)
        	{
        		MagickThing[z][i] = ByteBufUtils.readVarInt(buf, 2);
        	}
        	
        	int L = MagickThing[3][i];
    		SelectorList[i] = new int[L];
			SelectorPowerList[i] = new int[L];
			SelectorAttributeList[i] = new int[L];
        	for(int x = 0; x < L; x ++)
        	{
        		SelectorList[i][x] = ByteBufUtils.readVarInt(buf, 2);
        		SelectorPowerList[i][x] = ByteBufUtils.readVarInt(buf, 2);
        		SelectorAttributeList[i][x] = ByteBufUtils.readVarInt(buf, 2);
        	}
    	}
    	
    	MagickData = MagickThing;
    	Selector = SelectorList;
    	SelectorPower = SelectorPowerList;
    	SelectorAttribute = SelectorAttributeList;
    	
        uleast = buf.readLong();
        ulong = buf.readLong();
        PUUID = new UUID(ulong, uleast);
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	buf.writeInt(length);
    	for(int i = 0; i < length; i ++)
    	{
        	for(int z = 0; z < 4; z ++)
        	{
        		ByteBufUtils.writeVarInt(buf, MagickData[z][i], 2);
        	}
        	
        	for(int x = 0; x < MagickData[3][i]; x ++)
        	{
        		ByteBufUtils.writeVarInt(buf, Selector[i][x], 2);
        		ByteBufUtils.writeVarInt(buf, SelectorPower[i][x], 2);
        		ByteBufUtils.writeVarInt(buf, SelectorAttribute[i][x], 2);
        	}
    	}
    	
    	buf.writeLong(PUUID.getLeastSignificantBits());
    	buf.writeLong(PUUID.getMostSignificantBits());
    }
    
    public static class PacketMessageHandler implements IMessageHandler<NetworkPlayerTransMagickToClient, IMessage> {
        @Override
        public IMessage onMessage(NetworkPlayerTransMagickToClient message, MessageContext ctx) {
			World world = Minecraft.getMinecraft().world;
        	EntityPlayer player = world.getPlayerEntityByUUID(message.PUUID);
        	PlayerData data = PlayerDataHandler.get(player);
        	data.addMagick(MagickCompiler.compileMagick(message.MagickData, message.Selector, message.SelectorPower, message.SelectorAttribute, message.length, true, true));
            return null;
        }
    }
}
