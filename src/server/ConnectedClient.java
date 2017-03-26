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
			// TODO: Parse input, deal with commands and send messages
			String input;
			while((input = receive.readLine()) != null) {
				System.out.println(input);
			}
		}
		catch(Exception x) {
			x.printStackTrace();
		}
	}

	public void sendMessage(String message) {
		send.println(message);
	}

}
