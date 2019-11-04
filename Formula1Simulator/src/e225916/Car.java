package e225916;

public class Car {

	private int carNo;
	private String driverName;
	private double totalTime;
	private Tire tire;

	public Car() {
		/* */
	}

	public Car(String driverName, int carNo, Tire tire) {
		this.driverName = driverName;
		this.carNo = carNo;
		this.tire = tire;
	}

	public Tire getTire() {
		return tire;
	}

	public void setTire(Tire tire) {
		this.tire = tire;
	}

	public String getDriverName() {
		return driverName;
	}

	public void setDriverName(String driverName) {
		this.driverName = driverName;
	}

	public int getCarNo() {
		return carNo;
	}

	public void setCarNo(int carNo) {
		this.carNo = carNo;
	}

	public double getTotalTime() {
		return totalTime;
	}

	public void setTotalTime(double totalTime) {
		this.totalTime = totalTime;
	}

	public void tick(TrackFeature feature) {
		totalTime += (feature.getDistance() / tire.getSpeed()) + Math.random();
		tire.tick(feature);
		if (tire.getDegradation() > 70) {
			/* Pit Stop */
			tire = tire.changeTire();
			totalTime += 25;
		}
	}

}
