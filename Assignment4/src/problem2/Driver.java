package problem2;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;
import java.util.Set;

import problem3.MongoDBConnection;

public class Driver {

	public static void main(String[] args) {

		// read positive and negative words
		FileManager fileManager = new FileManager();
		List<String> positiveWordsList = fileManager.readWordsFromFile("positive-words.txt");
		Set<String> positiveWords = new HashSet<>(positiveWordsList);

		List<String> negativeWordsList = fileManager.readWordsFromFile("negative-words.txt");
		Set<String> negativeWords = new HashSet<>(negativeWordsList);

		// read tweets
		MongoDBConnection mongoConnection = new MongoDBConnection("ProcessedDB", "ProcessedTwitter");
		List<String> keys = Arrays.asList(new String[] { "full_text" });
		List<String> tweets = mongoConnection.extractContentsFromDB(keys);

		Formatter formatter = new Formatter();
		formatter.format("%10s %30s %15s %15s\n", "Tweet", "Match", "Polarity", "Tweet content");

		TweetProcessor processor = new TweetProcessor();

		for (int i = 0; i < tweets.size(); i++) {
			String tweet = tweets.get(i);
			int score = 0;
			String polarity;
			List<String> matches = new ArrayList<>();
			Map<String, Integer> bows = processor.createBagOfWords(tweet);

			// count scores
			for (Entry<String, Integer> entry : bows.entrySet()) {
				String word = entry.getKey();
				int count = entry.getValue();
				if (positiveWords.contains(word)) {
					score += count;
					matches.add(word);
				} else if (negativeWords.contains(word)) {
					score -= count;
					matches.add(word);
				}
			}

			if (score > 0) {
				polarity = "positive";
			} else if (score < 0) {
				polarity = "negative";
			} else {
				polarity = "neutral";
			}

			// format matched words
			String matchLine = "";
			for (int j = 0; j < matches.size(); j++) {
				String match = matches.get(j);
				if (j == 0) {
					matchLine += match;
				} else {
					matchLine += ", " + match;
				}
			}

			formatter.format("%10d %30s %15s %15s\n", i, matchLine, polarity, tweet);

		}

		System.out.print(formatter);

	}

}
