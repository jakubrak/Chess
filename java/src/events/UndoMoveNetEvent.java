package events;

import model.Colour;

public class UndoMoveNetEvent extends Event {
	private static final long serialVersionUID = 1L;
	private final Colour playersPiecesColour;

	public UndoMoveNetEvent(final Colour playersPiecesColour) {
		this.playersPiecesColour = playersPiecesColour;
	}

	public Colour getPlayersPiecesColour() {
		return playersPiecesColour;
	}
}
