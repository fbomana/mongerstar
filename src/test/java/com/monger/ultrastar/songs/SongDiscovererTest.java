package com.monger.ultrastar.songs;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;

import org.junit.jupiter.api.Test;

import com.monger.ultrastar.song.SongDiscoverer;
import com.monger.ultrastar.song.SongStorage;

public class SongDiscovererTest {

	@Test
	public void discoverStorageSearchEveryFolderForTxtFiles() {
		SongStorage storage = new TestSongStorage();
		SongDiscoverer discoverer = new SongDiscoverer( storage );
		final long i = System.currentTimeMillis();
		discoverer.discoverSongsOnFolder("/home/aitkiar/Documentos/trabajo/ultrastar/canciones/test");
		//discoverer.discoverSongsOnFolder("/home/aitkiar/Documentos/trabajo/ultrastar/canciones/español/Modestia Aparte - Es Por Tu Amor [VIDEO]/");
		final long t = System.currentTimeMillis() - i;
		System.out.println( String.format("Leidas %d canciones en %d ms", storage.getAllSongs().size(), t));
		assertFalse( storage.getAllSongs().isEmpty() );
		assertEquals( 7, storage.getAllSongs().size() );
	}
}
