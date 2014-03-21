package comprotocol;

import java.net.SocketAddress;

public class DataPacket extends Packet {
	
	public DataPacket() {
		super(ByteType.DATA);
	}

	public DataPacket(SocketAddress address) {
		super(ByteType.DATA, address);
	}

}
