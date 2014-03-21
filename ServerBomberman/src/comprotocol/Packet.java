package comprotocol;

import java.net.*;

class Packet{

	ByteType byteType;
	SocketAddress address;
	
	public Packet(ByteType byteType){
		this.byteType = byteType;
	}
	
	public Packet(ByteType byteType, SocketAddress address){
		this.byteType = byteType;
		this.address = address;
	}
	
	public DatagramPacket toDatagramPacket() throws SocketException{
		DatagramPacket packet;
		if(this.address!=null){
			packet = new DatagramPacket(this.byteType.getType(),this.byteType.getLength(),this.address);
		}else{
			packet = new DatagramPacket(this.byteType.getType(),this.byteType.getLength());
		}
		return packet;
	}
}
