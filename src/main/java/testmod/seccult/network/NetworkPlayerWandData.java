package testmod.seccult.network;

import java.util.UUID;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;

public class NetworkPlayerWandData implements IMessage {
	private int color2;
	private int color3;
	private int color4;
	
	private int wandstyle;
	
    public NetworkPlayerWandData() {}
    
    public NetworkPlayerWandData(int color, int color2, int color3, int wand)
    {
    	this.color2 = color;
    	this.color3 = color2;
    	this.color4 = color3;
    	this.wandstyle = wand;
    }
    
    @Override
    public void fromBytes(ByteBuf buf) {
        color2 = buf.readInt();
        color3 = buf.readInt();
        color4 = buf.readInt();
        wandstyle = buf.readInt();
    }

    @Override
    public void toBytes(ByteBuf buf) {
    	buf.writeInt(color2);
    	buf.writeInt(color3);
    	buf.writeInt(color4);
    	buf.writeInt(wandstyle);
    }
    
    public static class PacketMessageHandler implements IMessageHandler<NetworkPlayerWandData, IMessage> {

        @Override
        @SideOnly(Side.CLIENT)
        public IMessage onMessage(NetworkPlayerWandData message, MessageContext ctx) {
        	Minecraft mc = Minecraft.getMinecraft();
        	int color2 = message.color2;
        	int color3 = message.color3;
        	int color4 = message.color4;
        	
        	int wand = message.wandstyle;

        	PlayerData data = PlayerDataHandler.get(mc.player);
        	data.setColor(color2, color3, color4, wand);
            return null;
        }
    }
}
