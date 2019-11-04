package e225916;

public class LowSpeedTurn extends TrackFeature {

	public LowSpeedTurn(int turnNo, TurnDirection direction, double distance, double roughness) {
		this.featureNo = turnNo;
		this.turnDirection = direction;
		this.distance = distance;
		this.roughness = roughness;
		this.typeMultiplier = 1.3;
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
