package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.ServerSocket;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import logger.Logger;
import events.ConnectingSucceededEvent;
import events.ConnectionErrorEvent;
import events.Event;

/**
 * @author Jakub Rak
 */
class ServerThread extends NetThread {
	private final BlockingQueue<Event> blockingQueue;
	private final BlockingQueue<Event> viewBlockingQueue;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private ServerSocket serverSocket;
	private Socket clientSocket;
	private ListeningThread listeningThread;
	private final int port;
	private final int connectionTimeout = 5000;
	private volatile boolean threadState;

	public ServerThread(final BlockingQueue<Event> viewBlockingQueue) {
		this.viewBlockingQueue = viewBlockingQueue;
		blockingQueue = new LinkedBlockingQueue<Event>();
		port = 4444;
		threadState = true;
	}

	@Override
	public void putEventToBlockingQueue(final Event event) throws InterruptedException {
		blockingQueue.put(event);
	}

	@Override
	public void run() {
		try {
			Logger.print(this, "ServerThread started.");
			serverSocket = new ServerSocket(port);
			serverSocket.setSoTimeout(connectionTimeout);
			clientSocket = serverSocket.accept();

			if (clientSocket != null)
				Logger.print(this, "Connection created");
			output = new ObjectOutputStream(clientSocket.getOutputStream());
			input = new ObjectInputStream(clientSocket.getInputStream());
			listeningThread = new ListeningThread(this, input, viewBlockingQueue);
			listeningThread.start();
			Logger.print(this, "ListeningThread started.");
			try {
				viewBlockingQueue.put(new ConnectingSucceededEvent());
			}
			catch (final InterruptedException e) {
				e.printStackTrace();
			}
			while (threadState) {
				Event event = null;
				event = blockingQueue.take();
				if (event != null)
					output.writeObject(event);
			}
			Logger.print(this, "ServerThread stopped.");
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			try {
				Logger.print(this, "ServerThread stopped.");
				if (listeningThread != null)
					listeningThread.stopThread();
				if (input != null)
					input.close();
				if (output != null)
					output.close();
				if (serverSocket != null)
					serverSocket.close();
				viewBlockingQueue.put(new ConnectionErrorEvent());
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
			catch (final InterruptedException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stopThread() {
		try {
			Logger.print(this, "ServerThread stopped.");
			input.close();
			output.close();
			serverSocket.close();
			threadState = false;
		}
		catch (final IOException e) {
			e.printStackTrace();
		}

	}
}
