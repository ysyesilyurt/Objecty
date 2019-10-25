import java.util.ArrayList;

public class Session {

  private Track track;
  private ArrayList<Team> teamList;
  private int totalLaps;

  public Session() {
    // Fill this method
  }

  public Session(Track track, ArrayList<Team> teamList, int totalLaps) {
    // Fill this method
  }

  public Track getTrack() {
    return track;
  }

  public void setTrack(Track track) {
    this.track = track;
  }

  public ArrayList<Team> getTeamList() {
    return teamList;
  }

  public void setTeamList(ArrayList<Team> teamList) {
    this.teamList = teamList;
  }

  public int getTotalLaps() {
    return totalLaps;
  }

  public void setTotalLaps(int totalLaps) {
    this.totalLaps = totalLaps;
  }

  public void simulate() {
    // Fill this method
  }

  public String printWinnerTeam() {
    // Fill this method
  }

  private String printTimingTable() {
    // Fill this method
  }
}
