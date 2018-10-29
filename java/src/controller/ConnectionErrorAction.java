package controller;

import model.Model;
import view.View;
import events.Event;

class ConnectionErrorAction extends Action {
	@Override
	public void performAction(final Event event, final Model model, final View view, final Controller controller) {
		view.showConnectionErrorMessageDialog();
		view.setChessboardEnabled(false);
		view.setCreateConnectionButtonEnabled(true);
		view.setConnectToGameButtonEnabled(true);
		view.setNewGameButtonEnabled(false);
		view.setUndoMoveButtonEnabled(false);
		view.setGiveUpButtonEnabled(false);
	}
}
