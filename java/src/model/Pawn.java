package model;

import java.util.LinkedList;

import logger.Logger;

/**
 * @author Jakub Rak
 */
class Pawn extends Piece {
	private final int pieceId;
	private final Model model;
	private final Colour colour;
	private boolean wasMoved;
	private Square currentSquare;

	Pawn(final Model model, final Colour colour, final Square currentSquare) {
		pieceId = colour == Colour.WHITE ? 6 : 12;
		this.currentSquare = currentSquare;
		this.model = model;
		this.colour = colour;
		wasMoved = false;
	}

	@Override
	boolean isMoveValid(final DiagonalMove diagonalMove) {
		final boolean isRightTurn = model.getWhoseTurn() == colour;
		final boolean isPathClear = diagonalMove.isPathClear();
		final boolean isOneStepMove = diagonalMove.isOneStepMove();
		final boolean isForwardMove = diagonalMove.isForwardMove();
		final Piece capturedPiece = diagonalMove.getCapturedPiece();

		if (isRightTurn && isPathClear && isOneStepMove && isForwardMove && capturedPiece != null)
			return true;
		if (isRightTurn && isPathClear && isOneStepMove && isForwardMove && diagonalMove.checkEnPassant())
			return true;
		Logger.print(this, "Check results of " + colour + " pawn diagonal move:" + "isRightTurn: " + isRightTurn
				+ "\nisPathClear: " + isPathClear + "\nisOneStepMove: " + isOneStepMove + "\nisForwardMove: "
				+ isForwardMove + "\ncapturedPiece: " + capturedPiece);
		return false;
	}

	@Override
	boolean isMoveValid(final VerticalMove verticalMove) {
		final boolean isRightTurn = model.getWhoseTurn() == colour;
		final boolean isPathClear = verticalMove.isPathClear();
		final boolean isOneStepMove = verticalMove.isOneStepMove();
		final boolean isTwoStepsMove = verticalMove.isTwoStepsMove();
		final boolean isForwardMove = verticalMove.isForwardMove();
		final boolean isCapture = verticalMove.getCapturedPiece() != null;

		if (isRightTurn && isPathClear && isOneStepMove && isForwardMove && !isCapture)
			return true;
		if (isRightTurn && isPathClear && isTwoStepsMove && !wasMoved && isForwardMove && !isCapture)
			return true;
		Logger.print(this, "Check results of " + colour + " pawn vertical move:" + "\nisRightTurn:" + isRightTurn
				+ "\nisPathClear: " + isPathClear + "\nisOneStepMove: " + isOneStepMove + "\nisTwoStepsMove: "
				+ isTwoStepsMove + "\nwasMoved: " + wasMoved + "\nisForwardMove: " + isForwardMove + "\nisCapture: "
				+ isCapture);
		return false;
	}

	@Override
	LinkedList<Square> getAccessibleSquares() {
		final LinkedList<Square> accessibleSquares = new LinkedList<Square>();
		if (colour == Colour.WHITE) {
			if (currentSquare.getFile() + 0 >= 0 && currentSquare.getFile() + 0 <= 7 && currentSquare.getRank() + 1 >= 0
					&& currentSquare.getRank() + 1 <= 7)
				accessibleSquares.add(new Square(currentSquare.getFile() + 0, currentSquare.getRank() + 1));
			if (currentSquare.getFile() + 1 >= 0 && currentSquare.getFile() + 1 <= 7 && currentSquare.getRank() + 1 >= 0
					&& currentSquare.getRank() + 1 <= 7)
				accessibleSquares.add(new Square(currentSquare.getFile() + 1, currentSquare.getRank() + 1));
			if (currentSquare.getFile() - 1 >= 0 && currentSquare.getFile() - 1 <= 7 && currentSquare.getRank() + 1 >= 0
					&& currentSquare.getRank() + 1 <= 7)
				accessibleSquares.add(new Square(currentSquare.getFile() - 1, currentSquare.getRank() + 1));
			if (currentSquare.getFile() + 0 >= 0 && currentSquare.getFile() + 0 <= 7 && currentSquare.getRank() + 2 >= 0
					&& currentSquare.getRank() + 2 <= 7)
				accessibleSquares.add(new Square(currentSquare.getFile() + 0, currentSquare.getRank() + 2));
		}
		else {
			if (currentSquare.getFile() + 0 >= 0 && currentSquare.getFile() + 0 <= 7 && currentSquare.getRank() - 1 >= 0
					&& currentSquare.getRank() - 1 <= 7)
				accessibleSquares.add(new Square(currentSquare.getFile() + 0, currentSquare.getRank() - 1));
			if (currentSquare.getFile() + 1 >= 0 && currentSquare.getFile() + 1 <= 7 && currentSquare.getRank() - 1 >= 0
					&& currentSquare.getRank() - 1 <= 7)
				accessibleSquares.add(new Square(currentSquare.getFile() + 1, currentSquare.getRank() - 1));
			if (currentSquare.getFile() - 1 >= 0 && currentSquare.getFile() - 1 <= 7 && currentSquare.getRank() - 1 >= 0
					&& currentSquare.getRank() - 1 <= 7)
				accessibleSquares.add(new Square(currentSquare.getFile() - 1, currentSquare.getRank() - 1));
			if (currentSquare.getFile() + 0 >= 0 && currentSquare.getFile() + 0 <= 7 && currentSquare.getRank() - 2 >= 0
					&& currentSquare.getRank() - 2 <= 7)
				accessibleSquares.add(new Square(currentSquare.getFile() + 0, currentSquare.getRank() - 2));
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
