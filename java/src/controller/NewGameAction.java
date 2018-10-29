package controller;

import model.Colour;
import model.Model;
import view.View;
import events.Event;
import events.NewGameNetEvent;

public class NewGameAction extends Action {
	@Override
	public void performAction(final Event event, Model model, final View view, final Controller controller) {
		view.setNewGameButtonEnabled(false);
		view.setUndoMoveButtonEnabled(true);
		view.setGiveUpButtonEnabled(true);
		model.createNewGame();
		final NewGameNetEvent newGameNetEvent = new NewGameNetEvent();
		final long randomValue = Math.round(Math.random());
		if (randomValue == 0) {
			newGameNetEvent.setPlayersPiecesColour(Colour.WHITE);
			view.setStartChessboardSettings(Colour.WHITE);
		}
		else {
			newGameNetEvent.setPlayersPiecesColour(Colour.BLACK);
			view.setStartChessboardSettings(Colour.BLACK);
		}
		try {
			controller.putEventToNetThreadBlockingQueue(newGameNetEvent);
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		view.repaintChessboardPanel(model.getGameState());
	}
}
