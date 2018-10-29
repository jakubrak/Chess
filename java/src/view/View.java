package view;

import java.awt.Point;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import javax.swing.JOptionPane;
import javax.swing.SwingUtilities;

import model.Colour;
import events.Event;

/**
 * View of MVC design pattern
 * @author Jakub Rak
 */
public class View {
	private final BlockingQueue<Event> blockingQueue;
	private final MainFrame mainFrame;
	private final ChessboardPanel chessboardPanel;
	private final ControlPanel controlPanel;

	public View() {
		blockingQueue = new LinkedBlockingQueue<Event>();
		chessboardPanel = new ChessboardPanel(blockingQueue);
		controlPanel = new ControlPanel(blockingQueue, chessboardPanel);
		mainFrame = new MainFrame(chessboardPanel, controlPanel);
	}

	public BlockingQueue<Event> getBlockingQueue() {
		return blockingQueue;
	}

	/**
	 * Puts event to view blocking queue.
	 * @param event - event which is being put to vie blocking queue
	 * @throws InterruptedException 
	 */
	public void putEventToBlockingQueue(final Event event) throws InterruptedException {
		blockingQueue.put(event);
	}

	/**
	 * Repaints chessboard.
	 * @param gameState - positions of all pieces on chessboard
	 */
	public void repaintChessboardPanel(final int[][] gameState) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				chessboardPanel.repaintChessboardPanel(gameState);
			}
		});
	}

	/**
	 * Enables (or disables) chessboard.
	 * @param enabled - true to enable the chessboard, otherwise false
	 */
	public void setChessboardEnabled(final boolean enabled) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				chessboardPanel.setChessboardEnabled(enabled);
			}
		});
	}

	/**
	 * Sets point in where moving piece is currently located.
	 * @param movingPieceCurrentPoint
	 */
	public void setChessboardPanelMovingPieceCurrentPoint(final Point movingPieceCurrentPoint) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				chessboardPanel.setMovingPieceCurrentPoint(movingPieceCurrentPoint);
			}
		});
	}

	/**
	 * Sets point from where piece starts moving.
	 * @param movingPieceCurrentPoint
	 */
	public void setChessboardPanelMovingPieceStartPoint(final Point movingPieceStartPoint) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				chessboardPanel.setMovingPieceStartPoint(movingPieceStartPoint);
			}
		});
	}

	/**
	 * Enables (or disables) connectToGameButton.
	 * @param enabled - true to enable connectToGameButton, otherwise false
	 */
	public void setConnectToGameButtonEnabled(final boolean enabled) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				controlPanel.setConnectToGameButtonEnabled(enabled);
			}
		});
	}

	/**
	 * Enables (or disables) createConnectionButton.
	 * @param enabled - true to enable createConnectionButton, otherwise false
	 */
	public void setCreateConnectionButtonEnabled(final boolean enabled) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				controlPanel.setCreateConnectionButtonEnabled(enabled);
			}
		});
	}

	/**
	 * Enables (or disables) giveUpButton.
	 * @param enabled - true to enable giveUpButton, otherwise false
	 */
	public void setGiveUpButtonEnabled(final boolean enabled) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				controlPanel.setGiveUpButtonEnabled(enabled);
			}
		});
	}

	/**
	 * Enables (or disables) newGameButton.
	 * @param enabled - true to enable newGameButton, otherwise false
	 */
	public void setNewGameButtonEnabled(final boolean enabled) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				controlPanel.setNewGameButtonEnabled(enabled);
			}
		});
	}

	/**
	 * Sets colour of player's pieces.
	 * @param playersPiecesColour - colour of player's pieces
	 */
	public void setStartChessboardSettings(final Colour playersPiecesColour) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				chessboardPanel.setStartSettings(playersPiecesColour);
			}
		});
	}

	/**
	 * Enables (or disables) undoMoveButton.
	 * @param enabled - true to enable undoMoveButton, otherwise false
	 */
	public void setUndoMoveButtonEnabled(final boolean enabled) {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				controlPanel.setUndoMoveButtonEnabled(enabled);
			}
		});
	}

	/**
	 * Shows message dialog informing that black has won.
	 */
	public void showBlackWonMessageDialog() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(mainFrame, "Wygra³y CZARNE.", "Koniec gry", JOptionPane.PLAIN_MESSAGE);
			}
		});
	}

	/**
	 * Shows message dialog informing that connection error occured.
	 */
	public void showConnectionErrorMessageDialog() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(mainFrame, "Brak po³¹czenia", "B³¹d", JOptionPane.PLAIN_MESSAGE);
			}
		});
	}

	/**
	 * Shows message dialog informing that game end with tie.
	 */
	public void showTieMessageDialog() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(mainFrame, "Remis.", "Koniec gry", JOptionPane.PLAIN_MESSAGE);
			}
		});
	}

	/**
	 * Shows message dialog informing that white has won.
	 */
	public void showWhiteWonMessageDialog() {
		SwingUtilities.invokeLater(new Runnable() {
			@Override
			public void run() {
				JOptionPane.showMessageDialog(mainFrame, "Wygra³y BIA£E.", "Koniec gry", JOptionPane.PLAIN_MESSAGE);
			}
		});
	}

	/**
	 * Retrieves and removes event from view blocking queue.
	 * @return event
	 * @throws InterruptedException 
	 */
	public Event takeEventFromBlockingQueue() throws InterruptedException {
		return blockingQueue.take();
	}
}
