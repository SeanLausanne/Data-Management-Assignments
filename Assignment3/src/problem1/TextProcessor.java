package problem1;

public class TextProcessor {
	
	public static String cleanText(String text) {
		
		String cleanText;
		String urls = "\\b(https?)://[-A-Za-z0-9+@&#/%?~_|:;,.!]*[-A-Za-z0-9+@&#/%~_|]";
		String nonAlphanumeric = "[^A-Za-z0-9 ]";
		String multipleSpaces = "\\s+";
		
		cleanText = text.replaceAll(urls, " ").replaceAll(nonAlphanumeric, " ").replaceAll(multipleSpaces, " ").trim();
		
		return cleanText;
	}
	
}
