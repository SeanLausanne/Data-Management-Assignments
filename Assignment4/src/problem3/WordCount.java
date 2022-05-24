package problem3;

import java.util.HashMap;
import java.util.Map;

public class WordCount {
	// count word appearances in text
	public Map<String, Integer> countWords(String text, String[] keywords) {

		Map<String, Integer> results = new HashMap<>();
		String[] words = text.split(" ");

		for (String keyword : keywords) {
			results.put(keyword, 0);
		}

		for (String word : words) {
			for (String keyword : keywords) {
				if (keyword.equals(word)) {
					int count = results.get(keyword);
					results.put(keyword, count + 1);
				}
			}
		}

		return results;

	}

}
