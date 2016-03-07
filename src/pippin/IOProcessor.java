package pippin;
import java.io.*;
import java.util.Map;
import java.util.HashMap;
import java.util.StringTokenizer;

/**
 * Responsibility: processes the input and passes it to the CPU for decoding and
 * executing
 * 
 * @author Jesse Mallen & Abhijeet Patil
 * @version 12/10/04
 */
public class IOProcessor {
	// instance variables
	private BufferedReader	in;

	/**
	 * Constructor for objects of class IOProcessor
	 */
	public IOProcessor() {
		in = null;
	}

	/**
	 * Responsibility: to determine whether or not the input file is indeed an
	 * instruction file
	 * 
	 * @param file
	 *            the file to be recognized
	 * @throws java.io.IOException
	 *             if the file is not provided
	 */
	public void recognize(File file) throws IOException {
		in = new BufferedReader(new FileReader(file));
		String firstLine = in.readLine();
		StringTokenizer t = new StringTokenizer(firstLine);
		String firstPiece = t.nextToken();
		String firstPieceCaps = firstPiece.toUpperCase();
		CPU aCpu = new CPU();
		aCpu.createHashMap();
		Map aHashMap = aCpu.getHashMap();
		boolean keyExists = aHashMap.containsKey(firstPieceCaps);
		if (!keyExists) {
			throw new NotAnInstructionException(
					"File is not an instruction file. Please reset and retry.");
		}
	}

	/**
	 * Reads the instruction file, and returns a buffered reader of the file.
	 * 
	 * @param file
	 *            the File to be read
	 * @param aCpu
	 *            the cpu to use to read the input file
	 * @throws java.io.IOException
	 *             if the file doesn't exist
	 */
	public void readFile(File file, CPU aCpu) throws IOException {
		BufferedReader in = null;
		try {
			this.recognize(file);
			in = new BufferedReader(new FileReader(file));
			aCpu.read(in);
		}

		finally {
			if (in != null)
				in.close();
		}
	}
}