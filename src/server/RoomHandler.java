package server;

import java.util.*;

class RoomHandler {

	static ArrayList<Room> rooms;
	static int maxRooms;

	public RoomHandler(int maxRooms) {
		// Initialise list of rooms
		this.maxRooms = maxRooms;
		rooms = new ArrayList<Room>(maxRooms);

		// Make default room
		rooms.add(new Room("Default"));

		// TODO: Discover previously created rooms
	}

	public Room getDefaultRoom() { return rooms.get(0); }

	// TODO: Oh no, another linear scan, why no map?
	public Room getRoom(String name) {
		for (Room room : rooms) {
			if(room.getName().equals(name)) return room;
		}
		return null;
	}

	// Return a formatted string of the list of rooms
	public String getRoomList() {
		// TODO: Switch to String builder thing, can't remember what it's called
		String list = "";
		for (Room room : rooms) {
			list = list + room.getName() + "\n";
		}
		list = list.substring(0, list.length() - 1);
		return list;
	}

	// Remove room if it's not default
	public synchronized void removeRoom(String roomName) {
		// TODO: Implement
	}

	// Add a room if there is space to add
	public synchronized boolean createRoom(String roomName) {
		if(rooms.size() >= maxRooms) return false;
		rooms.add(new Room(roomName));
		return true;
	}

}
