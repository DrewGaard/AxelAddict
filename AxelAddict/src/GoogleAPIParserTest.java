import static org.junit.Assert.*;

import java.io.File;
import java.io.FileInputStream;
import java.io.IOException;

import org.json.JSONException;
import org.json.JSONObject;
import org.junit.Test;

public class GoogleAPIParserTest {
	
	private GoogleAPI googleApi;

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
	public void test1() throws JSONException {
		

		String str = "";
		try {
			str = readFileToString("testData/GoogleAPITestingDocs/Test1.txt");
		} catch (IOException e) {
		//	e.printStackTrace();
		}
		finally
		{
			try {
				str = readFileToString("AxelAddict/testData/GoogleAPITestingDocs/Test1.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		assertNotEquals("", str);
		JSONObject test1 = new JSONObject(str);
		
		googleApi = new GoogleAPI("family car");
		String parsedOutput = googleApi.googleAPIParser(test1);
		
		try {
			str = readFileToString("testData/GoogleAPITestingDocs/Test1Results.txt");
		} catch (IOException e) {
			//e.printStackTrace();
		}
		finally
		{
			try {
				str = readFileToString("AxelAddict/testData/GoogleAPITestingDocs/Test1Results.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		assertNotEquals("", str);
		assertEquals(str.length(), parsedOutput.length());
	}
	
	@Test
	public void test2() throws JSONException {
		

		String str = "";
		try {
			str = readFileToString("testData/GoogleAPITestingDocs/Test2.txt");
		} catch (IOException e) {
			//e.printStackTrace();
		}
		finally
		{
			try {
				str = readFileToString("AxelAddict/testData/GoogleAPITestingDocs/Test2.txt");
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		assertNotEquals("", str);
		JSONObject test2 = new JSONObject(str);
		
		googleApi = new GoogleAPI("family car");
		String parsedOutput = googleApi.googleAPIParser(test2);
		
		assertEquals(parsedOutput, "");
	}

}
