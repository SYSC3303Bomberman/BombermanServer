package comprotocol;

import java.net.SocketAddress;

public class JoinRequestPacket extends Packet {

	public JoinRequestPacket(SocketAddress address) {
		super(ByteType.JOIN_REQUEST, address);
	}

}
