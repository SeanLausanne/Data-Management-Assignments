package problem3;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Formatter;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Map.Entry;

public class Driver {

	public static void main(String[] args) {
		// get articles
		MongoDBConnection mongoConnection = new MongoDBConnection("ReuterDB", "Articles");
		List<String> keys = Arrays.asList(new String[] {"text"});
		List<String> articles = mongoConnection.extractContentsFromDB(keys);
		int nArticles = articles.size();
		List<String> cleanArticles = new ArrayList<>();
		
		for (int i = 0; i < nArticles; i++) {
			String ariticle = articles.get(i);
			cleanArticles.add(TextProcessor.cleanText(ariticle));
		}
		
		Map<String, Integer> wordApperances = new HashMap<>();
		Map<Integer, Double> articleFrequencies = new HashMap<>();
		String[] keywords = new String[] {"Canada", "Moncton", "Toronto"};
		
 		for (String keyword : keywords) {
			wordApperances.put(keyword, 0);
		}
 		
 		// read number of articles in which words appear
		ArticleProcessor articleProcessor = new ArticleProcessor();
		WordCount wordCounter = new WordCount();
		
		for (int i = 0; i < cleanArticles.size(); i++) {
			String article = cleanArticles.get(i);
			
			// appearnce
			for (String keyword : wordApperances.keySet()) {	
				if (articleProcessor.checkWordApperanceInArticle(keyword, article)) {	
					int count = wordApperances.get(keyword);
					wordApperances.put(keyword, count + 1);
				}
			}
			
			// frequency
			Map<String, Integer> countResult = wordCounter.countWords(article, new String[] {"Canada"});
			int count = countResult.get("Canada");
			int nWords = article.split(" ").length;
			double frequency = Double.valueOf(count) / nWords;
			articleFrequencies.put(i, frequency);
		}
		
		Formatter formatter = new Formatter();
		formatter.format("%15s %30d\n", "Total Documents", articles.size());
		formatter.format("%15s %10s %10s %10s\n", "Search Query", "df", "N/df", "log10(N/df)");
		
		for (String keyword : wordApperances.keySet()) {
			int count = wordApperances.get(keyword);
			double log = Math.log10(Double.valueOf(count) / nArticles);
			formatter.format("%15s %10s %10s %10s\n", keyword, String.valueOf(count), articles.size() + "/" + count,  String.format("%.4f", log));
		}
		
		// sort the results based on frequency
		List<Entry<Integer, Double>> frequencySorted = new ArrayList<>(articleFrequencies.entrySet());
		frequencySorted.sort(Entry.comparingByValue());
		
		formatter.format("\n");
		formatter.format("%15s %50s\n", "Term", "Canada");
		formatter.format("%15s %10s %10s\n", "Canada appeared in " + wordApperances.get("Canada"), "Total Words", "Frequency");
		
		int maxIdx = 0;
		// outputs
		for (int i = frequencySorted.size() -1; i >= 0; i--) {
			Entry<Integer, Double> entry = frequencySorted.get(i);
			int articleIdx = entry.getKey();
			String article = cleanArticles.get(articleIdx);
			double frequency = entry.getValue();
			int nWords = article.split(" ").length;
			formatter.format("%30s %10s %10s\n", "Article " + articleIdx, String.valueOf(nWords), String.format("%.4f", frequency));
			
			if (i == frequencySorted.size() -1 ) {
				maxIdx = articleIdx;
			}
		}
		
		System.out.println(formatter);
		
		System.out.println("Article with the highes frequence");
		System.out.println(articles.get(maxIdx));
	}

}
