package controller;

import model.Model;
import view.View;
import events.Event;

class ConnectingSucceededAction extends Action {
	@Override
	public void performAction(final Event event, final Model model, final View view, final Controller controller) {
		view.setNewGameButtonEnabled(true);
	}
}
