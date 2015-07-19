package Util;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.List;

public class SettingsHandler {

	File file;

	public SettingsHandler() {
		file = new File("Settings.syncnotes");
		try {
			file.createNewFile();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void set(String value) {
		BufferedWriter stream = null;
		try {
			stream = new BufferedWriter(new FileWriter(file, true));
			stream.write(value + "\n");
			stream.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

	public void update(String oldString, String newString) {
		try {
			BufferedReader file = new BufferedReader(new FileReader(this.file));
			PrintWriter writer = new PrintWriter(new File(this.file.getName() +".out"), "UTF-8");
			String line;

			while ((line = file.readLine()) != null)
			{
				line = line.replace(oldString, newString);
				writer.println(line);
			}
			file.close();
			writer.close();

			Files.write(Paths.get(this.file.getPath()), Files.readAllBytes(Paths.get(this.file.getPath() +".out")));
			new File(this.file.getName() +".out").delete();
		} catch (Exception e) {
			e.printStackTrace();
		}
	}

	public String getSetting(String key) {
		String line = null;

		try {
			List<String> fileLines = Files.readAllLines(file.toPath());
			for (String s: fileLines) {
				if (s.startsWith(key)) {
					line = s;
					break;
				}
			}
		} catch (IOException e) {
			e.printStackTrace();
		}

		return line.replace(key + ": ", "");
	}
}
