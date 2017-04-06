package client;

import java.net.*;
import java.io.*;
import java.util.*;

public class Sender implements Runnable {

	// Store the files that should be sent, to ensure safety when server requests
	public static ArrayList<File> filesToBeSent;

	PrintWriter send;
	String name;

	public static volatile boolean running = true;

	public Sender(PrintWriter send, String name) {
		this.send = send;
		this.name = name;

		filesToBeSent = new ArrayList<File>();
	}

	public void run() {
		
		try {
			// A BufferedReader for user input
			BufferedReader stdIn = new BufferedReader(new InputStreamReader(System.in));
			
			String toSend;
			while(running && (toSend = stdIn.readLine()) != null) {
				handleInput(toSend);
			}

			stdIn.close();
			send.close();
		}
		catch(Exception e) {
			e.printStackTrace();
		}
	}

	private void handleInput(String input) {
		// Command Handling - don't append name.
		if(input.charAt(0) == '/') {
			handleCommand(input);
		}
		// Otherwise append name to input and send
		else {
			String sendString = name + ": " + input;
			send.println(sendString);
		}
	}

	private void handleCommand (String input) {
		input = input.replace("\\ ", " ");
		if(input.startsWith("/send") || input.startsWith("/download")) {
			// Ensure only a filename is provided
			String args = input.substring(input.indexOf(" ") + 1);
			if(args.equals("")) {
				System.out.println("Please provide a file name and no other arguments to the function.");
				return;
			}

			// Check if file exists before trying to send it
			if(input.startsWith("/send")) {
				File file = new File(args);
				if(!file.exists()) {
					System.out.println("That file doesn't exist!");
					return;
				}
				filesToBeSent.add(file);
			}
		}
		if(input.startsWith("/disconnect")) running = false;

		// Send message if checks pass
		send.println(input);
	}
}