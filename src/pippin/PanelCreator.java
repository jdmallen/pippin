package pippin;
import javax.swing.JPanel;
import javax.swing.JButton;
import javax.swing.JFileChooser;
import javax.swing.JLabel;
import javax.swing.JTextArea;
import javax.swing.JTextField;
import javax.swing.JOptionPane;
import javax.swing.SwingConstants;
import javax.swing.Timer;
import javax.swing.Icon;
import javax.swing.ImageIcon;
import java.io.*;
import java.awt.event.ActionListener;
import java.awt.event.ActionEvent;
import java.awt.BorderLayout;
import java.awt.GridLayout;
import java.util.NoSuchElementException;

/**
 * Responsibility: creates panels to control and view a Pippin computer.
 * 
 * @author Jesse Mallen & Abhijeet Patil
 * @version 12/8/04
 */

public class PanelCreator {
	// Models
	private CPU			_cpu;
	private IOProcessor	_iop;
	private File		_inFile;
	
	private JTextArea	_instructionDisplay;
	private JTextArea	_dataDisplay;
	private JTextField	_accumulatorDisplay;
	private JTextField	_programCounterDisplay;
	private JTextField	_statusBar1;
	private JTextField	_statusBar2;
	private JTextField	_fileNameField;
	private Timer		_timer1;
	private Timer		_timer2;

	/**
	 * Responsibility: Construct a panel creator with the model
	 * 
	 * @param aCPU
	 *            a CPU
	 * @param anIOP
	 *            an I/O Processor
	 */
	public PanelCreator(CPU aCPU, IOProcessor anIOP) {
		_cpu = aCPU;
		_iop = anIOP;
		_inFile = null;
	}

	class ResetListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			reset();
		}
	}

	class ExitListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			int sure = JOptionPane.showConfirmDialog(null, "Are you sure?",
					"Pippin Simulator", JOptionPane.YES_NO_OPTION);
			if (sure == 0)
				System.exit(0);
		}
	}

	class LoadListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				_timer1.stop();
				_timer2.stop();
				_statusBar1.setText("");
				JFileChooser fileChooser = new JFileChooser();
				if (fileChooser.showOpenDialog(null) == JFileChooser.APPROVE_OPTION) {
					_inFile = fileChooser.getSelectedFile();
				}
				_iop.recognize(_inFile);
				_iop.readFile(_inFile, _cpu);

				_statusBar1.setText("File successfully loaded.");
				DataMemory dm = _cpu.getDataMemory();
				InstructionMemory im = _cpu.getInstructionMemory();
				Accumulator ac = _cpu.getAccumulator();
				ProgramCounter pc = _cpu.getProgramCounter();

				_dataDisplay.setText(dm.toString());
				_instructionDisplay.setText(im.toString());
				_programCounterDisplay.setText("" + pc.get());
				_accumulatorDisplay.setText("" + ac.getAccumulator());

				_fileNameField.setText("" + _inFile);
			} catch (NotAnInstructionException exception) {
				_statusBar1
						.setText("File is not an instruction file. Please retry.");
				reset();
			} catch (IOException exception) {
				_statusBar1.setText("You need to supply an instruction file.");
				reset();
			} catch (NullPointerException exception) {
				_statusBar1.setText("You need to supply an instruction file.");
				reset();
			} catch (NoSuchElementException exception) {
				_statusBar1
						.setText("Please supply a proper set of Instruction.");
				reset();
			} catch (ArrayIndexOutOfBoundsException exception) {
				_statusBar1
						.setText("Please supply a proper set of Instruction.");
				reset();
			}
		}
	}

	class StepListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				_timer1.stop();
				_timer2.stop();
				_statusBar1.setText("");
				_cpu.stepPippin();
				DataMemory theDataMemory = _cpu.getDataMemory();
				InstructionMemory theInstructionMemory = _cpu
						.getInstructionMemory();
				Accumulator ac = _cpu.getAccumulator();
				ProgramCounter pc = _cpu.getProgramCounter();
				_dataDisplay.setText(theDataMemory.toString());
				_instructionDisplay.setText(theInstructionMemory.toString());
				_programCounterDisplay.setText("" + pc.get());
				_accumulatorDisplay.setText("" + ac.getAccumulator());
				if (pc.get() >= 0)
					_statusBar1.setText("Instruction at program count "
							+ (pc.get() - 1) + " was executed.");
				else {
					_statusBar1.setForeground(java.awt.Color.BLUE);
					_statusBar1.setText("Program was fully executed.");
				}
			} catch (NullPointerException exception) {
				_statusBar1
						.setText("You must load an instruction file before stepping.");
				reset();
			} catch (ArrayIndexOutOfBoundsException exception) {
				_statusBar1
						.setText("You cannot step after the instructions are done.");
			}
		}
	}

	class ExecuteListener implements ActionListener {
		public void actionPerformed(ActionEvent event) {
			try {
				_timer1.stop();
				_timer2.stop();
				_statusBar1.setText("");
				_cpu.executePippin();

				DataMemory dm = _cpu.getDataMemory();
				InstructionMemory im = _cpu.getInstructionMemory();
				Accumulator ac = _cpu.getAccumulator();
				ProgramCounter pc = _cpu.getProgramCounter();

				_dataDisplay.setText(dm.toString());
				_instructionDisplay.setText(im.toString());
				_programCounterDisplay.setText("" + pc.get());
				_accumulatorDisplay.setText("" + ac.getAccumulator());
				_statusBar1.setText("Program was executed.");
			} catch (NullPointerException exception) {
				_statusBar1
						.setText("You must load an instruction file before executing.");
				reset();
			} catch (ArrayIndexOutOfBoundsException exception) {
				_statusBar1.setText("You cannot execute more than once.");
			}
		}
	}

	/**
	 * Responsibility: Create a panel with buttons to manipulate
	 * 
	 * @return a panel with a buttons
	 */
	public JPanel getButtonPanel() {
		JButton loadButton;
		JButton resetButton;
		JButton executeButton;
		JButton stepButton;
		JButton exitButton;

		loadButton = new JButton("LOAD INSTRUCTIONS");
		loadButton.addActionListener(new LoadListener());
		executeButton = new JButton("EXECUTE");
		executeButton.addActionListener(new ExecuteListener());
		stepButton = new JButton("STEP");
		stepButton.addActionListener(new StepListener());
		resetButton = new JButton("RESET");
		resetButton.addActionListener(new ResetListener());
		exitButton = new JButton("EXIT");
		exitButton.addActionListener(new ExitListener());
		
		JPanel buttonPanel1 = new JPanel(new BorderLayout(2, 2));
		buttonPanel1.add(loadButton, "North");
		buttonPanel1.add(executeButton, "Center");
		buttonPanel1.add(stepButton, "South");

		Icon bannerImage = new ImageIcon(
				"src/banner.gif");
		JLabel banner = new JLabel(bannerImage);
		JPanel imagePanel = new JPanel();
		imagePanel.add(banner, "North");

		JPanel buttonPanel2 = new JPanel(new BorderLayout(2, 2));
		buttonPanel2.add(resetButton, "North");
		buttonPanel2.add(exitButton, "South");

		JPanel panel = new JPanel(new BorderLayout(0, 1));
		panel.add(buttonPanel1, "North");
		panel.add(imagePanel, "Center");
		panel.add(buttonPanel2, "South");
		return panel;
	}

	/**
	 * Responsibility: Creates a panel with all the memory displays
	 * 
	 * @return the memory display panel
	 */
	public JPanel getMemoryDisplay() {
		_instructionDisplay = new JTextArea(33, 10);
		_dataDisplay = new JTextArea(33, 7);
		DataMemory dm = new DataMemory();
		_accumulatorDisplay = new JTextField(8);
		_programCounterDisplay = new JTextField(8);

		_instructionDisplay.setEditable(false);
		_dataDisplay.setEditable(false);
		_accumulatorDisplay.setEditable(false);
		_programCounterDisplay.setEditable(false);

		_instructionDisplay.setText("");
		_dataDisplay.setText(dm.toString());
		_accumulatorDisplay.setText("" + 0);
		_programCounterDisplay.setText("" + 0);

		JLabel memoryLabel1 = new JLabel("Accumulator");
		memoryLabel1.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel memoryLabel2 = new JLabel("Program Counter");
		memoryLabel2.setHorizontalAlignment(SwingConstants.LEFT);
		JLabel memoryLabel3 = new JLabel("Instruction Memory");
		JLabel memoryLabel4 = new JLabel("Data Memory");

		JPanel miniPanel1 = new JPanel(new GridLayout(2, 2));
		miniPanel1.add(memoryLabel1, "West");
		miniPanel1.add(_accumulatorDisplay, "East");
		miniPanel1.add(memoryLabel2, "West");
		miniPanel1.add(_programCounterDisplay, "East");

		JPanel miniPanel3 = new JPanel(new BorderLayout(2, 2));
		miniPanel3.add(memoryLabel3, "West");
		miniPanel3.add(memoryLabel4, "East");

		JPanel miniPanel4 = new JPanel(new BorderLayout(2, 2));
		miniPanel4.add(_instructionDisplay, "West");
		miniPanel4.add(_dataDisplay, "East");

		JPanel firstMemory = new JPanel(new BorderLayout(2, 2));
		firstMemory.add(miniPanel1, "North");

		JPanel secondMemory = new JPanel(new BorderLayout(2, 2));
		secondMemory.add(miniPanel3, "North");
		secondMemory.add(miniPanel4, "South");

		JPanel panel = new JPanel(new BorderLayout(2, 2));
		panel.add(firstMemory, "North");
		panel.add(secondMemory, "South");
		return panel;
	}

	/**
	 * Responsibility: Bonds the button and memory panels together
	 * 
	 * @return the main panel for the frame
	 */
	public JPanel getMainPanel() {
		JPanel buttons = this.getButtonPanel();
		JPanel memories = this.getMemoryDisplay();
		JPanel mainPanel = new JPanel(new BorderLayout(3, 2));
		mainPanel.add(buttons, "West");
		mainPanel.add(memories, "East");
		return mainPanel;
	}

	/**
	 * Responsibility: Creates a panel with a status bar for exception reporting
	 * 
	 * @return a panel with a status bar
	 */
	public JPanel getStatusBars() {
		_statusBar1 = new JTextField(30);
		_statusBar1.setText("");
		_statusBar1.setEditable(false);

		_statusBar2 = new JTextField(30);
		_statusBar2.setText("");
		_statusBar2.setEditable(false);

		JLabel fileNameLabel = new JLabel("File Loaded:");
		fileNameLabel.setHorizontalAlignment(SwingConstants.CENTER);
		_fileNameField = new JTextField(24);

		JPanel fileName = new JPanel();
		fileName.add(fileNameLabel, "West");
		fileName.add(_fileNameField, "East");

		JPanel panel = new JPanel(new BorderLayout(1, 1));
		panel.add(fileName, "North");
		panel.add(_statusBar1, "Center");
		panel.add(_statusBar2, "South");
		return panel;
	}

	/**
	 * Responsibility: resets the entire program, including all fields
	 */
	public void reset() {
		_cpu = new CPU();
		_iop = new IOProcessor();
		_inFile = null;
		DataMemory theDataMemory = _cpu.getDataMemory();
		InstructionMemory theInstructionMemory = _cpu.getInstructionMemory();
		Accumulator ac = _cpu.getAccumulator();
		ProgramCounter pc = _cpu.getProgramCounter();
		_dataDisplay.setText(theDataMemory.toString());
		_instructionDisplay.setText("");
		_programCounterDisplay.setText("" + pc.get());
		_accumulatorDisplay.setText("" + ac.getAccumulator());
		_fileNameField.setText("");
		_statusBar2.setText("Program was reset.");
		_statusBar1.setForeground(java.awt.Color.BLACK);
		_statusBar2.setForeground(java.awt.Color.BLACK);
		class StatusBar1Changer implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				_statusBar1.setText("");
			}
		}
		class StatusBar2Changer implements ActionListener {
			public void actionPerformed(ActionEvent event) {
				_statusBar2.setText("");
			}
		}
		ActionListener listener1 = new StatusBar1Changer();
		ActionListener listener2 = new StatusBar2Changer();
		final int DELAY1 = 6000;
		final int DELAY2 = 5000;
		_timer1 = new Timer(DELAY1, listener1);
		_timer2 = new Timer(DELAY2, listener2);
		_timer1.start();
		_timer2.start();
	}
}