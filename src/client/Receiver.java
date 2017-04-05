package client;

import java.net.*;
import java.io.*;
import java.util.Arrays;

public class Receiver implements Runnable {

	BufferedReader receive;
	String host; 
	// Host is required for file transfers, which are started on receiving the appropriate message.

	public Receiver(BufferedReader receive, String host) {
		this.receive = receive;
		this.host = host;
	}

	public void run() {
		// Continuously receive input and deal with it as necessary.
		try {
			String input;
			while((input = receive.readLine()) != null) {
				handleInput(input);
			}
		}
		catch(Exception x) {
			x.printStackTrace();
		}
	}

	private void handleInput(String input) {
		if(input.startsWith("/beginfilesend"))
			handleFileSend(input);
		else if(input.startsWith("/beginfilereceive"))
			handleFileReceive(input);
		else System.out.println(input);
	}

	private void handleFileSend(String input) {
		// TODO: Check if this file was previously requested to be sent.
		// Parse parameters
		String[] args = input.split(" ");

		int port = Integer.parseInt(args[1]);
		String fileName = String.join(" ", Arrays.copyOfRange(args, 2, args.length));

		// Begin thread to send file.
		Thread senderThread = new Thread(new FileSender(fileName, port, host));
		senderThread.start();
	}

	private void handleFileReceive(String input) {
		// Parse parameters
		String[] args = input.split(" ");
		
		int port = Integer.parseInt(args[1]);
		int fileSize = Integer.parseInt(args[2]);
		String fileName = String.join(" ", Arrays.copyOfRange(args, 3, args.length));

		// Begin thread to receive file.
		Thread receiverThread = new Thread(new FileReceiver(fileName, port, fileSize, host));
		receiverThread.start();
	}
}