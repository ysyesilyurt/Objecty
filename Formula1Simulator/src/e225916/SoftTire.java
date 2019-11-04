package e225916;

import static java.lang.Math.min;

public class SoftTire extends Tire {

	public SoftTire() {
		this.speed = 350;
		this.degradation = 0;
	}

	@Override
	public void tick(TrackFeature f) {
		degradation += f.getTypeMultiplier() * f.getRoughness() * 1.2;
		speed = (speed < 100) ? speed : speed - min(75, degradation) * 0.25;
	}

	@Override
	public Tire changeTire() {
		/* SoftTire -> MediumTire */
		return new MediumTire();
	}
}
