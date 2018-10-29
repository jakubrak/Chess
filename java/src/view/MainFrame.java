package view;

import java.awt.BorderLayout;

import javax.swing.JFrame;

/**
 * @author Jakub Rak
 */
class MainFrame extends JFrame {
	private static final long serialVersionUID = 1L;

	MainFrame(final ChessboardPanel chessboardPanel, final ControlPanel controlPanel) {
		setTitle("Szachy");
		setLayout(new BorderLayout());
		add(BorderLayout.WEST, chessboardPanel);
		add(BorderLayout.EAST, controlPanel);
		setDefaultCloseOperation(EXIT_ON_CLOSE);
		pack();
		setLocationRelativeTo(null);
		setResizable(false);
		setVisible(true);
	}
}