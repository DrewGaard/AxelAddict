import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.LinkedList;

public class KeywordMatching {
	public static LinkedList<KeywordPair> getKeywordsFromString(String input) {
		LinkedList<KeywordPair> keywords = selectKeywords();
		LinkedList<KeywordPair> keywordsFound = new LinkedList<KeywordPair>();
		
		for (KeywordPair keyword : keywords) {
			// TODO: check if this needs to be case-sensitive (brand names?)
			if (input.toLowerCase().indexOf(keyword.getName().toLowerCase()) != -1) {
				keywordsFound.add(keyword);
			}
		}
		
		return keywordsFound;
	}
	
	private static LinkedList<KeywordPair> selectKeywords() {
		String command = "SELECT * FROM keywords";
		LinkedList<KeywordPair> resultPairs = new LinkedList<KeywordPair>();
		
		try (Connection conn = DriverManager.getConnection("jdbc:sqlite:temp.db");
             Statement stmt = conn.createStatement()) 
		{
            ResultSet results = stmt.executeQuery(command);
            
			if (results != null) {
				while (results.next()) {
					resultPairs.add(new KeywordPair(results.getString("name"), results.getString("category")));
				}
			}
        }
		
		catch (SQLException e) {
            System.out.println(e.getMessage());
        }
		
		return resultPairs;
	}
}
