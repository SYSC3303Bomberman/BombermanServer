package server;

import java.net.*;
import java.util.concurrent.*;

public class Server {

	public static void main(String[] args) throws InterruptedException {
		// TODO Auto-generated method stub
		BlockingQueue<String> receiverQueue = new LinkedBlockingQueue<>();
		BlockingQueue<String> senderQueue = new LinkedBlockingQueue<>();
		BlockingQueue<DatagramSocket> clientSocket = new LinkedBlockingQueue<>();
		
		Thread listener = new Thread(new ClientListener(clientSocket, receiverQueue, senderQueue),"ClientListener");
		Thread handler = new Thread(new ClientHandler(clientSocket, receiverQueue, senderQueue),"ClientHandler");
		
		listener.start();
		handler.start();
		
		listener.join();
		handler.start();
		
	}

}
