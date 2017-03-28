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

	// TODO: Safety between threads?
	public void removeRoom(String roomName) {
		// TODO: Implement
	}

	public boolean createRoom(String roomName) {
		// TODO: Needs to be thread safe to stop too many rooms being created.
		if(rooms.size() >= maxRooms) return false;
		rooms.add(new Room(roomName));
		return true;
	}

}
