public class MediumTire extends Tire {

	public MediumTire() {
		this.speed = 310;
		this.degradation = 0;
	}

	@Override
	public void tick(TrackFeature f) {
		this.speed = 310;
		this.degradation = 0;
	}

	@Override
	public Tire changeTire() {
		/* MediumTire -> SoftTire */
		return new SoftTire();
	}
}
