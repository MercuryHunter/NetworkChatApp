package client;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;

class FileSender implements Runnable {

	String fileName, host;
	int port;

	public FileSender(String fileName, int port, String host) {
		this.fileName = fileName;
		this.port = port;
		this.host = host;
	}

	public void run() {
		try {
			File file = new File(fileName);
			int fileSize = (int)file.length();

			Socket dataSocket = new Socket(host, port);
			System.out.println("Uploading file...");

			GZIPOutputStream output = new GZIPOutputStream(dataSocket.getOutputStream());

			byte[] fileBytes  = new byte[fileSize];
			BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(file));
			fileInput.read(fileBytes, 0, fileSize); // Read file into byte array
			fileInput.close();

			System.out.printf("Sending %s to server. (%d bytes)\n", fileName, fileSize);
			
			output.write(fileBytes, 0, fileSize);
			output.flush();
			output.close();

			System.out.printf("Sent %s\n", fileName);
		}
		catch(Exception e) {
			System.err.println("Something broke in sending to server.");
			e.printStackTrace();
		}
	}

}