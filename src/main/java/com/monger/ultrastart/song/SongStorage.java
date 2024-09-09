package com.monger.ultrastart.song;

import java.util.Collection;
import java.util.List;

public interface SongStorage {

    public List<Song> getAllSongs();
    public List<Song> findSongsByTitle( String title );
    public List<Song> findSongsByAuthor( String author );
    public void addSong( Song song );
    public void addAllSongs( Collection<Song> songs );
}
