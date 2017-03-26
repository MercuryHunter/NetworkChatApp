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

	public Room join(ConnectedClient client) {
		clients.add(client);
		System.out.println("Client: " + client.ID + " joined room: " + name);
		return this;
	}

	public void sendMessage(String message, ConnectedClient sender) {
		for(ConnectedClient client : clients){
			if (client != sender) client.sendMessage(message);
		}
	}

}