package server;

import java.net.*;
import java.io.*;

public class Connector implements Runnable {

	ServerSocket server;
	public static volatile boolean running = true;

	public Connector(int portNum) {
		// Start a socket for incoming connections

		try {
			server = new ServerSocket(portNum);
		}
		catch(IOException e) {
			System.out.println("Error listening on port: " + portNum);
			e.printStackTrace();
		}

		System.out.println("Connector service started");
	}

	public void run() {
		// Add clients as space is available in the server.
		try {
			while(running) {
				// TODO: Blocking when messages happen so they don't come in mid-way.

				// Check if there's space for the client, and add as necessary.
				// As this is the only thing adding clients, we can't have too many added.
				if(Server.clients.size() >= Server.maxClients) {
					Thread.sleep(3000);
					continue;
				}

				// Accept in a new client
				Socket clientSocket = server.accept();
				System.out.println("Client connected");

				// Create a ConnectedClient object for them and add them to the server.
				ConnectedClient newClient = new ConnectedClient(clientSocket);
				Server.clients.add(newClient);
				new Thread(newClient).start();
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
