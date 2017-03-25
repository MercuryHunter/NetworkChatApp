package server;

import java.net.*;
import java.io.*;

class ConnectedClient implements Runnable {

	public int ID;
	private BufferedReader receive;

	public ConnectedClient(Socket socket) {
		try{
			receive = new BufferedReader(new InputStreamReader(socket.getInputStream()));
		}
		catch(Exception e) {
			System.err.println("Error creating thing. This is temporary.");
		}
	}

	public void run() {
		try {
			String input;
			while((input = receive.readLine()) != null) {
				System.out.println(input);
			}
		}
		catch(Exception x) {
			x.printStackTrace();
		}
	}

}
