package com.monger.ultrastar.queue;

public class NoSongsInQueueException extends RuntimeException {
	
	private static final long serialVersionUID = 1L;

	public NoSongsInQueueException() {
		super("No more unsiged songs in queue");
	}

}
