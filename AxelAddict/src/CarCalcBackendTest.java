import static org.junit.Assert.*;

import java.util.Calendar;

import org.junit.Test;

public class CarCalcBackendTest {

	private CarCalcBackend backend = new CarCalcBackend();
	
	@Test
	public void testRealHP() {
		int hp = 300;
		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		int tenYrs = curYear - 10;
		int fiftyYrs = curYear - 50;
		// new car with less than 50K miles and bought in the current year
		assertTrue(backend.realHP(hp, 0, 0, 0, curYear) == hp);
		// stock car
		assertTrue(backend.realHP(hp, 0, 0, 1, curYear) == hp-25);
		// really badly maintained car but still same year, should not affect performance to much
		// therefore horsepower should still be relatively the same
		assertTrue(backend.realHP(hp, 5, 5, 0, curYear) == hp);
		assertTrue(backend.realHP(hp, 2, 2, 0, curYear) == hp);
		// a car that was badly maintained for 10 years will have more horsepower than a car that was 
		// badly maintained for 50 years
		assertTrue(backend.realHP(hp, 3, 3, 0, tenYrs) > (backend.realHP(hp, 3, 3, 0, fiftyYrs)));
		// a car that was very badly maintained for 10 years will have more horsepower than a car that was 
		// slightly badly maintained for 50 years
		assertTrue(backend.realHP(hp, 4, 4, 0, tenYrs) > (backend.realHP(hp, 1, 1, 0, fiftyYrs)));
	}
	
	@Test
	public void testQuarterMile() {
		int hp = 300;
		int weight = 3500;
		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		int tenYrs = curYear - 10;
		int fiftyYrs = curYear - 50;
		// making sure weight and horsepower change the quarter mile of the car
		assertTrue(backend.quarterMile(hp, weight, 0, 0, 0, curYear) < backend.quarterMile(hp, weight + 1, 0, 0, 0, curYear));
		assertTrue(backend.quarterMile(hp, weight, 0, 0, 0, curYear) < backend.quarterMile(hp - 1, weight, 0, 0, 0, curYear));
		// car that is extremely badly maintained will still out put the same amount of power because decrease in a cars power 
		// happens overtime no matter how badly it is maintained.
		assertTrue(backend.quarterMile(hp, weight, 5, 5, 0, curYear) == backend.quarterMile(hp, weight, 0, 0, 0, curYear));
		// stock car will lose to the same car that has been tuned up (even by a little)
		assertTrue(backend.quarterMile(hp, weight, 0, 0, 0, curYear) < backend.quarterMile(hp, weight, 0, 0, 1, curYear));
		// a car that is older should output the same amount of power if it has been maintained well
		assertTrue(backend.quarterMile(hp, weight, 0, 0, 0, tenYrs) == backend.quarterMile(hp, weight, 0, 0, 0, curYear));
		assertTrue(backend.quarterMile(hp, weight, 0, 0, 0, tenYrs) == backend.quarterMile(hp, weight, 0, 0, 0, fiftyYrs));
	}
	
	@Test
	public void testZeroToSixty() {
		int hp = 300;
		int weight = 3500;
		int curYear = Calendar.getInstance().get(Calendar.YEAR);
		int tenYrs = curYear - 10;
		int fiftyYrs = curYear - 50;
		// making sure weight and horsepower change the 0-60 of the car
		assertTrue(backend.zeroToSixty(hp, weight, 0, 0, 0, curYear) < backend.zeroToSixty(hp, weight + 1, 0, 0, 0, curYear));
		assertTrue(backend.zeroToSixty(hp, weight, 0, 0, 0, curYear) < backend.zeroToSixty(hp - 1, weight, 0, 0, 0, curYear));
		// car that is extremely badly maintained will still out put the same amount of power because decrease in a cars power 
		// happens overtime no matter how badly it is maintained.
		assertTrue(backend.zeroToSixty(hp, weight, 5, 5, 0, curYear) == backend.zeroToSixty(hp, weight, 0, 0, 0, curYear));
		// stock car will lose to the same car that has been tuned up (even by a little)
		assertTrue(backend.zeroToSixty(hp, weight, 0, 0, 0, curYear) < backend.zeroToSixty(hp, weight, 0, 0, 1, curYear));
		// a car that is older should output the same amount of power if it has been maintained well
		assertTrue(backend.zeroToSixty(hp, weight, 0, 0, 0, tenYrs) == backend.zeroToSixty(hp, weight, 0, 0, 0, curYear));
		assertTrue(backend.zeroToSixty(hp, weight, 0, 0, 0, tenYrs) == backend.zeroToSixty(hp, weight, 0, 0, 0, fiftyYrs));
	}

}
