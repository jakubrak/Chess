package model;

import logger.Logger;
import exceptions.IllegalMoveException;

/**
 * @author Jakub Rak
 */
class VerticalMove extends Move {
	private final Model model;
	private final Piece piece;
	private Piece capturedPiece;
	private Piece pawnPromotedTo;
	private final Square startSquare;
	private final Square endSquare;
	private final boolean wasMoved;

	VerticalMove(final Model model, final Piece piece, final Square startSquare, final Square endSquare) {
		this.model = model;
		this.piece = piece;
		capturedPiece = model.getPiece(endSquare);
		pawnPromotedTo = null;
		this.startSquare = startSquare;
		this.endSquare = endSquare;
		capturedPiece = null;
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
		if (piece instanceof Pawn)
			if (piece.getColour() == Colour.WHITE) {
				if (endSquare.getRank() == 7) {
					model.removePieceFromList(piece);
					pawnPromotedTo = new Queen(model, Colour.WHITE, endSquare);
					model.addPieceToList(pawnPromotedTo);
					model.setPieceOnChessboard(endSquare, pawnPromotedTo);
				}
			}
			else if (endSquare.getRank() == 0) {
				model.removePieceFromList(piece);
				pawnPromotedTo = new Queen(model, Colour.WHITE, endSquare);
				model.addPieceToList(pawnPromotedTo);
				model.setPieceOnChessboard(endSquare, pawnPromotedTo);
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

	/**
	 * Checks if it is a forward move.
	 * @return true if it is a forward move, otherwise false
	 */
	boolean isForwardMove() {
		return startSquare.getRank() - endSquare.getRank() > 0 && piece.getColour() == Colour.BLACK
				|| startSquare.getRank() - endSquare.getRank() < 0 && piece.getColour() == Colour.WHITE;
	}

	/**
	 * Checks if it is one step move.
	 * @return true if it is one step move, otherwise false
	 */
	boolean isOneStepMove() {
		return Math.abs(startSquare.getRank() - endSquare.getRank()) == 1;
	}

	@Override
	boolean isPathClear() {
		Square square = endSquare;
		if (model.isSquareFree(square) == true)
			square = nextSquare(square);
		else if (piece.getColour() != model.getPiece(square).getColour()) {
			capturedPiece = model.getPiece(square);
			square = nextSquare(square);
			Logger.print(this, "Capture..");
		}
		else {
			Logger.print(this, "Path isn't clear.");
			return false;
		}
		while (square.equals(startSquare) == false) {
			if (model.isSquareFree(square) == false) {
				Logger.print(this, "Path isn't clear.");
				return false;
			}
			square = nextSquare(square);
		}
		Logger.print(this, "The path is clear...");
		return true;
	}

	/**
	 * Checks if it is two steps move.
	 * @return true if it is two steps move, otherwise false
	 */
	boolean isTwoStepsMove() {
		return Math.abs(endSquare.getRank() - startSquare.getRank()) == 2;
	}
	
	/**
	 * Gets next square on move's path (direction from {@link #endSquare} to {@link #startSquare})
	 * @param currentSquare - current square
	 * @return next square
	 */
	private Square nextSquare(final Square currentSquare) {
		final Square nextSquare = new Square();
		nextSquare.setFile(currentSquare.getFile());
		if (endSquare.getRank() < startSquare.getRank())
			nextSquare.setRank(currentSquare.getRank() + 1);
		else
			nextSquare.setRank(currentSquare.getRank() - 1);
		return nextSquare;
	}

	@Override
	void undoMove() {
		model.setPieceOnChessboard(startSquare, piece);
		model.setPieceOnChessboard(endSquare, capturedPiece);
		if (capturedPiece != null)
			model.addPieceToList(capturedPiece);
		if (pawnPromotedTo != null) {
			model.removePieceFromList(pawnPromotedTo);
			model.addPieceToList(piece);
		}
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
