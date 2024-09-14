package com.monger.ultrastar.queue;

public class NoUnsingedSongsException extends RuntimeException {
	public NoUnsingedSongsException() {
		super("No more unsiged songs in queue");
	}

}
