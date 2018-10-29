package controller;

import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;
import java.net.Socket;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

import logger.Logger;
import events.ConnectingSucceededEvent;
import events.ConnectionErrorEvent;
import events.Event;

public class ClientThread extends NetThread {
	private final BlockingQueue<Event> blockingQueue;
	private final BlockingQueue<Event> viewBlockingQueue;
	private ObjectOutputStream output;
	private ObjectInputStream input;
	private Socket clientSocket;
	private ListeningThread listeningThread;
	private final String ip;
	private final int port;
	private volatile boolean threadState;

	public ClientThread(final BlockingQueue<Event> viewBlockingQueue, final String ip) {
		this.viewBlockingQueue = viewBlockingQueue;
		this.ip = ip;
		port = 4444;
		blockingQueue = new LinkedBlockingQueue<Event>();
		threadState = true;
	}

	@Override
	public void putEventToBlockingQueue(final Event event) throws InterruptedException {
		blockingQueue.put(event);
	}

	@Override
	public void run() {
		try {
			Logger.print(this, "ClientThread started.");
			clientSocket = new Socket(ip, port);
			if (clientSocket != null)
				Logger.print(this, "Client connected to game");
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
			Logger.print(this, "ClientThread stopped.");
		}
		catch (final IOException e) {
			e.printStackTrace();
		}
		catch (InterruptedException e) {
			e.printStackTrace();
		}
		finally {
			try {
				Logger.print(this, "ClientThread stopped.");
				if (listeningThread != null)
					listeningThread.stopThread();
				if (input != null)
					input.close();
				if (output != null)
					output.close();
				if (clientSocket != null)
					clientSocket.close();
				viewBlockingQueue.put(new ConnectionErrorEvent());
			}
			catch (final InterruptedException e) {
				e.printStackTrace();
			}
			catch (final IOException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void stopThread() {
		try {
			Logger.print(this, "ClientThread stopped.");
			input.close();
			output.close();
			clientSocket.close();
			threadState = false;
		}
		catch (final IOException e) {
			e.printStackTrace();
		}

	}
}
