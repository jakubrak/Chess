package events;

import java.awt.Point;

public class MoveStartingEvent extends Event {
	private static final long serialVersionUID = 1L;
	private final Point startPoint;
	private final Point mirrorStartPoint;

	public MoveStartingEvent(final Point startPoint, final Point mirrorStartPoint) {
		this.startPoint = startPoint;
		this.mirrorStartPoint = mirrorStartPoint;
	}

	public Point getMirrorStartPoint() {
		return mirrorStartPoint;
	}

	public Point getStartPoint() {
		return startPoint;
	}
}
