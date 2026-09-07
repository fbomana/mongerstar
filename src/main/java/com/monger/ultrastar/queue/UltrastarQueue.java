package com.monger.ultrastar.queue;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.monger.ultrastar.singer.Singer;
import com.monger.ultrastar.singer.SingerStorage;
import com.monger.ultrastar.song.Song;

@Service
public class UltrastarQueue {

	private final List<Turn> turnHistory;
	private List<Turn> queue;
	private Turn currentTurn;

	private final SingerStorage singerStorage;

	@Autowired
	public UltrastarQueue( SingerStorage singerStorage ) {
		this.turnHistory = new ArrayList<>();
		this.queue = new ArrayList<>();
		this.currentTurn = null;
		this.singerStorage = singerStorage;
	}
	
	public void add( Singer singer1, Singer singer2, Song song ) {
		addSong( new Turn( singer1, singer2, song, false, LocalDateTime.now() ));
	}

	public void addSong( Turn turn ) {
		checkNotAlreadyInQueue( turn );
		int position = Collections.binarySearch( queue, turn );
		if ( position < 0 ) {
			queue.add(-position - 1, turn);
		}
		else {
			while( position < queue.size() && queue.get(position).calculateScore() == turn.calculateScore()) {
				position++;
			}
			queue.add( position, turn );
		}
	}
	
	private void checkNotAlreadyInQueue( Turn turn ) {
		for ( Turn t : queue ) {
			if ( t.equals( turn )) {
				throw new RuntimeException("Turn already on queue");
			}
		}
	}

	public Turn nextTurn() {
		if (queue.isEmpty()) {
			throw new NoSongsInQueueException();
		}

		if ( currentTurn != null ) {
			turnHistory.add(currentTurn.complete());
		}

		currentTurn = queue.remove(0 );
		singerStorage.increaseSingersScore();
		currentTurn.singer1().resetScore();
		currentTurn.singer2().resetScore();
		Collections.sort( queue );
		return currentTurn;
	}

	public void delayTurn() {
		if ( currentTurn != null && !queue.isEmpty() ) {
			Turn delayed = currentTurn;
			delayed.singer1().setScore( queue.get(0).singer1().getScore());
			delayed.singer2().setScore( queue.get(0).singer2().getScore());
			currentTurn = null;
			nextTurn();
			queue.add(0, delayed);
		}
	}

	public void removeSinger( Singer singer ) {
		if ( queue.isEmpty()) {
			throw new NoSongsInQueueException();
		}
		queue = queue.stream().filter( t -> !t.singer1().equals( singer ) && !t.singer2().equals( singer )).toList();
	}

	public int size() {
		return queue.size();
	}
	
	public List<Turn> getTurns() {
		return queue;
	}
	
	public Turn getCurrentTurn() {
		return currentTurn;
	}

	public List<Turn> getTurnHistory() {
		return turnHistory;
	}
}
