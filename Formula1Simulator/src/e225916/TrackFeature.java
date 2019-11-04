package e225916;

public abstract class TrackFeature {

	protected int featureNo;
	protected TurnDirection turnDirection;
	protected double distance;
	protected double roughness;
	protected double typeMultiplier;

	public int getFeatureNo() {
		return featureNo;
	}

	public double getRoughness() {
		return roughness;
	}

	public double getDistance() {
		return distance;
	}

	public TurnDirection getTurnDirection() {
		return turnDirection;
	}

	public void setFeatureNo(int featureNo) {
		this.featureNo = featureNo;
	}

	public void setTurnDirection(TurnDirection turnDirection) {
		this.turnDirection = turnDirection;
	}

	public void setDistance(double distance) {
		this.distance = distance;
	}

	public void setRoughness(double roughness) {
		this.roughness = roughness;
	}

	abstract public double getTypeMultiplier();

	abstract public void setTypeMultiplier(double newTypeMultiplier);
}
