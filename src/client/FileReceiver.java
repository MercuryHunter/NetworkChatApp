package client;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;

public class FileReceiver implements Runnable {

	String fileName, host;
	int port, fileSize;

	public FileReceiver(String fileName, int port, int fileSize, String host) {
		this.fileName = fileName;
		this.port = port;
		this.fileSize = fileSize;
		this.host = host;
	}

	public void run() {
		try {
			// Open data connection to server
			System.out.println("Beginning file download.");
			Socket dataSocket = new Socket(host, port);
			
			// Open compressed stream and stream to file output
			GZIPInputStream input = new GZIPInputStream(dataSocket.getInputStream());
			BufferedOutputStream fileOutput = new BufferedOutputStream(new FileOutputStream("download/" + fileName), fileSize);

			// Read data into buffer and write to file
			byte[] buffer = new byte[1024];
			int count;
			while ((count = input.read(buffer)) > 0) {
				fileOutput.write(buffer, 0, count);
			}

			// Close streams
			input.close();
			fileOutput.flush();
			fileOutput.close();

			System.out.printf("File %s downloaded. (%d bytes)\n", fileName, fileSize);
		}
		catch (UnknownHostException e) {
			System.err.println("Unable to see host: " + host);
		}
		catch (FileNotFoundException e) {
			System.err.println("Error writing file to disk.");
		}
		catch (IOException e) {
			System.err.println("File could not be downloaded properly.");
			System.err.println("File may be incomplete.");
			System.err.println("For bug assistance, provide the following report:");
			e.printStackTrace();
			System.err.println("End of Report.");
		}
	}

	// Debug code for file transfer
	private void debugPrintArrayOfBytes(byte[] bytes, String message) {
		System.out.println(message);
		for(int i = 0; i < bytes.length; i++) {
			System.out.print(bytes[i] + ";");
		}
		System.out.println();
	}

}