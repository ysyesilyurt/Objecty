package e225916;

import java.util.ArrayList;

public class Session {

	private Track track;
	private ArrayList<Team> teamList;
	private int totalLaps;

	public Session() {
		/* */
	}

	public Session(Track track, ArrayList<Team> teamList, int totalLaps) {
		this.track = track;
		this.teamList = teamList;
		this.totalLaps = totalLaps;
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
		if (track.isValidTrack()) {
			/* Simulate the Race */
			System.out.print(String.format("Track is valid.Strating simulation on %s for %d laps.\n",
					track.getTrackName(), getTotalLaps()));
			for (int i = 0; i < totalLaps; i++) {
				for (int j = 0; j < track.getTrackLength(); j++) {
					TrackFeature feature = track.getNextFeature();
					for (Team team : teamList) {
						for (Car car : team.getCarList()) {
							car.tick(feature);
						}
					}
				}
			}
			/* Race is finished, Construct Timing Table */
			constructTimingTable();
			System.out.print(printWinnerTeam());
			System.out.print(printTimingTable());
		}
		else {
			System.out.print("Track is invalid.Simulation aborted!\n");
			return;
		}
	}

	public String printWinnerTeam() {
    /* Team <team name> wins.<team color 1> [[, <team color 2>,<team color 3>, ...] and
     <team color n>] flags are waving everywhere.*/
    	Team winner = getWinnerTeam();
    	String toast = String.format("Team %s wins.", winner.getName());
    	if (winner != null) {
			for (int i = 0; i < winner.getTeamColors().length; i++) {
				toast = (i == 0) ?
						toast + winner.getTeamColors()[i] :
						(i + 1 == winner.getTeamColors().length) ?
						toast + " and " + winner.getTeamColors()[i] :
						toast + ", " + winner.getTeamColors()[i];
			}
			toast += " flags are waving everywhere.\n";
			return toast;
		}
		else
			return null;
	}

	private String printTimingTable() {
		/* <hrs>:<mins>:<secs>.<ms> */
		String table = "";
		ArrayList<Car> sortedCarList = constructTimingTable();
		for (Car car : sortedCarList) {
			table += String.format("%s(%d): %s\n", car.getDriverName(),
					car.getCarNo(), convertTime(car.getTotalTime()));
		}
		return table;
	}

	private Team getWinnerTeam() {
		double minTime = Integer.MAX_VALUE;
		Team winner = null;
		for (Team team : teamList) {
			for (Car car : team.getCarList()) {
				if (car.getTotalTime() < minTime) {
					minTime = car.getTotalTime();
					winner = team;
				}
			}
		}
		return winner;
	}

	private ArrayList<Car> constructTimingTable() {
		ArrayList<Car> sortedCarList = new ArrayList<>();
		for (Team team : teamList) {
			for (Car car : team.getCarList()) {
				sortedCarList.add(car);
			}
		}
		/* (Insertion) Sort the car list acc to their Total Time */
		int j;
		Car next;
		for (int i = 1; i < sortedCarList.size(); ++i) {
			next = sortedCarList.get(i);
			for (j = i - 1; j >= 0; --j) {
				if (sortedCarList.get(j).getTotalTime() > next.getTotalTime())
					sortedCarList.set(j+1, sortedCarList.get(j));
				else
					break;
			}
			sortedCarList.set(j+1, next);
		}
		return sortedCarList;
	}

	private String convertTime(double totalTime) {
		/* Converts seconds totalTime to <hrs>:<mins>:<secs>.<ms> */
		int hrs = (int) (totalTime / 3600);
		int mins = (int) ((totalTime % 3600) / 60);
		int secs = (int) ((totalTime % 3600) % 60);
		int ms = (int) (((totalTime % 3600) % 60) % 1000);
		return String.format("%d:%d:%d.%d", hrs, mins, secs, ms);
	}
}
