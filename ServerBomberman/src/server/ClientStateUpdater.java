package server;

import gamemechanic.*;
import comprotocol.*;

import java.io.UnsupportedEncodingException;
import java.net.*;
import java.util.concurrent.*;

public class ClientStateUpdater implements Runnable{
	
	BlockingQueue<DatagramPacket> receiverQueue;
	BlockingQueue<Board> senderQueue;
	Board gameboard;

	public ClientStateUpdater(BlockingQueue<DatagramPacket> receiverQueue, BlockingQueue<Board> senderQueue, Board gameboard) {
		this.receiverQueue = receiverQueue;
		this.senderQueue = senderQueue;
		this.gameboard = gameboard;
	}

	@Override
	public void run() {
		DatagramPacket movementPacket;
		Player player;
		String commandFromClient;

		while(true){
			try {
				movementPacket = receiverQueue.take();
				player = gameboard.hasPlayer(movementPacket.getSocketAddress());
				commandFromClient = new String(movementPacket.getData());
				
				System.out.println(commandFromClient+" "+commandFromClient.length());
				
				if(commandFromClient.equals("d") || commandFromClient.equals("u") ||
						commandFromClient.equals("l") || commandFromClient.equals("r")){
					player.move(commandFromClient);
				}else if(commandFromClient.equals("b")){
					player.placeBomb();
				}else{
					//gameboard.printBoard();
					senderQueue.put(gameboard);
					continue;
				}
				//System.out.print("Player("+player.getPlayerAddress()+") was at "
				//		+player.getX()+","+player.getY()+" and now at ");
				//player.move(new String(movementPacket.getData()));
				//System.out.println(player.getX()+","+player.getY());
				//gameboard.printBoard();
				senderQueue.put(gameboard);
			} catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
		
	}

}
