package comprotocol;

import java.net.SocketAddress;

public class StartRequestPacket extends Packet {

	public StartRequestPacket(SocketAddress address) {
		super(ByteType.START_REQUEST, address);
	}

}
