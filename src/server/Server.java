package server;

import java.net.*;
import java.io.*;
import java.util.*;

public class Server {

	// The clients array will hold the clients up until maxSize
	// and then reject them.
	static ArrayList<ConnectedClient> clients;
	static int maxClients;
	
	private Thread connectorThread;
	static RoomHandler roomHandler;

	private void startServer(int portNum, int maxClients, int maxRooms) {
		this.maxClients = maxClients;
		clients = new ArrayList<ConnectedClient>(maxClients);

		// Create default room and room handler
		roomHandler = new RoomHandler(maxRooms);

		// Begin connecting clients
		connectorThread = new Thread(new Connector(portNum));
		connectorThread.start();
	}

	public void disconnect(ConnectedClient client) {
		System.out.println("Client " + client.ID + " left server.");
		clients.remove(client);
	}

	public static void main(String[] args) {
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
