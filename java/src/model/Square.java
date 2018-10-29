package model;

import java.io.Serializable;

import view.Orientation;

/**
 * Square of chessboard
 * @author Jakub Rak
 */
public class Square implements Serializable {
	private static final long serialVersionUID = 1L;
	private int x, y;
	private Orientation orientation;

	public Square() {
	}

	public Square(final int x, final int y) {
		this.x = x;
		this.y = y;
		orientation = Orientation.NORMAL;
	}

	@Override
	public boolean equals(final Object obj) {
		final Square square = (Square) obj;
		if (this.getClass().equals(obj.getClass()) && square.x == x && square.y == y)
			return true;
		return false;
	}

	public Square getMirrorSquare() {
		final Square mirrorSquare = new Square(7 - x, 7 - y);
		mirrorSquare.orientation = Orientation.MIRROR;
		return mirrorSquare;
	}

	public Orientation getOrientation() {
		return orientation;
	}

	public int getFile() {
		return x;
	}

	public int getRank() {
		return y;
	}

	public void setOrientation(final Orientation orientation) {
		this.orientation = orientation;
	}

	public void setFile(final int file) {
		this.x = file;
	}

	public void setRank(final int rank) {
		this.y = rank;
	}

	@Override
	public String toString() {
		return "[" + x + ", " + y + "]";
	}
}
