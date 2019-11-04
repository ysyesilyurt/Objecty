package e225916;

import static java.lang.Math.min;

public class MediumTire extends Tire {

	public MediumTire() {
		this.speed = 310;
		this.degradation = 0;
	}

	@Override
	public void tick(TrackFeature f) {
		degradation += f.getTypeMultiplier() * f.getRoughness() * 1.1;
		speed = (speed < 100) ? speed : speed - min(75, degradation) * 0.25;
	}

	@Override
	public Tire changeTire() {
		/* MediumTire -> SoftTire */
		return new SoftTire();
	}
}
