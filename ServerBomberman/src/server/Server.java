package server;

import java.net.*;
import java.util.concurrent.*;

public class Server {

	public static void main(String[] args) throws InterruptedException, SocketException {
		// TODO Auto-generated method stub
		BlockingQueue<String> receiverQueue = new LinkedBlockingQueue<>();
		BlockingQueue<String> senderQueue = new LinkedBlockingQueue<>();
		BlockingQueue<DatagramPacket> clientQueue = new LinkedBlockingQueue<>();
		DatagramSocket serverSocket = new DatagramSocket(10000);
		
		Thread listener = new Thread(new ClientListener(clientQueue, receiverQueue, serverSocket),"ClientListener");
		Thread handler = new Thread(new ClientHandler(clientQueue, receiverQueue, senderQueue),"ClientHandler");
		
		listener.start();
		handler.start();
		
		listener.join();
		handler.join();
		
		serverSocket.close();
	}

}
