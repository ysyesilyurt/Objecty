package e225916;

public abstract class Tire {

	protected double speed;
	protected double degradation;

	public double getSpeed() {
		return speed;
	}

	public double getDegradation() {
		return degradation;
	}

	public void setSpeed(double speed) {
		this.speed = speed;
	}

	public void setDegradation(double degradation) {
		this.degradation = degradation;
	}

	abstract public void tick(TrackFeature f);

	abstract public Tire changeTire();
}
