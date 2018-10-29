package controller;

import logger.Logger;
import model.Model;
import model.Square;
import model.Winner;
import view.View;
import events.Event;
import events.GameOverEvent;
import events.MoveEndingEvent;
import events.MoveEndingNetEvent;
import exceptions.IllegalMoveException;

class MoveEndingAction extends Action {
	@Override
	public void performAction(final Event event, final Model model, final View view, final Controller controller) {
		if (model != null) {
			final MoveEndingEvent moveEndingEvent = (MoveEndingEvent) event;
			final Square startSquare = moveEndingEvent.getStartSqare();
			final Square endSquare = moveEndingEvent.getEndSquare();
			final MoveEndingNetEvent moveEndingNetEvent = new MoveEndingNetEvent(startSquare, endSquare);
			Logger.print(this, "\ndoMove(" + startSquare + ", " + endSquare + ")");
			try {
				controller.putEventToNetThreadBlockingQueue(moveEndingNetEvent);
				model.doMove(startSquare, endSquare);
				final Winner winner = model.getWinner();
				if (winner != null)
					view.putEventToBlockingQueue(new GameOverEvent(winner));
				view.setChessboardEnabled(false);
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
}
