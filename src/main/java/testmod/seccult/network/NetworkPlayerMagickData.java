package testmod.seccult.network;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.nbt.NBTTagInt;
import net.minecraft.nbt.NBTTagList;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerMagickDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;

public class NetworkPlayerMagickData implements IMessage {
	private static NBTTagList MagickData = new NBTTagList();
	
	private long ulong;
	private long uleast;
	private int lengh;
	
	private UUID PUUID;
	
    public NetworkPlayerMagickData() {}
    
    public NetworkPlayerMagickData(NBTTagList list, UUID p)
    {
    	this.PUUID = p;
    	this.MagickData = list;
    	this.lengh = list.tagCount();
    	//MagickData = convertNBTToList(list);
    }
    
    public static List<Integer> convertNBTToList(NBTTagList nbtlist)
    {
    	List<Integer> list = null;
    	for(int i = 0; i < nbtlist.tagCount(); i++)
    	{
    		list.add(nbtlist.getIntAt(i));
    	}
    	return list;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
    	
        uleast = buf.readLong();
        ulong = buf.readLong();
        lengh = buf.readInt();
        PUUID = new UUID(ulong, uleast);
        //MagickData.add(ByteBufUtils.readVarInt(buf, 50));
        NBTTagList list = new NBTTagList();
        for(int i = 0; i < lengh; i++) 
        list.appendTag(new NBTTagInt(ByteBufUtils.readVarInt(buf, 2)));
        
        MagickData = list;
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	buf.writeLong(PUUID.getLeastSignificantBits());
    	buf.writeLong(PUUID.getMostSignificantBits());
    	buf.writeInt(lengh);
    	for(int i = 0; i < MagickData.tagCount(); i ++)
    	{
    		ByteBufUtils.writeVarInt(buf, MagickData.getIntAt(i), 2);
    	}
    }
    
    public static class PacketMessageHandler implements IMessageHandler<NetworkPlayerMagickData, IMessage> {
        @Override
        public IMessage onMessage(NetworkPlayerMagickData message, MessageContext ctx) {
        	Minecraft mc = Minecraft.getMinecraft();
			World world = mc.world;
			
        	EntityPlayer player = world.getPlayerEntityByUUID(message.PUUID);
        	PlayerData data = PlayerDataHandler.get(player);
        	data.setMagickData(MagickData);
        	
            return null;
        }
}
}
