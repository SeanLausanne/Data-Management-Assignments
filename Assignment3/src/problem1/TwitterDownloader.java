package problem1;
import java.util.ArrayList;
import java.util.List;

import org.bson.Document;
import twitter4j.Query;
import twitter4j.QueryResult;
import twitter4j.Status;
import twitter4j.Twitter;
import twitter4j.TwitterException;
import twitter4j.TwitterObjectFactory;

public class TwitterDownloader {
	
	Twitter twitter;
	List<Status> statuses;
	List<Document> rawDocuments;
	List<Document> processedDocuments;
	
	public TwitterDownloader(Twitter twitter) {
		this.twitter = twitter;
		statuses = new ArrayList<>();
		rawDocuments = new ArrayList<>();
		processedDocuments = new ArrayList<>();
	}
	
	public void downloadTwitterWithKeyword(String keyword, int nTweets) {
		
		int nTweetsRemain = nTweets;
		
		// set query parameters
		Query query = new Query(keyword);
		query.setLocale("en");
		query.setLang("en");
		
		try {
			while (nTweetsRemain > 0) {
				
				// twitter has limit of 100
				int downloadBatch = Math.min(100, nTweetsRemain);
				nTweetsRemain -= downloadBatch;
				query.setCount(downloadBatch);
				
				QueryResult results = twitter.search(query);
				
				for (Status status : results.getTweets()) {
					statuses.add(status);
					String text = status.getText();
					
					String json = TwitterObjectFactory.getRawJSON(status);
					Document rawDocument = Document.parse(json);
					rawDocuments.add(rawDocument);
					
					Document processedDocument = Document.parse(json);
					processedDocument.put("full_text", TextProcessor.cleanText(text));
					processedDocuments.add(processedDocument);
				}
				
				// avoid duplicates
				Status lastStatus = statuses.get(statuses.size() - 1);
				long lastIDID = lastStatus.getId();
				query.setMaxId(lastIDID - 1);
			}

			System.out.println(statuses.size());
			
		} catch (TwitterException e) {
			e.printStackTrace();
		}

	}
}



