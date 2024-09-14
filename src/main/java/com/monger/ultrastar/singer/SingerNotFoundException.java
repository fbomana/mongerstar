package com.monger.ultrastar.singer;

public class SingerNotFoundException extends RuntimeException {
    private static final long serialVersionUID = 1L;

	public SingerNotFoundException(String message) {
        super(message);
    }
}
