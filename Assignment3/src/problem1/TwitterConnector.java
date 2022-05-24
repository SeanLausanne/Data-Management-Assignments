package problem1;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterConnector {
	
	String consumerKey = "TNjWcCXbFAZsMt0kOs4Ksjodi";
	String consumerSecret = "zW5XtLPb6ubC7lowXERca1cfDG53jTWlxtYUsvEetB3iYjvm3S";
	String accessToken = "1039356566519074817-WNB0X1rxuTg0XEXgApCtVwgGSU7rpu";
	String accessSecret = "Jrd0COZuOi8z2zZy3tICKnyfSJ6jY1XhTLLh0yUcNN4b0";
	ConfigurationBuilder configurationBuilder;
	
	public TwitterConnector() {
		configurationBuilder = new ConfigurationBuilder();
		configurationBuilder.setDebugEnabled(true);
		configurationBuilder.setOAuthConsumerKey(consumerKey);
		configurationBuilder.setOAuthConsumerSecret(consumerSecret);
		configurationBuilder.setOAuthAccessToken(accessToken);
		configurationBuilder.setOAuthAccessTokenSecret(accessSecret);
		configurationBuilder.setJSONStoreEnabled(true);
	}
	
	public Twitter getTwitterInstance() {
		TwitterFactory factory = new TwitterFactory(configurationBuilder.build());
		Twitter twitter = factory.getInstance();
		return twitter;
	}

}
