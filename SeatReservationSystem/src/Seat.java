public class Seat {

	private String name;
	private String takenBy;
	private boolean isBeingProcessed;

	public Seat(String name) {
		this.name = name;
		this.takenBy = null;
		this.isBeingProcessed = false;
	}

	public synchronized String getName() {
		return name;
	}

	public synchronized void setName(String name) {
		this.name = name;
	}

	/**
	 * If takenBy is null then it means this seat is available for "processing"
	 * Otherwise it means that this seat is being processed right now and seat may be reserved or not in the future
	 * @return String
	 */
	public synchronized String getTakenBy() {
		return takenBy;
	}

	/**
	 * This method is called from reserve() when a user initiates processing this seat
	 * @param takenBy
	 */
	private synchronized void setTakenBy(String takenBy) {
		this.takenBy = takenBy;
	}

	private boolean isBeingProcessed() {
		return isBeingProcessed;
	}

	private void setBeingProcessed(boolean beingProcessed) {
		this.isBeingProcessed = beingProcessed;
	}

	/**
	 * Users call this method on seat objects while they try to reserve seats. Since object is
	 * synchronized only one user at a time may enter this method. But users may wait on the seats
	 * due to following fact: It may be the case that, after user returns from this method it fails
	 * to reserve a seat that it wants in the future and it unreserves all the seats that it reserved earlier
	 * Hence in this case all the users that wanted this seat earlier should now be able to try to reserve it.
	 * @param name
	 * @return boolean (indicates whether processing was successful or not)
	 */
	public synchronized boolean reserve(String name) {
		try {
			while (isBeingProcessed()) {
				/* If this seat is being processed currently, then wait on it */
				this.wait();
			}
			setBeingProcessed(true);

			if (getTakenBy() == null) {
				/* Check if this seat is appropriate to process,
				If it is then reserve it for now (may be unreserved in the future) */
				setTakenBy(name);
				return true;
			}
			else {
				setBeingProcessed(false);
				return false;
			}
		}
		catch (InterruptedException e) {
			e.printStackTrace();
			return false;
		}
	}

	/**
	 * Called by a user that reserved this seat earlier but failed to reserve a seat
	 * on its wishlist, so it also yields this seat which is reserved earlier and
	 * notifies a thread that waits on it (if exists)
	 */
	public synchronized void unReserve() {
		setTakenBy(null); // Declare that seat is not taken anymore
		setBeingProcessed(false);
		this.notify();
	}

	/**
	 * Called by a user that reserved this seat earlier and also managed to reserve all other
	 * seats that it had on its wishlist. Semantically it declares that this seat is reserved and
	 * it also won't be available in the future, namely "finalized"
	 */
	public synchronized void finalize() {
		setBeingProcessed(false);
		this.notify();
	}
}
