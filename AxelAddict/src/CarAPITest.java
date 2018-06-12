import static org.junit.Assert.*;
import org.json.JSONException;
import org.junit.Test;

public class CarAPITest {

	CarAPI carAPI;
	
	@Test
	public void testCarAPIs() {
		carAPI = new CarAPI();
		try {
			carAPI.edmundsTestCall();
		} catch (JSONException e) {
			e.printStackTrace();
		}
		assertEquals(carAPI.getHttpCode(), 200);
	}
}
