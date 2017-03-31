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
					String[] args = input.split(" ");

				}
				else if(input.startsWith("/beginfilereceive")) {
					String[] args = input.split(" ");

					String fileName = args[1];
					int port = Integer.parseInt(args[2]);
					int fileSize = Integer.parseInt(args[3]);

					System.out.println("Received file args for download");

					Thread receiverThread = new Thread(new FileReceiver(fileName, port, fileSize, host));
					receiverThread.start();

					System.out.println("Receiving file: " + fileName);
				}
				else System.out.println(input);
			}
		}
		catch(Exception x) {
			x.printStackTrace();
		}
	}
}