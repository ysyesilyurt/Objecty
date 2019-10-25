import java.util.ArrayList;

public class Track {

  private String trackName;
  private ArrayList<TrackFeature> featureList;
  private boolean isClockwise;

  public Track() {
    // Fill this method
  }

  public Track(String trackName, ArrayList<TrackFeature> featureList, boolean isClockwise) {
    // Fill this method
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
    // Fill this method
  }

  public TrackFeature getNextFeature() {
    // Fill this method
  }

  public void addFeature(TrackFeature feature) {
    // Fill this method
  }

  public boolean isValidTrack() {
    // Fill this method
  }
}
