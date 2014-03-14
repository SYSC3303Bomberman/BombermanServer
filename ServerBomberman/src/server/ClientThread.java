package server;

import gamemechanic.Board;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ClientThread implements Runnable{
	
	BlockingQueue<DatagramPacket> receiverQueue;
	DatagramSocket serverSocket;
	Board gameboard;

	public ClientThread(BlockingQueue<DatagramPacket> receiverQueue, 
			DatagramSocket serverSocket, 
			Board gameboard) {
		
		this.receiverQueue = receiverQueue;
		this.serverSocket = serverSocket;
		this.gameboard = gameboard;
	}

	@Override
	public void run() {
		byte command[] = new byte[1];
		while(true){
			DatagramPacket clientPacket = new DatagramPacket(command,command.length);
			try {
				serverSocket.receive(clientPacket);
				System.out.println("Received: "+new String(clientPacket.getData())+" from "+clientPacket.getSocketAddress());
				receiverQueue.put(clientPacket);
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
