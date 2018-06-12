import static org.junit.Assert.*;

import org.junit.Test;

public class GoogleAPITest {
	
	private GoogleAPI google;
	
	@Test
	public void test() {
		// one simple test to make sure the google api is working
		google = new GoogleAPI("Testing connection");
		assertEquals(200, google.getHttpCode());
	}

}
