package com.monger.ultrastar.queue;

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

	private List<Turn> previousTurns;
	private List<Turn> queue;
	private final SingerStorage singerStorage;

	@Autowired
	public UltrastarQueue( SingerStorage singerStorage ) {
		this.previousTurns = new ArrayList<>();
		this.queue = new ArrayList<>();
		this.singerStorage = singerStorage;
	}
	
	public void add( Singer singer1, Singer singer2, Song song ) {
		addSong( new Turn( singer1, singer2, song, false ));
	}

	public void addSong( Turn turn ) {
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

	public Turn nextTurn() {
		if (queue.isEmpty()) {
			throw new NoSongsInQueueException();
		}
		Turn turn = queue.remove(0);
		previousTurns.add(turn.complete());
		turn.singer1().resetScore();
		turn.singer2().resetScore();
		singerStorage.increaseSingersScore();
		Collections.sort( queue );
		return turn;
	}

	public void delayTurn() {
		Turn delayed = queue.remove( 0 );
		queue.add( 1, delayed );
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
		if ( !previousTurns.isEmpty() ) {
			return previousTurns.get( previousTurns.size() -1 );
		}
		if ( !queue.isEmpty()) {
			return queue.get( 0 );
		}
		return null;
	}
}
