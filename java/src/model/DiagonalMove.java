package model;

import logger.Logger;
import exceptions.IllegalMoveException;

/**
 * @author Jakub Rak
 */
class DiagonalMove extends Move {
	private final Model model;
	private final Piece piece;
	private Piece capturedPiece;
	private Piece pawnPromotedTo;
	private final Square startSquare;
	private final Square endSquare;
	private final boolean wasMoved;

	DiagonalMove(final Model model, final Piece piece, final Square startSquare, final Square endSquare) {
		this.model = model;
		this.piece = piece;
		capturedPiece = model.getPiece(endSquare);
		pawnPromotedTo = null;
		this.startSquare = startSquare;
		this.endSquare = endSquare;
		capturedPiece = null;
		wasMoved = piece.wasMoved();
	}

	/**
	 * Checks if it might be en passant move.
	 * @return true if it might be en passant move, otherwise false
	 */
	boolean checkEnPassant() {
		final Move lastMove = model.getLastMove();
		if (lastMove != null && !lastMove.wasMoved() && lastMove.getPiece() instanceof Pawn
				&& startSquare.getRank() == lastMove.getEndSquare().getRank()
				&& endSquare.getFile() == lastMove.getEndSquare().getFile()
				&& Math.abs(startSquare.getFile() - lastMove.getEndSquare().getFile()) == 1) {
			capturedPiece = lastMove.getPiece();
			return true;
		}
		return false;
	}

	@Override
	void doMove() throws IllegalMoveException {
		if (capturedPiece != null) {
			model.removePieceFromList(capturedPiece);
			model.setPieceOnChessboard(capturedPiece.getCurrentSquare(), null);
		}
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
			square = getNextSquare(square);
		else if (piece.getColour() != model.getPiece(square).getColour()) {
			capturedPiece = model.getPiece(square);
			square = getNextSquare(square);
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
			square = getNextSquare(square);
		}
		Logger.print(this, "The path is clear...");
		return true;
	}
	
	/**
	 * Gets next square on move's path (direction from {@link #endSquare} to {@link #startSquare})
	 * @param currentSquare - current square
	 * @return next square
	 */
	private Square getNextSquare(final Square currentSquare) {
		final Square nextSquare = new Square();
		if (endSquare.getFile() < startSquare.getFile())
			nextSquare.setFile(currentSquare.getFile() + 1);
		else
			nextSquare.setFile(currentSquare.getFile() - 1);
		if (endSquare.getRank() < startSquare.getRank())
			nextSquare.setRank(currentSquare.getRank() + 1);
		else
			nextSquare.setRank(currentSquare.getRank() - 1);
		return nextSquare;
	}

	@Override
	void undoMove() {
		model.setPieceOnChessboard(startSquare, piece);
		if (capturedPiece != null && !capturedPiece.getCurrentSquare().equals(endSquare)) {
			model.setPieceOnChessboard(capturedPiece.getCurrentSquare(), capturedPiece);
			model.setPieceOnChessboard(endSquare, null);
			model.addPieceToList(capturedPiece);
		}
		else {
			model.setPieceOnChessboard(endSquare, capturedPiece);
			model.addPieceToList(capturedPiece);
		}
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
