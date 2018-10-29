package controller;

import model.Colour;
import model.Model;
import view.View;
import events.Event;
import events.UndoMoveNetEvent;

class UndoMoveNetAction extends Action {
	@Override
	public void performAction(final Event event, final Model model, final View view, final Controller controller) {
		final UndoMoveNetEvent undoMoveNetEvent = (UndoMoveNetEvent) event;
		final Colour playersPiecesColour = undoMoveNetEvent.getPlayersPiecesColour();
		model.undoMove();
		view.repaintChessboardPanel(model.getGameState());
		if (playersPiecesColour == model.getWhoseTurn())
			view.setChessboardEnabled(true);
		else
			view.setChessboardEnabled(false);
	}
}
