package com.monger.ultrastar.queue;

import com.monger.ultrastar.singer.Singer;
import com.monger.ultrastar.song.Song;

public record Turn ( Singer singer1, Singer singer2, Song song, boolean completed ) implements Comparable<Turn> {

	public Turn complete() {
		return new Turn ( singer1, singer2, song, true );
	}
	
	public boolean singersCoincide( Turn otherTurn ) {
		return otherTurn.singer1.name().equalsIgnoreCase( singer1.name() ) ||
			otherTurn.singer1.name().equalsIgnoreCase( singer2.name() ) ||
			otherTurn.singer2.name().equalsIgnoreCase( singer1.name() ) ||
			otherTurn.singer2.name().equalsIgnoreCase( singer2.name() );
	}

	public Long calculateScore() {
		return ((long)singer1.score()) + singer2.score();
	}

	@Override
	public int compareTo( Turn other ) {
		return other.calculateScore().compareTo( calculateScore() );
	}
}
