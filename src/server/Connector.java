package server;

import java.net.*;
import java.io.*;

public class Connector implements Runnable {

	ServerSocket server;
	Server mainServer;

	public Connector(int portNum) {
		try {
			// Start a socket for incoming connections
			server = new ServerSocket(portNum);
			mainServer = new Server();
		}
		catch(IOException e) {
			System.out.println("Error listening on port: " + portNum);
			e.printStackTrace();
		}

		System.out.println("Connector service started");
	}

	public void run() {
		try {
			while(true) {
				if(mainServer.clients.size() >= mainServer.maxClients) {
					Thread.sleep(3000);
					continue;
				}
				Socket clientSocket = server.accept();
				System.out.println("Client connected");

			}
		}
		catch (IOException e) {
			System.err.println("Error accepting client socket. Something is borked.");
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			System.err.println("Error, couldn't seem to sleep...");
			e.printStackTrace();
		}
	}

}
