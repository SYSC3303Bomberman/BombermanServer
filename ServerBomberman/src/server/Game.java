package server;

import gamemechanic.*;

import java.net.DatagramPacket;
import java.util.concurrent.BlockingQueue;

public class Game implements Runnable{
	
	BlockingQueue<DatagramPacket> receiverQueue;
	Board gameboard;

	public Game(BlockingQueue<DatagramPacket> receiverQueue, Board gameboard) {
		this.receiverQueue = receiverQueue;
		this.gameboard = gameboard;
	}

	@Override
	public void run() {
		DatagramPacket movementPacket;
		Player player;
		// TODO Auto-generated method stub
		while(true){
			try {
				movementPacket = receiverQueue.take();
				player = gameboard.hasPlayer(movementPacket.getSocketAddress());
				System.out.print("Player("+player.getPlayerAddress()+") was at "
						+player.getX()+","+player.getY()+" and now at ");
				player.move(new String(movementPacket.getData()));
				System.out.println(player.getX()+","+player.getY());
				gameboard.printBoard();
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
