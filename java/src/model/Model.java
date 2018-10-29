package model;

import java.util.Iterator;
import java.util.LinkedList;
import java.util.Stack;

import logger.Logger;
import exceptions.IllegalMoveException;

/**
 * Model of MVC design pattern
 * @author Jakub Rak
 */
public class Model {
	private Piece[][] chessboard;
	private Stack<Move> stackOfMoves;
	private LinkedList<Piece> listOfWhitePieces;
	private LinkedList<Piece> listOfBlackPieces;
	private Colour whoseTurn;
	private Piece whiteKing;
	private Piece blackKing;

	/**
	 * Does and remembers a chess move.
	 * @param startSquare - square from which a piece is being moved
	 * @param endSquare - square to which a piece is being moved
	 * @throws IllegalMoveException
	 */
	public void doMove(final Square startSquare, final Square endSquare) throws IllegalMoveException {
		final Move move = generateMove(startSquare, endSquare);
		move.doMove();
		stackOfMoves.push(move);
	}

	/**
	 * Gets mapping of current game state.
	 * @return table of pieces ids
	 */
	public int[][] getGameState() {
		final int[][] gameState = new int[8][8];
		for (int j = 7; j >= 0; --j)
			for (int i = 0; i < 8; ++i)
				if (chessboard[i][j] != null)
					gameState[i][j] = chessboard[i][j].getPieceId();
		return gameState;
	}

	/**
	 * Gets player's whose turn it is colour of pieces.
	 * @return colour of pieces
	 */
	public Colour getWhoseTurn() {
		return whoseTurn;
	}

	/**
	 * Checks if there is and who is the winner.
	 * @return the winner of the game
	 */
	public Winner getWinner() {
		if (isAnyMovePossible())
			return null;
		changeTurn();
		if (isKingInCheck()) {
			changeTurn();
			if (getWhoseTurn() == Colour.WHITE)
				return Winner.BLACK;
			else
				return Winner.WHITE;
		}
		return Winner.TIE;
	}

	/**
	 * Undoes a chess move.
	 */
	public void undoMove() {
		if (stackOfMoves.empty() == false) {
			final Move move = stackOfMoves.pop();
			move.undoMove();
		}
	}

	/**
	 * Checks if there is any move possible.
	 * @return true if there is any move possible, otherwise false
	 */
	private boolean isAnyMovePossible() {
		if (whoseTurn == Colour.WHITE) {
			final Iterator<Piece> piecesIterator = listOfWhitePieces.descendingIterator();
			while (piecesIterator.hasNext()) {
				final Piece piece = piecesIterator.next();
				for (final Square accessibleSquare : piece.getAccessibleSquares())
					try {
						doMove(piece.getCurrentSquare(), accessibleSquare);
						undoMove();
						return true;
					}
					catch (final IllegalMoveException e) {
						continue;
					}
			}
			return false;
		}
		else {
			final Iterator<Piece> piecesIterator = listOfBlackPieces.descendingIterator();
			while (piecesIterator.hasNext()) {
				final Piece piece = piecesIterator.next();
				for (final Square accessibleSquare : piece.getAccessibleSquares())
					try {
						doMove(piece.getCurrentSquare(), accessibleSquare);
						undoMove();
						return true;
					}
					catch (final IllegalMoveException e) {
						continue;
					}
			}
			return false;
		}
	}

	/**
	 * Creates a new game.
	 */
	public void createNewGame() {
		chessboard = new Piece[8][8];
		listOfWhitePieces = new LinkedList<Piece>();
		listOfBlackPieces = new LinkedList<Piece>();
		stackOfMoves = new Stack<Move>();
		whoseTurn = Colour.WHITE;
		
		/*Initial positions of pieces on chessboard[8][8]:
		 * 		0		1		2		3		4		5		6		7
		 * 0 WRook	WKnight	WBishop	WKing	WQueen	WBishop	WKnight	WRook	
		 * 1 WPawn	WPawn	WPawn	WPawn	WPawn	WPawn	WPawn	WPawn	
		 * 2 null	null	null	null	null	null	null	null	
		 * 3 null	null	null	null	null	null	null	null	
		 * 4 null	null	null	null	null	null	null	null	
		 * 5 null	null	null	null	null	null	null	null	
		 * 6 BPawn	BPawn	BPawn	BPawn	BPawn	BPawn	BPawn	BPawn
		 */
		
		chessboard[0][0] = new Rook(this, Colour.WHITE, new Square(0, 0));
		chessboard[1][0] = new Knight(this, Colour.WHITE, new Square(1, 0));
		chessboard[2][0] = new Bishop(this, Colour.WHITE, new Square(2, 0));
		chessboard[3][0] = new King(this, Colour.WHITE, new Square(3, 0));
		chessboard[4][0] = new Queen(this, Colour.WHITE, new Square(4, 0));
		chessboard[5][0] = new Bishop(this, Colour.WHITE, new Square(5, 0));
		chessboard[6][0] = new Knight(this, Colour.WHITE, new Square(6, 0));
		chessboard[7][0] = new Rook(this, Colour.WHITE, new Square(7, 0));

		listOfWhitePieces.add(chessboard[4][0]);
		listOfWhitePieces.add(chessboard[0][0]);
		listOfWhitePieces.add(chessboard[7][0]);
		listOfWhitePieces.add(chessboard[2][0]);
		listOfWhitePieces.add(chessboard[5][0]);
		listOfWhitePieces.add(chessboard[1][0]);
		listOfWhitePieces.add(chessboard[6][0]);
		listOfWhitePieces.add(chessboard[3][0]);

		chessboard[0][7] = new Rook(this, Colour.BLACK, new Square(0, 7));
		chessboard[1][7] = new Knight(this, Colour.BLACK, new Square(1, 7));
		chessboard[2][7] = new Bishop(this, Colour.BLACK, new Square(2, 7));
		chessboard[3][7] = new King(this, Colour.BLACK, new Square(3, 7));
		chessboard[4][7] = new Queen(this, Colour.BLACK, new Square(4, 7));
		chessboard[5][7] = new Bishop(this, Colour.BLACK, new Square(5, 7));
		chessboard[6][7] = new Knight(this, Colour.BLACK, new Square(6, 7));
		chessboard[7][7] = new Rook(this, Colour.BLACK, new Square(7, 7));

		listOfBlackPieces.add(chessboard[4][7]);
		listOfBlackPieces.add(chessboard[0][7]);
		listOfBlackPieces.add(chessboard[7][7]);
		listOfBlackPieces.add(chessboard[2][7]);
		listOfBlackPieces.add(chessboard[5][7]);
		listOfBlackPieces.add(chessboard[1][7]);
		listOfBlackPieces.add(chessboard[6][7]);
		listOfBlackPieces.add(chessboard[3][7]);

		for (int i = 0; i < 8; ++i) {
			chessboard[i][1] = new Pawn(this, Colour.WHITE, new Square(i, 1));
			chessboard[i][6] = new Pawn(this, Colour.BLACK, new Square(i, 6));
			listOfWhitePieces.add(chessboard[i][1]);
			listOfBlackPieces.add(chessboard[i][6]);
		}

		whiteKing = chessboard[3][0];
		blackKing = chessboard[3][7];
	}

	/**
	 * Adds piece to list of player's pieces.
	 * @param piece - piece which is being added
	 */
	void addPieceToList(final Piece piece) {
		if (piece != null)
			if (piece.getColour() == Colour.WHITE)
				listOfWhitePieces.add(piece);
			else
				listOfBlackPieces.add(piece);
	}

	/**
	 * Changes turn.
	 */
	void changeTurn() {
		if (whoseTurn == Colour.WHITE)
			whoseTurn = Colour.BLACK;
		else
			whoseTurn = Colour.WHITE;
	}

	/**
	 * Generates a chess move.
	 * @param startSquare - square from which a piece is being moved
	 * @param endSquare - square to which a piece is being moved
	 * @return {@link Move}
	 * @throws IllegalMoveException
	 */
	Move generateMove(final Square startSquare, final Square endSquare) throws IllegalMoveException {
		if (startSquare.getFile() < 0 || startSquare.getFile() > 7 || startSquare.getRank() < 0 || startSquare.getRank() > 7
				|| endSquare.getFile() < 0 || endSquare.getFile() > 7 || endSquare.getRank() < 0 || endSquare.getRank() > 7)
			throw new IllegalMoveException();
		if (isSquareFree(startSquare) == true)
			throw new IllegalMoveException();
		if (startSquare.getFile() == endSquare.getFile() && startSquare.getRank() != endSquare.getRank()) {
			final VerticalMove verticalMove = new VerticalMove(this,
					chessboard[startSquare.getFile()][startSquare.getRank()], startSquare, endSquare);
			if (chessboard[startSquare.getFile()][startSquare.getRank()].isMoveValid(verticalMove) == true)
				return verticalMove;
			throw new IllegalMoveException();
		}
		if (startSquare.getRank() == endSquare.getRank() && startSquare.getFile() != endSquare.getFile()) {
			final HorizontalMove horizontalMove = new HorizontalMove(this,
					chessboard[startSquare.getFile()][startSquare.getRank()], startSquare, endSquare);
			if (chessboard[startSquare.getFile()][startSquare.getRank()].isMoveValid(horizontalMove) == true)
				return horizontalMove;
			throw new IllegalMoveException();
		}
		if (Math.abs(startSquare.getFile() - endSquare.getFile()) == Math.abs(startSquare.getRank() - endSquare.getRank())) {
			final DiagonalMove diagonalMove = new DiagonalMove(this,
					chessboard[startSquare.getFile()][startSquare.getRank()], startSquare, endSquare);
			if (chessboard[startSquare.getFile()][startSquare.getRank()].isMoveValid(diagonalMove) == true)
				return diagonalMove;
			throw new IllegalMoveException();
		}
		if (Math.abs(startSquare.getFile() - endSquare.getFile()) == 2
				&& Math.abs(startSquare.getRank() - endSquare.getRank()) == 1
				|| Math.abs(startSquare.getFile() - endSquare.getFile()) == 1
				&& Math.abs(startSquare.getRank() - endSquare.getRank()) == 2) {
			final KnightsMove knightsMove = new KnightsMove(this, chessboard[startSquare.getFile()][startSquare.getRank()],
					startSquare, endSquare);
			if (chessboard[startSquare.getFile()][startSquare.getRank()].isMoveValid(knightsMove) == true)
				return knightsMove;
			throw new IllegalMoveException();
		}
		else
			throw new IllegalMoveException();
	}

	/**
	 * Gets last done move.
	 * @return move from top of {@link #stackOfMoves}
	 */
	Move getLastMove() {
		if (stackOfMoves.isEmpty() == false)
			return stackOfMoves.lastElement();
		else
			return null;
	}

	/**
	 * Gets piece if there is anyone on chessboard.
	 * @param square - square on where sought piece is
	 * @return piece which is on given square
	 */
	Piece getPiece(final Square square) {
		return chessboard[square.getFile()][square.getRank()];
	}

	/**
	 * Checks if king is in check.
	 * @return true if king is in check, otherwise false
	 */
	boolean isKingInCheck() {
		if (whoseTurn == Colour.BLACK) {
			Logger.print(this, "Checking if " + whiteKing + " is in check...");
			for (final Piece piece : listOfBlackPieces)
				try {
					generateMove(piece.getCurrentSquare(), whiteKing.getCurrentSquare());
					Logger.print(this, "King is in check.");
					return true;
				}
				catch (final IllegalMoveException e) {
					continue;
				}
			Logger.print(this, "King isn't in check.");
			return false;
		}
		else {
			Logger.print(this, "Checking if " + blackKing + " is in check...");
			for (final Piece piece : listOfWhitePieces)
				try {
					generateMove(piece.getCurrentSquare(), blackKing.getCurrentSquare());
					Logger.print(this, "King is in check.");
					return true;
				}
				catch (final IllegalMoveException e) {
					continue;
				}
			Logger.print(this, "King isn't in check.");
			return false;
		}
	}

	/**
	 * Checks if square is free.
	 * @param square - square which is checked
	 * @return true if square isn't occupied by any piece, otherwise false
	 */
	boolean isSquareFree(final Square square) {
		if (chessboard[square.getFile()][square.getRank()] == null)
			return true;
		else
			return false;
	}

	/**
	 * Prints chessboard.
	 */
	void printChessboard() {
		for (int j = 0; j < 8; ++j) {
			for (int i = 0; i < 8; ++i)
				System.out.print(chessboard[i][j] + "\t");
			System.out.println();
		}
		System.out.println();
	}

	/**
	 * Prints lists of pieces owned by each of players.
	 */
	void printlistsOfPieces() {
		System.out.println("List of white pieces:");
		for (final Piece piece : listOfWhitePieces)
			System.out.println(piece);
		System.out.println("List of black pieces:");
		for (final Piece piece : listOfBlackPieces)
			System.out.println(piece);
	}

	/**
	 * Removes given piece from list of pieces.
	 * @param piece - piece to remove
	 */
	void removePieceFromList(final Piece piece) {
		if (piece != null)
			if (piece.getColour() == Colour.WHITE)
				listOfWhitePieces.remove(piece);
			else
				listOfBlackPieces.remove(piece);
	}

	/**
	 * Set piece on square of chessboard.
	 * @param square - square on which piece is set
	 * @param piece - piece which is set
	 */
	void setPieceOnChessboard(final Square square, final Piece piece) {
		chessboard[square.getFile()][square.getRank()] = piece;
	}
}
