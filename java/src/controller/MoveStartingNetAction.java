package controller;

import model.Model;
import view.View;
import events.Event;
import events.MoveStartingNetEvent;

class MoveStartingNetAction extends Action {
	@Override
	public void performAction(final Event event, final Model model, final View view, final Controller controller) {
		final MoveStartingNetEvent moveStartingNetEvent = (MoveStartingNetEvent) event;
		view.setChessboardPanelMovingPieceStartPoint(moveStartingNetEvent.getMirrorStartPoint());
	}
}
