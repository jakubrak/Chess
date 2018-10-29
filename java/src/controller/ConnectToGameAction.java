package controller;

import model.Model;
import view.View;
import events.ConnectToGameEvent;
import events.Event;

class ConnectToGameAction extends Action {
	@Override
	public void performAction(final Event event, final Model model, final View view, final Controller controller) {
		ConnectToGameEvent connectToGameEvent = (ConnectToGameEvent) event;
		final NetThread netThread = new ClientThread(view.getBlockingQueue(), connectToGameEvent.getIp());
		controller.setNetThread(netThread);
		netThread.start();
		view.setCreateConnectionButtonEnabled(false);
		view.setConnectToGameButtonEnabled(false);
	}
}
