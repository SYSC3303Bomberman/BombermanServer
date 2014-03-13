package server;

import java.net.*;
import java.util.concurrent.*;

public class ClientHandler implements Runnable{
	
	BlockingQueue<DatagramPacket> clientQueue;
	BlockingQueue<String> receiverQueue, senderQueue;

	public ClientHandler(BlockingQueue<DatagramPacket> clientQueue,
			BlockingQueue<String> receiverQueue,
			BlockingQueue<String> senderQueue) {
		this.clientQueue = clientQueue;
		this.receiverQueue = receiverQueue;
		this.senderQueue = senderQueue;
	}

	@Override
	public void run() {
		// TODO Auto-generated method stub
		
	}

}
