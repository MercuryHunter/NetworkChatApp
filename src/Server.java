import java.net.*;
import java.io.*;
import java.util.*;

public class Server {

	// The clients array will hold the clients up until maxSize
	// and then reject them.
	static ArrayList<ConnectedClient> clients;
	static int maxSize;
	static MessageHandler messageHandler;

	private void startServer(int portNum, int serverSize) {
		maxSize = serverSize;
		clients = new ArrayList<ConnectedClient>(serverSize);

		// Begin connecting clients
		new Thread(new Connector(portNum)).start();
	}

	public static void main(String[] args) {
		// Args: ServerSize
		if(args.length != 2) {
			System.err.println("Usage: java Server <port> <size>");
			System.exit(-1);
		}

		int portNum = Integer.parseInt(args[0]);
		int serverSize = Integer.parseInt(args[1]);
		(new Server()).startServer(portNum, serverSize);
	}

}



class MessageHandler implements Runnable {

	public MessageHandler() {

	}

	public void run() {

	}

}

class ConnectedClient implements Runnable {

	public ConnectedClient() {

	}

	public void run() {

	}

}