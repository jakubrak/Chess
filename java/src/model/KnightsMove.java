package model;

import logger.Logger;
import exceptions.IllegalMoveException;

/**
 * @author Jakub Rak
 */
class KnightsMove extends Move {
	private final Model model;
	private final Piece piece;
	private Piece capturedPiece;
	private final Square startSquare;
	private final Square endSquare;
	private final boolean wasMoved;

	KnightsMove(final Model model, final Piece piece, final Square startSquare, final Square endSquare) {
		this.model = model;
		this.piece = piece;
		capturedPiece = model.getPiece(endSquare);
		this.startSquare = startSquare;
		this.endSquare = endSquare;
		wasMoved = piece.wasMoved();
	}

	@Override
	void doMove() throws IllegalMoveException {
		if (capturedPiece != null)
			model.removePieceFromList(capturedPiece);
		model.setPieceOnChessboard(endSquare, piece);
		model.setPieceOnChessboard(startSquare, null);
		piece.setCurrentSquare(endSquare);
		piece.setWasMoved(true);
		model.changeTurn();
		if (model.isKingInCheck() == true) {
			undoMove();
			throw new IllegalMoveException();
		}
		Logger.print(this, "The move has been done.");
	}

	@Override
	Piece getCapturedPiece() {
		return capturedPiece;
	}

	@Override
	Square getEndSquare() {
		return endSquare;
	}

	@Override
	Piece getPiece() {
		return piece;
	}

	@Override
	Square getStartSquare() {
		return startSquare;
	}

	@Override
	boolean isPathClear() {
		final Square square = endSquare;
		if (model.isSquareFree(square))
			return true;
		if (piece.getColour() != model.getPiece(square).getColour()) {
			capturedPiece = model.getPiece(square);
			return true;
		}
		Logger.print(this, "Path isn't clear.");
		return false;
	}

	@Override
	void undoMove() {
		model.setPieceOnChessboard(startSquare, piece);
		model.setPieceOnChessboard(endSquare, capturedPiece);
		if (capturedPiece != null)
			model.addPieceToList(capturedPiece);
		piece.setCurrentSquare(startSquare);
		piece.setWasMoved(wasMoved);
		model.changeTurn();
		Logger.print(this, "The move has been undone.");
	}

	@Override
	boolean wasMoved() {
		return wasMoved;
	}
}
