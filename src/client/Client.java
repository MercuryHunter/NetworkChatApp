package client;

import java.net.*;
import java.io.*;

public class Client {

	private String name;
	private Sender sender;
	private Receiver receiver;

	private void startClient(String host, int portNum, String client_name) {
		this.name = client_name;

		try {
			Socket mySocket = new Socket(host, portNum);
			System.out.println("Connected");
			
			// Start threads for sending and receiving
			sender = new Sender(new PrintWriter(mySocket.getOutputStream(), true), client_name);
			receiver = new Receiver(new BufferedReader(new InputStreamReader(mySocket.getInputStream())), host);
			
			Thread sendThread = new Thread(sender);
			Thread receiverThread = new Thread(receiver);

			sendThread.start();
			receiverThread.start();
		}
		catch(UnknownHostException e) {
			System.err.println("Unable to see host: " + host);
			e.printStackTrace();
			System.exit(1);
		}
		catch(IOException e) {
			System.err.println("Can't connect input/output to host: " + host);
			e.printStackTrace();
			System.exit(1);
		}
	}

	public static void main(String[] args) {
		if(args.length != 3) {
			System.err.println("Usage: java Client <host> <port> <client_name>");
			System.exit(-1);
		}

		String host = args[0];
		int portNum = Integer.parseInt(args[1]);
		String client_name = args[2];
		(new Client()).startClient(host, portNum, client_name);
	}

}
