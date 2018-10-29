package events;

import model.Colour;

public class NewGameNetEvent extends Event {
	private static final long serialVersionUID = 1L;
	private Colour playersPiecesColour;

	public Colour getPlayersPiecesColour() {
		return playersPiecesColour;
	}

	public void setPlayersPiecesColour(final Colour playersPiecesColour) {
		this.playersPiecesColour = playersPiecesColour;
	}
}
