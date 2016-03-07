package pippin;
import java.io.BufferedReader;
import java.util.StringTokenizer;
import java.util.Map;
import java.util.HashMap;
import java.io.IOException;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.JOptionPane;
import javax.swing.Timer;
import java.util.NoSuchElementException;

/**
 * Description: the CPU for a PIPPIN computer <br>
 * Responsiblities: create a panelCreator for creating panels to create panels
 * to populate a JFrame
 * 
 * @author Jesse Mallen & Abhijeet Patil
 * @version December 5, 2004
 */
public class CPU {
	private DataMemory			dataMemory;
	private InstructionMemory	instructionMemory;
	private Accumulator			accumulator;
	private ProgramCounter		programCounter;
	private Map					instructions;

	/**
	 * Responsibility: Constructor for objects of class CPU; passed a set of
	 * instructions.
	 */
	public CPU() {
		dataMemory = new DataMemory();
		instructionMemory = new InstructionMemory();
		accumulator = new Accumulator();
		programCounter = new ProgramCounter();
		instructions = new HashMap(19);
	}

	/**
	 * Responsibility: returns the CPU hash map for access in other classes.
	 * 
	 * @return the hash map of the instructions
	 */
	public Map getHashMap() {
		return instructions;
	}

	/**
	 * Responsibility: processes and executes the instructions from the read
	 * method all the way through.
	 * 
	 * @throws java.lang.NullPointerException
	 *             if there are no instructions to execute
	 */
	public void executePippin() throws NullPointerException {
		createHashMap();
		int initialCount = programCounter.get();
		Instruction firstInstruction = (Instruction) instructionMemory
				.get(initialCount);
		String firstInstructionName = firstInstruction.getOperator();
		Executable process = (Executable) instructions
				.get(firstInstructionName);
		process.execute(firstInstruction);
		int currentCount = programCounter.get();
		while (currentCount >= 0 && currentCount < 32) {
			Instruction anInstruction = (Instruction) instructionMemory
					.get(currentCount);
			String instructName = anInstruction.getOperator();
			Executable instruction = (Executable) instructions
					.get(instructName);
			instruction.execute(anInstruction);
			currentCount = programCounter.get();
		}
	}

	/**
	 * Responsibility: processes and executes the instructions from the read
	 * method, step by step.
	 * 
	 * @throws java.lang.NullPointerException
	 *             if there are no instructions to execute
	 */
	public void stepPippin() throws NullPointerException {
		createHashMap();
		int currentCount = programCounter.get();
		if (currentCount >= 0 && currentCount < 32) {
			Instruction instruction = (Instruction) instructionMemory
					.get(currentCount);
			String instructionName = instruction.getOperator();
			Executable process = (Executable) instructions.get(instructionName);
			process.execute(instruction);
		}
	}

	/**
	 * Reads the instruction file, line by line, and parses it into usable
	 * Instructions. It then adds the Instructions to the instructionMemory, and
	 * the data to the dataMemory.
	 * 
	 * @param in
	 *            the BufferedReader object from readFile in IOP
	 * @throws IOException .
	 * @throws java.util.NoSuchElementException
	 *             if the file provided was not valid
	 */
	public void read(BufferedReader in) throws IOException,
			NoSuchElementException {
		String input = in.readLine();
		int number = 0;
		Instruction thisInstruction;
		while (input != null && !input.equals("----")) {
			StringTokenizer instructionTokenizer = new StringTokenizer(input);
			String operator1 = instructionTokenizer.nextToken();
			String operator = operator1.toUpperCase();
			if (instructionTokenizer.hasMoreTokens()) {
				String operand = instructionTokenizer.nextToken();
				thisInstruction = new Instruction(operator, operand);
			} else
				thisInstruction = new Instruction(operator);
			instructionMemory.set(thisInstruction, number);
			input = in.readLine();
			number++;
		}
		number = 0;
		input = in.readLine();
		while (input != null && number < 32) {
			int dataMemoryEntry = Integer.parseInt(input);
			dataMemory.set(dataMemoryEntry, number);
			input = in.readLine();
			number++;
		}
	}

	/**
	 * Responsibility: creates a HashMap of all the instructions of the Pippin
	 * Computer
	 */
	public void createHashMap() {
		instructions.put("LOAD", new LOAD());
		instructions.put("STORE", new STORE());
		instructions.put("JUMP", new JUMP());
		instructions.put("JMPZ", new JMPZ());
		instructions.put("NOP", new NOP());
		instructions.put("HALT", new HALT());
		instructions.put("ADD", new ADD());
		instructions.put("SUB", new SUB());
		instructions.put("MUL", new MUL());
		instructions.put("DIV", new DIV());
		instructions.put("AND", new AND());
		instructions.put("NOT", new NOT());
		instructions.put("CMPZ", new CMPZ());
		instructions.put("CMPL", new CMPL());
	}

	/**
	 * Returns the DataMemory instance variable.
	 * 
	 * @return the DataMemory instance variable.
	 */
	public DataMemory getDataMemory() {
		return dataMemory;
	}

	/**
	 * Returns the DataMemory instance variable.
	 * 
	 * @return the DataMemory instance variable.
	 */
	public ProgramCounter getProgramCounter() {
		return programCounter;
	}

	/**
	 * Returns the InstructionMemory instance variable.
	 * 
	 * @return the InstructionMemory instance variable.
	 */
	public InstructionMemory getInstructionMemory() {
		return instructionMemory;
	}

	/**
	 * Returns the Accumulator instance variable.
	 * 
	 * @return the Accumulator instance variable.
	 */
	public Accumulator getAccumulator() {
		return accumulator;
	}

	class LOAD implements Executable {
		public void execute(Instruction a) throws NumberFormatException {
			String b = a.getOperand();
			int numberParsed;
			String number;
			if (b.charAt(0) == '%') {
				number = b.substring(1);
				numberParsed = Integer.parseInt(number);
				accumulator.setAccumulator(numberParsed);
			} else {
				numberParsed = Integer.parseInt(b);
				accumulator.setAccumulator(dataMemory.get(numberParsed));
			}
			programCounter.increment();
		}
	}

	class STORE implements Executable {
		public void execute(Instruction a) throws NumberFormatException {
			String b = a.getOperand();
			int location = Integer.parseInt(b);
			dataMemory.set(accumulator.getAccumulator(), location);
			programCounter.increment();
		}
	}

	class JUMP implements Executable {
		public void execute(Instruction a) throws NumberFormatException {
			String b = a.getOperand();
			int parsedString = Integer.parseInt(b);
			programCounter.set(parsedString);
		}
	}

	class JMPZ implements Executable {
		public void execute(Instruction a) throws NumberFormatException {
			String b = a.getOperand();
			if (accumulator.getAccumulator() == 0) {
				int parsedString = Integer.parseInt(b);
				programCounter.set(parsedString);
			} else
				programCounter.increment();
		}
	}

	class NOP implements Executable {
		public void execute(Instruction a) {
			programCounter.increment();
		}
	}

	class HALT implements Executable {
		public void execute(Instruction a) {
			programCounter.set(-1);
			accumulator.setAccumulator(0);
		}
	}

	class ADD implements Executable {
		public void execute(Instruction a) throws NumberFormatException {
			String b = a.getOperand();
			String number;
			int inputLength = b.length();
			int numberParsed;
			if (b.charAt(0) == '%') {
				number = b.substring(1, inputLength - 1);
				numberParsed = Integer.parseInt(number);
				accumulator.add(numberParsed);
			} else {
				numberParsed = Integer.parseInt(b);
				accumulator.add(dataMemory.get(numberParsed));
			}
			programCounter.increment();
		}
	}

	class SUB implements Executable {
		public void execute(Instruction a) throws NumberFormatException {
			String b = a.getOperand();
			int inputLength = b.length();
			int numberParsed;
			String number;
			if (b.charAt(0) == '%') {
				number = b.substring(1);
				numberParsed = Integer.parseInt(number);
				accumulator.subtract(numberParsed);
			} else {
				numberParsed = Integer.parseInt(b);
				accumulator.subtract(dataMemory.get(numberParsed));
			}
			programCounter.increment();
		}
	}

	class MUL implements Executable {
		public void execute(Instruction a) throws NumberFormatException {
			String b = a.getOperand();
			int numberParsed;
			String number;
			if (b.charAt(0) == '%') {
				number = b.substring(1);
				numberParsed = Integer.parseInt(number);
				accumulator.multiply(numberParsed);
			} else {
				numberParsed = Integer.parseInt(b);
				accumulator.multiply(dataMemory.get(numberParsed));
			}
			programCounter.increment();
		}
	}

	class DIV implements Executable {
		public void execute(Instruction a) throws NumberFormatException {
			String b = a.getOperand();
			int inputLength = b.length();
			int numberParsed;
			String number;
			if (b.charAt(0) == '%') {
				number = b.substring(1, inputLength - 1);
				numberParsed = Integer.parseInt(number);
				accumulator.divide(numberParsed);
			} else {
				numberParsed = Integer.parseInt(b);
				accumulator.divide(dataMemory.get(numberParsed));
			}
			programCounter.increment();
		}
	}

	class AND implements Executable {
		public void execute(Instruction a) throws NumberFormatException {
			String b = a.getOperand();
			int inputLength = b.length();
			int numberParsed;
			String number;
			if (b.charAt(0) == '%') {
				number = b.substring(1, inputLength - 1);
				numberParsed = Integer.parseInt(number);
				if (numberParsed != 0 && accumulator.getAccumulator() != 0)
					accumulator.setAccumulator(1);
				else
					accumulator.setAccumulator(0);
			} else {
				numberParsed = Integer.parseInt(b);
				int numFromDataMemory = dataMemory.get(numberParsed);
				if (numFromDataMemory != 0 && accumulator.getAccumulator() != 0)
					accumulator.setAccumulator(1);
				else
					accumulator.setAccumulator(0);
			}
			programCounter.increment();
		}
	}

	class NOT implements Executable {
		public void execute(Instruction a) {
			if (accumulator.getAccumulator() == 0)
				accumulator.setAccumulator(1);
			else
				accumulator.setAccumulator(0);
			programCounter.increment();
		}
	}

	class CMPZ implements Executable {
		public void execute(Instruction a) throws NumberFormatException {
			String b = a.getOperand();
			int numFromDataMemory = dataMemory.get(Integer.parseInt(b));
			if (numFromDataMemory == 0)
				accumulator.setAccumulator(1);
			else
				accumulator.setAccumulator(0);
			programCounter.increment();
		}
	}

	class CMPL implements Executable {
		public void execute(Instruction a) throws NumberFormatException {
			String b = a.getOperand();
			int numFromDataMemory = dataMemory.get(Integer.parseInt(b));
			if (numFromDataMemory < 0)
				accumulator.setAccumulator(1);
			else
				accumulator.setAccumulator(0);
			programCounter.increment();
		}
	}
}