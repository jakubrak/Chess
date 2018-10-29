package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.util.concurrent.BlockingQueue;

import logger.Logger;
import events.ConnectionErrorEvent;
import events.Event;

/**
 * @author Jakub Rak
 */
class ListeningThread extends Thread {
	private final ObjectInputStream input;
	private final BlockingQueue<Event> viewBlockingQueue;
	private final NetThread netThread;
	private volatile boolean threadState;

	public ListeningThread(final NetThread netThread, final ObjectInputStream input,
			final BlockingQueue<Event> viewBlockingQueue) throws IOException {
		this.viewBlockingQueue = viewBlockingQueue;
		this.input = input;
		this.netThread = netThread;
		threadState = true;
	}

	@Override
	public void run() {
		try {
			while (threadState) {
				Event event = null;
				event = (Event) input.readObject();
				if (event != null)
					viewBlockingQueue.put(event);
			}
			Logger.print(this, "ServerListeningThread stopped.");
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		catch (final ClassNotFoundException e) {
			e.printStackTrace();
		}
		catch (final InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			try {
				Logger.print(this, "ListeningThread stopped.");
				netThread.stopThread();
				viewBlockingQueue.put(new ConnectionErrorEvent());
			}
			catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}
	
	/**
	 * Stops listening thread.
	 */
	void stopThread() {
		Logger.print(this, "ListeningThread stopped.");
		threadState = false;
	}
}
