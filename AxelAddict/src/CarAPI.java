import java.net.*;
import java.util.ArrayList;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.*;

/*  Navjot's Edmund's car API key = 2wrdcmuyzsq72q52asnhv86c
 *  Drew's Edmund's car API key = czjjeptrnqryed954989vnjy
 *  Drew's Second Car API Key = fx4d97e7rnmnychh6vs4rpjc
 *  Another key = yzuc66d2at9d86acx6q6x7zw
 *  Edgar's key = bvjrv36yjcgat6qu8vcq9grn
 *  Yet another key = z466t2mq9gfemeu5vfk62fhq
 *  Yet another another key = 5fu92deejftrrec48re28nr7
 *  Another one(key) = avasesmvnvypk3kfzqecfuj3
 *  Just in case we decide to use some of there data 
 *  Still need an API that gives car thumb nails*/

public class CarAPI {
	private static String APIKEY = "bvjrv36yjcgat6qu8vcq9grn";	
	private static ArrayList<String> carMakes = new ArrayList<>();
	private static ArrayList<String> carModels = new ArrayList<>();
	
	private static ArrayList<String> carImages = new ArrayList<>();

	private ArrayList<String> carTrims = new ArrayList<>();
	private static ArrayList<Integer> carYear = new ArrayList<>();
	private CarSpec carSearchResults = new CarSpec();
	private static JSONObject makeData = new JSONObject();
	private static JSONObject modelData = new JSONObject();
	private ArrayList<String> carStyle = new ArrayList<>();
	private static ArrayList<Integer> makeId = new ArrayList<>();
	private static ArrayList<String> modelId = new ArrayList<>();
	private static ArrayList<Integer> yearId = new ArrayList<>();
	private static int code;
	ArrayList<CarSpec> tester = new ArrayList<CarSpec>();
	int[] hp;
	private static ArrayList<String> carSpecs = new ArrayList<>();
	
	
	CarAPI() {
		try {
			makeData = getAllCarMakes();
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public int getHttpCode() {
		return code;
	}
	
	public static ArrayList<String> getCarMakes() {
		return carMakes;
	}
	
	public ArrayList<String> getCarModels() {
		return carModels;
	}
	
	public ArrayList<String> getCarTrims() {
		return carTrims;
	}
	
	public ArrayList<Integer> getCarYear() {
		return carYear;
	}
	
	public ArrayList<String> getCarStyle() {
		return carStyle;
	}
	
	public ArrayList<String> getCarImages() {
		return carImages;
	}
	
	public CarSpec getCarSearchResults() {
		return carSearchResults;
	}
	
	public static void setCarMake(ArrayList<String> makes) {
		carMakes = makes;
	}
	// TODO: Here I am
	
	public void setCarTrims(String Make, String Model, int Year) {
		try {
			carTrims = getCarTrimFromMakeModel(Make, Model, Year);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void setCarModels(String Make) {
		try {
			carModels = getCarModelsFromMake(Make);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void setCarModelsFromMakeYear(String Make, int Year) {
		try {
			carModels = getCarModelsForMakeYear(Make, Year);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void setCarStyle(String Make, String Model, int Year) {
		try {
			carStyle = getCarStyle(Make, Model, Year);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public void setCarToSearch(String Make, String Model, int Year) {
		try {
			getSearchResults(Make, Model, Year, tester);
		} catch (JSONException e) {
			e.printStackTrace();
		}
	}
	
	public static ArrayList<String> carAPIParserStr (JSONObject json, String arrName, String strName) throws JSONException {
		ArrayList<String> tempArrList = new ArrayList<String>();
		if (!json.has(arrName)) return tempArrList;
		JSONArray tempArr = json.getJSONArray(arrName);
		for (int i = 0; i < tempArr.length(); i++){
			JSONObject tempObj = (JSONObject) tempArr.get(i);
			if (tempObj.has(strName))
				tempArrList.add(tempObj.getString(strName));
		}
		return tempArrList;
	}
	
	public static ArrayList<Integer> carAPIParserInt (JSONObject json, String arrName, String intName) throws JSONException {
		ArrayList<Integer> tempArrList = new ArrayList<Integer>();
		if (!json.has(arrName)) return tempArrList;
		JSONArray tempArr = json.getJSONArray(arrName);
		for (int i = 0; i < tempArr.length(); i++){
			JSONObject tempObj = (JSONObject) tempArr.get(i);
			if (tempObj.has(intName))
				tempArrList.add(tempObj.getInt(intName));
		}
		return tempArrList;
	}
	
	public static String readFileToString (String filePath) throws IOException{
		File file = new File(filePath);
		FileInputStream fis = new FileInputStream(file);
		byte[] data = new byte[(int) file.length()];
		fis.read(data);
		fis.close();
		String str = new String(data, "UTF-8");
		return str;
	}
	
	public static JSONObject getAllCarMakes() throws JSONException {
		
		JSONObject json = readFile();
		setCarMake(carAPIParserStr(json,"makes","niceName"));
		return json;
	}
	
	public int getLastYear(String make, String model) throws JSONException {
		JSONObject json = readFile();
		ArrayList<String> tempMakes = carAPIParserStr(json,"makes","niceName");
		int index = -1;
		for (int i=0; i< tempMakes.size(); i++){
			if (make.equals(tempMakes.get(i))){
				index = i;
				break;
			}
		}
		if (index == -1) {
			return -1;
		}
		JSONArray tempArr = json.getJSONArray("makes");
		JSONObject tempObj =tempArr.getJSONObject(index);
		ArrayList<String> models = carAPIParserStr(tempObj, "models", "niceName");
		index = -1;
		for (int i=0; i< models.size(); i++){
			if (model.equals(models.get(i))){
				index = i;
				break;
			}
		}
		if (index == -1) {
			return -1;
		}
		JSONArray tempModel = tempObj.getJSONArray("models");
		tempObj = tempModel.getJSONObject(index);
		tempArr = tempObj.getJSONArray("years");
		tempObj = tempArr.getJSONObject(tempArr.length()-1);
		return tempObj.getInt("year");
	}
	
	public static JSONObject readFile () {
		String str = "";
		try {
			str = readFileToString("../Resources/edmundsAPIData.txt");
		} catch (IOException a) {
			try {
				str = readFileToString("Resources/edmundsAPIData.txt");
			} catch (IOException b) {
				try {
					str = readFileToString("../../Resources/edmundsAPIData.txt");
				} catch (IOException e) {
					e.printStackTrace();
				}
			}
		}
		// getting the object data with the specific make the user wants
		JSONObject json = new JSONObject();
		try {
			json = new JSONObject(str);
		} catch (JSONException e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public ArrayList<String> getModelFromYearMake(String make, int year) throws JSONException {
		make = make.toLowerCase();
		JSONObject json = readFile();
		ArrayList<String> tempMakes = carAPIParserStr(json,"makes","niceName");
		int index = -1;
		for (int i=0; i< tempMakes.size(); i++){
			if (make.equals(tempMakes.get(i))){
				index = i;
				break;
			}
		}
		if (index == -1) {
			return null;
		}
		JSONArray tempArr = json.getJSONArray("makes");
		JSONObject tempObj =tempArr.getJSONObject(index);
		ArrayList<String> models = new ArrayList<String>();
		// narrowing down the models by year
		JSONArray tempModels = tempObj.getJSONArray("models");
		for (int i=0; i<tempModels.length(); i++) {
			JSONObject model = tempModels.getJSONObject(i);
			String mod = model.getString("niceName");
			JSONArray yrs = model.getJSONArray("years");
			for (int j=0; j<yrs.length(); j++) {
				JSONObject yr = (JSONObject) yrs.get(j);
				if (yr.getInt("year") == year) {
					models.add(mod);
				}
			}
		}
		carModels = models;
		return carModels;
	}
	
	public static void edmundsTestCall() throws JSONException {
		try {

			URL url = new URL("http://api.edmunds.com/api/vehicle/v2/makes?fmt=json&api_key="+APIKEY);
			HttpURLConnection conn = (HttpURLConnection) url.openConnection();
			conn.setRequestMethod("GET");
			conn.setRequestProperty("Accept", "application/json");
			code = conn.getResponseCode();

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
	}
	
	public static ArrayList<String> getCarModelsFromMake(String Make) throws JSONException {
		String lowerMake = Make.toLowerCase();
		JSONArray tempArr = makeData.getJSONArray("makes");
		int index = -1;
		ArrayList<String> carMakes = getCarMakes();
		for (int i = 0; i < carMakes.size(); i++){
			String make = carMakes.get(i);
			if(make.equals(lowerMake)){
				index = i;
				break;
			}
		}
		if (index == -1){
			return null;
		}
		modelData = tempArr.getJSONObject(index);
		carModels = carAPIParserStr(modelData, "models","niceName");
		return carModels;
	}
	
	public static ArrayList<Integer> getYearsFromModel(String Model) throws JSONException {
		String lowerModel = Model.toLowerCase();
		JSONArray tempArr = modelData.getJSONArray("models");
		int index = -1;
		for (int i = 0; i < carModels.size(); i++){
			String make = carModels.get(i);
			if(make.equals(lowerModel)){
				index = i;
				break;
			}
		}
		if (index == -1){
			return null;
		}
		JSONObject yrData = tempArr.getJSONObject(index);
		carYear = carAPIParserInt(yrData, "years", "years");
		
		return carYear;
	}
	// TODO: Need to drop API call for this and just parse our data
	public static ArrayList<String> getCarModelsForMakeYear(String Make, int Year) throws JSONException {
		try {
			URL url = new URL("https://vpic.nhtsa.dot.gov/api/vehicles/GetModelsForMakeYear/make/"+ Make + "/modelyear/"+ Year +"?format=json");
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
			ArrayList<String> carMakes = new ArrayList<>();
			while ((output = br.readLine()) != null) {
				realOutput = output;
			}
			
			JSONObject json = new JSONObject(realOutput);
			JSONArray tempArr = json.getJSONArray("Results");
			for (int i = 0; i < tempArr.length(); i++){
				JSONObject tempObj = (JSONObject) tempArr.get(i);
				carMakes.add(tempObj.getString("Model_Name"));
			}
			conn.disconnect();
			return carMakes;

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		return null;
	}
	// NOT TESTED
	private static ArrayList<String> getCarTrimFromMakeModel(String Make, String Model, int Year) throws JSONException {
		try {
			URL url = new URL("https://api.edmunds.com/api/vehicle/v2/"+Make+"/"+Model+"/"+Year+"?fmt=json&api_key=" + APIKEY);
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
			ArrayList<String> carMakes = new ArrayList<>();
			while ((output = br.readLine()) != null) {
				realOutput = output;
			}
			JSONObject json = new JSONObject(realOutput);
			
			
			JSONArray tempArr = json.getJSONArray("styles");
			for (int i = 0; i < tempArr.length(); i++){
				JSONObject tempObj = (JSONObject) tempArr.get(i);
				carMakes.add(tempObj.getString("trim"));
			}
			conn.disconnect();
			return carMakes;

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		return null;
	}
	// http://api.edmunds.com/api/vehicle/v2/makes?fmt=json&year=2016&api_key=czjjeptrnqryed954989vnjy
	//Testing to get info based on year entered------------
	public static ArrayList<String> getCarFromYear(int Year) throws JSONException {
		try {

			URL url = new URL("http://api.edmunds.com/api/vehicle/v2/makes?fmt=json&year="+Year+"&api_key="+APIKEY);
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
				realOutput = output;
			}
			makeData = new JSONObject(realOutput);
			carMakes = carAPIParserStr(makeData, "makes", "niceName");
			conn.disconnect();
			
			return carMakes;

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		return null;

	}
	//------------------------------------------------

	private static ArrayList<String> getCarStyle(String Make, String Model, int Year) throws JSONException {
		try {
			URL url = new URL("https://api.edmunds.com/api/vehicle/v2/" + Make + "/" + Model + "/" + Year + "/styles?fmt=json&api_key=" + APIKEY + "&view=full");
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
			ArrayList<String> carStyle = new ArrayList<>();
			while ((output = br.readLine()) != null) {
				realOutput = output;
			}
			JSONObject json = new JSONObject(realOutput);
			
			carStyle = carAPIParserStr(json, "styles", "trim");
			conn.disconnect();
			return carStyle;

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		return null;
	}
	
	String getCarImages(String Make, String Model, int Year) throws JSONException {
		try {
			URL url = new URL("https://api.edmunds.com/api/media/v2/" + Make + "/" + Model + "/" + Year + "/photos?api_key=" + APIKEY);
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
			ArrayList<String> carImages = new ArrayList<>();
			while ((output = br.readLine()) != null) {
				realOutput = output;
			}
			JSONObject json = new JSONObject(realOutput);
			
			//carImages = carAPIParserStr(json, "photos", "sources");
			
			
			
			JSONArray tempArr = json.getJSONArray("photos");
			
			
			JSONObject tempObject = (JSONObject) tempArr.get(0);
			
			
			JSONArray sources = (JSONArray) tempObject.get("sources");
			
				
			
			JSONObject link = (JSONObject) sources.get(3);
			
			
			
			
			JSONObject link2 = (JSONObject) link.get("link");
			
			
			
			String href = "https://media.ed.edmunds-media.com" + (String) link2.get("href");

			
			carImages.add(href);

			conn.disconnect();
			
			return href;

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		return null;
	}
	
	
	private static ArrayList<CarSpec> getSearchResults(String Make, String Model, int Year, ArrayList<CarSpec> carSpecs) throws JSONException {
		try {
			URL url = new URL("https://api.edmunds.com/api/vehicle/v2/" + Make + "/" + Model + "/" + Year + "/styles?fmt=json&api_key=" + APIKEY + "&view=full");
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
			ArrayList<String> carEngine = new ArrayList<>();
			while ((output = br.readLine()) != null) {
				realOutput = output;
			}
			JSONObject json = new JSONObject(realOutput);
			
			
			JSONArray tempArr = json.getJSONArray("styles");
			
			//CarSpec[] test = new CarSpec[100];
			/*
			for(int j = 0; j < 100; j++)
			{
				test[j] = new CarSpec();
			}
			*/
			
			for(int i = 0; i < tempArr.length(); i++)
			{
				
				CarSpec test = new CarSpec();
				JSONObject tempObject = (JSONObject) tempArr.get(i);
				
				try{
				JSONObject mpg = (JSONObject) tempObject.get("MPG");
				int cityMPG = Integer.parseInt((String) mpg.get("city"));
				int hwMPG = Integer.parseInt((String) mpg.get("highway"));
				test.setMPGCity(cityMPG);
				test.setMPGHighway(hwMPG);
				}catch(Exception ex){}
				
				try{
				JSONObject price = (JSONObject) tempObject.get("price");
				double baseMSRP = (double) price.get("baseMSRP");
				double resaleValue = (double) price.get("usedPrivateParty");
				test.setBaseValue(baseMSRP);
				test.setResaleValue(resaleValue);
				}catch(Exception ex){}
				
				try{
				JSONObject engine = (JSONObject) tempObject.get("engine");
				int hp = (int) engine.get("horsepower");
				test.setHorsepower(hp);
				}catch(Exception ex){}
				
				try{
				JSONObject engine = (JSONObject) tempObject.get("engine");
				int torque = (int) engine.get("torque");
				test.setTorque(torque);
				}catch(Exception ex){}
				
				try{
				JSONObject engine = (JSONObject) tempObject.get("engine");
				String gasType = (String) engine.get("fuelType");
				test.setGasType(gasType);
				}catch(Exception ex){}
				
				try{
				JSONObject engine = (JSONObject) tempObject.get("engine");
				String engineType = (String) engine.get("configuration");
				test.setEngineType(engineType); //This gets the configuration of the engine I might need to make a search to get cylinder count
				}catch(Exception ex){}
				
				try{
				JSONObject engine = (JSONObject) tempObject.get("engine");
				double displacement = (double) engine.get("displacement");
				test.setDisplacement(displacement);
				}catch(Exception ex){}
				
				try{
				JSONObject engine = (JSONObject) tempObject.get("engine");
				int cylinder = (int) engine.get("cylinder");
				test.setCylinder(cylinder);
				}catch(Exception ex){}
				
				
				String driveTrain = (String) tempObject.get("drivenWheels");
				test.setDriveTrain(driveTrain);
				
				try{
				JSONObject engine = (JSONObject) tempObject.get("engine");
				JSONObject engineRPM = (JSONObject) engine.get("rpm");
				int peakHP = (int) engineRPM.get("horsepower");
				int peakTQ = (int) engineRPM.get("torque");
				test.setPeakHP(peakHP);
				test.setPeakTQ(peakTQ);
				}catch(Exception ex){}
				
				String trim = (String) tempObject.get("trim");
				test.setTrim(trim);
				
				carSpecs.add(test);

			}
			
			conn.disconnect();
			return carSpecs;

		  } catch (MalformedURLException e) {

			e.printStackTrace();

		  } catch (IOException e) {

			e.printStackTrace();

		  }
		return null;
	}
	
	
	/*private static ArrayList<String> getCarSpecpublic CarSpec(String trim, String transmission, int horsepower, int peakHP, int torque, int peakTQ,
			String engineType, String driveTrain, String gasType, double weight, double displacement, double mPGCity,
			double mPGHighway, double baseValue, double resaleValue, String picture) throws Exception {
		
	}*/
	
	
	public static void main(String[] args) throws JSONException {
		
		CarAPI test = new CarAPI();
		}
}
