package e225916;

public class LowSpeedTurn extends TrackFeature {

	public LowSpeedTurn(int turnNo, TurnDirection direction, double distance, double roughness) {
		this.featureNo = turnNo;
		this.turnDirection = direction;
		this.distance = distance;
		this.roughness = roughness;
	}

	public double getTypeMultiplier() {
		return 1.3;
	}
}
