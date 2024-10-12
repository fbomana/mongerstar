package com.monger.ultrastar.queue;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import org.springframework.stereotype.Service;

import com.monger.ultrastar.singer.Singer;
import com.monger.ultrastar.song.Song;

@Service
public class UltrastarQueue {

	private List<Turn> singed;
	private List<Turn> unsinged;

	public UltrastarQueue( ) {
		this.singed = new ArrayList<>();
		this.unsinged = new ArrayList<>();
	}

	public void add( Singer singer1, Singer singer2, Song song ) {
		addSong( new Turn( singer1, singer2, song, false ));
	}
	public void addSong( Turn turn ) {
		int position = Collections.binarySearch( unsinged, turn );
		unsinged.add( -position -1, turn );
	}

	public Turn nextTurn() {
		Turn turn = unsinged.remove( 0 );
		singed.add( turn );
		return turn;
	}

	public Turn delayTurn() {
		Turn delayed = unsinged.remove( 0 );
		Turn actual = unsinged.remove( 0 );
		singed.add( actual );
		addSong( delayed );
		return actual;
	}

	public void removeSinger( Singer singer ) {
		unsinged = unsinged.stream().filter( t -> !t.singer1().equals( singer ) && !t.singer2().equals( singer )).toList();
	}

	public int size() {
		return unsinged.size();
	}
	
	public List<Turn> getTurns() {
		return turns;
	}
}
