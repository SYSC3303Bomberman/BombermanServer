package server;

import java.net.*;
import java.util.ArrayList;
import java.util.concurrent.*;

import gamemechanic.*;

public class Server {

	public static void main(String[] args) throws InterruptedException, SocketException {
		Board gameboard = new Board();
		
		BlockingQueue<DatagramPacket> receiverQueue = new LinkedBlockingQueue<>();
		BlockingQueue<Board> senderQueue = new LinkedBlockingQueue<Board>(); 
		ArrayList<Player> acceptedClients = new ArrayList<Player>();
		ArrayList<DatagramPacket> rejectedClients = new ArrayList<DatagramPacket>();
		DatagramSocket serverSocket = new DatagramSocket(ServerInfo.INITIAL_SERVER_SOCKET.getValue());
		
		gameboard.printBoard();
		  
		Thread listener = new Thread(new ClientListener(acceptedClients, rejectedClients, serverSocket, gameboard),"ClientListener");
		Thread handler = new Thread(new ActiveClientHandler(acceptedClients, receiverQueue, gameboard),"ClientHandler");
		Thread updater = new Thread(new ClientStateUpdater(receiverQueue, senderQueue, gameboard),"Updater");
		Thread sender = new Thread(new Sender(senderQueue, serverSocket));
		
		listener.start();
		handler.start();
		updater.start();
		sender.start();
		
		listener.join();
		handler.join();
		updater.join();
		sender.join();
		
		serverSocket.close();
	}

}
