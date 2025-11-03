package com.monger.ultrastar.songs;

import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.monger.ultrastar.song.Song;
import com.monger.ultrastar.song.SongStorage;

public class TestSongStorage implements SongStorage {
	
	Logger logger = LoggerFactory.getLogger( this.getClass());
	
	List<Song> songs;
	
	public TestSongStorage() {
		songs = new ArrayList<>();
	}

	@Override
	public List<Song> getAllSongs() {
		return songs;
	}

	@Override
	public List<Song> findSongsByTitle(String title) {
		return songs.stream().filter( song -> song.title().toLowerCase().contains( title.trim().toLowerCase())).toList();
	}

	@Override
	public List<Song> findSongsByAuthor(String author) {
		return songs.stream().filter( song -> song.author().toLowerCase().contains( author.trim().toLowerCase())).toList();
	}
	
	@Override
	public List<Song> findSongsByLanguage(String language) {
		return songs.stream().filter( song -> song.language().toLowerCase().contains( language.trim().toLowerCase())).toList();
	}
	
	@Override
	public List<String> findAllAuthors() {
		return songs.stream().map( Song::author ).toList();
	}

	@Override
	public void addSong(Song song) {
		logger.debug("Added song: {}", song );
		List<Song> songsByTitle = findSongsByTitle( song.title() );
		for ( Song songByTitle : songsByTitle ) {
			if ( songByTitle.equals( song )) {
				return;
			}
		}
		songs.add( song );		
	}

	@Override
	public void addAllSongs(Collection<Song> songs) {
		for( Song song : songs ) {
			addSong( song );
		}
	}

	@Override
	public List<String> findAllLanguages() {
		return songs.stream().map( Song::language ).toList();
	}

}
