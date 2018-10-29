package model;

import java.util.LinkedList;

import logger.Logger;

/**
 * @author Jakub Rak
 */
class Knight extends Piece {
	private final int pieceId;
	private final Model model;
	private final Colour colour;
	private boolean wasMoved;
	private Square currentSquare;

	Knight(final Model model, final Colour colour, final Square currentSquare) {
		pieceId = colour == Colour.WHITE ? 5 : 11;
		this.currentSquare = currentSquare;
		this.model = model;
		this.colour = colour;
		wasMoved = false;
	}

	@Override
	boolean isMoveValid(final KnightsMove knightsMove) {
		final boolean isRightTurn = model.getWhoseTurn() == colour;
		final boolean isPathClear = knightsMove.isPathClear();
		if (isRightTurn && isPathClear)
			return true;
		Logger.print(this, "Check results of " + colour + " knight knight's move:" + "\nisRightTurn:" + isRightTurn
				+ "\nisPathClear: " + isPathClear);
		return false;
	}

	@Override
	LinkedList<Square> getAccessibleSquares() {
		final LinkedList<Square> accessibleSquares = new LinkedList<Square>();
		if (currentSquare.getFile() + 1 >= 0 && currentSquare.getFile() + 1 <= 7 && currentSquare.getRank() + 2 >= 0
				&& currentSquare.getRank() + 2 <= 7)
			accessibleSquares.add(new Square(currentSquare.getFile() + 1, currentSquare.getRank() + 2));
		if (currentSquare.getFile() + 2 >= 0 && currentSquare.getFile() + 2 <= 7 && currentSquare.getRank() + 1 >= 0
				&& currentSquare.getRank() + 1 <= 7)
			accessibleSquares.add(new Square(currentSquare.getFile() + 2, currentSquare.getRank() + 1));
		if (currentSquare.getFile() + 2 >= 0 && currentSquare.getFile() + 2 <= 7 && currentSquare.getRank() - 1 >= 0
				&& currentSquare.getRank() - 1 <= 7)
			accessibleSquares.add(new Square(currentSquare.getFile() + 2, currentSquare.getRank() - 1));
		if (currentSquare.getFile() + 1 >= 0 && currentSquare.getFile() + 1 <= 7 && currentSquare.getRank() - 2 >= 0
				&& currentSquare.getRank() - 2 <= 7)
			accessibleSquares.add(new Square(currentSquare.getFile() + 1, currentSquare.getRank() - 2));
		if (currentSquare.getFile() - 1 >= 0 && currentSquare.getFile() - 1 <= 7 && currentSquare.getRank() - 2 >= 0
				&& currentSquare.getRank() - 2 <= 7)
			accessibleSquares.add(new Square(currentSquare.getFile() - 1, currentSquare.getRank() - 2));
		if (currentSquare.getFile() - 2 >= 0 && currentSquare.getFile() - 2 <= 7 && currentSquare.getRank() - 1 >= 0
				&& currentSquare.getRank() - 1 <= 7)
			accessibleSquares.add(new Square(currentSquare.getFile() - 2, currentSquare.getRank() - 1));
		if (currentSquare.getFile() - 2 >= 0 && currentSquare.getFile() - 2 <= 7 && currentSquare.getRank() + 1 >= 0
				&& currentSquare.getRank() + 1 <= 7)
			accessibleSquares.add(new Square(currentSquare.getFile() - 2, currentSquare.getRank() + 1));
		if (currentSquare.getFile() - 1 >= 0 && currentSquare.getFile() - 1 <= 7 && currentSquare.getRank() + 2 >= 0
				&& currentSquare.getRank() + 2 <= 7)
			accessibleSquares.add(new Square(currentSquare.getFile() - 1, currentSquare.getRank() + 2));
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
