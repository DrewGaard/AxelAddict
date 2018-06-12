import java.util.Calendar;

public class CarCalcBackend {
	
	public double extentOfDamage(int year){
		int yr = Calendar.getInstance().get(Calendar.YEAR);
		return (double)((yr - year));
	}

	public double realHP(double hp, int oilChangeFreq, int odometer, int state, int year){
		double realhp = 0.0;
		double perc = extentOfDamage(year)/100;
		if (state == 0){
			realhp = hp;
		}
		else{
			realhp = hp - 25;
		}
		if (odometer == 0 && oilChangeFreq == 0){
			return realhp;
		}
		realhp = realhp - (realhp * (((odometer + oilChangeFreq)*2.12)/100 ) * perc);
		return realhp;
	}
	
	public double realTQ(double tq, int oilChangeFreq, int odometer, int state, int year){
		double realtq = 0.0;
		double perc = extentOfDamage(year);
		if (state == 0){
			realtq = tq;
		}
		else{
			realtq = tq - 15;
		}
		if (odometer == 0 && oilChangeFreq == 0){
			return realtq;
		}
		realtq = realtq - (realtq * (((odometer + oilChangeFreq)*2.5)/100 ) * perc);
		return realtq;
	}
	
	public double quarterMile(double hp, double weight, int oilChangeFreq, int odometer, int state, int year){
		double realhp = realHP(hp, oilChangeFreq, odometer, state, year);
		return 6.269*java.lang.Math.cbrt((weight/realhp));
	}
	
	public double zeroToSixty(double hp, double weight, int oilChangeFreq, int odometer, int state, int year){
		double realhp = realHP(hp, oilChangeFreq, odometer, state, year);
		return ((weight * .454)/(realhp * .9));
	}
	
	public static void main(String[] args) {
		// for testing only
	}
}
