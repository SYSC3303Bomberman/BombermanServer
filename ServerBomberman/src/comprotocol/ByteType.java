package comprotocol;

import java.nio.ByteBuffer;

enum ByteType {
	ACK(intToByteArray((byte)0x00)),
	NACK(intToByteArray((byte)0x01)),
	JOIN_REQUEST(intToByteArray((byte)0x02)),
	START_REQUEST(intToByteArray((byte)0x03)),
	DATA(intToByteArray((byte)0x04));
	
	private byte[] type;
	private int length;
	
	private ByteType(byte[] type){
		this.type = type;
		this.length = type.length;
	}

	public byte[] getType() {
		return type;
	}
	
	public int getLength() {
		return length;
	}
	
	private static byte[] intToByteArray(byte number){
		//return ByteBuffer.allocate(2).putInt(number).array();
		return ByteBuffer.allocate(1).put(number).array();
	}
}
