package testmod.seccult.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.ByteBufUtils;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;

public class NetworkPlayerMagickData implements IMessage {
	private int[] MagickData;
	private float ControlAbility;
	private float ManaStrengh;
	
	private long ulong;
	private long uleast;
	private int lengh;
	
	private UUID PUUID;
	
    public NetworkPlayerMagickData() {}
    
    public NetworkPlayerMagickData(int[] list, UUID p, float c, float s)
    {
    	this.PUUID = p;
    	this.MagickData = list;
    	this.lengh = list.length;
    	this.ControlAbility = c;
    	this.ManaStrengh = s;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
    	
        uleast = buf.readLong();
        ulong = buf.readLong();
        lengh = buf.readInt();
        PUUID = new UUID(ulong, uleast);
        int[] list = new int[lengh];
        for(int i = 0; i < lengh; i++) 
        list[i] = ByteBufUtils.readVarInt(buf, 2);
        
        MagickData = list;
        ControlAbility = buf.readFloat();
        ManaStrengh = buf.readFloat();
        
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	buf.writeLong(PUUID.getLeastSignificantBits());
    	buf.writeLong(PUUID.getMostSignificantBits());
    	buf.writeInt(lengh);
    	for(int i = 0; i < MagickData.length; i ++)
    	{
    		ByteBufUtils.writeVarInt(buf, MagickData[i], 2);
    	}
    	buf.writeFloat(ControlAbility);
    	buf.writeFloat(ManaStrengh);
    }
    
    public static class PacketMessageHandler implements IMessageHandler<NetworkPlayerMagickData, IMessage> {
        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(NetworkPlayerMagickData message, MessageContext ctx) {
        	Minecraft mc = Minecraft.getMinecraft();
			World world = mc.world;
			
        	EntityPlayer player = world.getPlayerEntityByUUID(message.PUUID);
        	PlayerData data = PlayerDataHandler.get(player);
        	data.setMagickData(message.MagickData);
        	data.setControl(message.ControlAbility);
        	data.setStrengh(message.ManaStrengh);
            return null;
        }
}
}
