package testmod.seccult.network;

import io.netty.buffer.ByteBuf;
import net.minecraft.client.Minecraft;
import net.minecraftforge.fml.common.FMLCommonHandler;
import net.minecraftforge.fml.common.network.simpleimpl.IMessage;
import net.minecraftforge.fml.common.network.simpleimpl.IMessageHandler;
import net.minecraftforge.fml.common.network.simpleimpl.MessageContext;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import testmod.seccult.ModReclection;
import testmod.seccult.api.PlayerDataHandler;
import testmod.seccult.api.PlayerDataHandler.PlayerData;

public class NetworPlayerMagickData implements IMessage {
	private float vaule[] = new float[2];

	public NetworPlayerMagickData() {}
	
	public NetworPlayerMagickData(float[] vaule) {
		this.vaule = vaule;
	}
	
	@Override
	public void fromBytes(ByteBuf buf) 
	{
		float type = buf.readFloat();
		float amount = buf.readFloat();
		this.vaule[0] = type;
		this.vaule[1] = amount;
	}

	@Override
	public void toBytes(ByteBuf buf) 
	{
		buf.writeFloat(vaule[0]);
		buf.writeFloat(vaule[1]);
	}
	public static class PacketMessageHandler implements IMessageHandler<NetworPlayerMagickData, IMessage> {

		@Override
		@SideOnly(Side.CLIENT)
		public IMessage onMessage(NetworPlayerMagickData message, MessageContext ctx) {
			int type = (int) message.vaule[0];
			if(Minecraft.getMinecraft().player != null)
			{
			try {
				
				PlayerData data = PlayerDataHandler.get(Minecraft.getMinecraft().player);
			switch(type)
			{
				case 0:

					ModReclection.PlayerData_MaxManaValue(data, message.vaule[1]);
					break;
				case 1:
					ModReclection.PlayerData_ManaValue(data, message.vaule[1]);
					break;
			}
			} catch (NoSuchFieldException | SecurityException | IllegalArgumentException| IllegalAccessException e) {
				
				System.out.println("error at type: " + type);
			}
			}
			return null;
		}
		
	}
}
