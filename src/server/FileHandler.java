package server;

import java.net.*;
import java.io.*;
import java.util.*;

class FileHandler {

	private String roomName;
	private ArrayList<File> files;

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

	public void createDataConnection(int port) {

	}

	public void receiveFile(String fileName, int port) {

	}

	public void sendFile(String fileName, int port) {

	}

}