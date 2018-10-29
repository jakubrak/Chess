package controller;

import model.Model;
import view.View;
import events.Event;

class CreateConnectionAction extends Action {
	@Override
	public void performAction(final Event event, final Model model, final View view, final Controller controller) {
		final NetThread netThread = new ServerThread(view.getBlockingQueue());
		controller.setNetThread(netThread);
		netThread.start();
		view.setCreateConnectionButtonEnabled(false);
		view.setConnectToGameButtonEnabled(false);
	}
}
