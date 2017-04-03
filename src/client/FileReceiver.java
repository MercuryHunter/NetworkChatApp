package client;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;

class FileReceiver implements Runnable {

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
			Socket dataSocket = new Socket(host, port);
			System.out.println("Downloading file...");

			// Receive File
			GZIPInputStream input = new GZIPInputStream(dataSocket.getInputStream());
			BufferedOutputStream fileOutput = new BufferedOutputStream(new FileOutputStream("download/" + fileName), fileSize);

			byte[] buffer = new byte[1024];
			int count;
			while ((count = input.read(buffer)) > 0) {
				//debugPrintArrayOfBytes(buffer, "Read:");
				fileOutput.write(buffer, 0, count);
			}

			// Write into file and close streams
			input.close();
			fileOutput.flush();
			fileOutput.close();

			System.out.printf("File %s downloaded. (%d bytes)\n", fileName, fileSize);
		}
		catch (Exception e) {
			System.err.println("Something went wrong receiving from server.");
			e.printStackTrace();
		}
	}

	// DEBUG CODE
	private void debugPrintArrayOfBytes(byte[] bytes, String message) {
		System.out.println(message);
		for(int i = 0; i < bytes.length; i++) {
			System.out.print(bytes[i] + ";");
		}
		System.out.println();
	}

}