package com.monger.ultrastar.song;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/song")
public class SongController {
	
	private final SongStorage storage;
	
	public SongController ( SongStorage storage ) {
		this.storage = storage;
	}

	@GetMapping("/title/{value}")
    public List<Song> findSongsByTitle( @PathVariable("value") String title ) {
        return storage.findSongsByTitle( title );
    }
	
	@GetMapping("/author/{value}")
    public List<Song> findSongsByAuthor( @PathVariable("value") String author ) {
        return storage.findSongsByAuthor( author );
    }
	
	@GetMapping("")
    public List<Song> findAllSongs() {
        return storage.getAllSongs();
    }
}
