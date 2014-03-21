package server;

public enum ServerInfo{

	INITIAL_SERVER_SOCKET(10000),
	MAX_ACCEPTED_CLIENTS(2);
	
	private int value;
	
	private ServerInfo(int value){
		this.setValue(value);
	}

	private void setValue(int value) {
		this.value = value;
	}
	
	public int getValue() {
		return value;
	}
}
