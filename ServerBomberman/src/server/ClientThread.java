package server;

import java.io.*;
import java.net.*;
import java.util.concurrent.*;

public class ClientThread implements Runnable{
	
	BlockingQueue<String> receiverQueue;
	DatagramSocket serverSocket;

	public ClientThread(BlockingQueue<String> receiverQueue, DatagramSocket serverSocket) {
		
		this.receiverQueue = receiverQueue;
		this.serverSocket = serverSocket;
	}

	@Override
	public void run() {
		byte command[] = new byte[1];
		while(true){
			DatagramPacket clientPacket = new DatagramPacket(command,command.length);
			try {
				serverSocket.receive(clientPacket);
				receiverQueue.put(new String(clientPacket.getData()));
			} catch (IOException | InterruptedException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
		}
		
	}

}
