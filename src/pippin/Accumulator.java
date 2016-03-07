package pippin;

/**
 * Description of Class Accumulator: Holds a single integer for the PIPPIN
 * program. <br>
 * Responsiblities: Sets & returns that value.
 * 
 * @author Jesse Mallen & Abhijeet Patil
 * @version 12/8/2004
 */

public class Accumulator {
	private int	accumulator;

	/**
	 * Responsibility: Creates a Accumulator object
	 */
	public Accumulator() {
		accumulator = 0;
	}

	/**
	 * Responsibility: Sets the accumulator to a given value.
	 * 
	 * @param aNumber
	 *            given value
	 */
	public void setAccumulator(int aNumber) {
		accumulator = aNumber;
	}

	/**
	 * Responsibility: Adds a given value to the accumulator.
	 * 
	 * @param aNumber
	 *            given value
	 */
	public void add(int aNumber) {
		accumulator += aNumber;
	}

	/**
	 * Responsibility: Subtracts a given value from the accumulator.
	 * 
	 * @param aNumber
	 *            given value
	 */
	public void subtract(int aNumber) {
		accumulator -= aNumber;
	}

	/**
	 * Responsibility: Multiplies the accumulator by a given value.
	 * 
	 * @param aNumber
	 *            given value
	 */
	public void multiply(int aNumber) {
		accumulator *= aNumber;
	}

	/**
	 * Responsibility: Divides the accumulator by a given value.
	 * 
	 * @param aNumber
	 *            given value
	 */
	public void divide(int aNumber) {
		accumulator /= aNumber;
	}

	/**
	 * Responsibility: resets all instance variables to their default values.
	 */
	public void reset() {
		accumulator = 0;
	}

	/**
	 * Responsibility: Returns the value of the accumulator.
	 * 
	 * @return the value of the accumulator
	 */
	public int getAccumulator() {
		return accumulator;
	}
}