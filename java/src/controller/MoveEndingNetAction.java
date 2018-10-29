package controller;

import logger.Logger;
import model.Model;
import model.Square;
import model.Winner;
import view.Orientation;
import view.View;
import events.Event;
import events.GameOverEvent;
import events.MoveEndingNetEvent;
import exceptions.IllegalMoveException;

class MoveEndingNetAction extends Action {
	@Override
	public void performAction(final Event event, final Model model, final View view, final Controller controller) {
		final MoveEndingNetEvent moveEndingNetEvent = (MoveEndingNetEvent) event;
		final Square startSquare = moveEndingNetEvent.getStartSqare().getMirrorSquare();
		final Square endSquare = moveEndingNetEvent.getEndSquare().getMirrorSquare();
		Logger.print(this, "\ndoMove(" + startSquare + ", " + endSquare + ")");
		try {
			if (startSquare.getOrientation() == Orientation.NORMAL)
				model.doMove(startSquare, endSquare);
			else
				model.doMove(startSquare.getMirrorSquare(), endSquare.getMirrorSquare());
			final Winner winner = model.getWinner();
			if (winner != null)
				view.putEventToBlockingQueue(new GameOverEvent(winner));
			view.setChessboardEnabled(true);
		}
		catch (final IllegalMoveException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		view.setChessboardPanelMovingPieceStartPoint(null);
		view.setChessboardPanelMovingPieceCurrentPoint(null);
		view.repaintChessboardPanel(model.getGameState());
	}
}
