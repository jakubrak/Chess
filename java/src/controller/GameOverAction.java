package controller;

import model.Model;
import model.Winner;
import view.View;
import events.Event;
import events.GameOverEvent;
import events.GameOverNetEvent;

class GameOverAction extends Action {
	@Override
	public void performAction(final Event event, final Model model, final View view, final Controller controller) {
		final GameOverEvent gameOverEvent = (GameOverEvent) event;
		final Winner winner = gameOverEvent.getWinner();
		final GameOverNetEvent gameOverNetEvent = new GameOverNetEvent(winner);
		try {
			controller.putEventToNetThreadBlockingQueue(gameOverNetEvent);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
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
