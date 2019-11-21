import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

public class Simulator {

	private static ArrayList<ArrayList<Seat>> grid = new ArrayList<>();
	private static Lock gridLock = new ReentrantLock(); /// TODO Comment - javadoc

	public static void main(String[] args) {

		int n, m, k;
		ArrayList<User> userList = new ArrayList<>();

		Scanner inputScanner = new Scanner(System.in);
		n = inputScanner.nextInt();
		m = inputScanner.nextInt();
		inputScanner.nextLine();

		for (int i = 0; i < n; i++) {
			ArrayList<Seat> seats = new ArrayList<>();
			int row = 'A' + i;
			for (int j = 0; j < m; j++) {
				seats.add(new Seat(Character.toString(row) + j));
			}
			grid.add(seats);
		}

		k = inputScanner.nextInt();
		inputScanner.nextLine();
		for (int i = 0; i < k; i++) {
			String temp = inputScanner.nextLine();
			String[] tempLst = temp.split(" ");
			ArrayList<Seat> tempSeats = new ArrayList<>();
			for (int j = 1; j < tempLst.length; j++) {
				int row = tempLst[j].charAt(0) - 'A';
				int col = Integer.parseInt(tempLst[j].substring(1));
				tempSeats.add(grid.get(row).get(col));
			}
			userList.add(new User(tempLst[0], tempSeats));
		}

		Logger.InitLogger();
		ExecutorService executor = Executors.newFixedThreadPool(k);

		// Create and launch K threads
		for (int i = 0; i < k; i++) {
			executor.execute(userList.get(i));
		}

		executor.shutdown();

		// Wait until all tasks are finished
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS); // TODO check
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		// TODO: Are we supposed to align E seats with T's?
		String stateTemplate = "%s:%s";
		for (int i = 0; i < grid.size(); i++) {
			for (int j = 0; j < grid.get(i).size(); j++) {
				if (grid.get(i).get(j).getTakenBy() != null) {
					if (j != grid.get(i).size() - 1)
						System.out.print(String.format(stateTemplate, "T", grid.get(i).get(j).getTakenBy() + " "));
					else
						System.out.print(String.format(stateTemplate, "T", grid.get(i).get(j).getTakenBy()));
				} else {
					if (j != grid.get(i).size() - 1)
						System.out.print(String.format(stateTemplate, "E", " "));
					else
						System.out.print(String.format(stateTemplate, "E", ""));
				}
			}
			System.out.print("\n");
		}
	}

	public static class User implements Runnable {

		private String name;
		private ArrayList<Seat> seatList;

		public User(String name, ArrayList<Seat> seatList) {
			this.name = name;
			this.seatList = seatList;
		}

		@Override
		public void run() {
			int trial = 0;
			Random rndm = new Random();
			boolean giveUp = false;

			StringBuilder seats = new StringBuilder();
			for (Seat seat: seatList) {
				seats.append(seat.getName());
			}

			while (true) {
				trial++;
				gridLock.lock();
				for (Seat seat: seatList) {
					if (seat.getTakenBy() != null) {
						giveUp = true;
						break;
					}
				}
				if (!giveUp) {
					int chance = rndm.nextInt(10);
					if (chance != 0) {
						seatList.forEach((Seat seat) -> {seat.setTakenBy(name);});
						gridLock.unlock();
						try {
							Thread.sleep(100);
							String comment = String.format("Reservation success for %s after %d trial(s)!",
									name, trial);
							Logger.LogSuccessfulReservation(name, seats.toString(), System.nanoTime(), comment);
							break;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					else {
						gridLock.unlock();
						String comment = String.format("Database failure for %s, trying again... Trial no: %d",
								name, trial);
						Logger.LogDatabaseFailiure(name, seats.toString(), System.nanoTime(), comment);
						try {
							Thread.sleep(100);
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
				}
				else {
					gridLock.unlock();
					String comment = String.format("%s has failed reservation, so gives up... Has tried" +
							" to reserve: %d times", name, trial);
					Logger.LogFailedReservation(name, seats.toString(), System.nanoTime(), comment);
					break; // TODO: Direct termination?
				}
			}
		}
	}
}
