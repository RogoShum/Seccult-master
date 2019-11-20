package testmod.seccult.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.api.PlayerSpellReleaseTool;
import testmod.seccult.api.PlayerSpellReleaseTool.PlayerSpellTool;

public class CNetworkTransFloat implements IMessage {
	private float[] flo;
	
	private int type;
	private int size;
	
	public CNetworkTransFloat() {}

	public CNetworkTransFloat(float[] flo, int type, int size) {
		this.flo = flo;
		this.type = type;
		this.size = size;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) {
		this.size = buf.readInt();
		this.flo = new float[size];
		for(int i = 0; i < this.size; ++i)
			this.flo[i] = buf.readFloat();
		this.type = buf.readInt();
		
	}

	@Override
	public void toBytes(ByteBuf buf) {
		buf.writeInt(size);
		for(int i = 0; i < flo.length; ++i)
			buf.writeFloat(flo[i]);
		buf.writeInt(type);
		
	}

	public static class PacketMessageHandler implements IMessageHandler<CNetworkTransFloat, IMessage> {
		
		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(CNetworkTransFloat message, MessageContext ctx) {
			Minecraft mc = Minecraft.getMinecraft();
        	EntityPlayer player = mc.player;
			switch(message.type)
			{
				case 1:
					PlayerSpellTool tool = PlayerSpellReleaseTool.get(player);
					tool.setSpellColor(message.flo);
			}
			
			return null;
		}
	}
}
