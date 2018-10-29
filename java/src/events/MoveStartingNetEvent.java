package events;

import java.awt.Point;

public class MoveStartingNetEvent extends Event {
	private static final long serialVersionUID = 1L;
	private final Point mirrorStartPoint;

	public MoveStartingNetEvent(final Point mirrorStartPoint) {
		this.mirrorStartPoint = mirrorStartPoint;
	}

	public Point getMirrorStartPoint() {
		return mirrorStartPoint;
	}
}
