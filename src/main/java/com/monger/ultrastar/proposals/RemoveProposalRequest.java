package com.monger.ultrastar.proposals;

import com.monger.ultrastar.singer.Singer;
import com.monger.ultrastar.song.Song;

public record RemoveProposalRequest ( Song song, Singer singer ){
}
