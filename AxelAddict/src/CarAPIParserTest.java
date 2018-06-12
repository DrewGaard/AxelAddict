import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.ArrayList;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class CarAPIParserTest {

	private CarAPI testCarAPI = new CarAPI();
	
	public String readFileToString (String filePath) throws IOException{
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();
		String str = new String(data, "UTF-8");
		return str;
	}

	@Test
	public void testCarAPIParsers1() throws JSONException, IOException{
		String str = "";
		try {
			str = readFileToString("testData/CarAPITestingDocs/Test1.txt");
		} catch (IOException e) {
			//e.printStackTrace();
		}
		finally
		{
			try {
				str = readFileToString("AxelAddict/testData/CarAPITestingDocs/Test1.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		assertNotEquals(str,"");
		JSONObject json = new JSONObject(str);
		ArrayList<String> makes = testCarAPI.carAPIParserStr(json, "makes", "niceName");
		assertNotNull(makes);
		assertNotEquals(makes.size(),0);

		
		try {
			str = readFileToString("testData/CarAPITestingDocs/Test2.txt");
		} catch (IOException e) {
			//e.printStackTrace();
		}
		finally
		{
			try {
				str = readFileToString("AxelAddict/testData/CarAPITestingDocs/Test2.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		assertNotEquals(str,"");
		json = new JSONObject(str);
		ArrayList<String> models = testCarAPI.carAPIParserStr(json, "models", "niceName");
		assertNotNull(models);
		assertNotEquals(models.size(),0);
		
		try {
			str = readFileToString("testData/CarAPITestingDocs/Test3.txt");
		} catch (IOException e) {
			//e.printStackTrace();
		}
		finally
		{
			try {
				str = readFileToString("AxelAddict/testData/CarAPITestingDocs/Test3.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		
		assertNotEquals(str,"");
		json = new JSONObject(str);
		ArrayList<Integer> years = testCarAPI.carAPIParserInt(json, "years", "year");
		ArrayList<Integer> results = new ArrayList<Integer>();
		for (int i =0; i< 19; i++){
			int a = 1997;
			results.add(a+i);
			int actualYr = years.get(i);
			assertEquals(a+i, actualYr);
		}
		assertNotNull(years);
		assertNotEquals(years.size(),0);
		assertEquals(results.size(),years.size());
	}
	
	@Test
	public void carAPIParserTest2() throws JSONException{
		String str = "";
		ArrayList<String> empty = new ArrayList<String>();
		try {
			str = readFileToString("testData/CarAPITestingDocs/Test4.txt");
		} catch (IOException e) {
			//e.printStackTrace();
		}
		finally
		{
			try {
				str = readFileToString("AxelAddict/testData/CarAPITestingDocs/Test4.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		assertNotEquals(str,"");
		JSONObject json = new JSONObject(str);
		ArrayList<String> makes = testCarAPI.carAPIParserStr(json, "makes", "niceName");
		assertEquals(makes,empty);
	}

}
