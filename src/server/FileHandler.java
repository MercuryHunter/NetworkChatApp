package server;

import java.net.*;
import java.io.*;
import java.util.*;
import java.util.zip.*;

class FileHandler {

	private String roomName;
	private ArrayList<File> files;
	private ServerSocket server;

	public FileHandler(String roomName) {
		this.roomName = roomName;

		// Initialise folder
		files = new ArrayList<File>();
		File folder = new File("files/" + roomName);
		if(!folder.exists()) folder.mkdirs();
		getFilesForFolder(folder);
	}

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
	public File getFile(String fileName) {
		for (File file : files) {
			if (file.getName().equals(fileName)) return file;
		}
		return null;
	}

	private Socket createDataConnection(int port) {
		Socket clientSocket = null;
		try {
			server = new ServerSocket(port);
			clientSocket = server.accept();
		}
		catch(IOException e) {
			System.err.println("Error creating socket to client on port: " + port);
			e.printStackTrace();
		}
		return clientSocket;
	}

	// Client receives file
	public synchronized void receiveFile(ConnectedClient client, String fileName, int port) {
		try {
			File file = getFile(fileName);
			int fileSize = (int)file.length();

			client.sendMessage(String.format("%s %s %d %d","/beginfilereceive", fileName, port, fileSize));

			Socket dataSocket = createDataConnection(port);
			GZIPOutputStream output = new GZIPOutputStream(dataSocket.getOutputStream());

			byte[] fileBytes  = new byte[fileSize];
			BufferedInputStream fileInput = new BufferedInputStream(new FileInputStream(file));
			fileInput.read(fileBytes, 0, fileSize); // Read file into byte array
			fileInput.close();

			System.out.printf("Sending %s to client %d. (%d bytes)\n", fileName, client.ID, fileSize);
			
			output.write(fileBytes, 0, fileSize);
			output.flush();
			output.close();

			System.out.printf("Sent %s\n", fileName);
		}
		catch(Exception e) {
			System.err.println("Something broke in sending to client.");
			e.printStackTrace();
		}
	}

	public synchronized void sendFile(ConnectedClient client, String fileName, int port) {
		client.sendMessage(String.format("%s %s %d","/beginfilesend", fileName, port));
		Socket dataSocket = createDataConnection(port);
	}

}