package controller;

import model.Colour;
import model.Model;
import view.View;
import events.Event;
import events.NewGameNetEvent;

class NewGameNetAction extends Action {
	@Override
	public void performAction(final Event event, Model model, final View view, final Controller controller) {
		view.setNewGameButtonEnabled(false);
		view.setUndoMoveButtonEnabled(true);
		view.setGiveUpButtonEnabled(true);
		model.createNewGame();
		final NewGameNetEvent newGameNetEvent = (NewGameNetEvent) event;
		if (newGameNetEvent.getPlayersPiecesColour() == Colour.BLACK)
			view.setStartChessboardSettings(Colour.WHITE);
		else
			view.setStartChessboardSettings(Colour.BLACK);
		view.repaintChessboardPanel(model.getGameState());
	}
}
