package server;

import java.net.DatagramSocket;
import java.util.concurrent.BlockingQueue;

public class ClientHandler implements Runnable{
	
	BlockingQueue<DatagramSocket> clientSocket;
	BlockingQueue<String> receiverQueue, senderQueue;

	public ClientHandler(BlockingQueue<DatagramSocket> clientSocket,
			BlockingQueue<String> receiverQueue,
			BlockingQueue<String> senderQueue) {
		this.clientSocket = clientSocket;
		this.receiverQueue = receiverQueue;
		this.senderQueue = senderQueue;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
