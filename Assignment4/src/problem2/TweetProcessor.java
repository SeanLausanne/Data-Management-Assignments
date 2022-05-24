package problem2;

import java.util.HashMap;
import java.util.Map;

public class TweetProcessor {

	public Map<String, Integer> createBagOfWords(String text) {

		String[] words = text.split(" ");
		Map<String, Integer> wordCount = new HashMap<>();

		for (String word : words) {
			int count = wordCount.getOrDefault(word, 0);
			wordCount.put(word, count + 1);
		}

		return wordCount;
	}
}
