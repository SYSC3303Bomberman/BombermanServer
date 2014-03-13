package server;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.*;

public class ClientListener implements Runnable {
	
	BlockingQueue<DatagramPacket> clientQueue;
	BlockingQueue<String> receiverQueue;
	DatagramSocket serverSocket;

	public ClientListener(BlockingQueue<DatagramPacket> clientQueue,
			BlockingQueue<String> receiverQueue,
			DatagramSocket serverSocket) {
		this.clientQueue = clientQueue;
		this.receiverQueue = receiverQueue;
		this.serverSocket = serverSocket;
	}
	
	private void clientInitialization() throws SocketException, IOException, InterruptedException{
		byte revData[] = new byte[1];
		DatagramPacket receivedPacket = new DatagramPacket(revData,revData.length);
		Thread threads[] = new Thread[2];
		
		do {
			serverSocket.receive(receivedPacket);
			clientQueue.put(receivedPacket);

			threads[clientQueue.size()-1] = new Thread(new ClientThread(receiverQueue, serverSocket),"Client"+clientQueue.size());
			threads[clientQueue.size()-1].start();
			System.out.println("Client"+clientQueue.size());
		} while(clientQueue.size()<2);
		
		for(int i=1;i<=threads.length;i++){
			threads[i].join();
		}
	}

	@Override
	public void run() {
		try {
			clientInitialization();
		} catch (IOException | InterruptedException e) {	
			e.printStackTrace();
		}
	}

}
