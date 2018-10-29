package controller;

import events.Event;

/**
 * @author Jakub Rak
 */
abstract class NetThread extends Thread {
	/**
	 * Puts event to net thread blocking queue.
	 * @param event - event destined to send over the network
	 * @throws InterruptedException 
	 */
	public abstract void putEventToBlockingQueue(Event event) throws InterruptedException;

	/**
	 * Stops network thread.
	 */
	public abstract void stopThread();
}
