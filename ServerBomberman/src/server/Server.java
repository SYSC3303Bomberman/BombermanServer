package server;

import java.net.*;
import java.util.concurrent.*;
import gamemechanic.*;

public class Server {

	public static void main(String[] args) throws InterruptedException, SocketException {
		// TODO Auto-generated method stub
		BlockingQueue<DatagramPacket> receiverQueue = new LinkedBlockingQueue<>();
		//BlockingQueue<String> senderQueue = new LinkedBlockingQueue<>();
		BlockingQueue<DatagramPacket> clientQueue = new LinkedBlockingQueue<>();
		DatagramSocket serverSocket = new DatagramSocket(10000);
		Board gameboard = new Board();
		
		gameboard.printBoard();
		//---Instead of clientQueue we should have a Player queue  
		Thread listener = new Thread(new ClientListener(clientQueue, receiverQueue, serverSocket, gameboard),"ClientListener");
		//Thread handler = new Thread(new ClientHandler(clientQueue, senderQueue, serverSocket),"ClientHandler");
		//Thread logger = new Thread(new TestLogger(),"TestLogger");
		Thread game = new Thread(new Game(receiverQueue, gameboard),"Game");
		
		listener.start();
		//handler.start();
		game.start();
		
		listener.join();
		//handler.join();
		game.join();
		
		serverSocket.close();
	}

}
