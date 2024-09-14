package com.monger.ultrastar.songs;

import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

import org.junit.jupiter.api.Test;

import com.monger.ultrastar.song.Song;
import com.monger.ultrastar.song.SongDiscoverer;
import com.monger.ultrastar.song.SongStorage;

public class SongDiscovererTest {

	@Test
	public void discoverStorageSearchEveryFolderForTxtFiles() {
		SongStorage storage = new TestSongStorage();
		SongDiscoverer discoverer = new SongDiscoverer( storage );
		discoverer.discoverSongsOnFolder("/home/aitkiar/Documentos/trabajo/ultrastar/canciones/");
		assertFalse( storage.getAllSongs().isEmpty() );
		assertEquals( 7, storage.getAllSongs().size() );
	}
}
