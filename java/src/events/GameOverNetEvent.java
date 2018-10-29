package events;

import model.Winner;

public class GameOverNetEvent extends Event {
	private static final long serialVersionUID = 1L;
	private final Winner winner;

	public GameOverNetEvent(final Winner winner) {
		this.winner = winner;
	}

	public Winner getWinner() {
		return winner;
	}
}
