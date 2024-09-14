package com.monger.ultrastar.song;

public record Song( String title, String author, String language ) {
	public static final Song emptySong = new Song ( null, null, null  );
}
