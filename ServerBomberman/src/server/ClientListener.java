package server;

import java.net.*;
import java.util.concurrent.*;

public class ClientListener implements Runnable {
	
	BlockingQueue<DatagramSocket> clientSocket;
	BlockingQueue<String> receiverQueue, senderQueue;

	public ClientListener(BlockingQueue<DatagramSocket> clientSocket,
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
