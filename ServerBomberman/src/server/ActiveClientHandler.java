package server;

import gamemechanic.*;

import java.io.IOException;
import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.*;

import comprotocol.*;

public class ActiveClientHandler implements Runnable{
	
	ArrayList<Player> acceptedClients;
	BlockingQueue<DatagramPacket> receiverQueue;
	Board gameboard;

	public ActiveClientHandler(ArrayList<Player> acceptedClients,
			BlockingQueue<DatagramPacket> receiverQueue, 
			Board gameboard) {
		this.acceptedClients = acceptedClients;
		this.receiverQueue = receiverQueue;
		this.gameboard = gameboard;
	}
	
	public void handle() throws InterruptedException, IOException{
		DatagramSocket serverClientSocket;
		AckPacket ackPacket;
		BlockingQueue<Player> startGameQueue = new ArrayBlockingQueue<Player>(1);
		Thread threads[] = new Thread[ServerInfo.MAX_ACCEPTED_CLIENTS.getValue()];
		
		while(true){
			System.out.print("");
			while(gameboard.players.size() == ServerInfo.MAX_ACCEPTED_CLIENTS.getValue()){
				System.out.println();
				for(int i = 0; i<ServerInfo.MAX_ACCEPTED_CLIENTS.getValue(); i++){
					serverClientSocket = new DatagramSocket();

					ackPacket = new AckPacket(gameboard.players.get(i).getPlayerAddress());
					System.out.println(ackPacket.toDatagramPacket().getSocketAddress());
					serverClientSocket.send(ackPacket.toDatagramPacket());
				
					threads[i] = new Thread(new ClientThread(receiverQueue, startGameQueue,serverClientSocket, gameboard.players.get(i), gameboard),gameboard.players.get(i).getPlayerName());
					threads[i].start();
				
					System.out.println(gameboard.players.get(i).getPlayerName()+" joined.");
				}
				for(int i=1;i<=threads.length;i++){
					threads[i].join();
				}
				break;
			}
		}
	}

	@Override
	public void run() {
		try {
			handle();
		} catch (InterruptedException | IOException e) {
			e.printStackTrace();
		}
	}

}
