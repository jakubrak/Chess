package events;

import model.Winner;

public class GameOverEvent extends Event {
	private static final long serialVersionUID = 1L;
	private final Winner winner;

	public GameOverEvent(final Winner winner) {
		this.winner = winner;
	}

	public Winner getWinner() {
		return winner;
	}
}
