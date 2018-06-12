import static org.junit.Assert.*;

import org.junit.Test;

public class DBTesting {

	SqlKeywords SQL = new SqlKeywords();
	
	@Test
	public void testSelect()
	{
		assertTrue(SqlKeywords.connect() == true);
		assertTrue(SqlKeywords.createNewDatabase() == true);
		
		assertFalse(SqlKeywords.createNewTable("table") == true);
		
		assertTrue(SqlKeywords.selectData("keywords") == true);
		assertFalse(SqlKeywords.selectData("tempTable") == true);
		assertTrue(SqlKeywords.selectData("newtable") == false);
		
		assertTrue(SqlKeywords.insertData("keywords", "Tall", "Random") == true);
		assertFalse(SqlKeywords.insertData("tempTable", "Tall", "Random") == true);
		assertTrue(SqlKeywords.insertData("temp", "name", "cat") == false);
		
		assertTrue(SqlKeywords.removeData("keywords", "category", "Sport") == true);
		assertFalse(SqlKeywords.removeData("tempTable", "category", "Sport") == true);
		assertTrue(SqlKeywords.removeData("temp", "cat", "name") == false);
		
		assertTrue(SqlKeywords.addKeywords("Resources/Keywords.txt", "..Resources/Keywords.txt") == true);
		assertFalse(SqlKeywords.addKeywords("Resources/temp.txt", "../Resources/temp.txt") == true);
		assertTrue(SqlKeywords.addKeywords("Resources/textfile.txt", "../Resources/textfile.txt") == false);
	}
}
