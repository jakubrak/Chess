package model;

import exceptions.IllegalMoveException;

/**
 * Chess move.
 * @author Jakub Rak
 */
abstract class Move {

	/**
	 * Does a chess move.
	 * @throws IllegalMoveException
	 */
	abstract void doMove() throws IllegalMoveException;

	/**
	 * @return piece which is captured
	 */
	Piece getCapturedPiece() {
		return null;
	}

	/**
	 * @return square to which piece is being moved
	 */
	abstract Square getEndSquare();

	/**
	 * @return piece which is being moved
	 */
	abstract Piece getPiece();

	/**
	 * @return square from which piece is being moved
	 */
	abstract Square getStartSquare();
	
	/**
	 * Undoes a chess move.
	 */
	abstract void undoMove();

	/**
	 * @return true if piece which is being moved was moved any time before, otherwise false
	 */
	abstract boolean wasMoved();
	
	/**
	 * @return true if path which piece is being moved along with is clear, otherwise false
	 */
	abstract boolean isPathClear();
}
