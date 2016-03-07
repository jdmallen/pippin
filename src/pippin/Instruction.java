package pippin;

/**
 * Description of Class Instruction: contains the operator and operand for an
 * instruction
 * 
 * @author Jesse Mallen & Abhijeet Patil
 * @version 12/10/04
 */

class Instruction {
	// instance variables
	private String	operator;
	private String	operand;

	/**
	 * Constructor for objects of class Instruction
	 */
	public Instruction(String a, String b) {
		operator = a;
		operand = b;
	}

	/**
	 * Constructor for objects of class Instruction
	 */
	public Instruction(String a) {
		operator = a;
		operand = "none";
	}

	/**
	 * Responsibility: retrives operator
	 * 
	 * @return the operator
	 */
	public String getOperator() {
		return operator;
	}

	/**
	 * Responsibility: retrives operand
	 * 
	 * @return the operand
	 */
	public String getOperand() {
		return operand;
	}

	public boolean hasOperand() {
		String nullString = "none";
		String theOperand = this.getOperand();
		boolean operandExists = true;
		if (theOperand.equals(nullString))
			operandExists = false;
		return operandExists;
	}
}