package server;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server {

	// The clients array will hold the clients up until maxClients and then reject them.
	static ArrayList<ConnectedClient> clients;
	static int maxClients;
	
	// The main organisers the server starts - a connector service and room handler.
	private Thread connectorThread;
	static RoomHandler roomHandler;

	private void startServer(int portNum, int maxClients, int maxRooms) {
		// Initialise client arraylist
		this.maxClients = maxClients;
		clients = new ArrayList<ConnectedClient>(maxClients);

		// Create room handler
		roomHandler = new RoomHandler(maxRooms);

		// Begin connecting clients
		connectorThread = new Thread(new Connector(portNum));
		connectorThread.start();
	}

	// Disconnect a connected client
	public static void disconnect(ConnectedClient client) {
		if(clients.contains(client)) {
			System.out.println("Client " + client.ID + " left server.");
			clients.remove(client);
		}
	}

	public static void main(String[] args) {
		// Deal with command line arguments and begin server

		if(args.length != 3) {
			System.err.println("Usage: java Server <port> <max_clients> <max_rooms>");
			System.exit(1);
		}

		int portNum = Integer.parseInt(args[0]);
		int maxClients = Integer.parseInt(args[1]);
		int maxRooms = Integer.parseInt(args[2]);
		(new Server()).startServer(portNum, maxClients, maxRooms);
	}

}
