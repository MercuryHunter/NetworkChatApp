package client;

import java.net.*;
import java.io.*;

class Sender implements Runnable {

	PrintWriter send;
	String name;

	public Sender(PrintWriter send, String name) {
		this.send = send;
		this.name = name;
	}

	public void run() {
		try {
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			
			String toSend;
			// Send all data that comes in from stdIn
			while((toSend = stdIn.readLine()) != null) {
				// Don't append name if it's a command
				if(toSend.charAt(0) == '/') send.println(toSend);
				// Otherwise try to append name if it exists
				else {
					String sendString = name == "" ? toSend : name + ": " + toSend;
					send.println(sendString);
				}
			}
		}
		catch(Exception x) {
			x.printStackTrace();
		}
	}
}