package events;

import java.awt.Point;

public class MovePerformingEvent extends Event {
	private static final long serialVersionUID = 1L;
	private final Point currentPoint;
	private final Point mirrorCurrentPoint;

	public MovePerformingEvent(final Point currentPoint, final Point mirrorCurrentPoint) {
		this.currentPoint = currentPoint;
		this.mirrorCurrentPoint = mirrorCurrentPoint;
	}

	public Point getCurrentPoint() {
		return currentPoint;
	}

	public Point getMirrorCurrentPoint() {
		return mirrorCurrentPoint;
	}
}
