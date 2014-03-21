package comprotocol;

import java.net.SocketAddress;

public class AckPacket extends Packet {
	
	public AckPacket() {
		super(ByteType.ACK);
	}

	public AckPacket(SocketAddress address) {
		super(ByteType.ACK, address);
	}

}
