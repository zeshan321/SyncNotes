package Util;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.util.List;

public class FileHandler {

	File file;

	public FileHandler() {
		file = new File("ID.syncnotes");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void add(String ID) {
		FileWriter fw = null;

		try {
			fw = new FileWriter(file);
			fw.write(ID);
			fw.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public boolean registered() {
		boolean isRegistered = true;
		
		try {
			List<String> fileLines = Files.readAllLines(file.toPath());
			
			if (fileLines.isEmpty()) {
				isRegistered = false;
			}
			
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return isRegistered;
	}
}