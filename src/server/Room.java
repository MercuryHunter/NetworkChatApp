package server;

import java.util.*;

class Room {

	private int ID; // TODO: Check necessity
	private String name;

	private ArrayList<ConnectedClient> clients;

	public Room(String name) {
		this.name = name;
		clients = new ArrayList<ConnectedClient>();
	}

	public String getName() { return name; }

	public Room join(ConnectedClient client) {
		clients.add(client);
		System.out.println("Client " + client.ID + " joined room: " + name);
		return this;
	}

	public void disconnect(ConnectedClient client) {
		clients.remove(client);
	}

	public void sendMessage(String message, ConnectedClient sender) {
		for(ConnectedClient client : clients){
			if (client != sender) client.sendMessage(message);
		}
	}

}