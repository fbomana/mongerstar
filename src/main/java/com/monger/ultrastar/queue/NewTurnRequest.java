package com.monger.ultrastar.queue;

import com.monger.ultrastar.singer.Singer;
import com.monger.ultrastar.song.Song;

public record NewTurnRequest (Singer singer1, Singer singer2, Song song ){

}
