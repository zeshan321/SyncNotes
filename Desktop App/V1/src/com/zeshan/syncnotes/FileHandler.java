package com.zeshan.syncnotes;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;

public class FileHandler {

	File file;

	public FileHandler() {
		file = new File("ID.sn");
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
		BufferedReader br;
		boolean isRegistered = false;
		try {
			br = new BufferedReader(new FileReader(file));
			String line = br.readLine();
			if (line != null) {
				isRegistered = false;
			}
			br.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
		
		return isRegistered;
	}
}
