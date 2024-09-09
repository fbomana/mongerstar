package com.monger.ultrastart.singer;

public class SingerNotFoundException extends RuntimeException {
    public SingerNotFoundException(String message) {
        super(message);
    }
}
