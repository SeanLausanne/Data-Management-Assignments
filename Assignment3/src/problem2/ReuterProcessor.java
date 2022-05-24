package problem2;

import problem1.FileManager;
import problem1.MongoDBConnection;
import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import org.bson.Document;

public class ReuterProcessor {

	List<Document> articleDocuments;

	public ReuterProcessor() {
		this.articleDocuments = new ArrayList<>();
	}

	public void extractArticle(String fileName) {

		File file = new File(fileName);

		try {

			FileReader fileReader = new FileReader(file);
			BufferedReader reader = new BufferedReader(fileReader);
			StringBuffer currentRead = new StringBuffer();
			String currentLine;

			// read each line
			while ((currentLine = reader.readLine()) != null) {
				if (currentLine.indexOf("</REUTERS>") != -1) {
					// "</REUTERS>" found
					Document document = new Document();
					// find text
					Pattern textPattern = Pattern.compile("<TEXT>(.*?)</TEXT>");
					Matcher matcher = textPattern.matcher(currentRead);

					if (matcher.find()) {
						String article = matcher.group(1);
						// find title and body
						int titleStartIdx = article.indexOf("<TITLE>");
						int titleEndIdx = article.indexOf("</TITLE>");
						int bodyStartIdx = article.indexOf("<BODY>");
						int bodyEndIdx = article.indexOf("</BODY>");

						String title = "";
						if (titleStartIdx != -1) {
							title = article.substring(titleStartIdx + 7, titleEndIdx);
						}

						String text = "";
						if (bodyStartIdx != -1) {
							text = article.substring(bodyStartIdx + 6, bodyEndIdx);
						}

						document.put("title", title);
						document.put("text", text);
						articleDocuments.add(document);

					}
					currentRead.setLength(0);
				} else {
					// "</REUTERS>" not found
					currentRead.append(currentLine).append(' ');
				}
			}
			reader.close();
		}

		catch (IOException e) {
			e.printStackTrace();
		}
	}

	public static void main(String[] args) {

		ReuterProcessor extractor = new ReuterProcessor();
		extractor.extractArticle("./Reuters/reut2-009.sgm");
		extractor.extractArticle("./Reuters/reut2-014.sgm");

		List<Document> documents = extractor.articleDocuments;
		System.out.println(documents.size());

		// save to db
		MongoDBConnection mongoConnection = new MongoDBConnection("ReuterDB", "Articles");
		mongoConnection.saveDocumentListToDB(documents);

		List<String> keys = new ArrayList<>();
		keys.add("title");
		keys.add("text");
		// extract from db and write to file
		List<String> texts = mongoConnection.extractContentsFromDB(keys);
		FileManager fileManager = new FileManager();
		fileManager.saveTextsToFile("./reuterContents.txt", texts);
	}
}