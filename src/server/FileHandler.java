package server;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;

class FileHandler {

	// TODO: Deal with multiple spaces in file name

	private String roomName;
	private ArrayList<File> files;
	private ServerSocket server;
	private int port;

	public FileHandler(String roomName, int port) {
		this.roomName = roomName;
		this.port = port;

		// Initialise folder - store the names of files in room.
		files = new ArrayList<File>();
		File folder = new File(getFolderLocation());
		if(!folder.exists()) folder.mkdirs();
		getFilesForFolder(folder);

		// Server socket for file handling
		try {
			server = new ServerSocket(port);
		}
		catch(IOException e) {
			System.out.println("Room: " + roomName + " encountered an error creating a server socket for file transfer.");
			e.printStackTrace();
		}
	}

	// Get appropriate folder location for room
	private String getFolderLocation() {
		return "files/" + roomName;
	}

	// Scan files in a folder and add to files list
	private void getFilesForFolder(File folder) {
		for (File file : folder.listFiles()) {
			if (!file.isDirectory()) files.add(file);
		}
	}

	// Return a formatted string of the files stored in the room
	public String getFileList() {
		// TODO: String builder thing

		if(files.size() <= 0)
			return "No files were found in this room.";

		String list = "------- Start of file list -------\n";

		for(File file : files)
			list = list + file.getName() + "\n";

		list = list + "-------- End of file list --------";

		return list;
	}

	// TODO: Linear scan, oh no why, use a map?
	public boolean hasFile(String fileName) {
		for (File file : files) {
			if (file.getName().equals(fileName)) return true;
		}
		return false;
	}

	// Get a file based on fileName
	public File getFile(String fileName) {
		for (File file : files) {
			if (file.getName().equals(fileName)) return file;
		}
		return null;
	}

	// Initiate a data connection with a client.
	private Socket createDataConnection(int port) {
		Socket clientSocket = null;
		try {
			clientSocket = server.accept();
		}
		catch(IOException e) {
			System.err.println("Error creating socket to client on port: " + port);
			e.printStackTrace();
		}
		return clientSocket;
	}

	// This is from the perspective of a client *receiving* a file
	public synchronized void receiveFile(ConnectedClient client, String fileName) {
		try {
			System.out.println("Beginning file send to client.");

			// Deal with the file
			File file = getFile(fileName);
			int fileSize = (int)file.length();

			// Instruct client to open data connection and make use of it
			client.sendMessage(String.format("%s %d %d %s","/beginfilereceive", port, fileSize, fileName));

			// Open data connection to client
			Socket dataSocket = createDataConnection(port);

			// Open compressed stream output
			GZIPOutputStream output = new GZIPOutputStream(dataSocket.getOutputStream());

			// Read file into byte array using BufferedInputStream
			byte[] fileBytes  = new byte[fileSize];
			BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(file));
			fileInput.read(fileBytes, 0, fileSize);
			fileInput.close();

			System.out.printf("Sending %s to client %d. (%d bytes)\n", fileName, client.ID, fileSize);
			
			// Write to output data stream
			output.write(fileBytes, 0, fileSize);

			// Close streams
			output.flush();
			output.close();
			dataSocket.close();

			System.out.printf("Sent \"%s\"\n", fileName);
		}
		catch(Exception e) {
			System.err.println("Something broke in sending to client.");
			e.printStackTrace();
		}
	}

	// This is from the perspective of a client *sending* a file
	public synchronized void sendFile(ConnectedClient client, String fileName) {
		// TODO: Update file list.
		try {
			System.out.println("Beginning file receive from client.");

			// Instruct client to open data connection and make use of it
			client.sendMessage(String.format("%s %d %s","/beginfilesend", port, fileName));
			Socket dataSocket = createDataConnection(port);

			// Open compressed stream and stream to file output
			File file = new File(fileName);
			GZIPInputStream input = new GZIPInputStream(dataSocket.getInputStream());
			BufferedOutputStream fileOutput = new BufferedOutputStream(new FileOutputStream(getFolderLocation() + "/" + file.getName()));//, fileSize);
			
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
			dataSocket.close();

			System.out.printf("File \"%s\" downloaded to %s.\n", file.getName(), roomName); // TODO: File size displayed? 
		}
		catch(Exception e) {
			System.err.println("Something broke in receiving from client.");
			e.printStackTrace();
		}
	}

}