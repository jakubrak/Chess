package events;

import model.Square;

public class MoveEndingNetEvent extends Event {
	private static final long serialVersionUID = 1L;
	private final Square startSqare;
	private final Square endSquare;

	public MoveEndingNetEvent(final Square startSqare, final Square endSquare) {
		this.startSqare = startSqare;
		this.endSquare = endSquare;
	}

	public Square getEndSquare() {
		return endSquare;
	}

	public Square getStartSqare() {
		return startSqare;
	}
}
