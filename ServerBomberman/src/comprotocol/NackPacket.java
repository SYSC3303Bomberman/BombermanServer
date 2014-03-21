package comprotocol;

import java.net.SocketAddress;

public class NackPacket extends Packet {

	public NackPacket(ByteType byteType) {
		super(ByteType.NACK);
	}
	
	public NackPacket(SocketAddress address) {
		super(ByteType.NACK, address);
	}

}
