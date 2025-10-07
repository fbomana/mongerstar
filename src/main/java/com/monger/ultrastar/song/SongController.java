package com.monger.ultrastar.song;

import java.util.List;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import jakarta.websocket.server.PathParam;

@RestController
@RequestMapping("/songs")
public class SongController {
	
	private final SongStorage storage;
	
	public SongController ( SongStorage storage ) {
		this.storage = storage;
	}

	@GetMapping("/title/{value}")
    public List<Song> findSongsByTitle( @PathParam("value") String title) {
        return storage.findSongsByTitle( title );
    }
	
	@GetMapping("/author/{value}")
    public List<Song> findSongsByAuthor( @PathParam("value") String title) {
        return storage.findSongsByAuthor( title );
    }
	
	@GetMapping("/")
    public List<Song> findAllSongs() {
        return storage.getAllSongs();
    }
}
