public class SoftTire extends Tire {

	public SoftTire() {
		this.speed = 350;
		this.degradation = 0;
	}

	@Override
	public void tick(TrackFeature f) {
		this.speed = 350;
		this.degradation = 0;
	}

	@Override
	public Tire changeTire() {
		/* SoftTire -> MediumTire */
		return new MediumTire();
	}
}
