package problem1;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import twitter4j.Twitter;

public class Driver {
	
	public void downloadFromTwitterAndSave() {
		// connect to twitter
		TwitterConnector twitterConnector = new TwitterConnector();
		Twitter twitter = twitterConnector.getTwitterInstance();
		
		// download tweets
		TwitterDownloader twitterDownloader = new TwitterDownloader(twitter);
		String[] keywords = new String[] {"weather", "hockey", "canada", "temperature", "education"};
		
		for (String keyword : keywords) {
			twitterDownloader.downloadTwitterWithKeyword(keyword, 500);
		}
		
		List<Document> rawDocuments = twitterDownloader.rawDocuments;
		List<Document> processedDocuments = twitterDownloader.processedDocuments;
		
		System.out.println("total number of tweets downloaded: " + rawDocuments.size());
		
		// save to db
		MongoDBConnection mongoConnectionRaw = new MongoDBConnection("RawDB", "RawTwitter");
		mongoConnectionRaw.saveDocumentListToDB(rawDocuments);
		
		MongoDBConnection mongoConnectionProcessed = new MongoDBConnection("ProcessedDB", "ProcessedTwitter");
		mongoConnectionProcessed.saveDocumentListToDB(processedDocuments);
		
	}
		
	public void readFromMongoDBandSaveToFile() {
		// get cleaned tweets from DB
		MongoDBConnection mongoConnection = new MongoDBConnection("ProcessedDB", "ProcessedTwitter");
		List<String> keys = new ArrayList<>();
		keys.add("full_text");
		List<String> texts = mongoConnection.extractContentsFromDB(keys);
		FileManager fileManager = new FileManager();
		fileManager.saveTextsToFile("./cleanedTweets.txt", texts);
	}
	
	public static void main(String[] args) {
		
		Driver driver = new Driver();
		
		driver.downloadFromTwitterAndSave();
		
		driver.readFromMongoDBandSaveToFile();
		
	}
}
