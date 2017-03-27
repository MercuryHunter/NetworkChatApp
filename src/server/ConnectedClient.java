package server;

import java.net.*;
import java.io.*;

class ConnectedClient implements Runnable {

	public int ID;
	private BufferedReader receive;
	private PrintWriter send;
	private Room room;

	public ConnectedClient(Socket socket) {
		ID = 1; // TODO: Changing IDs

		// Join the default room
		room = Server.roomHandler.getDefaultRoom().join(this);

		try{
			receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
			send = new PrintWriter(socket.getOutputStream(), true);
		}
		catch(Exception e) {
			System.err.println("Error creating thing. This is temporary.");
		}
	}

	public void run() {
		try {
			String input;
			while((input = receive.readLine()) != null) {
				if(input.charAt(0) == '/') {
					String[] commandArgs = input.substring(1, input.length()).split(" ");
					handleCommand(commandArgs);
				}
				else room.sendMessage(input, this);
			}
		}
		catch(Exception x) {
			x.printStackTrace();
		}
	}

	private void handleCommand(String[] args) {
		// TODO: Command Handling
		String baseCommand = args[0].toLowerCase();
		switch(baseCommand) {
			case "list":
				sendMessage("Command still being developed");
				break;
			case "help":
				sendMessage("Commands include list, help, send, changechannel, disconnect, download, createchannel\nExperiment with them!");
				break;
			case "send":
				sendMessage("Command still being developed");
				break;
			case "changechannel":
				sendMessage("Command still being developed");
				break;
			case "disconnect":
				sendMessage("Command still being developed");
				break;
			case "download":
				sendMessage("Command still being developed");
				break;
			case "createchannel":
				sendMessage("Command still being developed");
				break;
			default:
				sendMessage("Command not understood");
		}
	}

	public void sendMessage(String message) {
		send.println(message);
	}

}
