package problem1;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.List;

public class FileManager {
	
	public void saveTextsToFile(String path, List<String> texts) {
		File file = new File(path);
		if (file.exists()) {
			file.delete();
		}
		try {
			file.createNewFile();
			FileWriter fileWritter = new FileWriter(file.getName(), true);
			BufferedWriter bufferWritter = new BufferedWriter(fileWritter);
			for (int i = 0; i < texts.size(); i++) {
				String text = texts.get(i);
				// System.out.println(i + ": " + text);
				bufferWritter.write(text + "\n");
			}
			bufferWritter.close();
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
