import java.net.*;
import java.io.*;

public class Connector implements Runnable {

	ServerSocket server;

	public Connector(int portNum) {
		try {
			// Start a socket for incoming connections
			server = new ServerSocket(portNum);
		}
		catch(IOException e) {
			System.out.println("Error listening on port: " + portNum);
			e.printStackTrace();
		}

		System.out.println("Connector service started");
	}

	public void run() {
		try {
			while(true) {
				if(Server.clients.size() >= Server.maxSize) {
					Thread.sleep(3000);
					continue;
				}
				Socket clientSocket = server.accept();

			}
		}
		catch (IOException e) {
			System.err.println("Error accepting client socket. Something is borked.");
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			System.err.println("Error, couldn't seem to sleep...");
			e.printStackTrace();
		}
	}

}