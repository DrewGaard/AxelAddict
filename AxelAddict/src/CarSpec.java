public class CarSpec {
	
	private String Trim;
	private String Transmission;
	private int Horsepower;
	private int PeakHP;
	private int Torque;
	private int PeakTQ;
	private String EngineType;
	private String DriveTrain;
	private String GasType;
	private double Weight;
	private double Displacement;
	private double MPGCity;
	private double MPGHighway;
	private double BaseValue;
	private double ResaleValue;
	private int Cylinder;
	private String Picture;
	
	public CarSpec(String trim, String transmission, int horsepower, int peakHP, int torque, int peakTQ,
			String engineType, String driveTrain, String gasType, double weight, double displacement, double mPGCity,
			double mPGHighway, double baseValue, double resaleValue, String picture) {
		super();
		Trim = trim;
		Transmission = transmission;
		Horsepower = horsepower;
		PeakHP = peakHP;
		Torque = torque;
		PeakTQ = peakTQ;
		EngineType = engineType;
		DriveTrain = driveTrain;
		GasType = gasType;
		Weight = weight;
		Displacement = displacement;
		MPGCity = mPGCity;
		MPGHighway = mPGHighway;
		BaseValue = baseValue;
		ResaleValue = resaleValue;
		Picture = picture;
	}
	public CarSpec() {
		// TODO Auto-generated constructor stub
	}
	public String getPicture() {
		return Picture;
	}
	public void setPicture(String picture) {
		Picture = picture;
	}
	public String getTrim() {
		return Trim;
	}
	public void setTrim(String trim) {
		Trim = trim;
	}
	public String getTransmission() {
		return Transmission;
	}
	public void setTransmission(String transmission) {
		Transmission = transmission;
	}
	public int getHorsepower() {
		return Horsepower;
	}
	public void setHorsepower(int horsepower) {
		Horsepower = horsepower;
	}
	public int getPeakHP() {
		return PeakHP;
	}
	public void setPeakHP(int peakHP) {
		PeakHP = peakHP;
	}
	public int getTorque() {
		return Torque;
	}
	public void setTorque(int torque) {
		Torque = torque;
	}
	public int getPeakTQ() {
		return PeakTQ;
	}
	public void setPeakTQ(int peakTQ) {
		PeakTQ = peakTQ;
	}
	public String getEngineType() {
		return EngineType;
	}
	public void setEngineType(String engineType) {
		EngineType = engineType;
	}
	public String getDriveTrain() {
		return DriveTrain;
	}
	public void setDriveTrain(String driveTrain) {
		DriveTrain = driveTrain;
	}
	public String getGasType() {
		return GasType;
	}
	public void setGasType(String gasType) {
		GasType = gasType;
	}
	public double getWeight() {
		return Weight;
	}
	public void setWeight(double weight) {
		Weight = weight;
	}
	public double getDisplacement() {
		return Displacement;
	}
	public void setDisplacement(double displacement) {
		Displacement = displacement;
	}
	public double getMPGCity() {
		return MPGCity;
	}
	public void setMPGCity(double mPGCity) {
		MPGCity = mPGCity;
	}
	public double getMPGHighway() {
		return MPGHighway;
	}
	public void setMPGHighway(double mPGHighway) {
		MPGHighway = mPGHighway;
	}
	public double getBaseValue() {
		return BaseValue;
	}
	public void setBaseValue(double baseValue) {
		BaseValue = baseValue;
	}
	public double getResaleValue() {
		return ResaleValue;
	}
	public void setResaleValue(double resaleValue) {
		ResaleValue = resaleValue;
	}
	public void setCylinder(int cylinder) {
		Cylinder  = cylinder;
	}
	public int getCylinder() {
		return Cylinder;
	}
}
