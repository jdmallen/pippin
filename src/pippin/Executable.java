package pippin;

/**
 * Responsibility: executes a particular PIPPIN instruction
 * 
 * @author Jesse Mallen & Abhijeet Patil
 * @version December 5, 2004
 */

public interface Executable {
	/**
	 * Responsibility: executes a particular PIPPIN instruction
	 * 
	 * @param a
	 *            The instruction to be executed
	 */
	void execute(Instruction a);
}