import java.util.ArrayList;

public class Track {

	private String trackName;
	private ArrayList<TrackFeature> featureList;
	private boolean isClockwise;
	private int featureCounter;

	public Track() {
		// TODO leaving this
		this.featureCounter = 0;
	}

	public Track(String trackName, ArrayList<TrackFeature> featureList, boolean isClockwise) {
		this.trackName = trackName;
		this.featureList = featureList;
		this.isClockwise = isClockwise;
		this.featureCounter = 0;
	}

	public String getTrackName() {
		return trackName;
	}

	public void setTrackName(String trackName) {
		this.trackName = trackName;
	}

	public ArrayList<TrackFeature> getFeatureList() {
		return featureList;
	}

	public void setFeatureList(ArrayList<TrackFeature> featureList) {
		this.featureList = featureList;
	}

	public boolean isClockwise() {
		return isClockwise;
	}

	public void setClockwise(boolean clockwise) {
		isClockwise = clockwise;
	}

	public int getTrackLength() {
		return featureList.size();
	}

	public TrackFeature getNextFeature() {
		TrackFeature feature = featureList.get(featureCounter);
		featureCounter = (featureCounter + 1 == featureList.size()) ? 0 : featureCounter + 1;
		return feature;
	}

	public void addFeature(TrackFeature feature) {
		featureList.add(feature);
	}

	public boolean isValidTrack() {
		int lcount = 0, rcount = 0;
		if (this.checkStraightCondition()) {
			for (TrackFeature feature : featureList) {
				if (feature.getTurnDirection() == TurnDirection.LEFT)
					lcount++;
				else if (feature.getTurnDirection() == TurnDirection.RIGHT)
					rcount++;
			}

			if (isClockwise && rcount == lcount + 4 || !isClockwise && lcount == rcount + 4)
				return true;
		}
		return false;
	}

	private boolean checkStraightCondition() {
		int min = Integer.MAX_VALUE, max = Integer.MIN_VALUE, minIndex = -1, maxIndex = -1;
		for (int i = 0; i < featureList.size(); i++) {
			if (featureList.get(i).featureNo < min) {
				min = featureList.get(i).featureNo;
				minIndex = i;
			}
			if (featureList.get(i).featureNo > max) {
				max = featureList.get(i).featureNo;
				maxIndex = i;
			}
		}
		if (minIndex != -1 && maxIndex != -1 && maxIndex != minIndex) {
			if (featureList.get(minIndex).getTurnDirection() == TurnDirection.STRAIGHT &&
					featureList.get(maxIndex).getTurnDirection() == TurnDirection.STRAIGHT)
				return true;
		}
		return false;
	}
}
