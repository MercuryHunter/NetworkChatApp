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

	public void createRoom(String roomName) {
		// TODO: Implement
	}

}
