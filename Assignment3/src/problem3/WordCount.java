package problem3;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaPairRDD;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import problem1.FileManager;
import scala.Tuple2;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;

public class WordCount {
	SparkConf sparkConf;
	JavaSparkContext sparkContext;

	public WordCount() {
		this.sparkConf = new SparkConf().setAppName("WordCount").setMaster("local");
		sparkContext = new JavaSparkContext(this.sparkConf);
	}

	public List<String> countWords(String fileName, String[] keywords) {
		// read file
		JavaRDD<String> file = sparkContext.textFile(fileName);
		// divide the words
		JavaRDD<String> words = file.flatMap(content -> Arrays.asList(content.split(" ")).iterator());
		// count the words
		JavaPairRDD<String, Integer> wordCount = words.mapToPair(word -> new Tuple2<String, Integer>(word, 1))
				.reduceByKey((x, y) -> x + y);

		Map<String, Integer> wordMap = wordCount.collectAsMap();
		List<String> wordCounts = new ArrayList<>();

		// get the count of keywords
		int count;
		for (String keyword : keywords) {
			if (wordMap.keySet().contains(keyword)) {
				count = wordMap.get(keyword);
			} else {
				count = 0;
			}
			String result = keyword + ": " + count;
			System.out.println(result);
			wordCounts.add(result);
		}

		return wordCounts;

	}

	public static void main(String[] args) {

		String path = "./cleanedTweets.txt";
		WordCount mapReducer = new WordCount();
		String[] keywords = new String[] { "Education", "Canada", "hot", "cold", "Flu", "Snow", "Indoor", "rain",
				"ice" };
		FileManager fileManager = new FileManager();
		// get the results and write to file
		List<String> twitterWordCounts = mapReducer.countWords("./cleanedTweets.txt", keywords);
		fileManager.saveTextsToFile("./twitterWordCount.txt", twitterWordCounts);

		List<String> reuterWordCounts = mapReducer.countWords("./reuterContents.txt", keywords);
		fileManager.saveTextsToFile("./reuterWordCount.txt", reuterWordCounts);
	}
}
