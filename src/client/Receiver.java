package client;

import java.net.*;
import java.io.*;

class Receiver implements Runnable {

	BufferedReader receive;
	String host;

	public Receiver(BufferedReader receive, String host) {
		this.receive = receive;
		this.host = host;
	}

	public void run() {
		try {
			String input;
			while((input = receive.readLine()) != null) {
				if(input.startsWith("/beginfilesend")) {
					// Check if this file was previously requested to be sent.
					String[] args = input.split(" ");

					String fileName = args[1];
					int port = Integer.parseInt(args[2]);

					System.out.println("Received send command for upload.");

					Thread senderThread = new Thread(new FileSender(fileName, port, host));
					System.out.println("Sending file: " + fileName);
					senderThread.start();
				}
				else if(input.startsWith("/beginfilereceive")) {
					String[] args = input.split(" ");

					String fileName = args[1];
					int port = Integer.parseInt(args[2]);
					int fileSize = Integer.parseInt(args[3]);

					System.out.println("Received file args for download.");

					Thread receiverThread = new Thread(new FileReceiver(fileName, port, fileSize, host));
					System.out.println("Receiving file: " + fileName);
					receiverThread.start();
				}
				else System.out.println(input);
			}
		}
		catch(Exception x) {
			x.printStackTrace();
		}
	}
}