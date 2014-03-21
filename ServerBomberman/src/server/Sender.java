package server;

import gamemechanic.Board;

import java.io.IOException;
import java.net.DatagramPacket;
import java.net.DatagramSocket;
import java.util.concurrent.BlockingQueue;

public class Sender implements Runnable{
	
	BlockingQueue<Board> senderQueue;
	DatagramSocket serverSocket;

	public Sender(BlockingQueue<Board> senderQueue, DatagramSocket serverSocket) {
		this.senderQueue = senderQueue;
		this.serverSocket = serverSocket;
	}

	@Override
	public void run() {
		Board gameboard;
		// TODO Auto-generated method stub
		while(true){
			try {
				System.out.println(senderQueue);
				gameboard = senderQueue.take();
				byte[] gameStateString = gameboard.toString().getBytes();
				DatagramPacket boardState1 = new DatagramPacket(gameStateString,gameStateString.length,gameboard.players.get(0).getPlayerAddress());
				DatagramPacket boardState2 = new DatagramPacket(gameStateString,gameStateString.length,gameboard.players.get(1).getPlayerAddress());
				
				System.out.println(boardState1.getSocketAddress()+" "+boardState2.getSocketAddress());
				
				serverSocket.send(boardState1);
				serverSocket.send(boardState2);
				
				
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
	}

}
