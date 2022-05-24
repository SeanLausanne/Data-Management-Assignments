package problem2;

import java.util.ArrayList;
import java.util.List;
import org.bson.Document;
import com.mongodb.MongoClient;
import com.mongodb.MongoClientURI;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoDatabase;

public class MongoDBConnection {

	MongoClientURI dbURI;
	MongoClient mongoClient;
	MongoDatabase db;
	MongoCollection<Document> collection;

	public MongoDBConnection(String dbName, String collectionName) {
		// connect to db
		this.dbURI = new MongoClientURI("mongodb+srv://root:root@dal-csci5408-f21-a3.proow.mongodb.net/test");
		this.mongoClient = new MongoClient(dbURI);
		this.db = mongoClient.getDatabase(dbName);
		this.collection = db.getCollection(collectionName);
	}

	public List<String> extractContentsFromDB(List<String> keys) {
		FindIterable<Document> results = collection.find();
		List<String> contents = new ArrayList<>();
		if (results != null) {
			for (Document document : results) {
				for (String key : keys) {
					String content = document.get(key).toString();
					contents.add(content);
				}
			}
		}
		return contents;
	}
}