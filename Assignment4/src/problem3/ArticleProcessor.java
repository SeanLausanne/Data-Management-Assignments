package problem3;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

public class ArticleProcessor {

	public boolean checkWordApperanceInArticle(String keyWord, String article) {
		String[] words = article.split(" ");
		Set<String> wordSet = new HashSet<String>(Arrays.asList(words));
		if (wordSet.contains(keyWord)) {
			return true;
		} else {
			return false;
		}
	}
}
