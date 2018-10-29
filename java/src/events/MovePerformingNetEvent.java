package events;

import java.awt.Point;

public class MovePerformingNetEvent extends Event {
	private static final long serialVersionUID = 1L;
	private final Point mirrorCurrentPoint;

	public MovePerformingNetEvent(final Point mirrorCurrentPoint) {
		this.mirrorCurrentPoint = mirrorCurrentPoint;
	}

	public Point getMirrorCurrentPoint() {
		return mirrorCurrentPoint;
	}
}
