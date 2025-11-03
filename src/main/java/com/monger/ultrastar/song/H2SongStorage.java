package com.monger.ultrastar.song;

import org.springframework.jdbc.core.simple.JdbcClient;
import org.springframework.stereotype.Repository;

import java.util.Collection;
import java.util.List;

@Repository
public class H2SongStorage implements SongStorage {

    final JdbcClient jdbcClient;

    public H2SongStorage( JdbcClient jdbcClient ) {
        this.jdbcClient = jdbcClient;
    }

    @Override
    public List<Song> getAllSongs() {
        return jdbcClient.sql("select * from songs order by author, title").query( Song.class ).list();
    }

    @Override
    public List<Song> findSongsByTitle( String title ) {
        return jdbcClient.sql("select * from songs where lower(title) like ? order by author, title").param("%" + title.toLowerCase() + "%").query( Song.class ).list();
    }

    @Override
    public List<Song> findSongsByAuthor( String author ) {
        return jdbcClient.sql("select * from songs where lower(author) like ? order by author, title").param("%" + author.toLowerCase() + "%").query( Song.class ).list();
    }
    
    @Override
    public List<Song> findSongsByLanguage( String language ) {
        return jdbcClient.sql("select * from songs where lower(language) like ? order by author, title").param("%" + language.toLowerCase() + "%").query( Song.class ).list();
    }

    @Override
    public void addSong(Song song) {
        jdbcClient.sql("insert into songs( title, author, language ) values ( ?, ?, ? )").param( song.title()).param( song.author()).param( song.language()).update();
    }

    @Override
    public void addAllSongs(Collection<Song> songs) {
        for ( Song song : songs ) {
            jdbcClient.sql("insert into songs( title, author, language ) values ( ?, ?, ? )").param( song.title()).param( song.author()).param( song.language()).update();
        }
    }

	@Override
	public List<String> findAllAuthors() {
		return jdbcClient.sql("select distinct lower(author) from songs order by 1").query( String.class ).list();
	}

	@Override
	public List<String> findAllLanguages() {
		return jdbcClient.sql("select distinct lower(language) from songs order by 1").query( String.class ).list();
	}
}
