package view;

import java.awt.Color;
import java.awt.Dimension;
import java.awt.Font;
import java.awt.Graphics;
import java.awt.Graphics2D;
import java.awt.Image;
import java.awt.Point;
import java.awt.event.MouseEvent;
import java.awt.event.MouseListener;
import java.awt.event.MouseMotionListener;
import java.awt.geom.Rectangle2D;
import java.util.concurrent.BlockingQueue;

import javax.swing.ImageIcon;
import javax.swing.JPanel;

import model.Colour;
import model.Square;
import events.Event;
import events.MoveEndingEvent;
import events.MovePerformingEvent;
import events.MoveStartingEvent;

/**
 * @author Jakub Rak
 */
class ChessboardPanel extends JPanel implements MouseListener, MouseMotionListener {
	private static final long serialVersionUID = 1L;
	private final BlockingQueue<Event> blockingQueue;
	private int squareSize;
	private int borderSize;
	private int fontSize;
	private int boardSize;
	private final Image images[] = new Image[13];
	private Point movingPieceStartPoint;
	private Point movingPieceCurrentPoint;
	private int gameState[][];
	private Orientation orientation;
	private boolean isChessboardEnabled;
	private Colour playersPiecesColour;

	ChessboardPanel(final BlockingQueue<Event> blockingQueue) {
		this.blockingQueue = blockingQueue;
		gameState = new int[8][8];
		boardSize = 500;
		squareSize = 3 * boardSize / 26;
		borderSize = (boardSize - 8 * squareSize) / 2;
		fontSize = squareSize / 5;
		setPreferredSize(new Dimension(boardSize, boardSize));
		setLayout(null);
		addMouseListener(this);
		addMouseMotionListener(this);
		isChessboardEnabled = false;

		images[1] = new ImageIcon(this.getClass().getResource("/res/200px-Chess_klt45.svg.png")).getImage();
		images[2] = new ImageIcon(this.getClass().getResource("/res/200px-Chess_qlt45.svg.png")).getImage();
		images[3] = new ImageIcon(this.getClass().getResource("/res/200px-Chess_rlt45.svg.png")).getImage();
		images[4] = new ImageIcon(this.getClass().getResource("/res/200px-Chess_blt45.svg.png")).getImage();
		images[5] = new ImageIcon(this.getClass().getResource("/res/200px-Chess_nlt45.svg.png")).getImage();
		images[6] = new ImageIcon(this.getClass().getResource("/res/200px-Chess_plt45.svg.png")).getImage();
		images[7] = new ImageIcon(this.getClass().getResource("/res/200px-Chess_kdt45.svg.png")).getImage();
		images[8] = new ImageIcon(this.getClass().getResource("/res/200px-Chess_qdt45.svg.png")).getImage();
		images[9] = new ImageIcon(this.getClass().getResource("/res/200px-Chess_rdt45.svg.png")).getImage();
		images[10] = new ImageIcon(this.getClass().getResource("/res/200px-Chess_bdt45.svg.png")).getImage();
		images[11] = new ImageIcon(this.getClass().getResource("/res/200px-Chess_ndt45.svg.png")).getImage();
		images[12] = new ImageIcon(this.getClass().getResource("/res/200px-Chess_pdt45.svg.png")).getImage();
	}

	@Override
	public void mouseClicked(final MouseEvent e) {
	}

	@Override
	public void mouseDragged(final MouseEvent e) {
		if (isChessboardEnabled == true)
			try {
				blockingQueue.put(new MovePerformingEvent(e.getPoint(), getMirrorPoint(e.getPoint())));
			}
			catch (final InterruptedException e1) {
				e1.printStackTrace();
			}
	}

	@Override
	public void mouseEntered(final MouseEvent e) {
	}

	@Override
	public void mouseExited(final MouseEvent e) {
	}

	@Override
	public void mouseMoved(final MouseEvent e) {
	}

	@Override
	public void mousePressed(final MouseEvent e) {
		if (isChessboardEnabled == true)
			try {
				blockingQueue.put(new MoveStartingEvent(e.getPoint(), getMirrorPoint(e.getPoint())));
			}
			catch (final InterruptedException e1) {
				e1.printStackTrace();
			}
	}
	
	@Override
	public void mouseReleased(final MouseEvent e) {
		try {
			if (movingPieceStartPoint != null && isChessboardEnabled == true)
				if (orientation == Orientation.NORMAL) {
					final Square startSquare = pointToSquare(movingPieceStartPoint);
					final Square endSquare = pointToSquare(e.getPoint());
					blockingQueue.put(new MoveEndingEvent(startSquare, endSquare));
				}
				else if (orientation == Orientation.MIRROR) {
					final Square startSquare = pointToSquare(movingPieceStartPoint);
					final Square endSquare = pointToSquare(e.getPoint());
					blockingQueue.put(new MoveEndingEvent(startSquare.getMirrorSquare(), endSquare.getMirrorSquare()));
				}
		}
		catch (final InterruptedException e1) {
			e1.printStackTrace();
		}
	}

	/**
	 * @param gameState - positions of all pieces on chessboard
	 * @return mirror game state
	 */
	private int[][] getMirrorGameState(final int[][] gameState) {
		final int[][] mirrorGameState = new int[8][8];
		for (int j = 7; j >= 0; --j)
			for (int i = 0; i < 8; ++i)
				mirrorGameState[7 - i][7 - j] = gameState[i][j];
		return mirrorGameState;
	}

	/**
	 * @param point - point which is being mirrored
	 * @return mirror point
	 */
	private Point getMirrorPoint(final Point point) {
		final Point mirrorPoint = new Point();
		mirrorPoint.x = boardSize - point.x;
		mirrorPoint.y = boardSize - point.y;
		return mirrorPoint;
	}

	/**
	 * 
	 * @param point - point which is being converted
	 * @return square which content given point 
	 */
	private Square pointToSquare(final Point point) {
		return new Square((point.x - borderSize) / squareSize, (point.y - borderSize) / squareSize);
	}

	@Override
	protected void paintComponent(final Graphics g) {
		super.paintComponent(g);

		final Graphics2D g2d = (Graphics2D) g;
		g2d.setColor(Color.BLACK);
		g2d.setFont(new Font("Arial", Font.BOLD, fontSize));
		if (orientation == Orientation.NORMAL)
		{
			char c = 'h';
			for (int i = 0; i < 8; ++i) {
				g2d.drawString(i + 1 + "", borderSize / 2 - fontSize / 2, i * squareSize + borderSize + squareSize / 2
						+ fontSize / 2);
				g2d.drawString(i + 1 + "", boardSize - borderSize / 2, i * squareSize + borderSize + squareSize / 2
						+ fontSize/ 2);
				g2d.drawString(c + "", i * squareSize + borderSize + squareSize / 2 - fontSize / 2, borderSize / 2
						+ fontSize / 2);
				g2d.drawString(c + "", i * squareSize + borderSize + squareSize / 2 - fontSize / 2, boardSize
						- borderSize / 2 + fontSize / 2);
				--c;
			}
		}
		else if (orientation == Orientation.MIRROR)
		{
			gameState = getMirrorGameState(gameState);
			char c = 'a';
			for (int i = 0; i < 8; ++i) {
				g2d.drawString(8 - i + "", borderSize / 2 - fontSize / 2, i * squareSize + borderSize + squareSize / 2
						+ fontSize / 2);
				g2d.drawString(8 - i + "", boardSize - borderSize / 2, i * squareSize + borderSize + squareSize / 2
						+ fontSize/ 2);
				g2d.drawString(c + "", i * squareSize + borderSize + squareSize / 2 - fontSize / 2, borderSize / 2
						+ fontSize / 2);
				g2d.drawString(c + "", i * squareSize + borderSize + squareSize / 2 - fontSize / 2, boardSize
						- borderSize / 2 + fontSize / 2);
				++c;
			}
		}
			
		if (movingPieceCurrentPoint == null && movingPieceStartPoint == null)
			for (int i = 0; i < gameState.length; ++i)
				for (int j = 0; j < gameState[i].length; ++j) {
					final Rectangle2D rectangle = new Rectangle2D.Double(i * squareSize + borderSize, j * squareSize
							+ borderSize, squareSize, squareSize);
					if (j % 2 == 0)
						if (i % 2 == 0)
							g2d.setColor(Color.getHSBColor((float) 0.083, (float) 0.38, (float) 1.0));
						else
							g2d.setColor(Color.getHSBColor((float) 0.083, (float) 0.66, (float) 0.82));
					else if (i % 2 == 0)
						g2d.setColor(Color.getHSBColor((float) 0.083, (float) 0.66, (float) 0.82));
					else
						g2d.setColor(Color.getHSBColor((float) 0.083, (float) 0.38, (float) 1.0));
					g2d.fill(rectangle);
					g2d.draw(rectangle);

					if (gameState[i][j] != 0)
						g2d.drawImage(images[gameState[i][j]], i * squareSize + borderSize,
								j * squareSize + borderSize, squareSize, squareSize, null);

				}
		else if (movingPieceCurrentPoint != null && movingPieceStartPoint != null) {
			final Square startSquare = pointToSquare(movingPieceStartPoint);
			for (int i = 0; i < gameState.length; ++i)
				for (int j = 0; j < gameState[i].length; ++j) {
					final Rectangle2D rectangle = new Rectangle2D.Double(i * squareSize + borderSize, j * squareSize
							+ borderSize, squareSize, squareSize);
					if (j % 2 == 0)
						if (i % 2 == 0)
							g2d.setColor(Color.getHSBColor((float) 0.083, (float) 0.38, (float) 1.0));
						else
							g2d.setColor(Color.getHSBColor((float) 0.083, (float) 0.66, (float) 0.82));
					else if (i % 2 == 0)
						g2d.setColor(Color.getHSBColor((float) 0.083, (float) 0.66, (float) 0.82));
					else
						g2d.setColor(Color.getHSBColor((float) 0.083, (float) 0.38, (float) 1.0));
					g2d.fill(rectangle);
					g2d.draw(rectangle);
					if (i != startSquare.getFile() || j != startSquare.getRank())
						g2d.drawImage(images[gameState[i][j]], i * squareSize + borderSize,
								j * squareSize + borderSize, squareSize, squareSize, null);

				}
			if (startSquare.getFile() >= 0 && startSquare.getFile() <= 7 && startSquare.getRank() >= 0
					&& startSquare.getRank() <= 7)
				g.drawImage(images[gameState[startSquare.getFile()][startSquare.getRank()]], movingPieceCurrentPoint.x
						- squareSize / 2, movingPieceCurrentPoint.y - squareSize / 2, squareSize, squareSize, null);
		}
	}

	/**
	 * @return colour of pieces of player
	 */
	Colour getPlayersPiecesColour() {
		return playersPiecesColour;
	}

	/**
	 * Repaints chessboard.
	 * @param gameState - positions of all pieces on chessboard
	 */
	void repaintChessboardPanel(final int[][] gameState) {
		this.gameState = gameState;
		repaint();
	}

	/**
	 * Enables (or disables) chessboard.
	 * @param enabled - true to enable chessboard, otherwise false
	 */
	void setChessboardEnabled(final boolean enabled) {
		this.isChessboardEnabled = enabled;
	}

	void setMovingPieceCurrentPoint(final Point movingPieceCurrentPoint) {
		this.movingPieceCurrentPoint = movingPieceCurrentPoint;
	}

	void setMovingPieceStartPoint(final Point movingPieceStartPoint) {
		this.movingPieceStartPoint = movingPieceStartPoint;
	}

	void setStartSettings(final Colour playersPiecesColour) {
		this.playersPiecesColour = playersPiecesColour;
		if (playersPiecesColour == Colour.BLACK)
			orientation = Orientation.NORMAL;
		else {
			orientation = Orientation.MIRROR;
			isChessboardEnabled = true;
		}
	}
}
