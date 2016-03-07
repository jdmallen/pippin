package pippin;

/**
 * Description of Class ProgramCounter: Contains the order of instructions to be
 * executed from InstructionMemory. <br>
 * Responsiblities: Increments, sets, and returns the value of the PIPPIN
 * program counter.
 * 
 * @author Jesse Mallen & Abhijeet Patil
 * @version 12/8/2004
 */

public class ProgramCounter {
	private int	programCounter;

	/**
	 * Responsibility: Creates a ProgramCounter object
	 */
	public ProgramCounter() {
		programCounter = 0;
	}

	/**
	 * Responsibility: Increments the program counter.
	 */
	public void increment() {
		programCounter++;
	}

	/**
	 * Responsibility: Sets the program counter to a given value.
	 * 
	 * @param aNumber
	 *            the number to set it to
	 */
	public void set(int aNumber) {
		programCounter = aNumber;
	}

	/**
	 * Responsibility: resets all instance variables to their default values.
	 */
	public void reset() {
		programCounter = 0;
	}

	/**
	 * Responsibility: Returns the value of the programcounter.
	 * 
	 * @return the value of the program counter
	 */
	public int get() {
		return programCounter;
	}
}