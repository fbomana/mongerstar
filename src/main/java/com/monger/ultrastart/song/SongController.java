package com.monger.ultrastart.song;

import org.springframework.web.bind.annotation.RequestMapping;

import java.util.List;

@RequestMapping("songs")
public class SongController {

    public List<Song> findSongsForTitle() {
        return null;
    }
}
