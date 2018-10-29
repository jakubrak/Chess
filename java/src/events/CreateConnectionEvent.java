package events;

import model.Colour;

public class CreateConnectionEvent extends Event {
	private static final long serialVersionUID = 1L;
	private Colour playerColour;

	public Colour getPlayerColour() {
		return playerColour;
	}

	public void setPlayerColour(final Colour playerColour) {
		this.playerColour = playerColour;
	}
}
