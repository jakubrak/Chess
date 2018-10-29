package model;

import java.util.LinkedList;

import logger.Logger;

/**
 * @author Jakub Rak
 */
public class Queen extends Piece {
	private final int pieceId;
	private final Model model;
	private final Colour colour;
	private boolean wasMoved;
	private Square currentSquare;

	Queen(final Model model, final Colour colour, final Square currentSquare) {
		pieceId = colour == Colour.WHITE ? 2 : 8;
		this.currentSquare = currentSquare;
		this.model = model;
		this.colour = colour;
		wasMoved = false;
	}

	@Override
	boolean isMoveValid(final DiagonalMove diagonalMove) {
		final boolean isRightTurn = model.getWhoseTurn() == colour;
		final boolean isPathClear = diagonalMove.isPathClear();
		if (isRightTurn && isPathClear)
			return true;
		Logger.print(this, "Check results of " + colour + " queen diagonal move:" + "\nisRightTurn:" + isRightTurn
				+ "\nisPathClear: " + isPathClear);
		return false;
	}

	@Override
	boolean isMoveValid(final HorizontalMove horizontalMove) {
		final boolean isRightTurn = model.getWhoseTurn() == colour;
		final boolean isPathClear = horizontalMove.isPathClear();
		if (isRightTurn && isPathClear)
			return true;
		Logger.print(this, "Check results of " + colour + " queen horizontal move:" + "\nisRightTurn:" + isRightTurn
				+ "\nisPathClear: " + isPathClear);
		return false;
	}

	@Override
	boolean isMoveValid(final VerticalMove verticalMove) {
		final boolean isRightTurn = model.getWhoseTurn() == colour;
		final boolean isPathClear = verticalMove.isPathClear();
		if (isRightTurn && isPathClear)
			return true;
		Logger.print(this, "Check results of " + colour + " queen vertical move:" + "\nisRightTurn:" + isRightTurn
				+ "\nisPathClear: " + isPathClear);
		return false;
	}

	@Override
	LinkedList<Square> getAccessibleSquares() {
		final LinkedList<Square> accessibleSquares = new LinkedList<Square>();
		for (int i = 1; i <= 7; ++i) {
			if (currentSquare.getFile() + 0 >= 0 && currentSquare.getFile() + 0 <= 7 && currentSquare.getRank() + i >= 0
					&& currentSquare.getRank() + i <= 7)
				accessibleSquares.add(new Square(currentSquare.getFile() + 0, currentSquare.getRank() + i));
			if (currentSquare.getFile() + i >= 0 && currentSquare.getFile() + i <= 7 && currentSquare.getRank() + i >= 0
					&& currentSquare.getRank() + i <= 7)
				accessibleSquares.add(new Square(currentSquare.getFile() + i, currentSquare.getRank() + i));
			if (currentSquare.getFile() + i >= 0 && currentSquare.getFile() + i <= 7 && currentSquare.getRank() + 0 >= 0
					&& currentSquare.getRank() + 0 <= 7)
				accessibleSquares.add(new Square(currentSquare.getFile() + i, currentSquare.getRank() + 0));
			if (currentSquare.getFile() + i >= 0 && currentSquare.getFile() + i <= 7 && currentSquare.getRank() - i >= 0
					&& currentSquare.getRank() - i <= 7)
				accessibleSquares.add(new Square(currentSquare.getFile() + i, currentSquare.getRank() - i));
			if (currentSquare.getFile() + 0 >= 0 && currentSquare.getFile() + 0 <= 7 && currentSquare.getRank() - i >= 0
					&& currentSquare.getRank() - i <= 7)
				accessibleSquares.add(new Square(currentSquare.getFile() + 0, currentSquare.getRank() - i));
			if (currentSquare.getFile() - i >= 0 && currentSquare.getFile() - i <= 7 && currentSquare.getRank() - i >= 0
					&& currentSquare.getRank() - i <= 7)
				accessibleSquares.add(new Square(currentSquare.getFile() - i, currentSquare.getRank() - i));
			if (currentSquare.getFile() - i >= 0 && currentSquare.getFile() - i <= 7 && currentSquare.getRank() + 0 >= 0
					&& currentSquare.getRank() + 0 <= 7)
				accessibleSquares.add(new Square(currentSquare.getFile() - i, currentSquare.getRank() + 0));
			if (currentSquare.getFile() - i >= 0 && currentSquare.getFile() - i <= 7 && currentSquare.getRank() + i >= 0
					&& currentSquare.getRank() + i <= 7)
				accessibleSquares.add(new Square(currentSquare.getFile() - i, currentSquare.getRank() + i));
		}
		return accessibleSquares;
	}

	@Override
	Colour getColour() {
		return colour;
	}

	@Override
	Square getCurrentSquare() {
		return currentSquare;
	}

	@Override
	int getPieceId() {
		return pieceId;
	}

	@Override
	void setCurrentSquare(final Square currentSquare) {
		this.currentSquare = currentSquare;
	}

	@Override
	void setWasMoved(final boolean wasMoved) {
		this.wasMoved = wasMoved;
	}

	@Override
	boolean wasMoved() {
		return wasMoved;
	}
}
