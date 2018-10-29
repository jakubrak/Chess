package controller;

import model.Colour;
import model.Model;
import view.View;
import events.Event;
import events.UndoMoveEvent;
import events.UndoMoveNetEvent;

class UndoMoveAction extends Action {
	@Override
	public void performAction(final Event event, final Model model, final View view, final Controller controller) {
		final UndoMoveEvent undoMoveEvent = (UndoMoveEvent) event;
		final Colour playersPiecesColour = undoMoveEvent.getPlayersPiecesColour();
		try {
			if (playersPiecesColour == Colour.BLACK)
				controller.putEventToNetThreadBlockingQueue(new UndoMoveNetEvent(Colour.WHITE));
			else
				controller.putEventToNetThreadBlockingQueue(new UndoMoveNetEvent(Colour.BLACK));
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		model.undoMove();
		view.repaintChessboardPanel(model.getGameState());
		if (playersPiecesColour == model.getWhoseTurn())
			view.setChessboardEnabled(true);
		else
			view.setChessboardEnabled(false);
	}
}
