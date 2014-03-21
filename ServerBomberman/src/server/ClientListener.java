package server;

import gamemechanic.*;
import comprotocol.*;

import java.io.IOException;
import java.net.*;
import java.util.*;

public class ClientListener implements Runnable {
	
	ArrayList<Player> acceptedClients;
	ArrayList<DatagramPacket> rejectedClients;
	DatagramSocket serverSocket;
	Board gameboard;

	public ClientListener(ArrayList<Player> acceptedClients,
			ArrayList<DatagramPacket> rejectedClients,
			DatagramSocket serverSocket, 
			Board gameboard) {
		this.acceptedClients = acceptedClients;
		this.rejectedClients = rejectedClients;
		this.serverSocket = serverSocket;
		this.gameboard = gameboard;
	}
	/*
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
	}*/
	
	@Override
	public void run() {
		try {
			AckPacket clientAckPacket = new AckPacket();
			String clientsName = "player";
			
			while(true) {
				DatagramPacket clientAckDPacket = clientAckPacket.toDatagramPacket();
				serverSocket.receive(clientAckDPacket);
				System.out.println("Received from "+clientAckDPacket.getSocketAddress());
				
				if(acceptedClients.size() < ServerInfo.MAX_ACCEPTED_CLIENTS.getValue()){
					//System.out.println("###Accepted");
					gameboard.addPlayer(clientAckDPacket.getSocketAddress(),clientsName+acceptedClients.size());
					acceptedClients.add(gameboard.players.get(acceptedClients.size()));
				}else{
					System.out.println("REJECTED");
					rejectedClients.add(clientAckDPacket);
				}
			}
		} catch (IOException e) {	
			e.printStackTrace();
		}
	}

}
