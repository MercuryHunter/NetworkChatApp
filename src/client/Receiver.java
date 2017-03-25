package client;

import java.net.*;
import java.io.*;

class Receiver implements Runnable {

	BufferedReader receive;

	public Receiver(BufferedReader receive) {
		this.receive = receive;
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