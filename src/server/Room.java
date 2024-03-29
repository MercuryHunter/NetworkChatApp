package server;

import java.util.*;

public class Room {

	public static int IDCounter = 0;
	public int ID;
	// TODO: Synchronize for messages

	// A room name and file handler for sending/receiving files
	private String name;
	public FileHandler fileHandler;
	private int port;

	// Clients connected only to this room.
	private ArrayList<ConnectedClient> clients;

	public Room(String name) {
		synchronized (Room.class) {
			ID = IDCounter;
			IDCounter++;
		}

		port = 15000 + ID;

		this.name = name;
		clients = new ArrayList<ConnectedClient>();
		fileHandler = new FileHandler(name, port);
	}

	public String getName() { return name; }
	public int getNumUsers() { return clients.size(); }

	public synchronized Room join(ConnectedClient client) {
		clients.add(client);
		System.out.println("Client " + client.ID + " joined room: " + name);
		sendMessage("Another user joined the room!", client);
		return this;
	}

	public synchronized void disconnect(ConnectedClient client) {
		System.out.println("Client " + client.ID + " left room: " + name);
		sendMessage("Another user left the room!", client);
		clients.remove(client);
	}

	public synchronized void sendMessage(String message, ConnectedClient sender) {
		for(ConnectedClient client : clients){
			if (client != sender) client.sendMessage(message);
		}
	}

}