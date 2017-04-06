package server;

import java.net.*;
import java.io.*;
import java.util.Arrays;

class ConnectedClient implements Runnable {

	public static int IDCounter = 0;
	public int ID;

	private BufferedReader receive;
	private PrintWriter send;

	private Room room;

	public ConnectedClient(Socket socket) {
		// Generate ID for client
		synchronized (ConnectedClient.class) {
			ID = IDCounter;
			IDCounter++;
		}

		// Join the default room
		room = Server.roomHandler.getDefaultRoom().join(this);

		// Create access points for input and output
		try{
			receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			send = new PrintWriter(socket.getOutputStream(), true);
		}
		catch(Exception e) {
			System.err.println("Error creating thing. This is temporary.");
		}

		sendMessage(roomMessage());
	}

	public void run() {
		// Handle user input
		try {
			String input;
			while((input = receive.readLine()) != null) {
				// If it's a command, handle it
				if(input.charAt(0) == '/') {
					String[] commandArgs = input.substring(1, input.length()).split(" ");
					handleCommand(commandArgs);
				}
				// Otherwise send the message to the room
				else room.sendMessage(input, this);
			}
		}
		catch(Exception x) {
			x.printStackTrace();
		}
	}

	// Deal with the commands users can send, and provide them with output
	private void handleCommand(String[] args) {
		// TODO: List users command.
		// TODO: Current room command.
		String baseCommand = args[0].toLowerCase();
		switch(baseCommand) {
			case "list":
				list();
				break;
			case "help":
				help();
				break;
			case "listfiles":
				listFiles();
				break;
			case "send":
				sendFile(args);
				break;
			case "download":
				downloadFile(args);
				break;
			case "createroom":
				createRoom(args);
				break;
			case "changeroom":
				changeRoom(args);
				break;
			case "disconnect":
				disconnect();
				break;
			default:
				sendMessage("Command not understood");
		}
	}

	private void list() {
		String list = Server.roomHandler.getRoomList();
		sendMessage(list);
	}

	private void help() {
		String help = "Commands include list, help, send <file>, download <file>,\ncreateroom <name>, changeroom <name>, disconnect\nExperiment with them!";
		sendMessage(help);
	}

	private void listFiles() {
		String list = room.fileHandler.getFileList();
		sendMessage(list);
	}

	private void sendFile(String[] args) {
		String filename = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
		if(args.equals("")) {
			sendMessage("Please provide a file name and no other arguments to the function.");
			return;
		}

		room.fileHandler.sendFile(this, filename);
	}

	private void downloadFile(String[] args) {
		String filename = String.join(" ", Arrays.copyOfRange(args, 1, args.length));
		if(args.equals("")) {
			sendMessage("Please provide a file name and no other arguments to the function.");
			return;
		}

		if(room.fileHandler.hasFile(filename))
			room.fileHandler.receiveFile(this, filename);
		else sendMessage("Sorry, we could not find the file you were looking for.");
	}

	private void createRoom(String[] args) {
		if(args.length != 2) {
			sendMessage("Please provide a room name and no other arguments to the function.");
			return;
		}
		// TODO: Check name for validity
		boolean roomCreated = Server.roomHandler.createRoom(args[1]);
		if(roomCreated) sendMessage("Room created successfully!");
		else sendMessage("Could not create the room.");
	}

	private void changeRoom(String[] args) {
		if(args.length != 2) {
			sendMessage("Please provide a room name and no other arguments to the function.");
			return;
		}

		Room newRoom = Server.roomHandler.getRoom(args[1]);
		if(newRoom != null) {
			room.disconnect(this);
			room = newRoom.join(this);

			sendMessage(roomMessage());
		}
		else sendMessage("Room not found!");
	}

	private void disconnect() {
		sendMessage("/disconnect");
		room.disconnect(this);
		Server.disconnect(this);
	}

	public void sendMessage(String message) {
		send.println(message);
	}

	private String roomMessage() {
		String userString;
		if(room.getNumUsers() <= 1) userString = "user";
		else userString = "users";
	
		String output = String.format("You have been connected to room: %s (%d %s)", room.getName(), room.getNumUsers(), userString);
		return output;
	}

}
