package com.monger.ultrastar.queue;

import com.monger.ultrastar.singer.Singer;
import com.monger.ultrastar.song.Song;

import java.time.LocalDateTime;

public record Turn (Singer singer1, Singer singer2, Song song, boolean completed, LocalDateTime timestamp) implements Comparable<Turn> {

	public Turn complete() {
		return new Turn ( singer1, singer2, song, true, timestamp );
	}
	
	public boolean singersCoincide( Turn otherTurn ) {
		return otherTurn.singer1.getName().equalsIgnoreCase( singer1.getName() ) ||
			otherTurn.singer1.getName().equalsIgnoreCase( singer2.getName() ) ||
			otherTurn.singer2.getName().equalsIgnoreCase( singer1.getName() ) ||
			otherTurn.singer2.getName().equalsIgnoreCase( singer2.getName() );
	}

	public Long calculateScore() {
		return ((long)singer1.getScore()) + singer2.getScore();
	}

	@Override
	public int compareTo( Turn other ) {
		int c =  other.calculateScore().compareTo( calculateScore() );
		if ( c == 0 ) {
			c = timestamp().compareTo( other.timestamp() );
		}
		return c;
	}
}
