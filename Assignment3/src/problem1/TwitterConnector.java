package problem1;

import twitter4j.Twitter;
import twitter4j.TwitterFactory;
import twitter4j.conf.ConfigurationBuilder;

public class TwitterConnector {
	
	String consumerKey = "your consumer key";
	String consumerSecret = "your consumer secret";
	String accessToken = "your access token";
	String accessSecret = "your access secret";
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
