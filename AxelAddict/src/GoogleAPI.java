import java.awt.List;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.lang.reflect.Array;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.ProtocolException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

//Drews' API key:  AIzaSyDz8vZqenRTkgAiHOowXCIca0xgihu5sPU 
//Navjots' API key: AIzaSyClV1NPhaiX9ie3RP_3mFT59HfGPfQOEs4
//We might all need to create an API Key so we can rotate through them if necessary.
//Base for code found here: http://stackoverflow.com/questions/10257276/java-code-for-using-google-custom-search-api


public class GoogleAPI {
	String userQry;
	int SIZE = 20;
	int resultsNum = 10;
	String dKey = "AIzaSyDz8vZqenRTkgAiHOowXCIca0xgihu5sPU";
	String dKey2 = "AIzaSyAbsMXP_KCNf5_Lwg07D5QyIhhUldJKNOE";
	String nKey = "AIzaSyClV1NPhaiX9ie3RP_3mFT59HfGPfQOEs4";
	String dKey3 = "AIzaSyCx_dLdbGVDbMVxmIr7lPS6QGNTG-apolE";
	String descriptions;
	int code; 
	
	GoogleAPI(String qry){
		setQry(qry);
		try {
			getResults();
		} catch (Exception e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
	
	public void setQry(String qry) {
		userQry = qry;
	}
	
	public String getDescriptions() { 
		return descriptions;
	}
	
	public int getHttpCode() {
		return code;
	}
	
	public String getResults() throws Exception {
		String key= dKey3;
	    String qry= userQry.replace(" ", "+");
	    URL url = new URL(
	            "https://www.googleapis.com/customsearch/v1?key="+key+ "&cx=013036536707430787589:_pqjad5hr1a&q="+ qry + "&alt=json");
	    HttpURLConnection conn = (HttpURLConnection) url.openConnection();
		conn.setRequestMethod("GET");
		conn.setRequestProperty("Accept", "application/json");
		code = conn.getResponseCode();
		if (code != 200) {
			throw new RuntimeException("Failed : HTTP error code : "
					+ conn.getResponseCode());
		}

		BufferedReader br = new BufferedReader(new InputStreamReader(
			(conn.getInputStream())));
		String output, realOutput = "";
		while ((output = br.readLine()) != null) {
			realOutput += output;
		}
	    JSONObject json = new JSONObject(realOutput);
	     
	    return googleAPIParser(json);
	}
	
	public String googleAPIParser (JSONObject json) throws JSONException {
		
			if (!json.has("items")) return "";
		    JSONArray tempArr = json.getJSONArray("items");
		    String totalResults = "";
		     for (int i = 0 ; i< tempArr.length(); i++) {
		    	JSONObject tempObj = (JSONObject) tempArr.get(i);
		    	String title = "";
		    	String snippet = "";
		    	if (!json.has("title"))
		    		title = tempObj.getString("title");
		    	if (!json.has("snippet"))
		    		snippet = tempObj.getString("snippet");
		    	totalResults = totalResults +" "+  snippet +" "+ title;
		    }  
		    descriptions = totalResults;
		    return totalResults;

	}
	
	public String keywordSearch (String keyword1, String keyword2, String carType) {
		if (carType.isEmpty())
			carType = "car";
		String query = keyword1 + " " + keyword2 + " " + carType;
		
		return userWordSearch(query);
	}
	
	public String userWordSearch (String str) {
		setQry(str);
		String results;
		try {
			results = getResults();
		} catch (Exception e) {
			results = "";
			e.printStackTrace();
		}
		return results;
	}
}