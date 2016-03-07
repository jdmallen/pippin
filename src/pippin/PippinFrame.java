package pippin;
import javax.swing.JFrame;
import javax.swing.JPanel;

/**
 * Description of Class PippinFrame: Display the state of a PIPPIN CPU <br>
 * Responsiblities: create a panelCreator for creating panels to create panels
 * to populate a JFrame
 * 
 * @author Jesse Mallen & Abhijeet Patil
 * @version December 5, 2004
 */
public class PippinFrame {
	final static int	FRAME_WIDTH		= 500;
	final static int	FRAME_HEIGHT	= 450;

	/**
	 * Responsibility: creates and shows the GUI with the necessary panels from
	 * PanelCreator
	 */
	public static void createAndShowFrame() {
		JFrame frame = new JFrame("Pippin Simulator");
		frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

		JPanel mainPanel;
		JPanel statusBar;
		JPanel otherMemoryDisplayPanel;
		IOProcessor iop = new IOProcessor();
		CPU aCPU = new CPU();
		PanelCreator creator = new PanelCreator(aCPU, iop);
		mainPanel = creator.getMainPanel();
		statusBar = creator.getStatusBars();

		frame.add(mainPanel, "North");
		frame.add(statusBar, "South");

		frame.pack();
		frame.setVisible(true);
	}

	/**
	 * Responsibility: executes createAndShowFrame() method and runs whole
	 * simulation
	 * 
	 * @param args
	 *            main method arguments
	 */
	public static void main(String[] args) {
		class AnotherControlFlow implements Runnable {
			public void run() {
				createAndShowFrame();
			}
		}
		javax.swing.SwingUtilities.invokeLater(new AnotherControlFlow());
	}
}