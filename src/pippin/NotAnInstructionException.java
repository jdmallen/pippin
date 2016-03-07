package pippin;
import java.io.IOException;

/**
 * Responsibility: stops the program if the file provided is not a set of Pippin
 * instructions.
 * 
 * @author Jesse Mallen & Abhijeet Patil
 * @version 12/10/04
 */
public class NotAnInstructionException extends IOException {
	/**
	 * Default constructor
	 */
	public NotAnInstructionException() {
	}

	/**
	 * Constructor with reason
	 * 
	 * @param reason
	 *            the reason the exception was thrown
	 */
	public NotAnInstructionException(String reason) {
		super(reason);
	}
}
