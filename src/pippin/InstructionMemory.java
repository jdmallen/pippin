package pippin;

/**
 * Description of Class InstructionMemory: contains the variable for the pippin
 * instruction memory
 * 
 * @author Jesse Mallen & Abhijeet Patil
 * @version 12/10/04
 */

public class InstructionMemory {
	private Instruction[]	instructionMemory;
	private int				size;

	/**
	 * Responsibility: Creates a InstructionMemory object
	 */
	public InstructionMemory() {
		instructionMemory = new Instruction[32];
		size = 0;
	}

	/**
	 * Add an item to the instruction memory at the specified position.
	 * 
	 * @param item
	 *            the item to the instruction memory (Precondition: size must be
	 *            at least one element less than capacity so that an item may be
	 *            added.)
	 * @param position
	 *            the position at which to add item (Precondition: must be
	 *            between the instruction memory's capacity (exclusive) and zero
	 *            (inclusive).)
	 * @throws java.lang.ArrayIndexOutOfBoundsException
	 *             if the position does not exist
	 */
	public void set(Instruction item, int position)
			throws ArrayIndexOutOfBoundsException {
		instructionMemory[position] = item;
		size++;
	}

	/**
	 * Get the item at the specified position in the Instruction Memory.
	 * 
	 * @param position
	 *            the position from which to get an item
	 * @return a reference to the item at the specified position
	 * @throws java.lang.IllegalArgumentException
	 *             if the provided value goes beyond the capacity of the array
	 */
	public Instruction get(int position) throws IllegalArgumentException {
		Instruction anInstruction = (Instruction) instructionMemory[position];
		return anInstruction;
	}

	/**
	 * Get the capacity of the instruction memory
	 * 
	 * @return this instruction memory's capacity
	 */
	public int getCapacity() {
		int itsCapacity = instructionMemory.length;
		return itsCapacity;
	}

	/**
	 * Get the size of the instruction memory
	 * 
	 * @return this instruction memory's size
	 */
	public int getSize() {
		return size;
	}

	/**
	 * Responsibility: resets all instance variables to their default values.
	 */
	public void reset() {
		Instruction empty = null;
		for (int i = 0; i < this.getCapacity(); i++) {
			this.set(empty, i);
		}
	}

	/**
	 * Get a String representation of the items in the instruction memory.
	 * 
	 * @return a String representing all of the items in this instruction memory
	 */
	public String toString() {
		int length = this.getSize();
		String report = "";
		for (int i = 0; i < length; i++) {
			Instruction anInstruction = this.get(i);
			Instruction nullInstruction = null;
			if (anInstruction.hasOperand() && anInstruction != nullInstruction)
				report = report + i + ": " + anInstruction.getOperator() + " "
						+ anInstruction.getOperand() + "\n";
			else
				report = report + i + ": " + anInstruction.getOperator() + "\n";
		}
		return report;
	}
}