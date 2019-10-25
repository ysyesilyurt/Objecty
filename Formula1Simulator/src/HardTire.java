import java.util.HashMap;

import static java.lang.Math.min;

public class HardTire extends Tire {

	public HardTire() {
		this.speed = 275;
		this.degradation = 0;
	}

	@Override
	public void tick(TrackFeature f) {
		degradation += f.getTypeMultiplier() * f.getRoughness() * 1.0;
		speed = (speed < 100) ? speed : speed - min(75, degradation) * 0.25; // TODO: if speed == 100 then I continue decrease!
	}

	@Override
	public Tire changeTire() {
		/* HardTire -> SoftTire */
		return new SoftTire();
	}
}
