package server;

import gamemechanic.Board;
import gamemechanic.Player;
import comprotocol.*;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ClientThread implements Runnable{
	
	BlockingQueue<DatagramPacket> receiverQueue;
	BlockingQueue<Player> startGameQueue;
	DatagramSocket serverSocket;
	Player player;
	Board gameboard;

	public ClientThread(BlockingQueue<DatagramPacket> receiverQueue, 
			BlockingQueue<Player> startGameQueue, 
			DatagramSocket serverSocket, 
			Player player, 
			Board gameboard) {
		
		this.receiverQueue = receiverQueue;
		this.startGameQueue = startGameQueue;
		this.serverSocket = serverSocket;
		this.player = player;
		this.gameboard = gameboard;
	}

	@Override
	public void run() {
		char receivedInput;
		try {
			DatagramPacket ack = new AckPacket(player.getPlayerAddress()).toDatagramPacket();
			DatagramPacket clientPacket = new DataPacket().toDatagramPacket();
			//System.out.println(ack.getData()[0]);
			serverSocket.send(ack);
			System.out.println("###Before Start Game.");
			while(startGameQueue.isEmpty()){
				serverSocket.receive(clientPacket);
				//receivedInput = new String(clientPacket.getData()).toCharArray()[0];
				if(clientPacket.getData()[0] == 0x02){
					startGameQueue.put(player);
					break;
				}
			}
			serverSocket.send(new AckPacket().toDatagramPacket());
			System.out.println("Game Started.");
			gameboard.printBoard();
			while(true){
				serverSocket.receive(clientPacket);
				receivedInput = new String(clientPacket.getData()).toCharArray()[0];
				System.out.println(receivedInput);
				if(receivedInput == 'u' || receivedInput == 'd' ||
						receivedInput == 'l' || receivedInput == 'r' || receivedInput == 'b'){
					//System.out.println("Received: "+new String(clientPacket.getData())+" from "+clientPacket.getSocketAddress());
					receiverQueue.put(clientPacket);
				}
			}
		} catch (IOException | InterruptedException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		
		
	}

}
