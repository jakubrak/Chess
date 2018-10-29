package controller;

import model.Model;
import model.Winner;
import view.View;
import events.Event;
import events.GameOverNetEvent;

class GameOverNetAction extends Action {
	@Override
	public void performAction(final Event event, final Model model, final View view, final Controller controller) {
		final GameOverNetEvent gameOverNetEvent = (GameOverNetEvent) event;
		final Winner winner = gameOverNetEvent.getWinner();
		if (winner == Winner.WHITE)
			view.showWhiteWonMessageDialog();
		else if (winner == Winner.BLACK)
			view.showBlackWonMessageDialog();
		else if (winner == Winner.TIE)
			view.showTieMessageDialog();
		view.setChessboardEnabled(false);
		view.setNewGameButtonEnabled(true);
		view.setUndoMoveButtonEnabled(false);
		view.setGiveUpButtonEnabled(false);
	}
}
