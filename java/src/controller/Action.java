package controller;

import model.Model;
import view.View;
import events.Event;

abstract class Action {
	/**
	 * Performs action.
	 */
	abstract public void performAction(Event event, Model model, View view, Controller controller);
}
