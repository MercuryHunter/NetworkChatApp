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
			byte[] file = new byte[fileSize];

			GZIPInputStream input = new GZIPInputStream(dataSocket.getInputStream());
			BufferedOutputStream bufferedOutputStream = new BufferedOutputStream(new FileOutputStream("download/" + fileName));
			
			int bytesRead = input.read(file, 0, fileSize);
			int current = bytesRead;
			do {
				bytesRead = input.read(file, current, (fileSize - current));
				if(bytesRead >= 0) current += bytesRead;
				System.out.println(current);
			} while(bytesRead > -1);

			// Write into file
			bufferedOutputStream.write(file, 0, fileSize);
			bufferedOutputStream.flush();

			System.out.printf("File %s download (%d bytes)\n", fileName, current);
		}
		catch (Exception e) {
			System.err.println("Something went wrong receiving the file");
			e.printStackTrace();
		}
	}

}