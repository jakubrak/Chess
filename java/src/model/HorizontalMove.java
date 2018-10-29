package model;

import logger.Logger;
import exceptions.IllegalMoveException;

/**
 * @author Jakub Rak
 */
class HorizontalMove extends Move {
	private final Model model;
	private final Piece piece;
	private Piece capturedPiece;
	private final Square startSquare;
	private final Square endSquare;
	private final boolean wasMoved;
	private HorizontalMove castling;

	HorizontalMove(final Model model, final Piece piece, final Square startSquare, final Square endSquare) {
		this.model = model;
		this.piece = piece;
		capturedPiece = model.getPiece(endSquare);
		this.startSquare = startSquare;
		this.endSquare = endSquare;
		wasMoved = piece.wasMoved();
		castling = null;
	}

	/**
	 * Checks if if it might be castling move.
	 * @return true if it might be castling move, otherwise false
	 */
	boolean checkCastling() {
		if (startSquare.getFile() - endSquare.getFile() == -2 && piece.getColour() == Colour.WHITE) {
			final Piece rook = model.getPiece(new Square(7, 0));
			final HorizontalMove horizontalMove = new HorizontalMove(model, rook, new Square(7, 0), new Square(4, 0));
			if (rook != null && rook.wasMoved() == false && rook.isMoveValid(horizontalMove)) {
				castling = horizontalMove;
				return true;
			}
			return false;
		}
		else if (startSquare.getFile() - endSquare.getFile() == 2 && piece.getColour() == Colour.WHITE) {
			final Piece rook = model.getPiece(new Square(0, 0));
			final HorizontalMove horizontalMove = new HorizontalMove(model, rook, new Square(0, 0), new Square(2, 0));
			if (rook != null && rook.wasMoved() == false && rook.isMoveValid(horizontalMove)) {
				castling = horizontalMove;
				return true;
			}
			return false;
		}
		else if (startSquare.getFile() - endSquare.getFile() == -2 && piece.getColour() == Colour.BLACK) {
			final Piece rook = model.getPiece(new Square(7, 7));
			final HorizontalMove horizontalMove = new HorizontalMove(model, rook, new Square(7, 7), new Square(4, 7));
			if (rook != null && rook.wasMoved() == false && rook.isMoveValid(horizontalMove)) {
				castling = horizontalMove;
				return true;
			}
			return false;
		}
		else if (startSquare.getFile() - endSquare.getFile() == 2 && piece.getColour() == Colour.BLACK) {
			final Piece rook = model.getPiece(new Square(0, 7));
			final HorizontalMove horizontalMove = new HorizontalMove(model, rook, new Square(0, 7), new Square(2, 7));
			if (rook != null && rook.wasMoved() == false && rook.isMoveValid(horizontalMove)) {
				castling = horizontalMove;
				return true;
			}
			return false;
		}
		else
			return false;
	}

	@Override
	void doMove() throws IllegalMoveException {
		if (castling == null) {
			if (capturedPiece != null)
				model.removePieceFromList(capturedPiece);
			model.setPieceOnChessboard(endSquare, piece);
			model.setPieceOnChessboard(startSquare, null);
			piece.setCurrentSquare(endSquare);
			piece.setWasMoved(true);
		}
		else {
			model.setPieceOnChessboard(castling.endSquare, piece);
			model.setPieceOnChessboard(startSquare, null);
			piece.setCurrentSquare(castling.endSquare);
			piece.setWasMoved(true);
			if (model.isKingInCheck() == true) {
				castling = null;
				model.undoMove();
				throw new IllegalMoveException();
			}
			model.setPieceOnChessboard(endSquare, piece);
			model.setPieceOnChessboard(startSquare, null);
			piece.setCurrentSquare(endSquare);
			model.setPieceOnChessboard(castling.endSquare, castling.piece);
			model.setPieceOnChessboard(castling.startSquare, null);
			castling.piece.setCurrentSquare(castling.endSquare);
			castling.piece.setWasMoved(true);
		}
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

	/**
	 * Checks if it is one step move.
	 * @return true if it is one step move, otherwise false
	 */
	boolean isOneStepMove() {
		return Math.abs(startSquare.getFile() - endSquare.getFile()) == 1;
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
	 * Gets next square on move's path (direction from {@link #endSquare} to {@link #startSquare})
	 * @param currentSquare - current square
	 * @return next square
	 */
	private Square nextSquare(final Square currentSquare) {
		final Square nextSquare = new Square();
		nextSquare.setRank(currentSquare.getRank());
		if (endSquare.getFile() < startSquare.getFile())
			nextSquare.setFile(currentSquare.getFile() + 1);
		else
			nextSquare.setFile(currentSquare.getFile() - 1);
		return nextSquare;
	}

	@Override
	void undoMove() {
		model.setPieceOnChessboard(startSquare, piece);
		model.setPieceOnChessboard(endSquare, capturedPiece);
		if (capturedPiece != null)
			model.addPieceToList(capturedPiece);
		piece.setCurrentSquare(startSquare);
		piece.setWasMoved(wasMoved);
		if (castling != null) {
			model.setPieceOnChessboard(castling.startSquare, castling.piece);
			model.setPieceOnChessboard(castling.endSquare, null);
			castling.piece.setCurrentSquare(castling.startSquare);
			castling.piece.setWasMoved(wasMoved);
		}
		model.changeTurn();
		Logger.print(this, "The move has been undone.");
	}

	@Override
	boolean wasMoved() {
		return wasMoved;
	}
}
