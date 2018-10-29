package view;

import java.awt.GridLayout;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.util.concurrent.BlockingQueue;

import javax.swing.Box;
import javax.swing.JButton;
import javax.swing.JOptionPane;
import javax.swing.JPanel;

import model.Colour;
import model.Winner;
import events.ConnectToGameEvent;
import events.CreateConnectionEvent;
import events.Event;
import events.GiveUpEvent;
import events.NewGameEvent;
import events.UndoMoveEvent;

class ControlPanel extends JPanel {
	private static final long serialVersionUID = 1L;
	private final JButton createConnectionButton, connectToGameButton, newGameButton, undoMoveButton, giveUpButton;

	ControlPanel(final BlockingQueue<Event> blockingQueue, final ChessboardPanel chessboardPanel) {
		createConnectionButton = new JButton("Stwórz po³¹czenie");
		connectToGameButton = new JButton("Pod³¹cz siê do gry");
		newGameButton = new JButton("Zacznij now¹ grê");
		undoMoveButton = new JButton("Cofnij");
		giveUpButton = new JButton("Poddaj siê");
		newGameButton.setEnabled(false);
		giveUpButton.setEnabled(false);
		undoMoveButton.setEnabled(false);

		createConnectionButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					blockingQueue.put(new CreateConnectionEvent());
				}
				catch (final InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});

		connectToGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					final String ip = JOptionPane.showInputDialog("Podaj adres IP, z którym chcesz siê po³¹czyæ", "127.0.0.1");
					blockingQueue.put(new ConnectToGameEvent(ip));
				}
				catch (final InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});

		newGameButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					blockingQueue.put(new NewGameEvent());
				}
				catch (final InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});

		undoMoveButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					blockingQueue.put(new UndoMoveEvent(chessboardPanel.getPlayersPiecesColour()));
				}
				catch (final InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});

		giveUpButton.addActionListener(new ActionListener() {
			@Override
			public void actionPerformed(final ActionEvent e) {
				try {
					final Colour playersPiecesColour = chessboardPanel.getPlayersPiecesColour();
					if (playersPiecesColour == Colour.BLACK)
						blockingQueue.put(new GiveUpEvent(Winner.WHITE));
					else
						blockingQueue.put(new GiveUpEvent(Winner.BLACK));
				}
				catch (final InterruptedException e1) {
					e1.printStackTrace();
				}
			}
		});

		setLayout(new GridLayout(7, 1, 0, 20));
		add(Box.createVerticalStrut(50));
		add(createConnectionButton);
		add(connectToGameButton);
		add(newGameButton);
		add(undoMoveButton);
		add(giveUpButton);
	}

	/**
	 * @see View#setConnectToGameButtonEnabled(boolean)
	 */
	void setConnectToGameButtonEnabled(final boolean enabled) {
		connectToGameButton.setEnabled(enabled);
	}

	/**
	 * @see View#setCreateConnectionButtonEnabled(boolean)
	 */
	void setCreateConnectionButtonEnabled(final boolean enabled) {
		createConnectionButton.setEnabled(enabled);
	}

	/**
	 * @see View#setGiveUpButtonEnabled(boolean)
	 */
	void setGiveUpButtonEnabled(final boolean enabled) {
		giveUpButton.setEnabled(enabled);
	}

	/**
	 * @see View#setNewGameButtonEnabled(boolean)
	 */
	void setNewGameButtonEnabled(final boolean enabled) {
		newGameButton.setEnabled(enabled);
	}

	/**
	 * @see View#setUndoMoveButtonEnabled(boolean)
	 */
	void setUndoMoveButtonEnabled(final boolean enabled) {
		undoMoveButton.setEnabled(enabled);
	}
}
