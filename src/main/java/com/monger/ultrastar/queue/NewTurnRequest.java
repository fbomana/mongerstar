package com.monger.ultrastar.queue;

import com.monger.ultrastar.singer.Singer;
import com.monger.ultrastar.song.Song;

public record NewTurnRequest (String singer1, String singer2, Song song ){

}
