package controller;

import model.Model;
import view.View;
import events.Event;
import events.MovePerformingNetEvent;

class MovePerformingNetAction extends Action {
	@Override
	public void performAction(final Event event, final Model model, final View view, final Controller controller) {
		final MovePerformingNetEvent movePerformingNetEvent = (MovePerformingNetEvent) event;
		view.setChessboardPanelMovingPieceCurrentPoint(movePerformingNetEvent.getMirrorCurrentPoint());
		view.repaintChessboardPanel(model.getGameState());
	}
}
