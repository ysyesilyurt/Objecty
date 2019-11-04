package e225916;

public class Straight extends TrackFeature {

	public Straight(int turnNo, TurnDirection direction, double distance, double roughness) {
		this.featureNo = turnNo;
		this.turnDirection = direction;
		this.distance = distance;
		this.roughness = roughness;
		this.typeMultiplier = 1.0;
	}

	@Override
	public double getTypeMultiplier() {
		return this.typeMultiplier;
	}

	@Override
	public void setTypeMultiplier(double newTypeMultiplier) {
		this.typeMultiplier = newTypeMultiplier;
	}
}
