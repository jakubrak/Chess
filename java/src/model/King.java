package model;

import java.util.LinkedList;

import logger.Logger;

/**
 * @author Jakub Rak
 */
class King extends Piece {
	private final int pieceId;
	private final Model model;
	private final Colour colour;
	private boolean wasMoved;
	private Square currentSquare;

	King(final Model model, final Colour colour, final Square currentSquare) {
		pieceId = colour == Colour.WHITE ? 1 : 7;
		this.currentSquare = currentSquare;
		this.model = model;
		this.colour = colour;
		wasMoved = false;
	}

	@Override
	boolean isMoveValid(final DiagonalMove diagonalMove) {
		final boolean isOneStepMove = diagonalMove.isOneStepMove();
		final boolean isRightTurn = model.getWhoseTurn() == colour;
		final boolean isPathClear = diagonalMove.isPathClear();

		if (isOneStepMove && isRightTurn && isPathClear)
			return true;
		Logger.print(this, "Check results of " + colour + " king diagonal move:" + "\nisRightTurn:" + isRightTurn
				+ "\nisPathClear: " + isPathClear + "\nisOneStepMove: " + isOneStepMove);
		return false;
	}

	@Override
	boolean isMoveValid(final HorizontalMove horizontalMove) {
		final boolean isOneStepMove = horizontalMove.isOneStepMove();
		final boolean isRightTurn = model.getWhoseTurn() == colour;
		final boolean isPathClear = horizontalMove.isPathClear();

		if (isOneStepMove && isRightTurn && isPathClear)
			return true;

		final boolean isCastling = horizontalMove.checkCastling();
		if (isRightTurn && isPathClear && !wasMoved && isCastling) {
			Logger.print(this, "Castling...");
			return true;
		}
		System.out
				.println("Check results of " + colour + " king horizontal move:" + "\nisRightTurn:" + isRightTurn
						+ "\nisPathClear: " + isPathClear + "\nisOneStepMove: " + isOneStepMove + "\nisCastling: "
						+ isCastling);
		return false;
	}

	@Override
	boolean isMoveValid(final VerticalMove verticalMove) {
		final boolean isOneStepMove = verticalMove.isOneStepMove();
		final boolean isRightTurn = model.getWhoseTurn() == colour;
		final boolean isPathClear = verticalMove.isPathClear();

		if (isOneStepMove && isRightTurn && isPathClear)
			return true;
		Logger.print(this, "Check results of " + colour + " king vertical move:" + "\nisRightTurn:" + isRightTurn
				+ "\nisPathClear: " + isPathClear + "\nisOneStepMove: " + isOneStepMove);
		return false;
	}

	@Override
	LinkedList<Square> getAccessibleSquares() {
		final LinkedList<Square> accessibleSquares = new LinkedList<Square>();
		if (currentSquare.getFile() + 0 >= 0 && currentSquare.getFile() + 0 <= 7 && currentSquare.getRank() + 1 >= 0
				&& currentSquare.getRank() + 1 <= 7)
			accessibleSquares.add(new Square(currentSquare.getFile() + 0, currentSquare.getRank() + 1));
		if (currentSquare.getFile() + 1 >= 0 && currentSquare.getFile() + 1 <= 7 && currentSquare.getRank() + 1 >= 0
				&& currentSquare.getRank() + 1 <= 7)
			accessibleSquares.add(new Square(currentSquare.getFile() + 1, currentSquare.getRank() + 1));
		if (currentSquare.getFile() + 1 >= 0 && currentSquare.getFile() + 1 <= 7 && currentSquare.getRank() + 0 >= 0
				&& currentSquare.getRank() + 0 <= 7)
			accessibleSquares.add(new Square(currentSquare.getFile() + 1, currentSquare.getRank() + 0));
		if (currentSquare.getFile() + 1 >= 0 && currentSquare.getFile() + 1 <= 7 && currentSquare.getRank() - 1 >= 0
				&& currentSquare.getRank() - 1 <= 7)
			accessibleSquares.add(new Square(currentSquare.getFile() + 1, currentSquare.getRank() - 1));
		if (currentSquare.getFile() + 0 >= 0 && currentSquare.getFile() + 0 <= 7 && currentSquare.getRank() - 1 >= 0
				&& currentSquare.getRank() - 1 <= 7)
			accessibleSquares.add(new Square(currentSquare.getFile() + 0, currentSquare.getRank() - 1));
		if (currentSquare.getFile() - 1 >= 0 && currentSquare.getFile() - 1 <= 7 && currentSquare.getRank() - 1 >= 0
				&& currentSquare.getRank() - 1 <= 7)
			accessibleSquares.add(new Square(currentSquare.getFile() - 1, currentSquare.getRank() - 1));
		if (currentSquare.getFile() - 1 >= 0 && currentSquare.getFile() - 1 <= 7 && currentSquare.getRank() + 0 >= 0
				&& currentSquare.getRank() + 0 <= 7)
			accessibleSquares.add(new Square(currentSquare.getFile() - 1, currentSquare.getRank() + 0));
		if (currentSquare.getFile() - 1 >= 0 && currentSquare.getFile() - 1 <= 7 && currentSquare.getRank() + 1 >= 0
				&& currentSquare.getRank() + 1 <= 7)
			accessibleSquares.add(new Square(currentSquare.getFile() - 1, currentSquare.getRank() + 1));
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
