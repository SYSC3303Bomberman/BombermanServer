package server;

import java.net.*;
import java.util.concurrent.*;

public class ClientHandler implements Runnable{
	
	BlockingQueue<DatagramPacket> clientQueue;
	BlockingQueue<String> senderQueue;
	DatagramSocket serverSocket;

	public ClientHandler(BlockingQueue<DatagramPacket> clientQueue,
			BlockingQueue<String> senderQueue, 
			DatagramSocket serverSocket) {
		this.clientQueue = clientQueue;
		this.senderQueue = senderQueue;
		this.serverSocket = serverSocket;
	}

	@Override
	public void run() {
		byte sentData[] = new byte[1];
		DatagramPacket packetToSend, clientPacket[] = new DatagramPacket[2];
		
		for(int i=0;i<clientQueue.size();i++){
			try {
				clientPacket[i] = clientQueue.take();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
		while(true){
			
		}
	}

}
