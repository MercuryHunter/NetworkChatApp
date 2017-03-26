package server;

import java.util.*;

class RoomHandler {

	static ArrayList<Room> rooms;
	static int maxRooms;

	public RoomHandler(int maxRooms) {
		this.maxRooms = maxRooms;
		rooms = new ArrayList<Room>(maxRooms);

		// Make default room
		rooms.add(new Room("Default"));
	}

	public Room getDefaultRoom() { return rooms.get(0); }

	// TODO: Safety between threads?
	public void removeRoom(String roomName) {
		// TODO: Implement
	}

	public void createRoom(String roomName) {
		// TODO: Implement
	}

}
