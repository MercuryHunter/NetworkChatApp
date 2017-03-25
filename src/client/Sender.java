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
			while((toSend = stdIn.readLine()) != null) {
				String sendString = name == "" ? toSend : name + ": " + toSend;
				send.println(sendString);
				//System.out.println(sendString);
			}
		}
		catch(Exception x) {
			x.printStackTrace();
		}
	}
}