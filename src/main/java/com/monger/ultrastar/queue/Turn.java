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

	public long calculateScore() {
		return ((long)singer1.getScore()) + singer2.getScore();
	}

	@Override
	public int compareTo( Turn other ) {
		if ( other.calculateScore() == calculateScore() ) {
			return timestamp().compareTo( other.timestamp() );
		}

		if ( other.calculateScore() > calculateScore()) {
			return 1;
		}
		return -1;
	}

	@Override
	public String toString() {
		return new StringBuilder("['").append( song.title() ).append("', ")
			.append( calculateScore() ).append(", ").append( singer1.getName() )
			.append( "-").append( singer1.getScore()).append(",")
			.append( singer2.getName() ).append("-").append(singer2.getScore())
			.append(",").append( timestamp.toString()).append("]").toString();
	}
}
