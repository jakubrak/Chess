package controller;

import java.util.HashMap;
import java.util.Map;

import model.Model;
import view.View;
import events.ConnectToGameEvent;
import events.ConnectingSucceededEvent;
import events.ConnectionErrorEvent;
import events.CreateConnectionEvent;
import events.Event;
import events.GameOverEvent;
import events.GameOverNetEvent;
import events.GiveUpEvent;
import events.MoveEndingEvent;
import events.MoveEndingNetEvent;
import events.MovePerformingEvent;
import events.MovePerformingNetEvent;
import events.MoveStartingEvent;
import events.MoveStartingNetEvent;
import events.NewGameEvent;
import events.NewGameNetEvent;
import events.UndoMoveEvent;
import events.UndoMoveNetEvent;

/**
 * Controller of MVC design pattern
 * @author Jakub Rak
 */
public class Controller {
	private final View view;
	volatile private Model model;
	private NetThread netThread;
	private final Map<Class<? extends Event>, Action> eventActionMap;

	public Controller() {
		view = new View();
		model = new Model();
		eventActionMap = new HashMap<Class<? extends Event>, Action>();
		fillEventActionMap();
	}

	/**
	 * Fills {@link #eventActionMap}
	 */
	private void fillEventActionMap() {
		eventActionMap.put(ConnectToGameEvent.class, new ConnectToGameAction());
		eventActionMap.put(CreateConnectionEvent.class, new CreateConnectionAction());
		eventActionMap.put(GameOverEvent.class, new GameOverAction());
		eventActionMap.put(MoveEndingEvent.class, new MoveEndingAction());
		eventActionMap.put(MovePerformingEvent.class, new MovePerformingAction());
		eventActionMap.put(MoveStartingEvent.class, new MoveStartingAction());
		eventActionMap.put(UndoMoveEvent.class, new UndoMoveAction());
		eventActionMap.put(NewGameEvent.class, new NewGameAction());
		eventActionMap.put(GameOverNetEvent.class, new GameOverNetAction());
		eventActionMap.put(MoveEndingNetEvent.class, new MoveEndingNetAction());
		eventActionMap.put(MovePerformingNetEvent.class, new MovePerformingNetAction());
		eventActionMap.put(MoveStartingNetEvent.class, new MoveStartingNetAction());
		eventActionMap.put(UndoMoveNetEvent.class, new UndoMoveNetAction());
		eventActionMap.put(NewGameNetEvent.class, new NewGameNetAction());
		eventActionMap.put(GiveUpEvent.class, new GiveUpAction());
		eventActionMap.put(ConnectionErrorEvent.class, new ConnectionErrorAction());
		eventActionMap.put(ConnectingSucceededEvent.class, new ConnectingSucceededAction());
	}

	/**
	 * Puts event to net thread blocking queue.
	 * @param event - event destined to send over the network
	 * @throws InterruptedException 
	 */
	public void putEventToNetThreadBlockingQueue(final Event event) throws InterruptedException {
		netThread.putEventToBlockingQueue(event);
	}
	
	/**
	 * Handles events.
	 */
	public void run() {
		while (true) {
			try {
				final Event event = view.takeEventFromBlockingQueue();
				final Action action = eventActionMap.get(event.getClass());
				action.performAction(event, model, view, this);
			}
			catch (InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Sets model of game.
	 * @param model - create new to start new game
	 */
	/*public void setModel(final Model model) {
		this.model = model;
	}*/

	
	public void setNetThread(final NetThread netThread) {
		this.netThread = netThread;
	}
}
