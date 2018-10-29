package model;

import java.util.LinkedList;

import logger.Logger;

/**
 * @author Jakub Rak
 */
abstract class Piece {
	/**
	 * Checks if diagonal move is valid for piece.
	 * @param diagonalMove
	 * @return true if move is valid, otherwise false
	 */
	boolean isMoveValid(final DiagonalMove diagonalMove) {
		Logger.print(this, this.getClass().getName() + " can't perform diagonal move.");
		return false;
	}

	/**
	 * Checks if horizontal move is valid for piece.
	 * @param horizontalMove
	 * @return true if move is valid, otherwise false
	 */
	boolean isMoveValid(final HorizontalMove horizontalMove) {
		Logger.print(this, this.getClass().getName() + " can't perform horizontal move.");
		return false;
	}

	/**
	 * Checks if knight's move is valid for piece.
	 * @param knightsMove
	 * @return true if knight's move is valid, otherwise false
	 */
	boolean isMoveValid(final KnightsMove knightsMove) {
		Logger.print(this, this.getClass().getName() + " can't perform knight's move.");
		return false;
	}

	/**
	 * Checks if vertical move is valid for piece.
	 * @param verticalMove
	 * @return true if vertical move is valid, otherwise false
	 */
	boolean isMoveValid(final VerticalMove verticalMove) {
		Logger.print(this, this.getClass().getName() + " can't perform vertical move.");
		return false;
	}

	/**
	 * Gets all squares which piece can access from current square (without checking if move is valid).
	 * @return list of squares
	 */
	abstract LinkedList<Square> getAccessibleSquares();

	/**
	 * Gets colour of piece.
	 * @return colour of piece
	 */
	abstract Colour getColour();

	/**
	 * Gets square on which piece currently stands.
	 * @return current square
	 */
	abstract Square getCurrentSquare();

	/**
	 * Gets piece identification number.
	 * @return piece id
	 */
	abstract int getPieceId();

	/**
	 * Sets square on which piece currently is.
	 * @param square - square on which piece is set
	 */
	abstract void setCurrentSquare(Square square);

	/**
	 * Remembers if piece was moved or not.
	 * @param wasMoved - true if piece was moved, otherwise false
	 */
	abstract void setWasMoved(boolean wasMoved);

	/**
	 * Checks if piece was moved any time before.
	 * @return true if piece was moved, otherwise false
	 */
	abstract boolean wasMoved();
	
	@Override
	public String toString()
	{
		return  this.getColour().toString().substring(0, 1) + this.getClass().getSimpleName();
	}
}
