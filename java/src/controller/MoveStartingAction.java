package controller;

import model.Model;
import view.View;
import events.Event;
import events.MoveStartingEvent;
import events.MoveStartingNetEvent;

class MoveStartingAction extends Action {
	@Override
	public void performAction(final Event event, final Model model, final View view, final Controller controller) {
		if (model != null) {
			final MoveStartingEvent moveStartingEvent = (MoveStartingEvent) event;
			final MoveStartingNetEvent moveStartingNetEvent = new MoveStartingNetEvent(
			moveStartingEvent.getMirrorStartPoint());
			try {
				controller.putEventToNetThreadBlockingQueue(moveStartingNetEvent);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
			view.setChessboardPanelMovingPieceStartPoint(moveStartingEvent.getStartPoint());
		}
	}
}
