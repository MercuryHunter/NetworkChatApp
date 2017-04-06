package server;

import java.util.*;

class RoomHandler {

	static ArrayList<Room> rooms;
	static int maxRooms;

	public static final String DEFAULT_ROOM_NAME = "Default";

	public RoomHandler(int maxRooms) {
		// Initialise list of rooms
		this.maxRooms = maxRooms;
		rooms = new ArrayList<Room>(maxRooms);

		// Make default room
		rooms.add(new Room(DEFAULT_ROOM_NAME));

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
		StringBuilder list = new StringBuilder("------- Start of room list -------\n");
		for (Room room : rooms)
			list.append(room.getName() + "\n");
		list.append("-------- End of room list --------");
		return list.toString();
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
