import java.util.*;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;

public class Main {

	private static ArrayList<ArrayList<Seat>> grid = new ArrayList<>();

	public static void main(String[] args) {

		int n, m, k;
		ArrayList<User> userList = new ArrayList<>();

		Scanner inputScanner = new Scanner(System.in);
		n = inputScanner.nextInt();
		m = inputScanner.nextInt();
		inputScanner.nextLine();

		for (int i = 0; i < n; i++) {
			ArrayList<Seat> seats = new ArrayList<>();
			char row = (char) ('A' + i);
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
			userList.add(new User(tempLst[0], normalizeSeats(tempSeats)));
		}

		ExecutorService executor = Executors.newFixedThreadPool(k);
		Logger.InitLogger();

		/* Create and launch K threads */
		for (int i = 0; i < k; i++) {
			executor.execute(userList.get(i));
		}

		executor.shutdown();

		/* Wait until all tasks are finished */
		try {
			executor.awaitTermination(Long.MAX_VALUE, TimeUnit.NANOSECONDS);
		} catch (InterruptedException e) {
			e.printStackTrace();
		}

		String stateTemplate = "%s:%s";
		for (int i = 0; i < grid.size(); i++) {
			for (int j = 0; j < grid.get(i).size(); j++) {
				if (grid.get(i).get(j).getTakenBy() != null) {
					if (j != grid.get(i).size() - 1)
						System.out.print(String.format(stateTemplate, "T",
								grid.get(i).get(j).getTakenBy() + " "));
					else
						System.out.print(String.format(stateTemplate, "T",
								grid.get(i).get(j).getTakenBy()));
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

	/**
	 * A static method of Main class which is used to normalize the order of
	 * given seat list according to their names in Lexicographical order.
	 *
	 * Normalization is done on users' seatlist to make sure we do not encounter
	 * a deadlock case, i.e there shouldn't be a deadlock scenerio as the following:
	 *
	 * Yavuz has A0 wants A1
	 * Selim has A1 wants A0 => This guy should've taken A0 before A1
	 * 						 => Namely his seatlist must have been normalized before
	 *
	 * @param seats
	 * @return
	 */
	public static ArrayList<Seat> normalizeSeats(ArrayList<Seat> seats) {
		ArrayList<Seat> newSeats = new ArrayList<>();
		Map<String, Seat> seatMap = new HashMap<>();
		for (Seat seat : seats) {
			seatMap.put(seat.getName(), seat);
		}
		List<String> sortedSeatNames = new ArrayList<>(seatMap.keySet());
		Collections.sort(sortedSeatNames);
		for (String name : sortedSeatNames) {
			newSeats.add(seatMap.get(name));
		}
		return newSeats;
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
			String giveUpSeat = null;

			StringBuilder seats = new StringBuilder();
			for (Seat seat: seatList) {
				seats.append(seat.getName());
			}

			while (true) {
				trial++;
				for (int i = 0; i < seatList.size(); i++) {
					/* Traverse all the seats on wishlist and initiate processing
					*  on them if not being processed or finalized by another user */
					if (!seatList.get(i).reserve(name)) {
						giveUpSeat = seatList.get(i).getName();
						for (int j = 0; j < i; j++) {
							/* Unreserve all the seats that are reserved earlier
							*  if failed to reserve one of the seats from wishlist */
							seatList.get(j).unReserve();
						}
						break;
					}
				}
				if (giveUpSeat == null) {
					int chance = rndm.nextInt(10);
					if (chance != 0) {
						try {
							for (Seat seat: seatList) {
								/* Since successfully managed to reserve all the seats
								 *  on the wishlist, declare the reserved seats as
								 *  "Finalized".*/
								seat.finalize();
							}
							Thread.sleep(50);
							String comment = String.format("Reservation success for" +
											" %s after %d trial(s)!",
									name, trial);
							Logger.LogSuccessfulReservation(name, seats.toString(),
									System.nanoTime(), comment);
							break;
						} catch (InterruptedException e) {
							e.printStackTrace();
						}
					}
					else {
						for (Seat seat: seatList) {
							/* DbFailure - Unreserve all the seats that are reserved earlier since
							 * other users that want this seat are able to reserve the seat now */
							seat.unReserve();
						}
						String comment = String.format("Database failure for %s," +
										" trying again... Trial no: %d",
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
					String comment = String.format("%s has failed to reserve %s," +
							" so gives up... Has tried" +
							" to reserve: %d times", name, giveUpSeat, trial);
					Logger.LogFailedReservation(name, seats.toString(),
							System.nanoTime(), comment);
					break;
				}
			}
		}
	}
}
