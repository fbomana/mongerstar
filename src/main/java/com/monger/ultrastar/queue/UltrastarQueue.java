package com.monger.ultrastar.queue;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;

import com.monger.ultrastar.singer.Singer;
import com.monger.ultrastar.song.Song;

@Service
public class UltrastarQueue {

	private List<Turn> turns;
	private int pointer;
	
	public UltrastarQueue( ) {
		this.turns = new ArrayList<>( 200 );
		this.pointer = 0;
	}
	
	public int size() {
		return  turns.size();
	}
	
	public Turn nextTurn() {
		turns.set(pointer, turns.get( pointer ).complete());
		do {
			pointer ++;
		} while( pointer < size() && turns.get(pointer).completed() );
		
		if ( pointer == turns.size() ) {
			throw new NoUnsingedSongsException();
		}
		return turns.get( pointer );
	}
	
	public Turn delayTurn() {
		Turn turn = turns.get( pointer );
		turns.remove( pointer );
		
		for ( int i = pointer + 1; i < turns.size(); i ++ ) {
			if ( canGoThere( turn, i )) {
				turns.set( i, turn );
				return turns.get( pointer );
			}
		}
		turns.add( turn );
		return turns.get(pointer);
	}
	
	public boolean canGoThere( Turn turn, int i ) {
		boolean canGoThere = true;
		if ( i < turns.size() -1  ) {
			canGoThere = !turns.get( i + 1 ).singersCoincide(turn);
		}
		if ( i > 0 ) {
			canGoThere = canGoThere && !turns.get( i - 1 ).singersCoincide(turn); 
		}
		return canGoThere;
	}
	
	public void add( Singer singer1, Singer singer2, Song song ) {
		Turn turn = new Turn( singer1, singer2, song, false );
		for ( int i = pointer+1; i < turns.size() -1; i ++ ) {
			if ( turns.get(i).singersCoincide( turns.get( i + 1 )) && 
				canGoThere( turn, i )) {
				turns.add( i, turn);
				return;
			}
		}
		turns.add( turn );
	}
	
	public void removeSinger( Singer singer ) {
		for ( int i = turns.size() -1; i >= pointer; i -- ) {
			Turn turno = turns.get( i );
			if ( turno.singer1().equals( singer ) || turno.singer2().equals( singer )) {
				turns.remove( i );
			}
		}

		if ( pointer == turns.size() ) {
			throw new NoUnsingedSongsException();
		}
	}
	
	public List<Turn> getTurns() {
		return turns;
	}
}
