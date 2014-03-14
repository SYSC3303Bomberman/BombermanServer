package server;

import gamemechanic.*;

import java.io.IOException;
import java.net.*;
import java.util.concurrent.*;

public class ClientListener implements Runnable {
	
	BlockingQueue<DatagramPacket> clientQueue;
	BlockingQueue<DatagramPacket> receiverQueue;
	DatagramSocket serverSocket;
	Board gameboard;

	public ClientListener(BlockingQueue<DatagramPacket> clientQueue,
			BlockingQueue<DatagramPacket> receiverQueue,
			DatagramSocket serverSocket, 
			Board gameboard) {
		this.clientQueue = clientQueue;
		this.receiverQueue = receiverQueue;
		this.serverSocket = serverSocket;
		this.gameboard = gameboard;
	}
	
	private void clientInitialization() throws SocketException, IOException, InterruptedException{
		byte revData[] = new byte[1];
		DatagramPacket receivedConnectAckPacket = new DatagramPacket(revData,revData.length), serverAckPacket;
		DatagramSocket serverClientSocket;
		Thread threads[] = new Thread[2];
		
		do {
			serverSocket.receive(receivedConnectAckPacket);
			clientQueue.put(receivedConnectAckPacket);
			
			serverClientSocket = new DatagramSocket();
			serverAckPacket = new DatagramPacket(revData,revData.length,receivedConnectAckPacket.getSocketAddress());
			serverClientSocket.send(serverAckPacket);
			
			gameboard.addPlayer(receivedConnectAckPacket.getSocketAddress());
			
			threads[clientQueue.size()-1] = new Thread(new ClientThread(receiverQueue, serverClientSocket, gameboard),"Client"+clientQueue.size());
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
