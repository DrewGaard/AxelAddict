import static org.junit.Assert.assertTrue;

import java.util.LinkedList;

import org.junit.Test;

public class KeywordMatchingTests {

	@Test
	public void testMultipleChoiceAnswers() {
		LinkedList<String> testResponses = new LinkedList<String>();
		
		testResponses.add("Yes, I'm eco-friendly");
		testResponses.add("Yes, I get emotional");
		testResponses.add("No, I'm lazy");
		testResponses.add("No, I'm somewhat of an introvert");
		testResponses.add("No, too boring for me");
		testResponses.add("Many, I enjoy seeing creative works by artistic people");
		testResponses.add("Yoga, it's calm and relaxing");
		
		LinkedList<KeywordPair> associatedKeywords = new LinkedList<KeywordPair>();
		
		for (String response : testResponses) {
			associatedKeywords.addAll(KeywordMatching.getKeywordsFromString(response));
		}
		
		assertTrue(resultsContainsKeyword(associatedKeywords, "eco-friendly"));
		assertTrue(resultsContainsKeyword(associatedKeywords, "emotional"));
		assertTrue(resultsContainsKeyword(associatedKeywords, "lazy"));
		assertTrue(resultsContainsKeyword(associatedKeywords, "introvert"));
		assertTrue(resultsContainsKeyword(associatedKeywords, "boring"));
		assertTrue(resultsContainsKeyword(associatedKeywords, "creative"));
		assertTrue(resultsContainsKeyword(associatedKeywords, "artistic"));
	}
	
	private boolean resultsContainsKeyword(LinkedList<KeywordPair> results, String keyword) {
		for (KeywordPair pair : results) {
			if (pair.getName().toLowerCase().equals(keyword.toLowerCase())) {
				return true;
			}
		}
		
		return false;
	}
}
