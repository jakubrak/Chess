package events;

import model.Square;

public class MoveEndingEvent extends Event {
	private static final long serialVersionUID = 1L;
	private final Square startSqare;
	private final Square endSquare;

	public MoveEndingEvent(final Square startSqare, final Square endSquare) {
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
