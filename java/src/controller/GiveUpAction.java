package controller;

import model.Model;
import view.View;
import events.Event;
import events.GameOverEvent;
import events.GiveUpEvent;

class GiveUpAction extends Action {
	@Override
	public void performAction(final Event event, final Model model, final View view, final Controller controller) {
		final GiveUpEvent giveUpEvent = (GiveUpEvent) event;
		try {
			view.putEventToBlockingQueue(new GameOverEvent(giveUpEvent.getWinner()));
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
	}
}
