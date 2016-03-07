package pippin;

/**
 * Description of Class DataMemory: contains the variable for the pippin data
 * memory
 * 
 * @author Jesse Mallen & Abhijeet Patil
 * @version 12/10/04
 */

public class DataMemory {
	private int[]	dataMemory;
	private int		size;

	/**
	 * Responsibility: Creates a DataMemory object
	 */
	public DataMemory() {
		dataMemory = new int[32];
		size = 0;
	}

	/**
	 * Add an item to the data memory at the specified position.
	 * 
	 * @param item
	 *            the item to the data memory (Precondition: size must be at
	 *            least one element less than capacity so that an item may be
	 *            added.)
	 * @param position
	 *            the position at which to add item (Precondition: must be
	 *            between the data memory's capacity (exclusive) and zero
	 *            (inclusive).)
	 * @throws java.lang.ArrayIndexOutOfBoundsException
	 *             if the user tries to execute an already executed set of
	 *             instructions
	 */
	public void set(int item, int position)
			throws ArrayIndexOutOfBoundsException {
		dataMemory[position] = item;
		size++;
	}

	/**
	 * Get the item at the specified position in the Data Memory.
	 * 
	 * @param position
	 *            the position from which to get an item
	 * @return a reference to the item at the specified position
	 * @throws java.lang.IllegalArgumentException
	 *             if the position doesn't contain anything
	 */
	public int get(int position) throws IllegalArgumentException {
		int anElement = (int) dataMemory[position];
		return anElement;
	}

	/**
	 * Get the capacity of the data memory
	 * 
	 * @return this data memory's capacity
	 */
	public int getCapacity() {
		int itsCapacity = dataMemory.length;
		return itsCapacity;
	}

	/**
	 * Get the size of the data memory
	 * 
	 * @return this data memory's size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Responsibility: resets all instance variables to their default values.
	 */
	public void reset() {
		int empty = 0;
		for (int i = 0; i < this.getCapacity(); i++) {
			this.set(empty, i);
		}
	}

	/**
	 * Get a String representation of the items in the data memory.
	 * 
	 * @return a String representing all of the items in this data memory
	 */
	public String toString() {
		String report = "";
		for (int i = 0; i < this.getCapacity(); i++) {
			int aNumber = this.get(i);
			report = report + i + ": " + aNumber + "\n";
		}
		return report;
	}
}