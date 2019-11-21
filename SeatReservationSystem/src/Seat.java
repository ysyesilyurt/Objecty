public class Seat {

	private String name;
	private String takenBy;

	public Seat(String name) {
		this.name = name;
		this.takenBy = null;
	}

	public synchronized String getName() {
		return name;
	}

	public synchronized void setName(String name) {
		this.name = name;
	}

	public synchronized String getTakenBy() {
		return takenBy;
	}

	public synchronized void setTakenBy(String takenBy) {
		this.takenBy = takenBy;
	}
}
