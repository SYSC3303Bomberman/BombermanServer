package comprotocol;

public class Byte {
	
	private byte[] b;
	private int length;
	
	public Byte(byte b[]){
		setB(b);
		setLength(b.length);
	}
	
	public byte[] getB() {
		return b;
	}

	public void setB(byte[] b) {
		this.b = b;
	}
	
	public int getLength() {
		return length;
	}

	public void setLength(int length) {
		this.length = length;
	}

	/*public void ackByte(){
		byte ack[] = {0};
		this.setB(ack);
		this.setLength(ack.length);
	}
	
	public void nackByte(){
		byte nack[] = {1};
		this.setB(nack);
		this.setLength(nack.length);
	}
	
	public void dataByte(byte[] b){
		this.setB(b);
		this.setLength(b.length);
	}*/
}
