package client;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;

public class FileSender implements Runnable {

	String fileName, host;
	int port;

	public FileSender(String fileName, int port, String host) {
		this.fileName = fileName;
		this.port = port;
		this.host = host;
	}

	public void run() {
		try {
			System.out.println("Beginning file upload.");

			// Deal with the file
			File file = new File(fileName);
			int fileSize = (int)file.length();

			// Open data connection to server
			Socket dataSocket = new Socket(host, port);

			// Check if file should actually be sent
			if(!Sender.filesToBeSent.contains(file)) {
				System.err.println("Server requested a file that shouldn't be sent!");
				dataSocket.close();
				return;
			}

			// Open compressed stream output
			GZIPOutputStream output = new GZIPOutputStream(dataSocket.getOutputStream());

			// Read file into byte array using BufferedInputStream
			byte[] fileBytes  = new byte[fileSize];
			BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(file));
			fileInput.read(fileBytes, 0, fileSize);
			fileInput.close();

			System.out.printf("Sending %s to server. (%d bytes)\n", fileName, fileSize);
			
			// Write to output data stream
			output.write(fileBytes, 0, fileSize);

			// Close streams
			output.flush();
			output.close();
			dataSocket.close();

			System.out.printf("Sent %s\n", fileName);
		}
		catch (UnknownHostException e) {
			System.err.println("Unable to see host: " + host);
		}
		catch (FileNotFoundException e) {
			System.err.println("Error reading file from disk. File not found.");
		}
		catch (IOException e) {
			System.err.println("File could not be uploaded properly.");
			System.err.println("File may be incomplete.");
			System.err.println("For bug assistance, provide the following report:");
			e.printStackTrace();
			System.err.println("End of Report.");
		}
	}
}