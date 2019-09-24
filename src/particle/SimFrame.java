package particle;

import java.awt.Dimension;

import javax.swing.JFrame;
import javax.swing.JPanel;

public class SimFrame extends JFrame {
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	public SimFrame() {
		SimPanel s = new SimPanel(1080, 720, 512);
		s.setPreferredSize(new Dimension(1080, 720));
		setResizable(false);
		add(s);
		pack();
		setDefaultCloseOperation(3);
		setLocationRelativeTo(null);
		setVisible(true);
	}

}
