package problem2;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class FileManager {

	public List<String> readWordsFromFile(String fileName) {

		List<String> words = new ArrayList<String>();

		try {
			File file = new File(fileName);
			FileReader fileReader = new FileReader(file);
			BufferedReader bufferReader = new BufferedReader(fileReader);
			String line;

			while ((line = bufferReader.readLine()) != null) {
				if (line.startsWith(",") || line.startsWith(" ")) {
					continue;
				}
				String word = line.strip();
				words.add(word);
			}

			fileReader.close();
		} catch (IOException e) {
			e.printStackTrace();
		}

		return words;
	}
}
