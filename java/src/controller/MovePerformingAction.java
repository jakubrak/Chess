package controller;

import model.Model;
import view.View;
import events.Event;
import events.MovePerformingEvent;
import events.MovePerformingNetEvent;

class MovePerformingAction extends Action {
	@Override
	public void performAction(final Event event, final Model model, final View view, final Controller controller) {
		if (model != null) {
			final MovePerformingEvent movePerformingEvent = (MovePerformingEvent) event;
			final MovePerformingNetEvent movePerformingNetEvent = new MovePerformingNetEvent(
			movePerformingEvent.getMirrorCurrentPoint());
			try {
				controller.putEventToNetThreadBlockingQueue(movePerformingNetEvent);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			view.setChessboardPanelMovingPieceCurrentPoint(movePerformingEvent.getCurrentPoint());
			view.repaintChessboardPanel(model.getGameState());
		}
	}
}
